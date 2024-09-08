<script setup>
import {onMounted} from "vue";
import {useRouter} from "vue-router";
import {progressbar} from "./store/progressbar/progressbar.js";
import {storeToRefs} from "pinia";

const {progressPercentage} = storeToRefs(progressbar())

onMounted(() => {
  useRouter().replace("/login")
  //debug
})

</script>

<template>
  <div class="app">
    <div :style="progressPercentage" class="progressbar"></div>
    <RouterView></RouterView>
  </div>
</template>

<style scoped>
.display-button {
  display: none;
}
</style>
