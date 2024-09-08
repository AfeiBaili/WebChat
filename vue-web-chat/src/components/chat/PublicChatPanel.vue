<script setup>
import ChatMessage from "./ChatMessage.vue";
import {publicChatMessageStore} from "../../store/chat/public-chat-message.js";
import {storeToRefs} from "pinia";
import {nextTick, onMounted, onUnmounted, ref, watch} from "vue";
import {Message} from "../../class/Message.js";
import {accountStore} from "../../store/user/account.js";
import {progressbar} from "../../store/progressbar/progressbar.js";
import axios from "axios";
import {localhost} from "../../../config.js";
import {useRouter} from "vue-router";

//变量
const {publicChatMessageList} = storeToRefs(publicChatMessageStore())
const account = accountStore().account
const message = ref("")
const progressController = progressbar().progressController
const progressSuccess = progressbar().progressSuccess
const unreadMessageCount = ref(0)
const chatContent = ref()
let unreadMessage = null
const onlineCounter = ref(0)
const webSocket = new WebSocket(`ws://${localhost}`)
const router = useRouter();

//方法
function sendMessage() {
  if (message.value === "") return
  progressController(20)
  const msg = new Message(Date.now() + "-" + Math.random().toFixed(4), account.id, account.name, message.value, Date.now());
  axios.post(`http://${localhost}/save-lobby-chat`, msg)
  webSocket.send(JSON.stringify(msg))
  message.value = ""

  nextTick(() => {
    scrollToBottom()
    progressSuccess()
  })
}

function enter(event) {
  if (event.shiftKey) {
    message.value += "\n"
  } else {
    sendMessage()
  }
}

function jumpPersonal() {
  router.push("/personal")
}

//WebSocket
webSocket.onmessage = (event) => {
  const data = JSON.parse(event.data);
  if (data.onlineCounter) {
    onlineCounter.value = data.onlineCounter
  } else {
    publicChatMessageList.value.push(data)
    nextTick(() => {
      scrollToBottom()
    })
  }
}

//滚动
function scrollToBottom() {
  if (chatContent.value.scrollHeight - Math.round((chatContent.value.clientHeight + chatContent.value.scrollTop)) < 1000) {
    chatContent.value.lastElementChild.scrollIntoView({behavior: "smooth"})
  } else {
    if (unreadMessageCount.value === 0) {
      unreadMessage = chatContent.value.lastElementChild
    }
    unreadMessageCount.value++
  }
}

//未读消息
function readUnreadMessage() {
  unreadMessage.scrollIntoView({behavior: "smooth"})
  unreadMessageCount.value = 0
}

//监视属性(已弃用)
watch(publicChatMessageList, () => {
  localStorage.setItem("publicChatMessageList", JSON.stringify(publicChatMessageList.value));
}, {deep: true})

//创建之时
onMounted(() => {
  if (chatContent.value.lastElementChild === null) return
  chatContent.value.lastElementChild.scrollIntoView({behavior: "smooth"})
})

//销毁之时
onUnmounted(() => {
  webSocket.close()
})
</script>

<template>
  <div class="chat-panel">
    <div class="left-panel">

    </div>
    <div class="right-panel">
      <div class="nav">
        <div class="online-counter">在线:{{ onlineCounter }}</div>
        <div class="icon-border">
          <div class="personal icon-yonghu iconfont" @click="jumpPersonal"></div>
        </div>
      </div>
      <div class="chat-content" ref="chatContent">
        <ChatMessage v-for="item in publicChatMessageList" :key="item.messageId" :item="item"></ChatMessage>
      </div>
      <div class="unread-message-count" v-show="unreadMessageCount !== 0" @click="readUnreadMessage">
        {{ unreadMessageCount }}
      </div>
      <div class="input-box">
        <div class="input-nav"></div>
        <textarea class="input" placeholder="Enter↩" v-model="message"
                  @keydown.enter.prevent="enter"/>
        <input type="button" value="发送" class="button" @click="sendMessage">
      </div>
    </div>
  </div>
