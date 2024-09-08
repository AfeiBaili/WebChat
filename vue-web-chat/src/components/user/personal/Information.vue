<script setup>
import {accountStore} from "../../../store/user/account.js";
import {nextTick, onMounted, ref, watch} from "vue";
import axios from "axios";
import {localhost} from "../../../../config.js";
import {progressbar} from "../../../store/progressbar/progressbar.js";

//变量
let account = accountStore().account;
const acc = ref({});
acc.value = {...account}
const isShowName = ref(false);
const isShowPassword = ref(false);
const isShowGender = ref(false);
const isShowEmail = ref(false);
const isShowBirthDate = ref(false);
const progressSuccess = progressbar().progressSuccess;
const progressController = progressbar().progressController;
const fileTips = ref("");
const avatarBase64 = ref("");

const nameRef = ref()
const emailRef = ref()
const genderRef = ref()
const birthDateRef = ref()
const fileRef = ref();

const showNameInput = ref(false)
const showEmailInput = ref(false)
const showGenderInput = ref(false)
const showBirthDateInput = ref(false)


//方法
function createDateFormat() {
  const date = new Date(account.createDate);
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const dateNumber = date.getDate();
  return year + "年" + month + "月" + dateNumber + "日";
}

function updateUser() {
  progressController(60)
  axios.post(`http://${localhost}/update`, acc.value).then((response) => {
    localStorage.setItem("account", JSON.stringify(response.data.data))
    progressSuccess()
  })
}

function cancelUpdate() {
  acc.value = {...account}
}

//钩子
onMounted(() => {
  //更上传文件时
  fileRef.value.addEventListener("change", (e) => {
    const file = e.target.files[0];
    if (file.size > 1024 * 1024 * 10) {
      fileTips.value = "文件太大"
    } else {
      const fileReader = new FileReader();
      fileReader.readAsDataURL(file)
      fileReader.onloadend = () => {
        avatarBase64.value = fileReader.result.split(",")[1]
        const newAccount = JSON.parse(localStorage.getItem("account"))
        newAccount.avatarBase64 = avatarBase64.value
        localStorage.setItem("account", JSON.stringify(newAccount))
        account = JSON.parse(localStorage.getItem("account"))

        axios.post(`http://${localhost}/update`, account).then((response) => {
          console.log(response.data)
        })
      }
    }
  })
  new FormData()
})
//监视属性
watch(showNameInput, (value) => {
  if (value) {
    nextTick(() => {
      nameRef.value.focus()
    })
  }
})
watch(showEmailInput, (value) => {
  if (value) {
    nextTick(() => {
      emailRef.value.focus()
    })
  }
})
watch(showBirthDateInput, (value) => {
  if (value) {
    nextTick(() => {
      birthDateRef.value.focus()
    })
  }
})
</script>

