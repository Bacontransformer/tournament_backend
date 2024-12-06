package com.ybk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ybk.dto.RefereeLoginDTO;
import com.ybk.entity.Referee;
import com.ybk.exception.AccountLockedException;
import com.ybk.exception.AccountNotFoundException;
import com.ybk.exception.PasswordErrorException;
import com.ybk.mapper.RefereeMapper;
import com.ybk.service.RefereeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class RefereeServiceImpl implements RefereeService {

    @Autowired
    private RefereeMapper refereeMapper;

    @Override
    public Referee login(RefereeLoginDTO refereeLoginDTO) {
        String username = refereeLoginDTO.getUsername();
        String password = refereeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        QueryWrapper<Referee> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Referee referee = refereeMapper.selectOne(wrapper);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (referee == null) {
            //账号不存在
            throw new AccountNotFoundException("用户名不存在");
        }

        //密码比对
        // 对前端传过来的明文密码进行处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(referee.getPassword())) {
            //密码错误
            throw new PasswordErrorException("密码错误");
        }

        if (referee.getIsPassed() == false) {
            //账号被锁定
            throw new AccountLockedException("账号未获批");
        }

        //3、返回实体对象
        return referee;
    }
}
