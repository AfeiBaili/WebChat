package com.afeibaili.chat.pojo;

import lombok.Data;

/**
 * @author AfeiB
 * @description 公共聊天消息类
 * @createDate 2024/8/21 下午12:12
 */
@Data
public class PublicChatMessage {
    String messageId;
    String userId;
    String userName;
    String message;
    Long messageDate;
}
