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
     * 保存比赛b信息
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

        Team teamA = teamMapper.selectById(matchBDTO.getTeamAId());
        String teamADepartment = teamA.getDepartment();

        Team teamB = teamMapper.selectById(matchBDTO.getTeamBId());
        String teamBDepartment = teamB.getDepartment();

        // 别漏了裁判
        MatchB matchB = MatchB.builder()
                .eventId(matchBDTO.getEventId())
                .sectionScore(matchBDTO.getSectionScore())
                .teamAId(matchBDTO.getTeamAId())
                .teamBId(matchBDTO.getTeamBId())
                .teamADepartment(teamADepartment)
                .teamBDepartment(teamBDepartment)
                .refereeId(matchBDTO.getRefereeId())
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
     * 更新比赛b信息
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
            Team teamA = teamMapper.selectById(matchBDTO.getTeamAId());
            String teamADepartment = teamA.getDepartment();
            matchB.setTeamADepartment(teamADepartment);
        }
        if(matchBDTO.getTeamBId()!=null){
            matchB.setTeamBId(matchBDTO.getTeamBId());
            Team teamB = teamMapper.selectById(matchBDTO.getTeamBId());
            String teamBDepartment = teamB.getDepartment();
            matchB.setTeamBDepartment(teamBDepartment);
        }
        if(matchBDTO.getRefereeId()!=null){
            matchB.setRefereeId(matchBDTO.getRefereeId());
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
     * 删除matchB信息
     * @param matchId
     */
    @Override
    public void delete(Long matchId) {
        matchBMapper.deleteById(matchId);
    }

    /**
     * 设置matchB比赛球员
     * @param matchBPlayerDTO
     */
    @Override
    public void setMatchBPlayer(MatchBPlayerDTO matchBPlayerDTO) {
        MatchB matchB = matchBMapper.selectById(matchBPlayerDTO.getMatchBId());
        if(matchB == null){
            throw new MatchCreateException("比赛不存在");
        }
        Long teamId = matchBPlayerDTO.getTeamId();
        if(teamId.equals(matchB.getTeamAId())){
            if(matchBPlayerDTO.getTeamAPlayerId1()!=null){
                matchB.setTeamAPlayerId1(matchBPlayerDTO.getTeamAPlayerId1());
            }
            if(matchBPlayerDTO.getTeamAPlayerId2()!=null){
                matchB.setTeamAPlayerId2(matchBPlayerDTO.getTeamAPlayerId2());
            }
            if(matchBPlayerDTO.getTeamAPlayerId3()!=null){
                matchB.setTeamAPlayerId3(matchBPlayerDTO.getTeamAPlayerId3());
            }
            if(matchBPlayerDTO.getTeamAPlayerId4()!=null){
                matchB.setTeamAPlayerId4(matchBPlayerDTO.getTeamAPlayerId4());
            }
            if(matchBPlayerDTO.getTeamASubstitutePlayerId1()!=null){
                matchB.setTeamASubstitutePlayerId1(matchBPlayerDTO.getTeamASubstitutePlayerId1());
            }
            if(matchBPlayerDTO.getTeamASubstitutePlayerId2()!=null){
                matchB.setTeamASubstitutePlayerId2(matchBPlayerDTO.getTeamASubstitutePlayerId2());
            }
            if(matchBPlayerDTO.getTeamASubstitutePlayerId3()!=null){
                matchB.setTeamASubstitutePlayerId3(matchBPlayerDTO.getTeamASubstitutePlayerId3());
            }
            if(matchBPlayerDTO.getTeamASubstitutePlayerId4()!=null){
                matchB.setTeamASubstitutePlayerId4(matchBPlayerDTO.getTeamASubstitutePlayerId4());
            }
        }else{
            if(matchBPlayerDTO.getTeamBPlayerId1()!=null){
                matchB.setTeamBPlayerId1(matchBPlayerDTO.getTeamBPlayerId1());
            }
            if(matchBPlayerDTO.getTeamBPlayerId2()!=null){
                matchB.setTeamBPlayerId2(matchBPlayerDTO.getTeamBPlayerId2());
            }
            if(matchBPlayerDTO.getTeamBPlayerId3()!=null){
                matchB.setTeamBPlayerId3(matchBPlayerDTO.getTeamBPlayerId3());
            }
            if(matchBPlayerDTO.getTeamBPlayerId4()!=null){
                matchB.setTeamBPlayerId4(matchBPlayerDTO.getTeamBPlayerId4());
            }
        }
        matchBMapper.updateById(matchB);
    }

    /**
     * 获取某个DOING的比赛b详细信息
     * @param matchBId
     * @return
     */
    @Override
    public MatchB getDoingMatchBDetail(Long matchBId) {
       return matchBMapper.selectById(matchBId);
    }

    /**
     * 获取未开始的比赛b信息
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
     * 获取进行中的比赛b信息
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
     * @param matchBId
     */
    @Override
    public void endMatchB(Long matchBId) {
        MatchB matchB = matchBMapper.selectById(matchBId);
        if(matchB == null){
            throw new MatchCreateException("比赛不存在");
        }
        if(matchB.getTeamAScore()==matchB.getSectionScore()*4){
            matchB.setWinnerTeamId(matchB.getTeamAId());
            matchB.setWinnerTeamDepartment(matchB.getTeamADepartment());
        } else if(matchB.getTeamBScore()==matchB.getSectionScore()*4){
            matchB.setWinnerTeamId(matchB.getTeamBId());
            matchB.setWinnerTeamDepartment(matchB.getTeamBDepartment());
        }
        matchB.setStatus(StatusConstant.END);
        matchBMapper.updateById(matchB);
    }

    /**
     * 宣布matchB开始
     * @param matchBId
     */
    @Override
    public void beginMatchB(Long matchBId) {
        MatchB matchB = matchBMapper.selectById(matchBId);
        if(matchB == null){
            throw new MatchCreateException("比赛不存在");
        }
        if(matchB.getStatus().equals(StatusConstant.DOING)){
            throw new MatchCreateException("比赛已开始");
        }
        matchB.setStatus(StatusConstant.DOING);
        matchB.setTeamAScore(0);
        matchB.setTeamBScore(0);
        matchBMapper.updateById(matchB);
    }

    /**
     * 裁判查看判决的matchB
     * @param pageQueryDTO
     * @return
     */
    @Override
    public PageResult getRefereeMatchB(PageQueryDTO pageQueryDTO) {
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
