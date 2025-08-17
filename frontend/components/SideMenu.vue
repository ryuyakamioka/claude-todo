<template>
  <div>
    <!-- Overlay -->
    <Transition name="overlay">
      <div
        v-if="isOpen"
        class="fixed inset-0 bg-black bg-opacity-50 z-40"
        @click="closeMenu"
      ></div>
    </Transition>

    <!-- Side Menu -->
    <Transition name="slide">
      <nav
        v-if="isOpen"
        class="fixed top-0 left-0 h-full w-64 bg-white shadow-xl z-50 overflow-y-auto md:block"
      >
        <div class="p-6">
          <!-- Menu Header -->
          <div class="flex items-center justify-between mb-8">
            <div class="flex items-center gap-3">
              <div class="w-8 h-8 text-gray-700">
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
              <h2 class="text-lg font-bold text-gray-800">Cat Todo</h2>
            </div>
            <button
              @click="closeMenu"
              class="p-1 rounded-full hover:bg-gray-100 transition-colors duration-200"
              aria-label="メニューを閉じる"
            >
              <svg class="w-5 h-5 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
              </svg>
            </button>
          </div>

          <!-- Menu Items -->
          <div class="space-y-2">
            <NuxtLink
              to="/"
              @click="closeMenu"
              class="flex items-center gap-3 px-4 py-3 text-gray-700 rounded-lg hover:bg-gray-100 transition-colors duration-200"
              :class="{ 'bg-blue-50 text-blue-700 border-r-4 border-blue-500': $route.path === '/' }"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v10a2 2 0 002 2h8a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-3 7h3m-3 4h3m-6-4h.01M9 16h.01"></path>
              </svg>
              <span class="font-medium">TODOリスト</span>
            </NuxtLink>
          </div>
        </div>
      </nav>
    </Transition>
  </div>
</template>

<script setup>
const props = defineProps({
  isOpen: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['close'])

const closeMenu = () => {
  emit('close')
}

// Close menu when route changes
const route = useRoute()
watch(route, () => {
  closeMenu()
})
</script>

<style scoped>
.overlay-enter-active,
.overlay-leave-active {
  transition: opacity 0.3s ease;
}

.overlay-enter-from,
.overlay-leave-to {
  opacity: 0;
}

.slide-enter-active,
.slide-leave-active {
  transition: transform 0.3s ease;
}

.slide-enter-from,
.slide-leave-to {
  transform: translateX(-100%);
}
</style>