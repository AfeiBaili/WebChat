package com.afeibaili.chat.mapper;

import com.afeibaili.chat.pojo.PublicChatMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author AfeiB
 * @description 针对于公共聊天表的mapper接口
 * @createDate 2024/8/21 下午1:06
 */
@Mapper
public interface PublicChatMessageMapper extends BaseMapper<PublicChatMessage> {
}
