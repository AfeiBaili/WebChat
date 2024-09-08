import {defineStore} from "pinia";

export const loginStatus = defineStore("login-status", {
    state() {
        return {
            isLoginStatus: true,
        }
    }
})