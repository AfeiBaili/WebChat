package com.afeibaili.chat.service;

import com.afeibaili.chat.consts.ResultConst;
import com.afeibaili.chat.mapper.AccountMapper;
import com.afeibaili.chat.pojo.Account;
import com.afeibaili.chat.pojo.PublicChatMessage;
import com.afeibaili.chat.pojo.Result;
import com.afeibaili.chat.service.mapper.impl.PublicChatMessageServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author AfeiB
 * @description 公共聊天的服务层
 * @createDate 2024/8/21 下午2:19
 */


@Service
public class LobbyChatService {
    @Autowired
    PublicChatMessageServiceImpl publicChatMessageService;

    @Autowired
    AccountMapper accountMapper;

    public Result getLobbyChatMessageList() {
        Page<PublicChatMessage> page = new Page<>(1, 20);
        List<PublicChatMessage> list = publicChatMessageService.list(page, new LambdaQueryWrapper<PublicChatMessage>().
                orderByDesc(PublicChatMessage::getMessageDate));
        Collections.reverse(list);
        return new Result(list, ResultConst.SUCCESS);
    }

    public Result saveLobbyChatMessage(PublicChatMessage publicChatMessage) {
        publicChatMessageService.save(publicChatMessage);
        return new Result(null, ResultConst.SUCCESS);
    }

    public Result getUserAvatarById(String id) {
        Account account = accountMapper.selectOne(new LambdaQueryWrapper<Account>().eq(Account::getId, id));
        if (account == null) {
            return new Result(null, ResultConst.SUCCESS);
        }
        return new Result(account.getAvatarBase64(), ResultConst.SUCCESS);
    }
}
