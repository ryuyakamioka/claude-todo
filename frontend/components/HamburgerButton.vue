<template>
  <button
    @click="toggleMenu"
    class="fixed top-4 left-4 z-50 p-2 bg-white rounded-lg shadow-md hover:shadow-lg transition-all duration-200 focus:outline-none focus:ring-2 focus:ring-blue-300"
    :class="{ 'bg-blue-50': isMenuOpen }"
    aria-label="メニューを開く"
  >
    <div class="w-6 h-6 flex flex-col justify-center space-y-1">
      <span
        class="block w-full h-0.5 bg-gray-700 transition-all duration-300 origin-center"
        :class="{ 'rotate-45 translate-y-1': isMenuOpen }"
      ></span>
      <span
        class="block w-full h-0.5 bg-gray-700 transition-all duration-300"
        :class="{ 'opacity-0': isMenuOpen }"
      ></span>
      <span
        class="block w-full h-0.5 bg-gray-700 transition-all duration-300 origin-center"
        :class="{ '-rotate-45 -translate-y-1': isMenuOpen }"
      ></span>
    </div>
  </button>
</template>

<script setup>
const props = defineProps({
  isMenuOpen: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['toggle'])

const toggleMenu = () => {
  emit('toggle')
}
</script>