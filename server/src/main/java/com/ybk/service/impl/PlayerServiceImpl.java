package com.ybk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ybk.context.BaseContext;
import com.ybk.dto.PageQueryDTO;
import com.ybk.dto.role.PlayerDTO;
import com.ybk.entity.Player;
import com.ybk.entity.Team;
import com.ybk.mapper.PlayerMapper;
import com.ybk.mapper.TeamMapper;
import com.ybk.result.PageResult;
import com.ybk.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerMapper playerMapper;

    @Autowired
    private TeamMapper teamMapper;

    /**
     * 添加队员
     * @param playerDTO
     */
    @Override
    public void save(PlayerDTO playerDTO) {
        Player player = new Player();
        Long leaderId = BaseContext.getCurrentId();
        // 查出对应的team_id
        QueryWrapper<Team> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("leader_id", leaderId);
        Team team = teamMapper.selectOne(queryWrapper);
        player.setTeamId(team.getTeamId());
        player.setLeaderId(leaderId);
        player.setName(playerDTO.getName());
        player.setAge(playerDTO.getAge());
        player.setDepartment(playerDTO.getDepartment());
        player.setGender(playerDTO.getGender());
        player.setPhone(playerDTO.getPhone());
        player.setRole(playerDTO.getRole());
        player.setIsActive(playerDTO.getIsActive());
        player.setJoinTime(LocalDateTime.now());
        playerMapper.insert(player);
    }

    /**
     * 更新队员信息
     * @param playerDTO
     */
    @Override
    public void update(PlayerDTO playerDTO) {
        Player player = new Player();
        // 判断每个字段是否为空，不为空才进行更新
        if (playerDTO.getName() != null && !playerDTO.getName().isEmpty()) {
            player.setName(playerDTO.getName());
        }
        if (playerDTO.getDepartment() != null && !playerDTO.getDepartment().isEmpty()) {
            player.setDepartment(playerDTO.getDepartment());
        }
        if (playerDTO.getGender() != null && !playerDTO.getGender().isEmpty()) {
            player.setGender(playerDTO.getGender());
        }
        if (playerDTO.getAge() != null) {
            player.setAge(playerDTO.getAge());
        }
        if (playerDTO.getPhone() != null && !playerDTO.getPhone().isEmpty()) {
            player.setPhone(playerDTO.getPhone());
        }
        if (playerDTO.getRole() != null && !playerDTO.getRole().isEmpty()) {
            player.setRole(playerDTO.getRole());
        }
        if (playerDTO.getIsActive() != null) {
            player.setIsActive(playerDTO.getIsActive());
        }
        playerMapper.updateById(player);
    }

    /**
     * 删除队员
     * @param ids
     */
    @Override
    public void delete(List<Long> ids) {
        playerMapper.deleteBatchIds(ids);
    }

    /**
     * 分页查询当前leaderId下的所有队员
     * @param pageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(PageQueryDTO pageQueryDTO) {
        // 查询当前leaderId下的所有队员
        Page<Player> page = new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getPageSize());
        QueryWrapper<Player> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("leader_id", BaseContext.getCurrentId());
        page = playerMapper.selectPage(page, queryWrapper);
        PageResult pageResult = new PageResult(page.getTotal(), page.getRecords());
        return pageResult;
    }
}
