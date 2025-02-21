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
import com.ybk.service.MatchAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private MatchModeMapper matchModeMapper;

    @Autowired
    private TeamMapper teamMapper;

    /**
     * 校验matchA编辑请求体
     * @param matchADTO
     */
    private void validateMatchADTO(MatchADTO matchADTO) {
        if (matchADTO.getTeamAId() == null || matchADTO.getTeamBId() == null) {
            throw new MatchCreateException("请选择两支球队");
        }
        if(matchADTO.getGameCount()==null){
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
                .gameCount(matchADTO.getGameCount())
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
        if(matchADTO.getGameCount()!=null) {
            matchA.setGameCount(matchADTO.getGameCount());
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
        Integer roundCount = matchA.getGameCount();
        // 从数据库中查询出各个mode的各个count的具体信息
        for (String mode : modes ){
            Map<Integer, MatchMode> matchSetMap = new HashMap<>();
            for (int i = 0; i < roundCount; i++) {
                MatchMode matchMode = matchModeMapper.selectOne(
                        new LambdaQueryWrapper<MatchMode>()
                                .eq(MatchMode::getMatchAId, matchA.getMatchAId())
                                .eq(MatchMode::getMode, mode)
                                .eq(MatchMode::getCurrentGame, i)
                );
                matchSetMap.put(i, matchMode);
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

    /**
     * 判决matchA结束
     * @param endMatchDTO
     */
    @Override
    public void endMatchA(EndMatchDTO endMatchDTO) {
        Long matchAId = endMatchDTO.getMatchId();
        Long teamId = endMatchDTO.getTeamId();
        MatchA matchA = matchAMapper.selectById(matchAId);
        matchA.setWinnerTeamId(teamId);
        Team team = teamMapper.selectById(teamId);
        matchA.setWinnerTeamDepartment(team.getDepartment());
        matchA.setStatus(StatusConstant.END);
        matchAMapper.updateById(matchA);
    }

    /**
     * 查看判分的matchA简略信息
     * @param pageQueryDTO
     * @return
     */
    @Override
    public PageResult getRefereeMatchABrief(PageQueryDTO pageQueryDTO) {
        Page<MatchA> page = new Page<>(pageQueryDTO.getPage(),pageQueryDTO.getPageSize());
        // 筛选出refereeId为当前referee的id的matchA
        // 并且状态为UNSTART或者DOING
        // 最后按早时间从近到远排序
        page = matchAMapper.selectPage(page,
                new LambdaQueryWrapper<MatchA>()
                        .eq(MatchA::getRefereeId, BaseContext.getCurrentId())
                        .and(i -> i.eq(MatchA::getStatus, StatusConstant.UNSTART)
                                .or()
                                .eq(MatchA::getStatus, StatusConstant.DOING))
                        .orderByAsc(MatchA::getBeginTime)
        );
        return new PageResult(page.getTotal(),page.getRecords());
    }

    /**
     * 开始matchA
     * @param beginMatchDTO
     */
    @Override
    public void beginMatchA(BeginMatchDTO beginMatchDTO) {
        MatchA matchA = matchAMapper.selectById(beginMatchDTO.getMatchId());
        if(matchA == null){
            throw new MatchCreateException("比赛不存在");
        }
        if(!matchA.getStatus().equals(StatusConstant.UNSTART)){
            throw new MatchCreateException("比赛已开始");
        }
        // 比赛状态设置为进行中
        matchA.setStatus(StatusConstant.DOING);
        matchAMapper.updateById(matchA);
        for (String mode : matchA.getModes()) {
            for (int i = 0; i < matchA.getGameCount(); i++) {
                MatchMode matchMode = MatchMode.builder()
                        .matchAId(matchA.getMatchAId())
                        .mode(mode)
                        .currentGame(i)
                        .currentRound(1)
                        .teamAGameScore(0)
                        .teamBGameScore(0)
                        .teamScoreList(new ArrayList<>())
                        .winnerTeamId(null)
                        .build();
                matchModeMapper.insert(matchMode);
            }
        }
    }

    /**
     * 对matchA某一模式的某一轮的某一回合进行判分
     *
     * @param scoreDTO
     */
    @Override
    public void matchAScore(MatchAScoreDTO scoreDTO) {
        Long matchModeId = scoreDTO.getMatchModeId();
        Long matchAId = scoreDTO.getMatchAId();
        Long teamId = scoreDTO.getTeamId();
        Integer currentGame = scoreDTO.getCurrentGame();
        Integer plusOrMinus = scoreDTO.getPlusOrMinus();
        MatchA matchA = matchAMapper.selectById(matchAId);
        MatchMode matchMode = matchModeMapper.selectById(matchModeId);
        if (matchMode == null) {
            throw new MatchCreateException("matchMode不存在");
        }
        if (teamId.equals(matchA.getTeamAId())) {
            if (currentGame == 1) {
                matchMode.setTeamARoundScore1(matchMode.getTeamARoundScore1() + plusOrMinus);
            } else if (currentGame == 2) {
                matchMode.setTeamARoundScore2(matchMode.getTeamARoundScore2() + plusOrMinus);
            } else {
                matchMode.setTeamARoundScore3(matchMode.getTeamARoundScore3() + plusOrMinus);
            }
        } else {
            if (currentGame == 1) {
                matchMode.setTeamBRoundScore1(matchMode.getTeamBRoundScore1() + plusOrMinus);
            } else if (currentGame == 2) {
                matchMode.setTeamBRoundScore2(matchMode.getTeamBRoundScore2() + plusOrMinus);
            } else {
                matchMode.setTeamBRoundScore3(matchMode.getTeamBRoundScore3() + plusOrMinus);
            }
        }
        // 判断本局是否结束
        Integer winScore = matchA.getWinScore();
        if(currentGame == 1){
            Integer teamARoundScore1 = matchMode.getTeamARoundScore1();
            Integer teamBRoundScore1 = matchMode.getTeamBRoundScore1();
            // 有一队伍的分数达到winScore并且两个队伍的差距大于1就是本局结束
            if(teamARoundScore1 >= winScore || teamBRoundScore1 >= winScore){
                if(teamARoundScore1-teamBRoundScore1>1){
                    matchMode.setTeamAGameScore(1);
                }else{
                    matchMode.setTeamBGameScore(1);
                }
            }
        }
        else if(currentGame == 2){
            Integer teamARoundScore2 = matchMode.getTeamARoundScore2();
            Integer teamBRoundScore2 = matchMode.getTeamBRoundScore2();
            if(teamARoundScore2 >= winScore || teamBRoundScore2 >= winScore){
                if(teamARoundScore2-teamBRoundScore2>1){
                    matchMode.setTeamAGameScore(matchMode.getTeamAGameScore()+1);
                }else{
                    matchMode.setTeamBGameScore(matchMode.getTeamBGameScore()+1);
                }
            }
        }
        else if(currentGame == 3){
            Integer teamARoundScore3 = matchMode.getTeamARoundScore3();
            Integer teamBRoundScore3 = matchMode.getTeamBRoundScore3();
            if(teamARoundScore3 >= winScore || teamBRoundScore3 >= winScore){
                if(teamARoundScore3-teamBRoundScore3>1){
                    matchMode.setTeamAGameScore(matchMode.getTeamAGameScore()+1);
                }else{
                    matchMode.setTeamBGameScore(matchMode.getTeamBGameScore()+1);
                }
            }
        }
        // 判断整个比赛是否结束
        if(matchMode.getTeamAGameScore() == matchA.getGameCount()/2+1){
            matchA.setWinnerTeamId(matchA.getTeamAId());
        }
        if(matchMode.getTeamBGameScore() == matchA.getGameCount()/2+1){
            matchA.setWinnerTeamId(matchA.getTeamBId());
        }
    }
}
