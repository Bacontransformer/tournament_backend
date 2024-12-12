package com.ybk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ybk.dto.AuthorizeQueryDTO;
import com.ybk.dto.LeaderDTO;
import com.ybk.dto.LeaderLoginDTO;
import com.ybk.entity.Leader;
import com.ybk.exception.AccountLockedException;
import com.ybk.exception.AccountNotFoundException;
import com.ybk.exception.PasswordErrorException;
import com.ybk.exception.UsernameAlreadyExistedException;
import com.ybk.mapper.LeaderMapper;
import com.ybk.result.PageResult;
import com.ybk.service.LeaderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class LeaderServiceImpl implements LeaderService {
    @Autowired
    private LeaderMapper leaderMapper;

    /**
     * 新增领队
     * @param leaderDTO
     */
    @Override
    public void save(LeaderDTO leaderDTO) {
        String username = leaderDTO.getUsername();
        QueryWrapper<Leader> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        if (leaderMapper.selectOne(wrapper) != null) {
            throw new UsernameAlreadyExistedException("用户名已存在");
        }
        Leader leader = new Leader();
        BeanUtils.copyProperties(leaderDTO,leader);
        leader.setIsPassed(false);
        leader.setCreateTime(LocalDateTime.now());
        leader.setUpdateTime(LocalDateTime.now());
        leader.setPassword(DigestUtils.md5DigestAsHex(leaderDTO.getPasswordFirst().getBytes()));
        leaderMapper.insert(leader);
    }

    /**
     * 领队登录
     * @param leaderLoginDTO
     * @return
     */
    @Override
    public Leader login(LeaderLoginDTO leaderLoginDTO) {
        String username = leaderLoginDTO.getUsername();
        String password = leaderLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        QueryWrapper<Leader> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Leader leader = leaderMapper.selectOne(wrapper);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (leader == null) {
            //账号不存在
            throw new AccountNotFoundException("用户名不存在");
        }

        //密码比对
        // 对前端传过来的明文密码进行处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(leader.getPassword())) {
            //密码错误
            throw new PasswordErrorException("密码错误");
        }

        if (leader.getIsPassed() == false) {
            //账号被锁定
            throw new AccountLockedException("账号未获批");
        }

        //3、返回实体对象
        return leader;
    }

    /**
     * 修改信息
     * @param leaderDTO
     */
    @Override
    public void udpate(LeaderDTO leaderDTO) {
        String username = leaderDTO.getUsername();
        QueryWrapper<Leader> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Leader leader = leaderMapper.selectOne(wrapper);
        leader.setUpdateTime(LocalDateTime.now());
        if(leaderDTO.getPasswordFirst()!=null&&!leaderDTO.getPasswordFirst().isEmpty()){
            leader.setPassword(leaderDTO.getPasswordFirst());
        }
        if(leaderDTO.getName()!=null&&!leaderDTO.getName().isEmpty()){
            leader.setName(leaderDTO.getName());
        }
        if(leaderDTO.getDepartment()!=null&&!leaderDTO.getDepartment().isEmpty()){
            leader.setDepartment(leaderDTO.getDepartment());
        }
        if(leaderDTO.getPhone()!=null&&!leaderDTO.getPhone().isEmpty()){
            leader.setPhone(leaderDTO.getPhone());
        }
        leaderMapper.updateById(leader);
    }

    /**
     * 分页查询
     * @param authorizeQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(AuthorizeQueryDTO authorizeQueryDTO) {
        // 创建分页对象
        Page<Leader> page = new Page<>(authorizeQueryDTO.getPage(), authorizeQueryDTO.getPageSize());

        // 构造查询条件
        QueryWrapper<Leader> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_passed", false);

        // 执行分页查询
        page = leaderMapper.selectPage(page, queryWrapper);

        // 封装结果返回
        PageResult pageResult = new PageResult();
        pageResult.setRecords(page.getRecords());
        pageResult.setTotal(page.getTotal()); // 修正为总记录数
        return pageResult;
    }

}
