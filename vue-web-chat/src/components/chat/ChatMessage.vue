<script setup>

import {accountStore} from "../../store/user/account.js";
import {computed} from "vue";
import {publicChatUserStore} from "../../store/chat/public-chat-user.js";

const props = defineProps(["item"])
const messageItem = props.item

const account = accountStore().account
const isMe = account.id === messageItem.userId
const publicChatUser = publicChatUserStore().publicChatUser

//方法
/*function getPublicChatUserAvatar() {
  if (publicChatUser[props.item.userId]) {
    return publicChatUser[props.item.userId];
  } else {
    publicChatUser[props.item.userId] = props.item.userName
    console.log(publicChatUser[props.item.userId])

    let data;

    axios({
      method: "post",
      url: `http://${localhost}/get-avatar`,
      params: {
        id: props.item.userId
      }
    }).then((response) => {
      data = response.data.data;
    })
    return data
  }
}*/

//计算属性
const getDate = computed(() => {
  const date = new Date(messageItem.messageDate);
  return date.getFullYear() + "年" + (date.getMonth() + 1) + "月" + date.getDate() + "日 " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
})

</script>

<template>
  <div :class="isMe?'chat-message-panel-my':'chat-message-panel'" class="chat-message">
    <div class="name" v-show="!isMe">{{ messageItem.userName }}</div>
    <div class="message-date">{{ getDate }}</div>
    <div class="name" v-show="isMe">{{ messageItem.userName }}</div>
    <div class="avatar-container">
      <div class="avatar">
        <!--
                <img :src="`data:image/*;base64,${getPublicChatUserAvatar()}`" v-show="account.avatarBase64">
        -->
      </div>
    </div>
    <div class="message-container">
      <div class="message">{{ messageItem.message }}</div>
    </div>
  </div>
</template>

<style scoped>
.chat-message-panel-my {
  margin-top: -20px;
  text-align: right;

  .avatar-container {
    text-align: right;

    .avatar {
      width: 50px;
      height: 50px;
      border-radius: 50%;
      display: inline-block;

      background: url("/image/avatar.png") no-repeat center/cover;

      img {
        width: 100%;
        height: auto;
        object-fit: cover;
        aspect-ratio: 1 / 1;

        border-radius: 50%;
      }
    }
  }

  .name {
    display: inline-block;
    color: white;
    font-size: 0.9em;
    position: relative;
    right: 55px;
    top: 20px;
  }

  .message-date {
    margin-right: 10px;
    display: inline-block;
    color: transparent;
    font-size: 0.5em;
    position: relative;
    right: 55px;
    top: 20px;
    transition: color 0.5s;
  }

  .message-container {
    width: 87%;
    display: inline-block;

    .message {
      word-break: break-all;
      text-align: left;
      display: inline-block;
      background-color: #363636;
      padding: 10px;
      border-radius: 5px;
      color: white;
      position: relative;
      right: 54px;
      top: -25px;
    }
  }
}

.chat-message-panel {
  margin-top: -20px;

  .avatar {
    width: 50px;
    height: 50px;
    border-radius: 50%;

    background: url("/image/avatar.png") no-repeat center/cover;

    img {
      width: 100%;
      height: auto;
      object-fit: cover;
      aspect-ratio: 1 / 1;

      border-radius: 50%;
    }
  }

  .name {
    display: inline-block;
    color: white;
    font-size: 0.9em;
    position: relative;
    left: 55px;
    top: 20px;
  }

  .message-date {
    margin-left: 10px;
    display: inline-block;
    color: transparent;
    font-size: 0.5em;
    position: relative;
    left: 55px;
    top: 20px;
    transition: color 0.5s;
  }

  .message-container {
    width: 87%;
    display: inline-block;

    .message {
      word-break: break-all;
      text-align: left;
      display: inline-block;
      background-color: #363636;
      padding: 10px;
      border-radius: 5px;
      color: white;
      position: relative;
      left: 54px;
      top: -25px;
    }
  }
}

.chat-message-panel-my .message-date:hover, .chat-message-panel .message-date:hover {
  color: gray;
}
</style>