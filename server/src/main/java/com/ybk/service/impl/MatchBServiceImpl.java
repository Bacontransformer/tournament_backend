package com.ybk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ybk.context.BaseContext;
import com.ybk.dto.match.AssignmentDTO;
import com.ybk.dto.match.MatchBDTO;
import com.ybk.entity.Assignment;
import com.ybk.entity.Leader;
import com.ybk.entity.MatchB;
import com.ybk.entity.Player;
import com.ybk.exception.MatchCreateException;
import com.ybk.mapper.AssignmentMapper;
import com.ybk.mapper.LeaderMapper;
import com.ybk.mapper.MatchBMapper;
import com.ybk.mapper.PlayerMapper;
import com.ybk.service.MatchBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MatchBServiceImpl implements MatchBService {
    @Autowired
    private LeaderMapper leaderMapper;

    @Autowired
    private MatchBMapper matchBMapper;

    @Autowired
    private PlayerMapper playerMapper;

    @Autowired
    private AssignmentMapper assignmentMapper;

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

    @Override
    public void setMatchBPlayer(AssignmentDTO assignmentDTO) {
        MatchB matchB = matchBMapper.selectById(assignmentDTO.getMatchId());
        if(matchB == null){
            throw new MatchCreateException("比赛不存在");
        }
        Player player = playerMapper.selectById(assignmentDTO.getPlayerId());
        if(player == null){
            throw new MatchCreateException("球员不存在");
        }
        Long leaderId = player.getLeaderId();
        if(!leaderId.equals(BaseContext.getCurrentId())){
            throw new MatchCreateException("非此领队球员");
        }
        Long teamId = leaderMapper.selectOne(
                new LambdaQueryWrapper<Leader>()
                        .select(Leader::getTeamId)
                        .eq(Leader::getLeaderId, leaderId)
        ).getTeamId();
        Assignment assignment = Assignment.builder()
                .playerId(assignmentDTO.getPlayerId())
                .teamId(teamId)
                .isSubstitute(assignmentDTO.getIsSubstitute())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .matchId(assignmentDTO.getMatchId())
                .typeOrder(assignmentDTO.getTypeOrder())
                .build();
        assignmentMapper.insert(assignment);
    }

    @Override
    public void updateMatchBPlayer(AssignmentDTO assignmentDTO) {
        Assignment assignment = assignmentMapper.selectById(assignmentDTO.getAssignmentId());
        if(assignment == null){
            throw new MatchCreateException("信息不存在");
        }
        MatchB matchB = matchBMapper.selectById(assignmentDTO.getMatchId());
        if(matchB == null){
            throw new MatchCreateException("比赛不存在");
        }
        Player player = playerMapper.selectById(assignmentDTO.getPlayerId());
        if(player == null){
            throw new MatchCreateException("球员不存在");
        }
        Long leaderId = player.getLeaderId();
        if(!leaderId.equals(BaseContext.getCurrentId())){
            throw new MatchCreateException("非此领队球员");
        }
        // 使用 Lambda查询只获取 teamId
        Leader leader = leaderMapper.selectOne(
                new LambdaQueryWrapper<Leader>()
                        .select(Leader::getTeamId)
                        .eq(Leader::getLeaderId, leaderId)
        );
        assignment.setTeamId(leader.getTeamId());
        if(assignmentDTO.getPlayerId() != null){
            assignment.setPlayerId(assignmentDTO.getPlayerId());
        }
        if(assignmentDTO.getIsSubstitute() != null){
            assignment.setIsSubstitute(assignmentDTO.getIsSubstitute());
        }
        if(assignmentDTO.getTypeOrder() != null){
            assignment.setTypeOrder(assignmentDTO.getTypeOrder());
        }
        assignment.setUpdateTime(LocalDateTime.now());
        assignmentMapper.updateById(assignment);
    }

    @Override
    public void deleteMatchBPlayer(Long assignmentId) {
        assignmentMapper.deleteById(assignmentId);
    }
}