<template>
  <div class="panel">
    <div class="background-image">

    </div>
    <div class="avatar">
      <img class="img" src="/image/avatar.png" :alt="acc.name +' is avatar'" v-show="account.avatarBase64==null"/>
      <img class="img" :src="`data:image/*;base64,${account.avatarBase64}`" :alt="acc.name +' is avatar'"
           v-show="account.avatarBase64!=null"/>
    </div>
    <div class="name" @mouseenter="isShowName=true" @mouseleave="isShowName=false">
      <div>{{ acc.name }}<span>的个人资料</span>
        <span class="modify" v-show="isShowName" @click="showNameInput = true">✏️</span>
        <input type="text" v-model="acc.name" v-show="showNameInput" @blur="showNameInput=false" ref="nameRef"
               @keydown.enter="showNameInput=false">
      </div>
      <label>
        上传头像
        <input type="file" accept="image/*" ref="fileRef">
        <span class="file-tips">{{ fileTips }}</span>
      </label>
    </div>
    <hr class="divider-line"/>

    <div class="field id">
      我的ID：
      <div>{{ acc.id }}</div>
    </div>

    <div class="field gender" @mouseenter="isShowGender=true" @mouseleave="isShowGender=false">
      性别：
      <div>{{ acc.gender }}</div>
      <div>
        <input type="radio" name="gender" value="男" v-model="acc.gender" v-show="showGenderInput">
        <input type="radio" name="gender" value="女" v-model="acc.gender" v-show="showGenderInput">
      </div>
      <span class="modify" v-show="isShowGender" @click="showGenderInput=true">✏️</span>
    </div>

    <div class="field level">
      等级：
      <div>{{ acc.level }}</div>
    </div>

    <div class="field email" @mouseenter="isShowEmail=true" @mouseleave="isShowEmail=false">
      我的邮箱：
      <input type="text" v-model="acc.email" v-show="showEmailInput"
             @blur="showEmailInput=false" ref="emailRef" @keydown.enter="showEmailInput=false">
      <div v-show="!showEmailInput">{{ acc.email }}</div>
      <span class="modify" v-show="isShowEmail" @click="showEmailInput = true">✏️</span>
    </div>

    <div class="field birth-date" @mouseenter="isShowBirthDate=true" @mouseleave="isShowBirthDate=false">
      出生日期：
      <input type="text" v-model="acc.birthDate" v-show="showBirthDateInput"
             @blur="showBirthDateInput=false" ref="birthDateRef" @keydown.enter="showBirthDateInput=false">
      <div v-show="acc.birthDate!==0&&!showBirthDateInput">{{ acc.birthDate }}</div>
      <div v-show="acc.birthDate===0&&!showBirthDateInput" class="not-filled">未填写</div>
      <span class="modify" v-show="isShowBirthDate" @click="showBirthDateInput = true">✏️</span>
    </div>

    <div class="field create-date">
      创建日期：
      <div>{{ createDateFormat() }}</div>
    </div>

    <input class="field" type="button" value="保存" @click="updateUser"/>
    <input class="field" type="button" value="取消" @click="cancelUpdate"/>
  </div>
</template>

<style scoped>
.not-filled {
  color: gray;
}

.panel {
  overflow-y: auto;
  scrollbar-color: #111111 #2b2b2b;
  position: relative;

  .background-image {
    background-color: white;
    width: 100%;
    height: 30%;
  }

  .avatar {
    width: 20vw;
    height: 20vw;
    background-color: #111111;
    margin-top: -11vw;
    border-radius: 50%;
    position: relative;

    img {
      width: 97%;
      height: auto;
      object-fit: cover;
      aspect-ratio: 1 / 1;

      border-radius: 50%;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translateY(-50%) translateX(-50%);
    }
  }

  .name {
    position: absolute;
    top: 30%;
    left: 25%;
    font-size: 3em;
    color: #3d9aef;

    span {
      font-size: 0.4em;
      color: #b7b7b7;
    }

    .modify {
      cursor: pointer;
    }
  }

  .divider-line {
    width: 90%;
    margin-top: 50px;
    margin-bottom: 50px;
  }

  .field {
    margin-left: 60px;
    margin-bottom: 30px;
    padding: 30px;
    color: #bababa;
    width: 30%;
    text-align: left;
    position: relative;

    font-size: 1.2em;

    border: #000000 1px solid;
    border-radius: 10px;
    background-color: #1c1c1c;
    box-shadow: rgba(0, 0, 0, 0.2) 0 0 30px 10px;
  }

  .field div {
    display: inline-block;
    position: absolute;
    right: 30px;
  }

  .field span, .field input {
    display: inline-block;
    position: absolute;
    right: 0;
    cursor: pointer;
  }

  .field input, .name input {
    right: 30px;
    vertical-align: middle;
    border: none;

    background-color: #111111;
    width: 200px;
    color: #bababa;
    height: 30%;
  }

  .field input[type='radio'] {
    position: relative;
    width: 20px;
    height: 20px;
    vertical-align: top;
  }

  .name input {
    height: 20px;
    vertical-align: bottom;

    background-color: #111111;
  }

  .name label {
    font-size: 0.4em;
    color: #bababa;
    border-radius: 10px;
    border: 1px solid black;
    padding: 10px;
    position: absolute;
  }

  .name label input[type="file"] {
    display: none;
  }

  .name label .file-tips {
    color: orangered;
  }

  input[type="button"] {
    width: 15%;
    text-align: center;
    font-size: 1.5em;
    transition: all 0.2s;
  }

  input[type="button"]:hover {
    background-color: #00c0fc;
    color: #111111;
  }

  input[type="button"] + input:hover {
    background-color: orangered;
    color: #111111;
  }

  input[type="button"]:active {
    background-color: black !important;
  }
}
</style>