<template>
  <div class="mb-6 flex flex-wrap gap-2 justify-center">
    <button
      v-for="filter in filters"
      :key="filter.value"
      @click="$emit('filter-change', filter.value)"
      class="px-4 py-2 rounded-full text-sm font-medium transition-all duration-200 flex items-center gap-2"
      :class="[
        currentFilter === filter.value
          ? 'bg-blue-500 text-white shadow-md'
          : 'bg-white text-gray-600 border border-gray-300 hover:bg-gray-50 hover:border-gray-400'
      ]"
    >
      <span class="text-lg">{{ filter.emoji }}</span>
      <span>{{ filter.label }}</span>
      <span 
        v-if="filter.count !== undefined" 
        class="px-2 py-0.5 rounded-full text-xs"
        :class="[
          currentFilter === filter.value
            ? 'bg-white bg-opacity-20 text-white'
            : 'bg-gray-100 text-gray-600'
        ]"
      >
        {{ filter.count }}
      </span>
    </button>
  </div>
</template>

<script setup>
const props = defineProps({
  currentFilter: {
    type: String,
    default: 'pending'
  },
  todos: {
    type: Array,
    default: () => []
  }
})

const emit = defineEmits(['filter-change'])

const filters = computed(() => {
  const allCount = props.todos.length
  const completedCount = props.todos.filter(todo => todo.completed).length
  const pendingCount = props.todos.filter(todo => !todo.completed).length

  return [
    {
      value: 'pending',
      label: '未完了',
      emoji: '⏳',
      count: pendingCount
    },
    {
      value: 'completed',
      label: '完了',
      emoji: '✅',
      count: completedCount
    },
    {
      value: 'all',
      label: 'すべて',
      emoji: '📋',
      count: allCount
    }
  ]
})
</script>