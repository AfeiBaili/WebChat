package com.afeibaili.chat.controller;

import com.afeibaili.chat.pojo.PublicChatMessage;
import com.afeibaili.chat.pojo.Result;
import com.afeibaili.chat.service.LobbyChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author AfeiB
 * @description 接送消息的控制类
 * @createDate 2024/8/22 下午9:35
 */

@RestController
@CrossOrigin
public class LobbyChatController {

    @Autowired
    LobbyChatService lobbyChatService;

    @PostMapping("get-lobby-chat")
    public Result getLobbyChatList() {
        return lobbyChatService.getLobbyChatMessageList();
    }

    @PostMapping("save-lobby-chat")
    public Result saveLobbyChat(@RequestBody PublicChatMessage publicChatMessage) {
        return lobbyChatService.saveLobbyChatMessage(publicChatMessage);
    }

    @PostMapping("get-avatar")
    public Result saveLobbyChat(@RequestParam String id) {
        return lobbyChatService.getUserAvatarById(id);
    }
}
