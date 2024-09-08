<script setup>
import {computed, onMounted, reactive, ref, watch} from "vue"
import {Account} from "../../class/Account.js"
import {loginStatus} from "../../store/user/login-status.js"
import {storeToRefs} from "pinia"
import axios from "axios"
import {useRouter} from "vue-router"
import {progressbar} from "../../store/progressbar/progressbar.js"
import {localhost} from "../../../config.js";

//变量值
const progressController = progressbar().progressController
const progressSuccess = progressbar().progressSuccess
const {isLoginStatus} = storeToRefs(loginStatus())
const responseMessage = reactive({
  message: "",
  color: "orangered"
})
const username = ref("")
const password = ref("")
const email = ref("")
const showPassword = ref(false)
const router = useRouter()
const rememberMe = ref(false)

//正则表达式
const usernameRex = /^[a-zA-Z0-9_\u4e00-\u9fa5]{2,}$/
const passwordRex = /^[a-zA-Z0-9.]{6,}$/
const emailRex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/

//登录模块
function login() {
  if (isLoginStatus.value !== true) {
    isLoginStatus.value = true
    responseMessage.message = ""
    return
  }
  progressController(70)
  if (!usernameRex.test(username.value)) {
    responseMessage.message = "账号不正确!"
    responseMessage.color = "#FF4500"
    progressSuccess()
    return
  }
  if (!passwordRex.test(password.value)) {
    responseMessage.message = "密码不正确!"
    responseMessage.color = "#FF4500"
    progressSuccess()
    return
  }

  loginRequest(new Account(null,
      username.value,
      password.value,
      null,
      email.value,
      Date.now(),
      0,
      0,
      0,
      null
  ))

  responseMessage.message = ""
}

function register() {
  if (isLoginStatus.value !== false) {
    isLoginStatus.value = false
    responseMessage.message = ""
    return
  }
  progressController(70)
  if (!usernameRex.test(username.value)) {
    responseMessage.message = "账号格式必须为a-Z、0-9、和_符号!"
    responseMessage.color = "#FF4500"
    progressSuccess()
    return
  }
  if (!passwordRex.test(password.value)) {
    responseMessage.message = "密码格式只能包含.符号,且大于6个字符!"
    responseMessage.color = "#FF4500"
    progressSuccess()
    return
  }
  if (!emailRex.test(email.value)) {
    responseMessage.message = "邮箱格式错误!"
    responseMessage.color = "#FF4500"
    progressSuccess()
    return
  }

  registerRequest(new Account(null,
      username.value,
      password.value,
      null,
      email.value,
      Date.now(),
      0,
      0,
      0,
      null
  ))
  responseMessage.message = ""
}

//请求登录&结果处理模块
async function loginRequest(data) {
  try {
    const response = await axios.post(`http://${localhost}/login`, data)
    progressSuccess()
    saveAccount(response.data.data)
    switch (response.data.code) {
      case 200:
        responseMessage.message = "登陆成功!"
        responseMessage.color = "#00b2ff"
        await router.push("/chat")
        break
      case 507:
        responseMessage.message = "用户不存在!"
        responseMessage.color = "#FF4500"
        break
      case 508:
        responseMessage.message = "密码不正确!"
        responseMessage.color = "#FF4500"
        break
    }
  } catch (error) {
    responseMessage.message = "网络错误!"
    responseMessage.color = "#FF4500"
    progressSuccess()
  }

}

async function registerRequest(data) {
  try {
    const response = await axios.post(`http://${localhost}/register`, data)
    progressSuccess()
    saveAccount(response.data.data)
    switch (response.data.code) {
      case 200:
        responseMessage.message = "注册成功!"
        responseMessage.color = "#00ff04"
        await router.push("/chat")
        break
      case 506:
        responseMessage.message = "用户已存在!"
        responseMessage.color = "#00b2ff"
        break
    }
  } catch (error) {
    responseMessage.message = "网络错误!"
    responseMessage.color = "#FF4500"
    progressSuccess()
  }
}

//保存用户到本地
function saveAccount(account) {
  localStorage.setItem("account", JSON.stringify(account))
}

