package com.ybk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ybk.constant.StatusConstant;
import com.ybk.context.BaseContext;
import com.ybk.dto.PageQueryDTO;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Service
public class MatchAServiceImpl implements MatchAService {
    @Autowired
    private LeaderMapper leaderMapper;

    @Autowired
    private MatchAMapper matchAMapper;

    @Autowired
    private PlayerMapper playerMapper;

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    private MatchSetMapper matchSetMapper;
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


    /**
     * 获取正在进行的某一MatchA详细比赛信息
     * @param matchAId
     * @return
     */
    @Override
    public MatchA getDoingMatchADetail(Long matchAId) {
        MatchA matchA = matchAMapper.selectById(matchAId);
        if(matchA == null){
            throw new MatchCreateException("比赛不存在");
        }
        if(!matchA.getStatus().equals(StatusConstant.DOING)){
            throw new MatchCreateException("比赛未开始");
        }
        LinkedList<String> modes = matchA.getModes();
        Integer roundCount = matchA.getRoundCount();
        // 从数据库中查询出各个mode的各个count的具体信息
        for (String mode : modes ){
            Map<Integer, MatchSet> matchSetMap = new HashMap<>();
            for (int i = 0; i < roundCount; i++) {
                MatchSet matchSet = matchSetMapper.selectOne(
                        new LambdaQueryWrapper<MatchSet>()
                                .eq(MatchSet::getMatchAId, matchA.getMatchAId())
                                .eq(MatchSet::getMode, mode)
                                .eq(MatchSet::getRoundCount, i)
                );
                matchSetMap.put(i,matchSet);
            }
            matchA.getMatchSets().put(mode,matchSetMap);
        }
        return matchA;
    }

    /**
     * 获取所有未开始的比赛简略信息
     * @param pageQueryDTO
     * @return
     */
    @Override
    public PageResult getUnStartMatchABrief(PageQueryDTO pageQueryDTO) {
        Page<MatchA> page = new Page<>(pageQueryDTO.getPage(),pageQueryDTO.getPageSize());
        page = matchAMapper.selectPage(page,
                new LambdaQueryWrapper<MatchA>()
                        .eq(MatchA::getStatus, StatusConstant.UNSTART)
                        .ge(MatchA::getBeginTime, LocalDateTime.now())
                        .orderByAsc(MatchA::getBeginTime)
        );
        return new PageResult(page.getTotal(),page.getRecords());
    }

    /**
     * 获取所有进行中的比赛简略信息
     * @param pageQueryDTO
     * @return
     */
    @Override
    public PageResult getDoingMatchABrief(PageQueryDTO pageQueryDTO) {
        Page<MatchA> page = new Page<>(pageQueryDTO.getPage(),pageQueryDTO.getPageSize());
        page = matchAMapper.selectPage(page,
                new LambdaQueryWrapper<MatchA>()
                        .eq(MatchA::getStatus, StatusConstant.DOING)
                        .ge(MatchA::getBeginTime, LocalDateTime.now())
                        .orderByAsc(MatchA::getBeginTime)
        );
        return new PageResult(page.getTotal(),page.getRecords());
    }
}
