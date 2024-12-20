package com.ybk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ybk.context.BaseContext;
import com.ybk.dto.match.RegistrationDTO;
import com.ybk.entity.Leader;
import com.ybk.entity.Registration;
import com.ybk.mapper.LeaderMapper;
import com.ybk.mapper.RegistrationMapper;
import com.ybk.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private RegistrationMapper registrationMapper;

    @Autowired
    private LeaderMapper leaderMapper;

    @Override
    public void register(RegistrationDTO registrationDTO) {
        Long leaderId = BaseContext.getCurrentId();

        // 使用 Lambda 查询只获取 teamId
        Leader leader = leaderMapper.selectOne(
                new LambdaQueryWrapper<Leader>()
                        .select(Leader::getTeamId)
                        .eq(Leader::getLeaderId, leaderId)
        );

        Long teamId = leader.getTeamId();

        Registration registration = Registration.builder()
                .eventId(registrationDTO.getEventId())
                .teamId(teamId)
                .teamName(registrationDTO.getTeamName())
                .build();

        registrationMapper.insert(registration);
    }

}
