<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Hamburger Button -->
    <HamburgerButton 
      :is-menu-open="isMenuOpen" 
      @toggle="toggleMenu" 
    />
    
    <!-- Side Menu -->
    <SideMenu 
      :is-open="isMenuOpen" 
      @close="closeMenu" 
    />
    
    <div class="container mx-auto px-4 py-8 transition-all duration-300" :class="{ 'md:ml-64': isMenuOpen }">
      <!-- Header -->
      <HeaderSection />
      
      <!-- Todo Content -->
      <div class="max-w-2xl mx-auto">
        <!-- Todo Form -->
        <TodoForm 
          :loading="loading" 
          @submit="handleFormSubmit" 
        />
        
        <!-- Todo List -->
        <TodoList 
          :todos="todos"
          :loading="loading"
          :error="error"
          @toggle="toggleTodo"
          @delete="deleteTodo"
          @update="updateTodo"
          @reorder="reorderTodos"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
const config = useRuntimeConfig()
const apiBase = config.public.apiBase

// State
const todos = ref([])
const loading = ref(false)
const error = ref(null)
const isMenuOpen = ref(false)

// API functions
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

const addTodo = async (todoData) => {
  try {
    loading.value = true
    error.value = null
    const body = { 
      title: todoData.title, 
      description: todoData.description, 
      completed: false 
    }
    if (todoData.dueDate) {
      body.dueDate = todoData.dueDate
    }
    const newTodoItem = await $fetch(`${apiBase}/todos`, {
      method: 'POST',
      body
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

const reorderTodos = async (newOrder) => {
  try {
    const todoIds = newOrder.map(todo => todo.id)
    await $fetch(`${apiBase}/todos/reorder`, {
      method: 'PUT',
      body: todoIds
    })
    todos.value = newOrder
  } catch (err) {
    error.value = 'Failed to reorder todos'
    console.error('Error reordering todos:', err)
    await fetchTodos()
  }
}

// Event handlers
const handleFormSubmit = (todoData) => {
  addTodo(todoData)
}

const toggleTodo = async (id) => {
  const todo = todos.value.find(t => t.id === id)
  if (todo) {
    await updateTodo(id, { completed: !todo.completed })
  }
}

// Menu functions
const toggleMenu = () => {
  isMenuOpen.value = !isMenuOpen.value
}

const closeMenu = () => {
  isMenuOpen.value = false
}

// Initialize
onMounted(async () => {
  await fetchTodos()
})
</script>