<script setup>
import {useRouter} from "vue-router";
import {onMounted} from "vue";

//变量
const router = useRouter();

//方法
function backChat() {
  router.push("/chat")
}

function exitLogin() {
  router.replace("/login")
}

//钩子
onMounted(() => {
  router.push("/information")
})
</script>

<template>
  <div class="user-panel">
    <div class="left-panel">
      <div class="list">
        <router-link class="list-item" to="/information" active-class="active-item">我的信息</router-link>
        <router-link class="list-item" to="/palette" active-class="active-item">调色盘</router-link>
        <router-link class="list-item" to="/setting" active-class="active-item">个人设置</router-link>
        <div class="list-item back-chat" @click="backChat">回到聊天</div>
      </div>
    </div>
    <div class="right-panel">
      <router-view class="view"></router-view>
    </div>
  </div>
</template>

<style scoped>
.user-panel {
  width: 100vw;
  height: 100vh;
  position: absolute;
  overflow: hidden;

  .left-panel {
    width: 300px;
    height: 100%;
    background-color: rgba(17, 17, 17, 0.8);
    position: absolute;

    .list-item {
      display: block;
      color: #d7d7d7;
      cursor: pointer;
      text-decoration: none;
      padding: 14px;
      font-size: 1.2em;
      transition: all 0.3s;
    }

    .back-chat {
      position: absolute;
      width: calc(100% - 28px);
      bottom: 0;
      font-weight: bold;
    }

    .list-item:hover {
      background-color: #363b52;
    }

    .active-item {
      background-color: #515257;
      color: white;
    }
  }

  .right-panel {
    width: calc(100% - 300px);
    height: 100%;
    position: absolute;
    right: 0;

    .view {
      display: inline-block;
      width: 100%;
      height: 100%;
    }
  }
}
</style>