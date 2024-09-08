import {defineStore} from "pinia";

export const accountStore = defineStore("accountStore", {
    state() {
        return {
            account: JSON.parse(localStorage.getItem("account"))
        }
    }
})