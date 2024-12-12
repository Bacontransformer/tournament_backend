package com.ybk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ybk.dto.AuthorizeQueryDTO;
import com.ybk.dto.RefereeLoginDTO;
import com.ybk.entity.Leader;
import com.ybk.entity.Referee;
import com.ybk.exception.AccountLockedException;
import com.ybk.exception.AccountNotFoundException;
import com.ybk.exception.PasswordErrorException;
import com.ybk.mapper.RefereeMapper;
import com.ybk.result.PageResult;
import com.ybk.service.RefereeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.sql.Ref;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RefereeServiceImpl implements RefereeService {

    @Autowired
    private RefereeMapper refereeMapper;

    /**
     * 裁判登录
     * @param refereeLoginDTO
     * @return
     */
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

    /**
     * 分页查询
     * @param authorizeQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(AuthorizeQueryDTO authorizeQueryDTO) {
        // 创建分页对象
        Page<Referee> page = new Page<>(authorizeQueryDTO.getPage(), authorizeQueryDTO.getPageSize());

        // 构造查询条件
        QueryWrapper<Referee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_passed", false);

        // 执行分页查询
        page = refereeMapper.selectPage(page, queryWrapper);

        // 封装结果返回
        PageResult pageResult = new PageResult();
        pageResult.setRecords(page.getRecords());
        pageResult.setTotal(page.getTotal()); // 修正为总记录数
        return pageResult;
    }

    @Override
    @Transactional
    public void pass(List<Integer> ids) {
        // 构造需要更新的 Referee 实体
        Referee updatedReferee = new Referee();
        updatedReferee.setIsPassed(true);
        updatedReferee.setUpdateTime(LocalDateTime.now());

        // 更新条件：通过 ids 筛选
        UpdateWrapper<Referee> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("refereeId", ids);

        // 调用 Mapper 批量更新
        refereeMapper.update(updatedReferee, updateWrapper);
    }
}
