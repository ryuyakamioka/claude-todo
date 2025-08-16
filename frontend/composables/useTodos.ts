import type { Todo } from '~/types/todo'

export const useTodos = () => {
  const config = useRuntimeConfig()
  const apiBase = config.public.apiBase

  const todos = ref<Todo[]>([])
  const loading = ref(false)
  const error = ref<string | null>(null)

  const fetchTodos = async () => {
    try {
      loading.value = true
      error.value = null
      const { data } = await $fetch<Todo[]>(`${apiBase}/todos`)
      todos.value = data || []
    } catch (err) {
      error.value = 'Failed to fetch todos'
      console.error('Error fetching todos:', err)
    } finally {
      loading.value = false
    }
  }

  const addTodo = async (title: string) => {
    try {
      loading.value = true
      error.value = null
      const newTodo = await $fetch<Todo>(`${apiBase}/todos`, {
        method: 'POST',
        body: { title, completed: false }
      })
      todos.value.push(newTodo)
    } catch (err) {
      error.value = 'Failed to add todo'
      console.error('Error adding todo:', err)
    } finally {
      loading.value = false
    }
  }

  const updateTodo = async (id: number, updates: Partial<Todo>) => {
    try {
      loading.value = true
      error.value = null
      const updatedTodo = await $fetch<Todo>(`${apiBase}/todos/${id}`, {
        method: 'PUT',
        body: updates
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

  const deleteTodo = async (id: number) => {
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

  const toggleTodo = async (id: number) => {
    const todo = todos.value.find(t => t.id === id)
    if (todo) {
      await updateTodo(id, { completed: !todo.completed })
    }
  }

  return {
    todos: readonly(todos),
    loading: readonly(loading),
    error: readonly(error),
    fetchTodos,
    addTodo,
    updateTodo,
    deleteTodo,
    toggleTodo
  }
}