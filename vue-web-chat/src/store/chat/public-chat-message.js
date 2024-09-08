import {defineStore} from "pinia";
import axios from "axios";
import {localhost} from "../../../config.js";

let publicChatMessageListRequest = []

axios.post(`http://${localhost}/get-lobby-chat`).then(response => {
    publicChatMessageListRequest = response.data.data;
})

export const publicChatMessageStore = defineStore("publicChatMessage", {
    actions: {

    },
    state() {
        return {
            publicChatMessageList: publicChatMessageListRequest
        }
    }
})