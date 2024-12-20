package com.ybk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ybk.context.BaseContext;
import com.ybk.dto.match.MatchADTO;
import com.ybk.dto.match.AssignmentDTO;
import com.ybk.dto.match.RegistrationPageDTO;
import com.ybk.entity.*;
import com.ybk.exception.MatchCreateException;
import com.ybk.mapper.*;
import com.ybk.result.PageResult;
import com.ybk.service.MatchAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MatchAServiceImpl implements MatchAService {
    @Autowired
    private LeaderMapper leaderMapper;

    @Autowired
    private MatchAMapper matchAMapper;

    @Autowired
    private PlayerMapper playerMapper;

    @Autowired
    private RegistrationMapper registrationMapper;

    @Autowired
    private AssignmentMapper assignmentMapper;

    /**
     * 校验matchA编辑请求体
     * @param matchADTO
     */
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

    /**
     * 新建matchA
     * @param matchADTO
     */
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

    /**
     * 更新matchA基本信息
     * @param matchADTO
     */
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

    /**
     * 删除matchA
     * @param matchId
     */
    @Override
    public void delete(Long matchId) {
        matchAMapper.deleteById(matchId);
    }

    /**
     * 分页查询matchA报名队伍信息
     * @param registrationPageDTO
     * @return
     */
    @Override
    public PageResult pageTeam(RegistrationPageDTO registrationPageDTO) {
        Page<Registration> page = new Page<>(registrationPageDTO.getPage(), registrationPageDTO.getPageSize());
        QueryWrapper<Registration> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("event_id", registrationPageDTO.getEventId());
        page = registrationMapper.selectPage(page, queryWrapper);
        PageResult pageResult = new PageResult(page.getTotal(), page.getRecords());
        return pageResult;
    }

    /**
     * 设置matchA比赛选手
     * @param assignmentDTO
     */
    @Override
    public void setMatchAPlayer(AssignmentDTO assignmentDTO) {
        MatchA matchA = matchAMapper.selectById(assignmentDTO.getMatchId());
        if(matchA == null){
            throw new MatchCreateException("比赛不存在");
        }
        Player player = playerMapper.selectById(assignmentDTO.getPlayerId());
        if(player == null){
            throw new MatchCreateException("球员不存在");
        }
        Long leaderId = player.getLeaderId();
        if(leaderId.equals(BaseContext.getCurrentId())){
            throw new MatchCreateException("非此领队球员");
        }
        // 使用 Lambda 查询只获取 teamId
        Leader leader = leaderMapper.selectOne(
                new LambdaQueryWrapper<Leader>()
                        .select(Leader::getTeamId)
                        .eq(Leader::getLeaderId, leaderId)
        );
        Long teamId = leader.getTeamId();
        Assignment assignment = Assignment.builder()
                .playerId(assignmentDTO.getPlayerId())
                .matchType(assignmentDTO.getMatchType())
                .isSubstitute(assignmentDTO.getIsSubstitute())
                .typeOrder(assignmentDTO.getTypeOrder())
                .teamId(teamId)
                .matchId(assignmentDTO.getMatchId())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        assignmentMapper.insert(assignment);
    }

    /**
     * 修改MatchA参赛选手
     * @param assignmentDTO
     */
    public void updateMatchAPlayer(AssignmentDTO assignmentDTO) {
        Assignment assignment = assignmentMapper.selectById(assignmentDTO.getAssignmentId());
        if(assignment == null){
            throw new MatchCreateException("信息不存在");
        }
        MatchA matchA = matchAMapper.selectById(assignmentDTO.getMatchId());
        if(matchA == null){
            throw new MatchCreateException("比赛不存在");
        }
        Player player = playerMapper.selectById(assignmentDTO.getPlayerId());
        if(player == null){
            throw new MatchCreateException("球员不存在");
        }
        Long leaderId = player.getLeaderId();
        if(leaderId.equals(BaseContext.getCurrentId())){
            throw new MatchCreateException("非此领队球员");
        }
        // 使用 Lambda查询只获取 teamId
        Leader leader = leaderMapper.selectOne(
                new LambdaQueryWrapper<Leader>()
                        .select(Leader::getTeamId)
                        .eq(Leader::getLeaderId, leaderId)
        );

        assignment.setTeamId(leader.getTeamId());
        if(assignmentDTO.getPlayerId()!=null){
            assignment.setPlayerId(assignmentDTO.getPlayerId());
        }
        if(assignmentDTO.getMatchType()!=null){
            assignment.setMatchType(assignmentDTO.getMatchType());
        }
        if(assignmentDTO.getIsSubstitute()!=null){
            assignment.setIsSubstitute(assignmentDTO.getIsSubstitute());
        }
        if(assignmentDTO.getTypeOrder()!=null){
            assignment.setTypeOrder(assignmentDTO.getTypeOrder());
        }
        assignment.setUpdateTime(LocalDateTime.now());
        assignmentMapper.updateById(assignment);
    }

    /**
     * 删除MatchA参赛选手
     * @param assignmentId
     */
    @Override
    public void deleteMatchAPlayer(Long assignmentId) {
        Assignment assignment = assignmentMapper.selectById(assignmentId);
        if(assignment == null){
            throw new MatchCreateException("参赛选手不存在");
        }
        assignmentMapper.deleteById(assignmentId);
    }
}
