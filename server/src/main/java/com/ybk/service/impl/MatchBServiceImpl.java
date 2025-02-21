package com.ybk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ybk.constant.StatusConstant;
import com.ybk.context.BaseContext;
import com.ybk.dto.PageQueryDTO;
import com.ybk.dto.match.*;
import com.ybk.entity.*;
import com.ybk.exception.MatchCreateException;
import com.ybk.mapper.*;
import com.ybk.result.PageResult;
import com.ybk.service.MatchBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

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

    @Autowired
    private TeamMapper teamMapper;

    /**
     * 验证比赛建立请求
     * @param matchBDTO
     */
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

    /**
     * 保存比赛信息
     * @param matchBDTO
     */
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

    /**
     * 更新比赛信息
     * @param matchBDTO
     */
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

    /**
     * 删除比赛信息
     * @param matchId
     */
    @Override
    public void delete(Long matchId) {
        matchBMapper.deleteById(matchId);
    }

    /**
     * 设置比赛球员
     * @param assignmentDTO
     */
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

    /**
     * 更新比赛球员
     * @param assignmentDTO
     */
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

    /**
     * 删除比赛球员
     * @param assignmentId
     */
    @Override
    public void deleteMatchBPlayer(Long assignmentId) {
        assignmentMapper.deleteById(assignmentId);
    }

    @Override
    public MatchB getDoingMatchBDetail(Long matchBId) {
        return null;
    }

    /**
     * 获取未开始的比赛信息
     * @param pageQueryDTO
     * @return
     */
    @Override
    public PageResult getUnStartMatchBBrief(PageQueryDTO pageQueryDTO) {
        Page<MatchB> page = new Page<>(pageQueryDTO.getPage(),pageQueryDTO.getPageSize());
        page = matchBMapper.selectPage(page,
                new LambdaQueryWrapper<MatchB>()
                        .eq(MatchB::getStatus, StatusConstant.UNSTART)
                        .ge(MatchB::getBeginTime, LocalDateTime.now())
                        .orderByAsc(MatchB::getBeginTime)
        );
        return new PageResult(page.getTotal(),page.getRecords());
    }

    /**
     * 获取进行中的比赛信息
     * @param pageQueryDTO
     * @return
     */
    @Override
    public PageResult getDoingMatchBBrief(PageQueryDTO pageQueryDTO) {
        Page<MatchB> page = new Page<>(pageQueryDTO.getPage(),pageQueryDTO.getPageSize());
        page = matchBMapper.selectPage(page,
                new LambdaQueryWrapper<MatchB>()
                        .eq(MatchB::getStatus, StatusConstant.DOING)
                        .ge(MatchB::getBeginTime, LocalDateTime.now())
                        .orderByAsc(MatchB::getBeginTime)
        );
        return new PageResult(page.getTotal(),page.getRecords());
    }

    /**
     * 判决matchB结束
     * @param endMatchDTO
     */
    @Override
    public void endMatchB(EndMatchDTO endMatchDTO) {
        Long matchBId = endMatchDTO.getMatchId();
        Long teamId = endMatchDTO.getTeamId();
        MatchB matchB = matchBMapper.selectById(matchBId);
        matchB.setWinnerTeamId(teamId);
        Team team = teamMapper.selectById(teamId);
        matchB.setWinnerTeamDepartment(team.getDepartment());
        matchB.setStatus(StatusConstant.END);
        matchBMapper.updateById(matchB);
    }

    /**
     * 宣布matchB开始
     * @param beginMatchDTO
     */
    @Override
    public void beginMatchB(BeginMatchDTO beginMatchDTO) {
        MatchB matchB = matchBMapper.selectById(beginMatchDTO.getMatchId());
        if(matchB == null){
            throw new MatchCreateException("比赛不存在");
        }
        if(matchB.getStatus().equals(StatusConstant.DOING)){
            throw new MatchCreateException("比赛已开始");
        }
        matchB.setStatus(StatusConstant.DOING);
        matchB.setScoreList(new ArrayList<>());
        matchB.setTeamAScore(0);
        matchB.setTeamBScore(0);
        matchBMapper.updateById(matchB);
    }

    /**
     *
     * @param pageQueryDTO
     * @return
     */
    @Override
    public PageResult getRefereeMatchBBrief(PageQueryDTO pageQueryDTO) {
        Page<MatchB> page = new Page<>(pageQueryDTO.getPage(),pageQueryDTO.getPageSize());
        page = matchBMapper.selectPage(page,
                new LambdaQueryWrapper<MatchB>()
                        .and(i -> i.eq(MatchB::getStatus, StatusConstant.UNSTART)
                                .or()
                                .eq(MatchB::getStatus, StatusConstant.DOING))
                        .eq(MatchB::getRefereeId, BaseContext.getCurrentId())
                        .orderByAsc(MatchB::getBeginTime)
        );
        return new PageResult(page.getTotal(),page.getRecords());
    }

    /**
     * 对matchB某一回合进行判分
     * @param scoreDTO
     */
    @Override
    public void matchBScore(MatchBScoreDTO scoreDTO) {
        Integer plusOrMinus = scoreDTO.getPlusOrMinus();
        Long teamId = scoreDTO.getTeamId();
        Long matchBId = scoreDTO.getMatchBId();
        MatchB matchB = matchBMapper.selectById(matchBId);
        if(matchB == null){
            throw new MatchCreateException("比赛不存在");
        }
        Integer sectionScore = matchB.getSectionScore();
        Integer currentSection = matchB.getCurrentSection();
        Integer teamAScore = matchB.getTeamAScore();
        Integer teamBScore = matchB.getTeamBScore();
        Long teamAId = matchB.getTeamAId();
        Long teamBId = matchB.getTeamBId();
        if(teamId.equals(teamAId)){
            Integer teamAScoreTemp = teamAScore + plusOrMinus;
            if( currentSection.equals(1)&&teamAScoreTemp.equals(sectionScore)) {
                matchB.setCurrentSection(2);
                matchB.setCurrentTeamAPlayerId1(matchB.getTeamAPlayerId2());
                matchB.setCurrentTeamAPlayerId2(matchB.getTeamAPlayerId3());
                matchB.setCurrentTeamBPlayerId1(matchB.getTeamBPlayerId2());
                matchB.setCurrentTeamBPlayerId2(matchB.getTeamBPlayerId3());
            }else if( currentSection.equals(2)&&teamAScoreTemp.equals(sectionScore*2)){
                matchB.setCurrentSection(3);
                matchB.setCurrentTeamAPlayerId1(matchB.getTeamAPlayerId3());
                matchB.setCurrentTeamAPlayerId2(matchB.getTeamAPlayerId4());
                matchB.setCurrentTeamBPlayerId1(matchB.getTeamBPlayerId3());
                matchB.setCurrentTeamBPlayerId2(matchB.getTeamBPlayerId4());
            }else if( currentSection.equals(3)&&teamAScoreTemp.equals(sectionScore*3)){
                matchB.setCurrentSection(4);
                matchB.setCurrentTeamAPlayerId1(matchB.getTeamAPlayerId4());
                matchB.setCurrentTeamAPlayerId2(matchB.getTeamAPlayerId1());
                matchB.setCurrentTeamBPlayerId1(matchB.getTeamBPlayerId4());
                matchB.setCurrentTeamBPlayerId2(matchB.getTeamBPlayerId1());
            }else if( currentSection.equals(4)&&teamAScoreTemp.equals(sectionScore*4)){
                matchB.setWinnerTeamId(teamAId);
                matchB.setWinnerTeamDepartment(matchB.getTeamADepartment());
                matchB.setStatus(StatusConstant.END);
            }
        }else {
            Integer teamBScoreTemp = teamBScore + plusOrMinus;
            if( currentSection.equals(1)&&teamBScoreTemp.equals(sectionScore)){
                matchB.setCurrentSection(2);
                matchB.setCurrentTeamAPlayerId1(matchB.getTeamAPlayerId2());
                matchB.setCurrentTeamAPlayerId2(matchB.getTeamAPlayerId3());
                matchB.setCurrentTeamBPlayerId1(matchB.getTeamBPlayerId2());
                matchB.setCurrentTeamBPlayerId2(matchB.getTeamBPlayerId3());
            }
            if( currentSection.equals(2)&&teamBScoreTemp.equals(sectionScore*2)){
                matchB.setCurrentSection(3);
                matchB.setCurrentTeamAPlayerId1(matchB.getTeamAPlayerId3());
                matchB.setCurrentTeamAPlayerId2(matchB.getTeamAPlayerId4());
                matchB.setCurrentTeamBPlayerId1(matchB.getTeamBPlayerId3());
                matchB.setCurrentTeamBPlayerId2(matchB.getTeamBPlayerId4());
            }
            if( currentSection.equals(3)&&teamBScoreTemp.equals(sectionScore*3)){
                matchB.setCurrentSection(4);
                matchB.setCurrentTeamAPlayerId1(matchB.getTeamAPlayerId4());
                matchB.setCurrentTeamAPlayerId2(matchB.getTeamAPlayerId1());
                matchB.setCurrentTeamBPlayerId1(matchB.getTeamBPlayerId4());
                matchB.setCurrentTeamBPlayerId2(matchB.getTeamBPlayerId1());
            }
            if( currentSection.equals(4)&&teamBScoreTemp.equals(sectionScore*4)){
                matchB.setWinnerTeamId(teamBId);
                matchB.setWinnerTeamDepartment(matchB.getTeamBDepartment());
                matchB.setStatus(StatusConstant.END);
            }
        }
    }
}
