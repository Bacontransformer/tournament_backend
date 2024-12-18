package com.ybk.service.impl;

import com.ybk.dto.match.MatchBDTO;
import com.ybk.entity.MatchB;
import com.ybk.exception.MatchCreateException;
import com.ybk.mapper.MatchBMapper;
import com.ybk.service.MatchBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MatchBServiceImpl implements MatchBService {
    @Autowired
    private MatchBMapper matchBMapper;

    private void validateMatchBDTO(MatchBDTO matchBDTO) {
        if (matchBDTO.getTeamAId() == null || matchBDTO.getTeamBId() == null) {
            throw new MatchCreateException("请选择两支球队");
        }
        if (matchBDTO.getSectionScore() == null) {
            throw new MatchCreateException("请选择比赛分数");
        }
        if (matchBDTO.getVenueNumber() == null) {
            throw new MatchCreateException("请选择比赛场地");
        }
        if (matchBDTO.getBeginTime() == null) {
            throw new MatchCreateException("请选择比赛开始时间");
        }
        if (matchBDTO.getBeginTime().isBefore(LocalDateTime.now())) {
            throw new MatchCreateException("比赛开始时间不能早于当前时间");
        }
        if (matchBDTO.getMinTeamAgeSum() > matchBDTO.getMaxTeamAgeSum()) {
            throw new MatchCreateException("球队年龄范围不正确");
        }
        if (matchBDTO.getEventId() == null) {
            throw new MatchCreateException("请选择比赛所属活动");
        }
    }
    @Override
    public void save(MatchBDTO matchBDTO) {
        validateMatchBDTO(matchBDTO);

        // 设置默认值
        if (matchBDTO.getMaxSubstitutePlayer() == null) {
            matchBDTO.setMaxSubstitutePlayer(100);
        }
        if (matchBDTO.getMaxTeamAgeSum() == null) {
            matchBDTO.setMaxTeamAgeSum(1000);
        }
        if (matchBDTO.getMinTeamAgeSum() == null) {
            matchBDTO.setMinTeamAgeSum(0);
        }

        MatchB matchB = MatchB.builder()
                .eventId(matchBDTO.getEventId())
                .sectionScore(matchBDTO.getSectionScore())
                .teamAId(matchBDTO.getTeamAId())
                .teamBId(matchBDTO.getTeamBId())
                .venueNumber(matchBDTO.getVenueNumber())
                .maxSubstitutePlayer(matchBDTO.getMaxSubstitutePlayer())
                .minTeamAgeSum(matchBDTO.getMinTeamAgeSum())
                .maxTeamAgeSum(matchBDTO.getMaxTeamAgeSum())
                .beginTime(matchBDTO.getBeginTime())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        matchBMapper.insert(matchB);
    }

    @Override
    public void update(MatchBDTO matchBDTO) {
        MatchB matchB = new MatchB();
        matchB.setMatchBId(matchBDTO.getMatchBId());
        if(matchBDTO.getEventId()!=null){
            matchB.setEventId(matchBDTO.getEventId());
        }
        if(matchBDTO.getSectionScore()!=null){
            matchB.setSectionScore(matchBDTO.getSectionScore());
        }
        if(matchBDTO.getTeamAId()!=null){
            matchB.setTeamAId(matchBDTO.getTeamAId());
        }
        if(matchBDTO.getTeamBId()!=null){
            matchB.setTeamBId(matchBDTO.getTeamBId());
        }
        if(matchBDTO.getVenueNumber()!=null){
            matchB.setVenueNumber(matchBDTO.getVenueNumber());
        }
        if(matchBDTO.getMaxSubstitutePlayer()!=null){
            matchB.setMaxSubstitutePlayer(matchBDTO.getMaxSubstitutePlayer());
        }
        if(matchBDTO.getMinTeamAgeSum()!=null){
            matchB.setMinTeamAgeSum(matchBDTO.getMinTeamAgeSum());
        }
        if(matchBDTO.getMaxTeamAgeSum()!=null){
            matchB.setMaxTeamAgeSum(matchBDTO.getMaxTeamAgeSum());
        }
        matchB.setBeginTime(matchBDTO.getBeginTime());
        matchBMapper.updateById(matchB);
    }

    @Override
    public void delete(Long matchId) {
        matchBMapper.deleteById(matchId);
    }
}
