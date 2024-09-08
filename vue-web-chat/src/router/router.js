import {createRouter, createWebHashHistory} from "vue-router";
import Panel from "../components/user/Panel.vue";
import PublicChatPanel from "../components/chat/PublicChatPanel.vue";
import PersonalInformation from "../components/user/personal/PersonalInformation.vue";
import Information from "../components/user/personal/Information.vue";
import Palette from "../components/user/personal/Palette.vue";
import Setting from "../components/user/personal/Setting.vue";

const routes = [
    {path: '/login', component: Panel},
    {path: '/chat', component: PublicChatPanel},
    {
        path: '/personal', component: PersonalInformation, children: [
            {path: '/information', component: Information},
            {path: '/palette', component: Palette},
            {path: '/setting', component: Setting}
        ]
    },
]

export const router = createRouter({
    history: createWebHashHistory(),
    routes,
})