//加载本用户到网络
onMounted(() => {
  const account = JSON.parse(localStorage.getItem("account"))
  rememberMe.value = JSON.parse(localStorage.getItem("rememberMe"))
  if (account !== null) {
    username.value = account.name
  }
  if (account !== null && rememberMe.value) {
    password.value = account.password
    username.value = account.name
  }
})

//计算属性模块
const showPasswordFunction = computed(() => {
  return showPassword.value ? "text" : "password"
})

//监视属性
watch(rememberMe, () => {
  localStorage.setItem("rememberMe", JSON.stringify(rememberMe.value))
})

</script>

<template>
  <div class="panel">
    <div class="panel-body">
      <div class="login">
        <input class="input username-input" type="text" placeholder="用户名" v-model.trim="username" maxlength="16"
               @keydown.enter="login"/>
        <input class="input password-input" :type="showPasswordFunction" placeholder="密码"
               v-model.trim="password"
               maxlength="50"
               @keydown.ctrl="showPassword = true" @keyup="showPassword = false" @keydown.enter="login"/>
        <transition v-show="!isLoginStatus" name="email-input">
          <input class="input email-input" type="text" placeholder="邮箱" v-model.trim="email"/>
        </transition>
        <label>记住我
          <input class="checkbox-input" type="checkbox" v-model="rememberMe"/>
        </label>
        <transition v-show="!isLoginStatus" name="checkbox-input">
          <input class="login-and-register-button not-available" type="button" value="注册并登录">
        </transition>
        <input class="login-button" type="button" value="登录" @click="login">
        <input class="register-button" type="button" value="注册" @click="register">
        <div class="error-tips" :style="responseMessage">{{ responseMessage.message }}</div>
      </div>
    </div>
  </div>
</template>

<style scoped>

.email-input-enter-active, .checkbox-input-enter-active {
  animation: show 0.3s;
}

.email-input-leave-active, .checkbox-input-leave-active {
  animation: show 0.3s reverse;
}

@keyframes show {
  from {
    opacity: 0;
    transform: translateY(-100%);
  }
  to {
    opacity: 1;
    transform: translateY(0%);
  }
}


.panel .panel-body {
  width: 300px;
  height: 270px;
  border-radius: 15px;
  padding: 20px;

  position: absolute;
  z-index: 1;
  top: 50vh;
  left: 50vw;
  transform: translate(-50%, -50%);

  .login {
    width: 300px;
    position: relative;

    .error-tips {
      position: absolute;
      bottom: -80px;
      font-size: 0.8em;
      color: orangered;
    }

    label {
      color: #c0c0c0;
      font-size: 1em;
      vertical-align: middle;

      input {
        vertical-align: middle;
      }
    }


    input {
      display: inline-block;
    }

    .input::placeholder {
      font-weight: bold;
      color: #8a8a8a;
    }

    .input {
      width: 300px;
      height: 25px;
      background-color: transparent;
      border-bottom: white solid 2px;
      border-right: none;
      border-left: none;
      border-top: none;
      outline: none;
      margin-bottom: 20px;

      color: white;
      font-size: 1.2em;
      font-weight: bold;
    }

    .input:focus {
      background: linear-gradient(0deg, rgba(255, 255, 255, 0.1), transparent);
    }

    input:hover {
      transition: 0.5s;
      background-color: #3f3f3f;
    }

    input[type=button] {
      position: absolute;
      width: 45%;
      height: 40px;
      color: white;
      margin-top: 40px;
      border-left: none;
      border-right: none;
      border-top: none;
      border-bottom: white solid 3px;
    }

    .login-button {
      background-color: transparent;
      left: 0;
    }

    .register-button {
      background-color: transparent;
      right: 0;
    }

    .login-and-register-button {
      background-color: transparent;
      bottom: -10px;
      right: 0;
    }
  }
}

@media screen and (min-height: 100vw) {
  .panel {

    .panel-body {

      .login {
        background-color: rgba(0, 0, 0, 0.7);
        padding: 20px 10px 90px 10px;
        border-radius: 5px;
      }
    }
  }
}
</style>