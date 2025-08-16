<template>
  <div class="min-h-screen bg-gray-50">
    <NuxtRouteAnnouncer />
    <div class="container mx-auto px-4 py-8">
      <div class="text-center mb-8">
        <div class="flex items-center justify-center gap-3 mb-2">
          <!-- Cat Logo SVG -->
          <div class="w-10 h-10 text-gray-700">
            <svg viewBox="0 0 100 100" fill="currentColor" class="w-full h-full">
              <!-- Cat head -->
              <ellipse cx="50" cy="55" rx="25" ry="20"/>
              <!-- Cat ears -->
              <polygon points="30,35 20,20 40,30"/>
              <polygon points="70,35 80,20 60,30"/>
              <!-- Cat ears inner -->
              <polygon points="32,33 26,24 36,29" fill="rgba(255,255,255,0.3)"/>
              <polygon points="68,33 74,24 64,29" fill="rgba(255,255,255,0.3)"/>
              <!-- Cat eyes -->
              <ellipse cx="42" cy="50" rx="3" ry="4" fill="white"/>
              <ellipse cx="58" cy="50" rx="3" ry="4" fill="white"/>
              <ellipse cx="42" cy="51" rx="1.5" ry="2" fill="black"/>
              <ellipse cx="58" cy="51" rx="1.5" ry="2" fill="black"/>
              <!-- Cat nose -->
              <polygon points="50,58 48,62 52,62" fill="rgba(255,255,255,0.8)"/>
              <!-- Cat mouth -->
              <path d="M50 62 Q45 65 40 62" stroke="rgba(255,255,255,0.6)" stroke-width="1.5" fill="none"/>
              <path d="M50 62 Q55 65 60 62" stroke="rgba(255,255,255,0.6)" stroke-width="1.5" fill="none"/>
              <!-- Cat whiskers -->
              <line x1="25" y1="52" x2="35" y2="50" stroke="rgba(255,255,255,0.7)" stroke-width="1"/>
              <line x1="25" y1="58" x2="35" y2="58" stroke="rgba(255,255,255,0.7)" stroke-width="1"/>
              <line x1="75" y1="52" x2="65" y2="50" stroke="rgba(255,255,255,0.7)" stroke-width="1"/>
              <line x1="75" y1="58" x2="65" y2="58" stroke="rgba(255,255,255,0.7)" stroke-width="1"/>
            </svg>
          </div>
          <h1 class="text-3xl font-bold text-gray-800">Cat Todo</h1>
        </div>
        <p class="text-sm text-gray-500">Purr-fectly organized tasks</p>
      </div>
      
      <div class="max-w-2xl mx-auto">
        <!-- Todo Form -->
        <form @submit.prevent="handleSubmit" class="mb-6">
          <div class="space-y-4">
            <div class="flex flex-col sm:flex-row gap-4">
              <input
                v-model="newTodo"
                type="text"
                placeholder="Todo title..."
                class="flex-1 px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                :disabled="loading"
                required
              />
              <button
                type="submit"
                :disabled="loading || !newTodo.trim()"
                class="px-6 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                {{ loading ? 'Adding...' : 'Add Todo' }}
              </button>
            </div>
            <textarea
              v-model="newTodoDescription"
              placeholder="Description (optional)..."
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent resize-none"
              :disabled="loading"
              rows="3"
            ></textarea>
          </div>
        </form>
        
        <!-- Error message -->
        <div v-if="error" class="mt-4 p-4 bg-red-100 border border-red-400 text-red-700 rounded">
          {{ error }}
        </div>

        <!-- Loading state -->
        <div v-if="loading && todos.length === 0" class="mt-8 text-center text-gray-500">
          Loading todos...
        </div>

        <!-- Todo List -->
        <div v-else class="mt-8">
          <div v-if="todos.length === 0" class="text-center text-gray-500 py-8">
            No todos yet. Add one above!
          </div>
          
          <div v-else class="space-y-2">
            <div
              v-for="todo in todos"
              :key="todo.id"
              class="flex items-center gap-3 p-4 bg-white rounded-lg shadow-sm border border-gray-200 hover:shadow-md transition-shadow"
            >
              <input
                type="checkbox"
                :checked="todo.completed"
                @change="toggleTodo(todo.id)"
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
                      @click="startEditing(todo)"
                      class="p-1 text-gray-400 hover:text-blue-600 transition-colors"
                      title="Edit"
                    >
                      <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
                      </svg>
                    </button>
                    <button
                      @click="deleteTodo(todo.id)"
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
              </div>

              <div v-else class="flex-1 space-y-3">
                <div class="flex gap-2">
                  <input
                    v-model="editTitle"
                    type="text"
                    class="flex-1 px-3 py-1 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                    placeholder="Title"
                    @keyup.enter="saveEdit"
                    @keyup.escape="cancelEdit"
                    ref="editInput"
                  />
                  <button
                    @click="saveEdit"
                    class="px-3 py-1 bg-green-500 text-white rounded hover:bg-green-600 text-sm"
                  >
                    Save
                  </button>
                  <button
                    @click="cancelEdit"
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
                  @keyup.escape="cancelEdit"
                ></textarea>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const config = useRuntimeConfig()
