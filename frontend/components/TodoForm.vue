<template>
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
      <div class="flex flex-col sm:flex-row gap-4">
        <label class="flex-1">
          <span class="block text-sm font-medium text-gray-700 mb-1">Due Date</span>
          <input
            v-model="newTodoDueDate"
            type="date"
            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
            :disabled="loading"
          />
        </label>
        <button
          type="button"
          @click="setDefaultDueDate"
          class="px-4 py-2 text-sm bg-gray-100 text-gray-700 rounded-lg hover:bg-gray-200 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2 disabled:opacity-50 h-fit mt-6"
          :disabled="loading"
        >
          1 Week Later
        </button>
      </div>
    </div>
  </form>
</template>

<script setup>
// Props
const props = defineProps({
  loading: { type: Boolean, default: false }
})

// Emit events
const emit = defineEmits(['submit'])

// Form state
const newTodo = ref('')
const newTodoDescription = ref('')
const newTodoDueDate = ref('')

// Form handlers
const handleSubmit = () => {
  if (newTodo.value.trim()) {
    const dueDate = newTodoDueDate.value ? new Date(newTodoDueDate.value + 'T23:59:59').toISOString() : null
    emit('submit', {
      title: newTodo.value.trim(),
      description: newTodoDescription.value.trim() || null,
      dueDate
    })
    // Clear form
    newTodo.value = ''
    newTodoDescription.value = ''
    newTodoDueDate.value = ''
  }
}

const setDefaultDueDate = () => {
  const oneWeekLater = new Date()
  oneWeekLater.setDate(oneWeekLater.getDate() + 7)
  newTodoDueDate.value = oneWeekLater.toISOString().slice(0, 10)
}

// Initialize default due date on mount
onMounted(() => {
  setDefaultDueDate()
})
</script>