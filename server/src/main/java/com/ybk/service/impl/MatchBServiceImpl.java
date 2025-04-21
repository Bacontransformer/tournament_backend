package com.ybk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ybk.constant.StatusConstant;
import com.ybk.context.BaseContext;
import com.ybk.dto.PageQueryDTO;
import com.ybk.dto.match.*;
import com.ybk.entity.*;
import com.ybk.exception.BaseException;
import com.ybk.exception.MatchCreateException;
import com.ybk.mapper.*;
import com.ybk.result.PageResult;
import com.ybk.service.MatchBService;
import com.ybk.vo.LeaderMatchBVO;
import com.ybk.vo.LeaderMatchModeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchBServiceImpl implements MatchBService {
    @Autowired
    private LeaderMapper leaderMapper;

    @Autowired
    private MatchBMapper matchBMapper;

    @Autowired
    private PlayerMapper playerMapper;

    @Autowired
    private RefereeMapper refereeMapper;


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
                .substituteRefereeId(matchBDTO.getSubstituteRefereeId())
                .refereeName((refereeMapper.selectById(matchBDTO.getRefereeId())).getName())
                .venueNumber(matchBDTO.getVenueNumber())
                .maxSubstitutePlayer(matchBDTO.getMaxSubstitutePlayer())
                .minTeamAgeSum(matchBDTO.getMinTeamAgeSum())
                .maxTeamAgeSum(matchBDTO.getMaxTeamAgeSum())
                .beginTime(matchBDTO.getBeginTime())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        if(matchBDTO.getSubstituteRefereeId()!=null){
            matchB.setSubstituteRefereeName((refereeMapper.selectById(matchBDTO.getSubstituteRefereeId())).getName());
        }
        matchBMapper.insert(matchB);
    }

    /**
     * 更新比赛b信息
     * @param matchBDTO
     */
    @Override
    public void update(MatchBDTO matchBDTO) {
        MatchB matchB = matchBMapper.selectById(matchBDTO.getMatchBId());
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
            matchB.setRefereeName(refereeMapper.selectById(matchBDTO.getRefereeId()).getName());
        }
        if(matchBDTO.getSubstituteRefereeId()!=null){
            matchB.setSubstituteRefereeId(matchBDTO.getSubstituteRefereeId());
            matchB.setSubstituteRefereeName(refereeMapper.selectById(matchBDTO.getSubstituteRefereeId()).getName());
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
        if(matchBDTO.getBeginTime()!=null){
            matchB.setBeginTime(matchBDTO.getBeginTime());
        }
        matchBMapper.updateById(matchB);
    }

    /**
     * 删除matchB信息
     * @param matchId
     */
    @Override
    public void delete(Integer matchId) {
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
        Integer leaderId = BaseContext.getCurrentId();
        Team team = teamMapper.selectOne(new QueryWrapper<Team>().eq("leader_id", leaderId));
        Integer teamId = team.getTeamId();
        if(teamId.equals(matchB.getTeamAId())){
            if(matchBPlayerDTO.getTeamAPlayerId1()!=null){
                matchB.setTeamAPlayerId1(matchBPlayerDTO.getTeamAPlayerId1());
                matchB.setTeamAPlayerName1(playerMapper.selectById(matchBPlayerDTO.getTeamAPlayerId1()).getName());
            }
            if(matchBPlayerDTO.getTeamAPlayerId2()!=null){
                matchB.setTeamAPlayerId2(matchBPlayerDTO.getTeamAPlayerId2());
                matchB.setTeamAPlayerName2(playerMapper.selectById(matchBPlayerDTO.getTeamAPlayerId2()).getName());
            }
            if(matchBPlayerDTO.getTeamAPlayerId3()!=null){
                matchB.setTeamAPlayerId3(matchBPlayerDTO.getTeamAPlayerId3());
                matchB.setTeamAPlayerName3(playerMapper.selectById(matchBPlayerDTO.getTeamAPlayerId3()).getName());
            }
            if(matchBPlayerDTO.getTeamAPlayerId4()!=null){
                matchB.setTeamAPlayerId4(matchBPlayerDTO.getTeamAPlayerId4());
                matchB.setTeamAPlayerName4(playerMapper.selectById(matchBPlayerDTO.getTeamAPlayerId4()).getName());
            }
            if(matchBPlayerDTO.getTeamASubstitutePlayerId1()!=null){
                matchB.setTeamASubstitutePlayerId1(matchBPlayerDTO.getTeamASubstitutePlayerId1());
                matchB.setTeamASubstitutePlayerName1(playerMapper.selectById(matchBPlayerDTO.getTeamASubstitutePlayerId1()).getName());
            }
            if(matchBPlayerDTO.getTeamASubstitutePlayerId2()!=null){
                matchB.setTeamASubstitutePlayerId2(matchBPlayerDTO.getTeamASubstitutePlayerId2());
                matchB.setTeamASubstitutePlayerName2(playerMapper.selectById(matchBPlayerDTO.getTeamASubstitutePlayerId2()).getName());
            }
            if(matchBPlayerDTO.getTeamASubstitutePlayerId3()!=null){
                matchB.setTeamASubstitutePlayerId3(matchBPlayerDTO.getTeamASubstitutePlayerId3());
                matchB.setTeamASubstitutePlayerName3(playerMapper.selectById(matchBPlayerDTO.getTeamASubstitutePlayerId3()).getName());
            }
            if(matchBPlayerDTO.getTeamASubstitutePlayerId4()!=null){
                matchB.setTeamASubstitutePlayerId4(matchBPlayerDTO.getTeamASubstitutePlayerId4());
                matchB.setTeamASubstitutePlayerName4(playerMapper.selectById(matchBPlayerDTO.getTeamASubstitutePlayerId4()).getName());
            }
        }else{
            if(matchBPlayerDTO.getTeamBPlayerId1()!=null){
                matchB.setTeamBPlayerId1(matchBPlayerDTO.getTeamBPlayerId1());
                matchB.setTeamBPlayerName1(playerMapper.selectById(matchBPlayerDTO.getTeamBPlayerId1()).getName());
            }
            if(matchBPlayerDTO.getTeamBPlayerId2()!=null){
                matchB.setTeamBPlayerId2(matchBPlayerDTO.getTeamBPlayerId2());
                matchB.setTeamBPlayerName2(playerMapper.selectById(matchBPlayerDTO.getTeamBPlayerId2()).getName());
            }
            if(matchBPlayerDTO.getTeamBPlayerId3()!=null){
                matchB.setTeamBPlayerId3(matchBPlayerDTO.getTeamBPlayerId3());
                matchB.setTeamBPlayerName3(playerMapper.selectById(matchBPlayerDTO.getTeamBPlayerId3()).getName());
            }
            if(matchBPlayerDTO.getTeamBPlayerId4()!=null){
                matchB.setTeamBPlayerId4(matchBPlayerDTO.getTeamBPlayerId4());
                matchB.setTeamBPlayerName4(playerMapper.selectById(matchBPlayerDTO.getTeamBPlayerId4()).getName());
            }
            if(matchBPlayerDTO.getTeamBSubstitutePlayerId1()!=null){
                matchB.setTeamBSubstitutePlayerId1(matchBPlayerDTO.getTeamBSubstitutePlayerId1());
                matchB.setTeamBSubstitutePlayerName1(playerMapper.selectById(matchBPlayerDTO.getTeamBSubstitutePlayerId1()).getName());
            }
            if(matchBPlayerDTO.getTeamBSubstitutePlayerId2()!=null){
                matchB.setTeamBSubstitutePlayerId2(matchBPlayerDTO.getTeamBSubstitutePlayerId2());
                matchB.setTeamBSubstitutePlayerName2(playerMapper.selectById(matchBPlayerDTO.getTeamBSubstitutePlayerId2()).getName());
            }
            if(matchBPlayerDTO.getTeamBSubstitutePlayerId3()!=null){
                matchB.setTeamBSubstitutePlayerId3(matchBPlayerDTO.getTeamBSubstitutePlayerId3());
                matchB.setTeamBSubstitutePlayerName3(playerMapper.selectById(matchBPlayerDTO.getTeamBSubstitutePlayerId3()).getName());
            }
            if(matchBPlayerDTO.getTeamBSubstitutePlayerId4()!=null){
                matchB.setTeamBSubstitutePlayerId4(matchBPlayerDTO.getTeamBSubstitutePlayerId4());
                matchB.setTeamBSubstitutePlayerName4(playerMapper.selectById(matchBPlayerDTO.getTeamBSubstitutePlayerId4()).getName());
            }
        }
        matchBMapper.updateById(matchB);
    }

    /**
     * 获取未开始的比赛b信息
     * @param visitorQueryDTO
     * @return
     */
    @Override
    public PageResult getUnStartMatchBBrief(VisitorQueryDTO visitorQueryDTO) {
        if (visitorQueryDTO == null) {
            throw new BaseException("visitorQueryDTO cannot be null");
        }
        Page<MatchB> page = new Page<>(visitorQueryDTO.getPage(), visitorQueryDTO.getPageSize());
        String name = visitorQueryDTO.getName();
        String department = visitorQueryDTO.getDepartment();
        LambdaQueryWrapper<MatchB> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MatchB::getStatus, StatusConstant.UNSTART)
                //.ge(MatchB::getBeginTime, LocalDateTime.now())
                .orderByAsc(MatchB::getBeginTime);
        if (name != null) {
            queryWrapper.and(wrapper -> wrapper
                    .like(MatchB::getTeamAPlayerName1, name)
                    .or()
                    .like(MatchB::getTeamAPlayerName2, name)
                    .or()
                    .like(MatchB::getTeamAPlayerName3, name)
                    .or()
                    .like(MatchB::getTeamAPlayerName4, name)
                    .or()
                    .like(MatchB::getTeamBPlayerName1, name)
                    .or()
                    .like(MatchB::getTeamBPlayerName2, name)
                    .or()
                    .like(MatchB::getTeamBPlayerName3, name)
                    .or()
                    .like(MatchB::getTeamBPlayerName4, name)
                    .or()
                    .like(MatchB::getTeamASubstitutePlayerName1, name)
                    .or()
                    .like(MatchB::getTeamASubstitutePlayerName2, name)
                    .or()
                    .like(MatchB::getTeamASubstitutePlayerName3, name)
                    .or()
                    .like(MatchB::getTeamASubstitutePlayerName4, name)
                    .or()
                    .like(MatchB::getTeamBSubstitutePlayerName1, name)
                    .or()
                    .like(MatchB::getTeamBSubstitutePlayerName2, name)
                    .or()
                    .like(MatchB::getTeamBSubstitutePlayerName3, name)
                    .or()
                    .like(MatchB::getTeamBSubstitutePlayerName4, name)
            );
        }
        if (department != null){
            queryWrapper.and(wrapper -> wrapper
                    .like(MatchB::getTeamADepartment, department)
                    .or()
                    .like(MatchB::getTeamBDepartment, department)
            );
        }
        page = matchBMapper.selectPage(page, queryWrapper);
        return new PageResult(page.getTotal(), page.getRecords());
    }

    /**
     * 获取进行中的比赛b信息
     * @param visitorQueryDTO
     * @return
     */
    @Override
    public PageResult getDoingMatchBBrief(VisitorQueryDTO visitorQueryDTO) {
        if (visitorQueryDTO == null) {
            throw new BaseException("visitorQueryDTO cannot be null");
        }
        Page<MatchB> page = new Page<>(visitorQueryDTO.getPage(), visitorQueryDTO.getPageSize());
        String name = visitorQueryDTO.getName();
        String department = visitorQueryDTO.getDepartment();
        LambdaQueryWrapper<MatchB> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MatchB::getStatus, StatusConstant.DOING)
                //.ge(MatchB::getBeginTime, LocalDateTime.now())
                .orderByAsc(MatchB::getBeginTime);
        if (name != null) {
            queryWrapper.and(wrapper -> wrapper
                    .like(MatchB::getTeamAPlayerName1, name)
                    .or()
                    .like(MatchB::getTeamAPlayerName2, name)
                    .or()
                    .like(MatchB::getTeamAPlayerName3, name)
                    .or()
                    .like(MatchB::getTeamAPlayerName4, name)
                    .or()
                    .like(MatchB::getTeamBPlayerName1, name)
                    .or()
                    .like(MatchB::getTeamBPlayerName2, name)
                    .or()
                    .like(MatchB::getTeamBPlayerName3, name)
                    .or()
                    .like(MatchB::getTeamBPlayerName4, name)
                    .or()
                    .like(MatchB::getTeamASubstitutePlayerName1, name)
                    .or()
                    .like(MatchB::getTeamASubstitutePlayerName2, name)
                    .or()
                    .like(MatchB::getTeamASubstitutePlayerName3, name)
                    .or()
                    .like(MatchB::getTeamASubstitutePlayerName4, name)
                    .or()
                    .like(MatchB::getTeamBSubstitutePlayerName1, name)
                    .or()
                    .like(MatchB::getTeamBSubstitutePlayerName2, name)
                    .or()
                    .like(MatchB::getTeamBSubstitutePlayerName3, name)
                    .or()
                    .like(MatchB::getTeamBSubstitutePlayerName4, name)
            );
        }
        if (department != null){
            queryWrapper.and(wrapper -> wrapper
                    .like(MatchB::getTeamADepartment, department)
                    .or()
                    .like(MatchB::getTeamBDepartment, department)
            );
        }
        page = matchBMapper.selectPage(page, queryWrapper);
        return new PageResult(page.getTotal(), page.getRecords());
    }

    /**
     * 判决matchB结束
     * @param matchBId
     */
    @Override
    public void endMatchB(Integer matchBId) {
        MatchB matchB = matchBMapper.selectById(matchBId);
        if(matchB == null){
            throw new MatchCreateException("比赛不存在");
        }
        Integer teamAScore = matchB.getTeamAScore();
        Integer teamBScore = matchB.getTeamBScore();
        Integer sectionScore = matchB.getSectionScore();
        if((teamAScore.equals(sectionScore)||teamBScore.equals(sectionScore)&&(Math.abs(teamAScore-teamBScore))>2)){
            matchB.setStatus(StatusConstant.END);
            if(teamAScore.equals(sectionScore)){
                matchB.setWinnerTeamId(matchB.getTeamAId());
                matchB.setWinnerTeamId(matchB.getTeamAId());
                matchB.setWinnerTeamDepartment(matchB.getTeamADepartment());
            } else {
                matchB.setWinnerTeamId(matchB.getTeamBId());
                matchB.setWinnerTeamDepartment(matchB.getTeamBDepartment());
                matchB.setWinnerTeamDepartment(matchB.getTeamBDepartment());
            }
        }
        matchBMapper.updateById(matchB);
    }

    /**
     * 宣布matchB开始
     * @param matchBId
     */
    @Override
    public void beginMatchB(Integer matchBId) {
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
        matchB.setCurrentSection(1);
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
        QueryWrapper<MatchB> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("referee_id", BaseContext.getCurrentId()).or().eq("substitute_referee_id", BaseContext.getCurrentId());
        queryWrapper.eq("status", StatusConstant.UNSTART).or().eq("status", StatusConstant.DOING);
        queryWrapper.orderByAsc("begin_time");
        page = matchBMapper.selectPage(page, queryWrapper);
        return new PageResult(page.getTotal(),page.getRecords());
    }

    /**
     * 对matchB某一回合进行判分
     * @param scoreDTO
     */
    @Override
    public void matchBScore(MatchBScoreDTO scoreDTO) {
        Integer matchBId = scoreDTO.getMatchBId();
        MatchB matchB = matchBMapper.selectById(matchBId);
        if(matchB == null){
            throw new MatchCreateException("比赛不存在");
        }
        Integer teamAScore = scoreDTO.getTeamAScore();
        Integer teamBScore = scoreDTO.getTeamBScore();
        int sectionScore = matchB.getSectionScore();
        if(teamAScore.equals(sectionScore*4)||teamBScore.equals(sectionScore*4)){
            matchB.setStatus(StatusConstant.END);
        } else if(teamAScore.compareTo(sectionScore*3)>=0||teamBScore.compareTo(sectionScore*3)>=0){
            matchB.setCurrentSection(4);
        } else if(teamAScore.compareTo(sectionScore*2)>=0||teamBScore.compareTo(sectionScore*2)>=0){
            matchB.setCurrentSection(3);
        } else if(teamAScore.compareTo(sectionScore)>=0||teamBScore.compareTo(sectionScore)>=0){
            matchB.setCurrentSection(2);
        }
        matchB.setTeamAScore(teamAScore);
        matchB.setTeamBScore(teamBScore);
        matchBMapper.updateById(matchB);
    }

    /**
     * 分页查询的比赛B
     * @param matchQueryDTO
     * @return
     */
    @Override
    public PageResult queryPage(MatchQueryDTO matchQueryDTO) {
        Page<MatchB> page = new Page<>(matchQueryDTO.getPage(),matchQueryDTO.getPageSize());
        QueryWrapper<MatchB> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("event_id",matchQueryDTO.getEventId());
//        if (matchQueryDTO.getDepartment() != null) {
//            queryWrapper
//                    .eq("team_a_department",matchQueryDTO.getDepartment())
//                    .or()
//                    .eq("team_b_department",matchQueryDTO.getDepartment());
//        }
        matchBMapper.selectPage(page,queryWrapper);
        return new PageResult(page.getTotal(),page.getRecords());
    }

    /**
     * 领队分页查询比赛B
     * @param pageQueryDTO
     * @return
     */
    @Override
    public PageResult leaderPageMatchB(PageQueryDTO pageQueryDTO) {
        Page<MatchB> page = new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getPageSize());
        Integer leaderId = BaseContext.getCurrentId();
        Team team = teamMapper.selectOne(new QueryWrapper<Team>().eq("leader_id", leaderId));

        if (team == null) {
            throw new MatchCreateException("队伍不存在");
        }

        Integer teamId = team.getTeamId();

        QueryWrapper<MatchB> matchModeQueryWrapper = new QueryWrapper<>();
        matchModeQueryWrapper
                .eq("team_a_id", teamId)
                .or()
                .eq("team_b_id", teamId);

        page = matchBMapper.selectPage(page, matchModeQueryWrapper);

        Page<LeaderMatchBVO> pageVO = new Page<>();
        pageVO.setTotal(page.getTotal());
        List<MatchB> records = page.getRecords();
        List<LeaderMatchBVO> recordsVO = new ArrayList<>();
        for(MatchB matchB : records){
            LeaderMatchBVO leaderMatchBVO = new LeaderMatchBVO();
            BeanUtils.copyProperties(matchB, leaderMatchBVO);
            leaderMatchBVO.setTeamADepartment(teamMapper.selectById(matchB.getTeamAId()).getDepartment());
            leaderMatchBVO.setTeamBDepartment(teamMapper.selectById(matchB.getTeamBId()).getDepartment());
            if(teamId.equals(matchB.getTeamAId())){
                leaderMatchBVO.setIsTeamA(true);
            } else if (teamId.equals(matchB.getTeamBId())) {
                leaderMatchBVO.setIsTeamA(false);
            }
            recordsVO.add(leaderMatchBVO);
        }
        pageVO.setRecords(recordsVO);

        return new PageResult(pageVO.getTotal(), pageVO.getRecords());
    }

    /**
     * 清空matchB上场球员
     * @param clearMatchBPlayerDTO
     */
    @Override
    public void deleteMatchBPlayer(ClearMatchBPlayerDTO clearMatchBPlayerDTO) {
        Integer matchBId = clearMatchBPlayerDTO.getMatchBId();
        Integer leaderId = BaseContext.getCurrentId();
        Team team = teamMapper.selectOne(new QueryWrapper<Team>().eq("leader_id", leaderId));
        MatchB matchB = matchBMapper.selectById(matchBId);
        if (team.getTeamId().equals(matchB.getTeamAId())) {
            matchB.setTeamAPlayerId1(null);
            matchB.setTeamAPlayerId2(null);
            matchB.setTeamAPlayerId3(null);
            matchB.setTeamAPlayerId4(null);
            matchB.setTeamAPlayerName1(null);
            matchB.setTeamAPlayerName2(null);
            matchB.setTeamAPlayerName3(null);
            matchB.setTeamAPlayerName4(null);
            matchB.setTeamASubstitutePlayerId1(null);
            matchB.setTeamASubstitutePlayerId2(null);
            matchB.setTeamASubstitutePlayerId3(null);
            matchB.setTeamASubstitutePlayerId4(null);
            matchB.setTeamASubstitutePlayerName1(null);
            matchB.setTeamASubstitutePlayerName2(null);
            matchB.setTeamASubstitutePlayerName3(null);
            matchB.setTeamASubstitutePlayerName4(null);
        } else if(team.getTeamId().equals(matchB.getTeamBId())){
            matchB.setTeamBPlayerId1(null);
            matchB.setTeamBPlayerId2(null);
            matchB.setTeamBPlayerId3(null);
            matchB.setTeamBPlayerId4(null);
            matchB.setTeamBPlayerName1(null);
            matchB.setTeamBPlayerName2(null);
            matchB.setTeamBPlayerName3(null);
            matchB.setTeamBPlayerName4(null);
            matchB.setTeamBSubstitutePlayerId1(null);
            matchB.setTeamBSubstitutePlayerId2(null);
            matchB.setTeamBSubstitutePlayerId3(null);
            matchB.setTeamBSubstitutePlayerId4(null);
            matchB.setTeamBSubstitutePlayerName1(null);
            matchB.setTeamBSubstitutePlayerName2(null);
            matchB.setTeamBSubstitutePlayerName3(null);
            matchB.setTeamBSubstitutePlayerName4(null);
        }
        matchBMapper.updateById(matchB);
    }

    @Override
    public LeaderMatchBVO leaderGetMatchB(Integer matchBId) {
        Integer leaderId = BaseContext.getCurrentId();
        Team team = teamMapper.selectOne(new QueryWrapper<Team>().eq("leader_id", leaderId));
        MatchB matchB = matchBMapper.selectById(matchBId);
        LeaderMatchBVO leaderMatchBVO = new LeaderMatchBVO();
        BeanUtils.copyProperties(matchB, leaderMatchBVO);
        if(team.getTeamId().equals(matchB.getTeamAId())){
            leaderMatchBVO.setIsTeamA(true);
        } else if (team.getTeamId().equals(matchB.getTeamBId())) {
            leaderMatchBVO.setIsTeamA(false);
        }
        return leaderMatchBVO;
    }

    @Override
    public PageResult getEndMatchBBrief(VisitorQueryDTO visitorQueryDTO) {
        if (visitorQueryDTO == null) {
            throw new BaseException("visitorQueryDTO cannot be null");
        }
        Page<MatchB> page = new Page<>(visitorQueryDTO.getPage(), visitorQueryDTO.getPageSize());
        String name = visitorQueryDTO.getName();
        String department = visitorQueryDTO.getDepartment();
        LambdaQueryWrapper<MatchB> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MatchB::getStatus, StatusConstant.END)
                //.ge(MatchB::getBeginTime, LocalDateTime.now())
                .orderByAsc(MatchB::getBeginTime);
        if (name != null) {
            queryWrapper.and(wrapper -> wrapper
                    .like(MatchB::getTeamAPlayerName1, name)
                    .or()
                    .like(MatchB::getTeamAPlayerName2, name)
                    .or()
                    .like(MatchB::getTeamAPlayerName3, name)
                    .or()
                    .like(MatchB::getTeamAPlayerName4, name)
                    .or()
                    .like(MatchB::getTeamBPlayerName1, name)
                    .or()
                    .like(MatchB::getTeamBPlayerName2, name)
                    .or()
                    .like(MatchB::getTeamBPlayerName3, name)
                    .or()
                    .like(MatchB::getTeamBPlayerName4, name)
                    .or()
                    .like(MatchB::getTeamASubstitutePlayerName1, name)
                    .or()
                    .like(MatchB::getTeamASubstitutePlayerName2, name)
                    .or()
                    .like(MatchB::getTeamASubstitutePlayerName3, name)
                    .or()
                    .like(MatchB::getTeamASubstitutePlayerName4, name)
                    .or()
                    .like(MatchB::getTeamBSubstitutePlayerName1, name)
                    .or()
                    .like(MatchB::getTeamBSubstitutePlayerName2, name)
                    .or()
                    .like(MatchB::getTeamBSubstitutePlayerName3, name)
                    .or()
                    .like(MatchB::getTeamBSubstitutePlayerName4, name)
            );
        }
        if (department != null){
            queryWrapper.and(wrapper -> wrapper
                    .like(MatchB::getTeamADepartment, department)
                    .or()
                    .like(MatchB::getTeamBDepartment, department)
            );
        }
        page = matchBMapper.selectPage(page, queryWrapper);
        return new PageResult(page.getTotal(), page.getRecords());
    }
}