const apiBase = config.public.apiBase

const todos = ref([])
const loading = ref(false)
const error = ref(null)
const newTodo = ref('')
const newTodoDescription = ref('')
const editingId = ref(null)
const editTitle = ref('')
const editDescription = ref('')

const fetchTodos = async () => {
  try {
    loading.value = true
    error.value = null
    const data = await $fetch(`${apiBase}/todos`)
    todos.value = data || []
  } catch (err) {
    error.value = 'Failed to fetch todos'
    console.error('Error fetching todos:', err)
  } finally {
    loading.value = false
  }
}

const addTodo = async (title, description) => {
  try {
    loading.value = true
    error.value = null
    const newTodoItem = await $fetch(`${apiBase}/todos`, {
      method: 'POST',
      body: { title, description, completed: false }
    })
    todos.value.push(newTodoItem)
  } catch (err) {
    error.value = 'Failed to add todo'
    console.error('Error adding todo:', err)
  } finally {
    loading.value = false
  }
}

const updateTodo = async (id, updates) => {
  try {
    loading.value = true
    error.value = null
    const currentTodo = todos.value.find(todo => todo.id === id)
    if (!currentTodo) {
      throw new Error('Todo not found')
    }
    
    const updatedTodo = await $fetch(`${apiBase}/todos/${id}`, {
      method: 'PUT',
      body: { ...currentTodo, ...updates }
    })
    const index = todos.value.findIndex(todo => todo.id === id)
    if (index !== -1) {
      todos.value[index] = updatedTodo
    }
  } catch (err) {
    error.value = 'Failed to update todo'
    console.error('Error updating todo:', err)
  } finally {
    loading.value = false
  }
}

const deleteTodo = async (id) => {
  try {
    loading.value = true
    error.value = null
    await $fetch(`${apiBase}/todos/${id}`, {
      method: 'DELETE'
    })
    todos.value = todos.value.filter(todo => todo.id !== id)
  } catch (err) {
    error.value = 'Failed to delete todo'
    console.error('Error deleting todo:', err)
  } finally {
    loading.value = false
  }
}

const toggleTodo = async (id) => {
  const todo = todos.value.find(t => t.id === id)
  if (todo) {
    await updateTodo(id, { completed: !todo.completed })
  }
}

const handleSubmit = () => {
  if (newTodo.value.trim()) {
    addTodo(newTodo.value.trim(), newTodoDescription.value.trim() || null)
    newTodo.value = ''
    newTodoDescription.value = ''
  }
}

const startEditing = (todo) => {
  editTitle.value = todo.title
  editDescription.value = todo.description || ''
  editingId.value = todo.id
  nextTick(() => {
    const editInput = document.querySelector('input[ref="editInput"]')
    if (editInput) editInput.focus()
  })
}

const saveEdit = () => {
  if (editTitle.value.trim() && editingId.value) {
    updateTodo(editingId.value, { 
      title: editTitle.value.trim(),
      description: editDescription.value.trim() || null
    })
  }
  editingId.value = null
  editTitle.value = ''
  editDescription.value = ''
}

const cancelEdit = () => {
  editingId.value = null
  editTitle.value = ''
  editDescription.value = ''
}

onMounted(() => {
  fetchTodos()
})
</script>
