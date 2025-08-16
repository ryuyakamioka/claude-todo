<template>
  <div class="flex items-center gap-3 p-4 bg-white rounded-lg shadow-sm border border-gray-200 hover:shadow-md transition-shadow">
    <input
      type="checkbox"
      :checked="todo.completed"
      @change="$emit('toggle')"
      class="w-5 h-5 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 focus:ring-2"
    />
    
    <div v-if="!isEditing" class="flex-1 flex items-center justify-between">
      <span
        :class="{
          'line-through text-gray-500': todo.completed,
          'text-gray-800': !todo.completed
        }"
        class="text-lg"
      >
        {{ todo.title }}
      </span>
      
      <div class="flex gap-2 ml-4">
        <button
          @click="startEditing"
          class="p-1 text-gray-400 hover:text-blue-600 transition-colors"
          title="Edit"
        >
          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
          </svg>
        </button>
        <button
          @click="$emit('delete')"
          class="p-1 text-gray-400 hover:text-red-600 transition-colors"
          title="Delete"
        >
          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path>
          </svg>
        </button>
      </div>
    </div>

    <div v-else class="flex-1 flex gap-2">
      <input
        v-model="editTitle"
        type="text"
        class="flex-1 px-3 py-1 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
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
  </div>
</template>

<script setup lang="ts">
import type { Todo } from '~/types/todo'

interface Props {
  todo: Todo
}

interface Emits {
  (e: 'toggle'): void
  (e: 'delete'): void
  (e: 'update', title: string): void
}

defineProps<Props>()
const emit = defineEmits<Emits>()

const isEditing = ref(false)
const editTitle = ref('')
const editInput = ref<HTMLInputElement>()

const startEditing = () => {
  editTitle.value = props.todo.title
  isEditing.value = true
  nextTick(() => {
    editInput.value?.focus()
  })
}

const saveEdit = () => {
  if (editTitle.value.trim() && editTitle.value !== props.todo.title) {
    emit('update', editTitle.value.trim())
  }
  isEditing.value = false
}

const cancelEdit = () => {
  isEditing.value = false
  editTitle.value = ''
}

const props = defineProps<Props>()
</script>