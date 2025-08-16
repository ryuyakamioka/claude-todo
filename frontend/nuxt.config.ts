// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: '2025-07-15',
  devtools: { enabled: true },
  modules: ['@nuxtjs/tailwindcss'],
  runtimeConfig: {
    public: {
      apiBase: 'http://localhost:8081/api'
    }
  },
  app: {
    head: {
      title: 'Cat Todo - Purr-fectly organized tasks',
      meta: [
        { name: 'description', content: 'A cute cat-themed todo application built with Nuxt3 and Spring Boot' }
      ]
    }
  }
})
