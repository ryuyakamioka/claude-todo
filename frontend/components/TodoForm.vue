<template>
  <div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6 mb-8">
    <form @submit.prevent="handleSubmit">
    <div class="space-y-4">
      <!-- Title Input - Always Visible -->
      <div class="flex flex-col sm:flex-row gap-4">
        <input
          ref="titleInput"
          v-model="newTodo"
          type="text"
          placeholder="Add a new todo..."
          class="flex-1 px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all duration-200"
          :disabled="loading"
          @focus="showFormDetails = true"
          @blur="handleTitleBlur"
          required
        />
        <button
          v-show="showFormDetails"
          type="submit"
          :disabled="loading || !newTodo.trim()"
          class="px-6 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed transition-all duration-200 transform"
          :class="showFormDetails ? 'opacity-100 translate-y-0' : 'opacity-0 translate-y-2'"
        >
          {{ loading ? 'Adding...' : 'Add Todo' }}
        </button>
      </div>
      
      <!-- Extended Form Details - Conditionally Visible -->
      <div 
        v-show="showFormDetails"
        class="overflow-hidden transition-all duration-300 ease-in-out"
        :class="showFormDetails ? 'max-h-96 opacity-100' : 'max-h-0 opacity-0'"
      >
        <div class="space-y-4 pt-2">
          <!-- Description -->
          <div class="transform transition-all duration-300 delay-100"
               :class="showFormDetails ? 'translate-y-0 opacity-100' : 'translate-y-4 opacity-0'">
            <textarea
              v-model="newTodoDescription"
              placeholder="Add description (optional)..."
              class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent resize-none"
              :disabled="loading"
              rows="3"
            ></textarea>
          </div>
          
          <!-- Due Date -->
          <div class="transform transition-all duration-300 delay-150"
               :class="showFormDetails ? 'translate-y-0 opacity-100' : 'translate-y-4 opacity-0'">
            <div class="flex flex-col sm:flex-row gap-4">
              <label class="flex-1">
                <span class="block text-sm font-medium text-gray-700 mb-1">Due Date</span>
                <input
                  v-model="newTodoDueDate"
                  type="date"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                  :disabled="loading"
                />
              </label>
              <button
                type="button"
                @click="setDefaultDueDate"
                class="px-4 py-2 text-sm bg-gray-100 text-gray-700 rounded-lg hover:bg-gray-200 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2 disabled:opacity-50 h-fit mt-6"
                :disabled="loading"
              >
                1 Week Later
              </button>
            </div>
          </div>
          
          <!-- Action Buttons -->
          <div class="transform transition-all duration-300 delay-200"
               :class="showFormDetails ? 'translate-y-0 opacity-100' : 'translate-y-4 opacity-0'">
            <div class="flex gap-2 justify-end">
              <button
                type="button"
                @click="cancelForm"
                class="px-4 py-2 text-sm text-gray-600 bg-gray-100 rounded-lg hover:bg-gray-200 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2"
                :disabled="loading"
              >
                Cancel
              </button>
              <button
                type="submit"
                :disabled="loading || !newTodo.trim()"
                class="px-6 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                {{ loading ? 'Adding...' : 'Add Todo' }}
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    </form>
  </div>
</template>

<script setup>
// Props
const props = defineProps({
  loading: { type: Boolean, default: false }
})

// Emit events
const emit = defineEmits(['submit'])

// Form state
const newTodo = ref('')
const newTodoDescription = ref('')
const newTodoDueDate = ref('')
const showFormDetails = ref(false)

// Template refs
const titleInput = ref(null)

// Form handlers
const handleSubmit = () => {
  if (newTodo.value.trim()) {
    const dueDate = newTodoDueDate.value ? new Date(newTodoDueDate.value + 'T23:59:59').toISOString() : null
    emit('submit', {
      title: newTodo.value.trim(),
      description: newTodoDescription.value.trim() || null,
      dueDate
    })
    // Clear form and reset state
    clearForm()
  }
}

const setDefaultDueDate = () => {
  const oneWeekLater = new Date()
  oneWeekLater.setDate(oneWeekLater.getDate() + 7)
  newTodoDueDate.value = oneWeekLater.toISOString().slice(0, 10)
}

const clearForm = () => {
  newTodo.value = ''
  newTodoDescription.value = ''
  newTodoDueDate.value = ''
  showFormDetails.value = false
  setDefaultDueDate()
}

const cancelForm = () => {
  clearForm()
  titleInput.value?.blur()
}

const handleTitleBlur = () => {
  // Check if the blur is happening because user clicked outside the form
  // Use setTimeout to allow for clicks on form elements to be processed first
  setTimeout(() => {
    // Check if the newly focused element is within the form
    const activeElement = document.activeElement
    const formElement = titleInput.value?.closest('form')
    
    if (!formElement?.contains(activeElement)) {
      // Only hide if there's no content in the form
      if (!newTodo.value.trim() && !newTodoDescription.value.trim()) {
        showFormDetails.value = false
      }
    }
  }, 100)
}

// Initialize default due date on mount
onMounted(() => {
  setDefaultDueDate()
})
</script>