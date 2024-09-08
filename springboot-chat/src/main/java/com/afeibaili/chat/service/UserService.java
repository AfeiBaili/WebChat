package com.afeibaili.chat.service;

import com.afeibaili.chat.mapper.AccountMapper;
import com.afeibaili.chat.pojo.Account;
import com.afeibaili.chat.pojo.Result;
import com.afeibaili.chat.consts.ResultConst;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author AfeiB
 * @description 用户逻辑类
 * @createDate 2024/8/16 下午10:40
 */

@Service
public class UserService {
    @Autowired
    AccountMapper accountMapper;

    /**
     * 保存新用户并判断是否存在
     */
    public Result registerUser(Account account) {
        LambdaQueryWrapper<Account> eq = new LambdaQueryWrapper<Account>().eq(Account::getName, account.getName());
        Account result = accountMapper.selectOne(eq);
        if (result != null) {
            return new Result(null, ResultConst.USERNAME_EXISTS);
        }
        accountMapper.insert(account);
        Account acc = accountMapper.selectOne(new LambdaQueryWrapper<Account>().eq(Account::getName, account.getName()));
        return new Result(acc, ResultConst.SUCCESS);
    }

    /**
     * 登录判断
     */
    public Result loginUser(Account account) {
        Account selectOne = accountMapper.selectOne(new LambdaQueryWrapper<Account>().eq(Account::getName, account.getName()));
        if (selectOne == null) {
            return new Result(null, ResultConst.USERNAME_ERROR);
        }
        if (!account.getPassword().equals(selectOne.getPassword())) {
            return new Result(null, ResultConst.PARAMETER_ERROR);
        }
        return new Result(selectOne, ResultConst.SUCCESS);
    }

    public Result updateUser(Account account) {
        int i = accountMapper.updateById(account);
        if (i > 0) {
            return new Result(account, ResultConst.SUCCESS);
        }
        return new Result(account, ResultConst.USER_UPDATE_ERROR);
    }
}