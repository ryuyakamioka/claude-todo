<template>
  <div>
    <div v-if="todos.length === 0" class="text-center text-gray-500 py-8">
      No todos yet. Add one above!
    </div>
    
    <div v-else class="space-y-2">
      <TodoItem
        v-for="todo in todos"
        :key="todo.id"
        :todo="todo"
        @toggle="$emit('toggle-todo', todo.id)"
        @delete="$emit('delete-todo', todo.id)"
        @update="(title) => $emit('update-todo', todo.id, title)"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Todo } from '~/types/todo'

interface Props {
  todos: Todo[]
}

interface Emits {
  (e: 'toggle-todo', id: number): void
  (e: 'delete-todo', id: number): void
  (e: 'update-todo', id: number, title: string): void
}

defineProps<Props>()
defineEmits<Emits>()
</script>