package com.ybk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ybk.dto.PageQueryDTO;
import com.ybk.dto.role.LeaderDTO;
import com.ybk.dto.role.LeaderLoginDTO;
import com.ybk.entity.Leader;
import com.ybk.entity.Player;
import com.ybk.entity.Team;
import com.ybk.exception.AccountLockedException;
import com.ybk.exception.AccountNotFoundException;
import com.ybk.exception.PasswordErrorException;
import com.ybk.exception.UsernameAlreadyExistedException;
import com.ybk.mapper.LeaderMapper;
import com.ybk.mapper.PlayerMapper;
import com.ybk.mapper.TeamMapper;
import com.ybk.result.PageResult;
import com.ybk.service.LeaderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaderServiceImpl implements LeaderService {
    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private LeaderMapper leaderMapper;

    @Autowired
    private PlayerMapper playerMapper;

    /**
     * 新增领队
     *
     * @param leaderDTO
     */
    @Override
    @Transactional
    public void save(LeaderDTO leaderDTO) {
        String username = leaderDTO.getUsername();
        QueryWrapper<Leader> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        if (leaderMapper.selectOne(wrapper) != null) {
            throw new UsernameAlreadyExistedException("用户名已存在");
        }
        // 新建一个leader记录
        Leader leader = new Leader();
        BeanUtils.copyProperties(leaderDTO, leader);
        leader.setIsPassed(false);
        leader.setAge(leaderDTO.getAge());
        leader.setGender(leaderDTO.getGender());
        leader.setCreateTime(LocalDateTime.now());
        leader.setUpdateTime(LocalDateTime.now());
        leader.setPassword(DigestUtils.md5DigestAsHex(leaderDTO.getPasswordFirst().getBytes()));
        leaderMapper.insert(leader);
        // 新建一个team记录
        Integer leaderId = leader.getLeaderId();
        Team team = Team.builder()
                .leaderId(leaderId)
                .LeaderName(leader.getName())
                .name(leaderDTO.getTeamName())
                .introduction(leaderDTO.getIntroduction())
                .department(leaderDTO.getDepartment())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        teamMapper.insert(team);
        // 将队伍id回设Leader实体
        leader.setTeamId(team.getTeamId());
        leaderMapper.updateById(leader);

        // 插入第一条球员记录，领队自身也是球员
        Player player = Player.builder()
                .leaderId(leaderId)
                .name(leaderDTO.getName())
                .phone(leaderDTO.getPhone())
                .department(leaderDTO.getDepartment())
                .gender(leaderDTO.getGender())
                .teamId(team.getTeamId())
                .isActive(true)
                .joinTime(LocalDateTime.now())
                .role("leader")
                .age(leaderDTO.getAge())
                .build();
        playerMapper.insert(player);
    }


    /**
     * 领队登录
     *
     * @param leaderLoginDTO
     * @return
     */
    @Override
    public Leader login(LeaderLoginDTO leaderLoginDTO) {
        String username = leaderLoginDTO.getUsername();
        String password = leaderLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        QueryWrapper<Leader> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
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
     * 修改领队信息
     *
     * @param leaderDTO
     */
    @Override
    public void udpate(LeaderDTO leaderDTO) {
        String username = leaderDTO.getUsername();
        QueryWrapper<Leader> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        Leader leader = leaderMapper.selectOne(wrapper);
        leader.setUpdateTime(LocalDateTime.now());
        if (leaderDTO.getPasswordFirst() != null && !leaderDTO.getPasswordFirst().isEmpty()) {
            leader.setPassword(leaderDTO.getPasswordFirst());
        }
        if (leaderDTO.getName() != null && !leaderDTO.getName().isEmpty()) {
            leader.setName(leaderDTO.getName());
        }
        if (leaderDTO.getGender() != null && !leaderDTO.getGender().isEmpty()) {
            leader.setGender(leaderDTO.getGender());
        }
        if (leaderDTO.getAge() != null) {
            leader.setAge(leaderDTO.getAge());
        }
        if (leaderDTO.getDepartment() != null && !leaderDTO.getDepartment().isEmpty()) {
            leader.setDepartment(leaderDTO.getDepartment());
        }
        if (leaderDTO.getPhone() != null && !leaderDTO.getPhone().isEmpty()) {
            leader.setPhone(leaderDTO.getPhone());
        }
        leaderMapper.updateById(leader);
    }

    /**
     * 分页查询领队
     *
     * @param pageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(PageQueryDTO pageQueryDTO) {
        // 设置默认值
        pageQueryDTO.setPage(pageQueryDTO.getPage() == 0 ? 1 : pageQueryDTO.getPage());
        pageQueryDTO.setPageSize(pageQueryDTO.getPageSize() == 0 ? 10 : pageQueryDTO.getPageSize());
        // 创建分页对象
        Page<Leader> page = new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getPageSize());
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

    /**
     * 通过领队
     * @param leaderId
     */
    @Override
    @Transactional
    public void passLeader(Integer leaderId) {
        Leader leader = leaderMapper.selectById(leaderId);
        leader.setIsPassed(true);
        leaderMapper.updateById(leader);
    }
}
