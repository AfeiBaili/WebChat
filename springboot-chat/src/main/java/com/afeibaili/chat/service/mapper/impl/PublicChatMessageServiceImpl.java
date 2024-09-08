package com.afeibaili.chat.service.mapper.impl;

import com.afeibaili.chat.mapper.PublicChatMessageMapper;
import com.afeibaili.chat.pojo.PublicChatMessage;
import com.afeibaili.chat.service.mapper.PublicChatMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author AfeiB
 * @description 公共聊天消息服务层映射实现
 * @createDate 2024/8/21 下午12:53
 */
@Service
public class PublicChatMessageServiceImpl
        extends ServiceImpl<PublicChatMessageMapper, PublicChatMessage>
        implements PublicChatMessageService {
}