</template>

<style scoped>
.chat-panel {
  width: 100vw;
  height: 100vh;
  background-color: #171717;

  .left-panel {
    width: 300px;
    height: 100%;
    background-color: rgba(17, 17, 17, 0.8);

    position: absolute;
    left: 0;
  }

  .right-panel {
    width: calc(100% - 300px);
    height: 100%;
    background-color: rgba(24, 24, 24, 0.2);

    position: absolute;
    right: 0;

    .unread-message-count {
      display: inline-block;
      width: 30px;
      height: 30px;
      background-color: #067ee1;
      text-align: center;
      font-size: 1.3em;
      border-radius: 15px;
      color: white;

      position: absolute;
      right: 40px;
      bottom: 220px;
    }

    .chat-content {
      height: calc(100% - 50px - 200px);
      overflow-y: auto;
      padding: 0 70px;
      scrollbar-color: #000000 #1a1a1a;
    }

    .nav {
      width: 100%;
      background-color: rgb(17, 17, 17);
      height: 50px;
      position: relative;

      .online-counter {
        color: white;
        border-bottom: #097e2f solid 3px;
        display: inline-block;
        padding: 5px;
        margin-left: 15px;
        margin-top: 7px;
      }

      .icon-border {
        position: absolute;
        top: 10%;
        right: 20px;

        border-radius: 10px;
        padding: 4px;
        border: 1px solid #bababa;

        .personal {
          color: #bababa;
          font-size: 2em;
          transition: all 0.2s;
        }

        .personal:hover {
          color: white;
          transform: scale(120%);
        }
      }
    }

    .input-box {
      width: 100%;
      height: 200px;
      background-color: rgba(17, 17, 17, 0.5);

      position: absolute;
      bottom: 0;
      left: 50%;
      transform: translateX(-50%);
      text-align: left;

      .input-nav {
        height: 20%;
        width: 100%;
        background-color: #111111;
      }

      .input {
        height: 80%;
        width: 100%;
        border: none;
        outline: none;
        resize: none;
        scrollbar-color: black #1a1a1a;
        position: absolute;

        color: #c0c0c0;
        font-size: 1.2em;

        vertical-align: bottom;
        background-color: rgba(66, 66, 66, 0.2);
        border-bottom: 3px solid #c0c0c0;
        box-sizing: border-box;
        font-family: sans-serif;
      }

      .input::placeholder {
        font-family: "Microsoft JhengHei", sans-serif;
      }

      .input:hover {
        background-color: rgba(49, 49, 49, 0.6);
        border-radius: 8px;
      }

      .input:focus {
        background-color: transparent;
        color: #bababa;
      }

      .button {
        margin-top: 30px;
        height: 40px;
        width: 10%;
        vertical-align: bottom;
        border: none;
        position: absolute;
        bottom: 0;
        right: 0;

        color: #c0c0c0;
        font-size: 1.2em;
        background-color: transparent;
        border-bottom: 3px solid #3d9aef;
        transition: all 0.5s;
        box-sizing: border-box;
      }

      .button:hover {
        background-color: #3d9aef;
        border-radius: 8px;
        transform: translateY(-4px);
      }

      .button:active {
        transition: all 0.0s;
        background-color: #02518f;
      }
    }
  }
}

@media screen and (min-height: 100vw) {
  .chat-panel {
    width: 100vw;
    height: 100vh;

    .left-panel {
      width: 20vw;
      height: 100%;
      transform: translateX(-100%);
    }

    .right-panel {
      width: 100%;
      height: 100%;

      position: absolute;
      right: 0;

      .input-box {

        .button {
          width: 100px;
        }
      }

      .chat-content {
        padding: 0 10px;
      }

      .unread-message-count {
        position: absolute;
        left: 50%;
        transform: translateX(-50%);
      }
    }
  }

}
</style>