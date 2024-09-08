import {defineStore} from "pinia";

export const publicChatUserStore = defineStore("publicChatUser", {
    state() {
        return {
            publicChatUser: {}
        }
    }
})