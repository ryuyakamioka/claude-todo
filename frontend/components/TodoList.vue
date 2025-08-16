<template>
  <div class="mt-8">
    <!-- Error message -->
    <div v-if="error" class="mb-4 p-4 bg-red-100 border border-red-400 text-red-700 rounded">
      {{ error }}
    </div>

    <!-- Loading state -->
    <div v-if="loading && todos.length === 0" class="text-center text-gray-500">
      Loading todos...
    </div>

    <!-- Empty state -->
    <div v-else-if="todos.length === 0" class="text-center text-gray-500 py-8">
      No todos yet. Add one above!
    </div>
    
    <!-- Todo Items -->
    <div v-else class="space-y-2" ref="todoListRef">
      <div
        v-for="todo in todos"
        :key="todo.id"
        class="flex items-center gap-3 p-4 bg-white rounded-lg shadow-sm border border-gray-200 hover:shadow-md transition-shadow"
        :data-id="todo.id"
      >
        <!-- Drag handle -->
        <div class="drag-handle cursor-move text-gray-400 hover:text-gray-600 flex flex-col text-xs leading-none">
          <span>⋮</span>
          <span>⋮</span>
        </div>
        
        <input
          type="checkbox"
          :checked="todo.completed"
          @change="$emit('toggle', todo.id)"
          class="w-5 h-5 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 focus:ring-2"
        />
        
        <div v-if="!editingId || editingId !== todo.id" class="flex-1 flex flex-col">
          <div class="flex items-center justify-between">
            <span
              :class="{
                'line-through text-gray-500': todo.completed,
                'text-gray-800': !todo.completed
              }"
              class="text-lg font-medium"
            >
              {{ todo.title }}
            </span>
            <div class="flex gap-2 ml-4">
              <button
                @click="handleStartEditing(todo)"
                class="p-1 text-gray-400 hover:text-blue-600 transition-colors"
                title="Edit"
              >
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
                </svg>
              </button>
              <button
                @click="$emit('delete', todo.id)"
                class="p-1 text-gray-400 hover:text-red-600 transition-colors"
                title="Delete"
              >
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path>
                </svg>
              </button>
            </div>
          </div>
          <div v-if="todo.description" class="mt-2">
            <p
              :class="{
                'line-through text-gray-400': todo.completed,
                'text-gray-600': !todo.completed
              }"
              class="text-sm whitespace-pre-wrap"
            >
              {{ todo.description }}
            </p>
          </div>
          <div v-if="todo.dueDate" class="mt-2">
            <p
              :class="{
                'line-through text-gray-400': todo.completed,
                'text-red-500': !todo.completed && isOverdue(todo.dueDate),
                'text-blue-500': !todo.completed && !isOverdue(todo.dueDate)
              }"
              class="text-xs flex items-center gap-1"
            >
              📅 Due: {{ formatDateTime(todo.dueDate) }}
              <span v-if="!todo.completed && isOverdue(todo.dueDate)" class="text-red-500 font-medium">(Overdue)</span>
            </p>
          </div>
        </div>

        <div v-else class="flex-1 space-y-3">
          <div class="flex gap-2">
            <input
              v-model="editTitle"
              type="text"
              class="flex-1 px-3 py-1 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Title"
              @keyup.enter="handleSaveEdit"
              @keyup.escape="handleCancelEdit"
              ref="editInput"
            />
            <button
              @click="handleSaveEdit"
              class="px-3 py-1 bg-green-500 text-white rounded hover:bg-green-600 text-sm"
            >
              Save
            </button>
            <button
              @click="handleCancelEdit"
              class="px-3 py-1 bg-gray-500 text-white rounded hover:bg-gray-600 text-sm"
            >
              Cancel
            </button>
          </div>
          <textarea
            v-model="editDescription"
            class="w-full px-3 py-1 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500 resize-none"
            placeholder="Description (optional)"
            rows="3"
            @keyup.escape="handleCancelEdit"
          ></textarea>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Due Date</label>
            <input
              v-model="editDueDate"
              type="date"
              class="w-full px-3 py-1 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import Sortable from 'sortablejs'

// Props
const props = defineProps({
  todos: { type: Array, default: () => [] },
  loading: { type: Boolean, default: false },
  error: { type: String, default: null }
})

// Emits
const emit = defineEmits(['toggle', 'delete', 'update', 'reorder'])

// Edit state
const editingId = ref(null)
const editTitle = ref('')
const editDescription = ref('')
const editDueDate = ref('')

// Template ref
const todoListRef = ref(null)
let sortableInstance = null

// Utility functions
const formatDateTime = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleDateString()
}

const isOverdue = (dateString) => {
  if (!dateString) return false
  const dueDate = new Date(dateString)
  const today = new Date()
  today.setHours(0, 0, 0, 0)
  return dueDate < today
}

// Edit handlers
const handleStartEditing = (todo) => {
  editTitle.value = todo.title
  editDescription.value = todo.description || ''
  editDueDate.value = todo.dueDate ? new Date(todo.dueDate).toISOString().slice(0, 10) : ''
  editingId.value = todo.id
  nextTick(() => {
    const editInput = document.querySelector('input[ref="editInput"]')
    if (editInput) editInput.focus()
  })
}

const handleSaveEdit = () => {
  if (editTitle.value.trim() && editingId.value) {
    const updates = {
      title: editTitle.value.trim(),
      description: editDescription.value.trim() || null
    }
    if (editDueDate.value) {
      updates.dueDate = new Date(editDueDate.value + 'T23:59:59').toISOString()
    }
    emit('update', editingId.value, updates)
  }
  handleCancelEdit()
}

const handleCancelEdit = () => {
  editingId.value = null
  editTitle.value = ''
  editDescription.value = ''
  editDueDate.value = ''
}

// Setup drag and drop
const initializeSortable = () => {
  if (todoListRef.value && props.todos.length > 0 && !sortableInstance) {
    sortableInstance = new Sortable(todoListRef.value, {
      animation: 150,
      ghostClass: 'sortable-ghost',
      chosenClass: 'sortable-chosen',
      dragClass: 'sortable-drag',
      handle: '.drag-handle',
      onEnd: async (evt) => {
        const { oldIndex, newIndex } = evt
        
        if (oldIndex !== newIndex) {
          const newTodos = [...props.todos]
          const movedItem = newTodos.splice(oldIndex, 1)[0]
          newTodos.splice(newIndex, 0, movedItem)
          
          emit('reorder', newTodos)
        }
      }
    })
  }
}

// Watch for todos changes to initialize sortable when data is available
watch(() => props.todos, () => {
  nextTick(() => {
    if (props.todos.length > 0 && !sortableInstance) {
      initializeSortable()
    }
  })
}, { immediate: true })

onMounted(async () => {
  await nextTick()
  initializeSortable()
})

onUnmounted(() => {
  if (sortableInstance) {
    sortableInstance.destroy()
  }
})
</script>

<style scoped>
.sortable-ghost {
  opacity: 0.5;
  background: #f3f4f6;
  border: 2px dashed #d1d5db;
}

.sortable-chosen {
  cursor: grabbing;
}

.sortable-drag {
  opacity: 0.8;
  transform: rotate(2deg);
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
}

.cursor-move:hover {
  cursor: grab;
}

.cursor-move:active {
  cursor: grabbing;
}
</style>