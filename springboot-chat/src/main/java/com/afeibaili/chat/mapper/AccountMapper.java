package com.afeibaili.chat.mapper;

import com.afeibaili.chat.pojo.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author AfeiB
 * @description 针对表【account】的数据库操作Mapper
 * @createDate 2024-08-14 21:51:19
 * @Entity com.afeibaili.chat.pojo.Account
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

}




