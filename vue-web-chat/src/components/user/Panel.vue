<script setup>
import UserRegisterLogin from "./UserRegisterLogin.vue"
import {loginStatus} from "../../store/user/login-status.js";
import {storeToRefs} from "pinia";

const status = loginStatus();

const {isLoginStatus} = storeToRefs(status);
</script>

<template>
  <div class="panel">
    <UserRegisterLogin></UserRegisterLogin>
    <transition v-show="isLoginStatus" name="login">
      <div class="font login-font">LOGIN</div>
    </transition>
    <transition v-show="!isLoginStatus" name="register">
      <div class="font register-font">REGISTER</div>
    </transition>
  </div>
</template>

<style scoped>
.login-enter-active {
  animation: show-login-text 0.5s;
}

.login-leave-active {
  animation: show-login-text 0.5s reverse;
}

.register-enter-active {
  animation: show-login-text 0.5s;
}

.register-leave-active {
  animation: show-login-text 0.5s reverse;
}

@keyframes show-login-text {
  from {
    transform: translateY(-100%);
  }
  to {
    transform: translateY(0%);
  }
}

@keyframes showRegisterText {
  from {
    transform: translateY(-100%);
  }
  to {
    transform: translateY(0%);
  }
}


.panel {
  position: absolute;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  background: linear-gradient(90deg, rgba(0, 3, 24, 0.7), #001b23);

  .register-font {
    position: absolute;
    right: 10vw;
  }

  .login-font {
    position: absolute;
    left: 10vw;
  }

  .font {
    writing-mode: tb;
    font-size: 30vh;
    font-weight: bolder;
    -webkit-text-stroke: 10px #888888;
    color: transparent;
    text-shadow: #ffffff 15px 15px;
  }
}

@media screen and (min-height: 100vw) {
  .panel {

    .font {
      writing-mode: tb;
      font-size: 30vh;
      font-weight: bolder;
      -webkit-text-stroke: 10px #28377e;
      color: transparent;
      text-shadow: #d7d7d7 15px 15px;
    }
  }
}
</style>