package com.ybk.service.impl;

import com.ybk.dto.match.MatchADTO;
import com.ybk.entity.MatchA;
import com.ybk.exception.MatchCreateException;
import com.ybk.mapper.MatchAMapper;
import com.ybk.service.MatchAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MatchAServiceImpl implements MatchAService {
    @Autowired
    private MatchAMapper matchAMapper;

    private void validateMatchADTO(MatchADTO matchADTO) {
        if (matchADTO.getTeamAId() == null || matchADTO.getTeamBId() == null) {
            throw new MatchCreateException("请选择两支球队");
        }
        if(matchADTO.getRoundCount()==null){
            throw new MatchCreateException("请输入比赛轮数");
        }
        if (matchADTO.getVenueNumber() == null) {
            throw new MatchCreateException("请选择比赛场地");
        }
        if (matchADTO.getWinScore() == null) {
            throw new MatchCreateException("请输入每局胜分线");
        }
        if (matchADTO.getBeginTime() == null) {
            throw new MatchCreateException("请选择比赛开始时间");
        }
        if (matchADTO.getBeginTime().isBefore(LocalDateTime.now())) {
            throw new MatchCreateException("比赛开始时间不能早于当前时间");
        }
        if (matchADTO.getMinTeamAgeSum() > matchADTO.getMaxTeamAgeSum()) {
            throw new MatchCreateException("球队年龄范围不正确");
        }
        if (matchADTO.getEventId() == null) {
            throw new MatchCreateException("请选择比赛所属活动");
        }
    }

    @Override
    public void save(MatchADTO matchADTO) {
        validateMatchADTO(matchADTO);

        // 设置默认值
        if (matchADTO.getMaxSubstitutePlayer() == null) {
            matchADTO.setMaxSubstitutePlayer(100);
        }
        if (matchADTO.getMaxTeamAgeSum() == null) {
            matchADTO.setMaxTeamAgeSum(1000);
        }
        if (matchADTO.getMinTeamAgeSum() == null) {
            matchADTO.setMinTeamAgeSum(0);
        }
        MatchA matchA = MatchA.builder()
                .eventId(matchADTO.getEventId())
                .teamAId(matchADTO.getTeamAId())
                .teamBId(matchADTO.getTeamBId())
                .modes(matchADTO.getModes())
                .venueNumber(matchADTO.getVenueNumber())
                .maxParticipationTimes(matchADTO.getMaxParticipationTimes())
                .minTeamAgeSum(matchADTO.getMinTeamAgeSum())
                .maxTeamAgeSum(matchADTO.getMaxTeamAgeSum())
                .maxSubstitutePlayer(matchADTO.getMaxSubstitutePlayer())
                .beginTime(matchADTO.getBeginTime())
                .roundCount(matchADTO.getRoundCount())
                .winScore(matchADTO.getWinScore())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        matchAMapper.insert(matchA);
    }

    @Override
    public void update(MatchADTO matchADTO) {
        MatchA matchA = new MatchA();
        matchA.setMatchAId(matchADTO.getMatchAId());
        if(matchADTO.getEventId()!=null) {
            matchA.setEventId(matchADTO.getEventId());
        }
        if(matchADTO.getTeamAId()!=null) {
            matchA.setTeamAId(matchADTO.getTeamAId());
        }
        if(matchADTO.getTeamBId()!=null) {
            matchA.setTeamBId(matchADTO.getTeamBId());
        }
        if(matchADTO.getVenueNumber()!=null) {
            matchA.setVenueNumber(matchADTO.getVenueNumber());
        }
        if(matchADTO.getModes()!=null && matchADTO.getModes().size()>0) {
            matchA.setModes(matchADTO.getModes());
        }
        if(matchADTO.getMaxParticipationTimes()!=null) {
            matchA.setMaxParticipationTimes(matchADTO.getMaxParticipationTimes());
        }
        if(matchADTO.getMinTeamAgeSum()!=null) {
            matchA.setMinTeamAgeSum(matchADTO.getMinTeamAgeSum());
        }
        if(matchADTO.getMaxTeamAgeSum()!=null) {
            matchA.setMaxTeamAgeSum(matchADTO.getMaxTeamAgeSum());
        }
        if(matchADTO.getMaxSubstitutePlayer()!=null) {
            matchA.setMaxSubstitutePlayer(matchADTO.getMaxSubstitutePlayer());
        }
        if(matchADTO.getBeginTime()!=null){
            matchA.setBeginTime(matchADTO.getBeginTime());
        }
        if(matchADTO.getRoundCount()!=null) {
            matchA.setRoundCount(matchADTO.getRoundCount());
        }
        if(matchADTO.getWinScore()!=null) {
            matchA.setWinScore(matchADTO.getWinScore());
        }
        matchA.setUpdateTime(LocalDateTime.now());
        matchAMapper.updateById(matchA);
    }

    @Override
    public void delete(Long matchId) {
        matchAMapper.deleteById(matchId);
    }
}
