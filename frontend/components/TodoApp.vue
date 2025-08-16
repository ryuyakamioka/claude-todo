<template>
  <div class="max-w-2xl mx-auto">
    <TodoForm @add-todo="handleAddTodo" :loading="loading" />
    
    <div v-if="error" class="mt-4 p-4 bg-red-100 border border-red-400 text-red-700 rounded">
      {{ error }}
    </div>

    <div v-if="loading && todos.length === 0" class="mt-8 text-center text-gray-500">
      Loading todos...
    </div>

    <TodoList 
      v-else
      :todos="todos" 
      @toggle-todo="handleToggleTodo"
      @delete-todo="handleDeleteTodo"
      @update-todo="handleUpdateTodo"
      class="mt-8"
    />
  </div>
</template>

<script setup lang="ts">
const { todos, loading, error, fetchTodos, addTodo, toggleTodo, deleteTodo, updateTodo } = useTodos()

onMounted(() => {
  fetchTodos()
})

const handleAddTodo = async (title: string) => {
  await addTodo(title)
}

const handleToggleTodo = async (id: number) => {
  await toggleTodo(id)
}

const handleDeleteTodo = async (id: number) => {
  await deleteTodo(id)
}

const handleUpdateTodo = async (id: number, title: string) => {
  await updateTodo(id, { title })
}
</script>