<template>
  <form @submit.prevent="handleSubmit" class="mb-6">
    <div class="flex flex-col sm:flex-row gap-4">
      <input
        v-model="newTodo"
        type="text"
        placeholder="Add a new todo..."
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
  </form>
</template>

<script setup lang="ts">
interface Props {
  loading: boolean
}

interface Emits {
  (e: 'add-todo', title: string): void
}

defineProps<Props>()
const emit = defineEmits<Emits>()

const newTodo = ref('')

const handleSubmit = () => {
  if (newTodo.value.trim()) {
    emit('add-todo', newTodo.value.trim())
    newTodo.value = ''
  }
}
</script>