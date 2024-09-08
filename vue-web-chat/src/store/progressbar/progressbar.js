import {defineStore} from "pinia";

export const progressbar = defineStore("progressbar", {
    state() {
        return {
            progressPercentage: {
                width: "0%",
                transition: "width 0.1s"
            }
        }
    }, actions: {
        progressController(progressPercentage) {
            this.progressPercentage.width = progressPercentage + "%"
        },
        progressSuccess() {
            this.progressPercentage.width = 100 + "%"
            this.progressReset()
        },
        progressReset() {
            this.progressPercentage.transition = "width 0s"
            setTimeout(() => {
                this.progressPercentage.width = "0%"
            }, 100)
        }
    }
})