<template>
  <div class="text-center mb-8">
    <div class="flex items-center justify-center gap-3 mb-2">
      <!-- Cat Logo SVG -->
      <div class="w-10 h-10 text-gray-700">
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
      <h1 class="text-3xl font-bold text-gray-800">Cat Todo</h1>
    </div>
    <p class="text-sm text-gray-500">Purr-fectly organized tasks</p>
  </div>
</template>

<script setup>
</script>