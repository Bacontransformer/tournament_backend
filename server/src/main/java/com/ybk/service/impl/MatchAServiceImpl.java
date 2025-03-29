package com.ybk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        if (matchADTO.getWinScore() == null) {
            throw new MatchCreateException("请输入每局胜分线");
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
        Team teamA = teamMapper.selectById(matchADTO.getTeamAId());
        String teamADepartment = teamA.getDepartment();
        Team teamB = teamMapper.selectById(matchADTO.getTeamBId());
        String teamBDepartment = teamB.getDepartment();
        MatchA matchA = MatchA.builder()
                .eventId(matchADTO.getEventId())
                .teamAId(matchADTO.getTeamAId())
                .teamBId(matchADTO.getTeamBId())
                .teamADepartment(teamADepartment)
                .teamBDepartment(teamBDepartment)
                .maxParticipationTimes(matchADTO.getMaxParticipationTimes())
                .minTeamAgeSum(matchADTO.getMinTeamAgeSum())
                .maxTeamAgeSum(matchADTO.getMaxTeamAgeSum())
                .maxSubstitutePlayer(matchADTO.getMaxSubstitutePlayer())
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
    public void delete(Integer matchId) {
        matchAMapper.deleteById(matchId);
        // matchModeMapper删除对应的matchAId字段的数据
        matchModeMapper.delete(
                new LambdaQueryWrapper<MatchMode>()
                        .eq(MatchMode::getMatchAId, matchId)
        );
    }

    /**
     * 设置matchA比赛选手
     * @param matchAPlayerDTO
     */
    @Override
    public void setMatchAPlayer(MatchAPlayerDTO matchAPlayerDTO) {
        MatchA matchA = matchAMapper.selectById(matchAPlayerDTO.getMatchAId());
        if(matchA == null){
            throw new MatchCreateException("比赛不存在");
        }
        Player player = playerMapper.selectById(matchAPlayerDTO.getPlayerId());
        if(player == null){
            throw new MatchCreateException("球员不存在");
        }
        Integer leaderId = player.getLeaderId();
        if(leaderId.equals(BaseContext.getCurrentId())){
            throw new MatchCreateException("非此领队球员");
        }
        // 使用 Lambda 查询只获取 teamId
        Leader leader = leaderMapper.selectOne(
                new LambdaQueryWrapper<Leader>()
                        .select(Leader::getTeamId)
                        .eq(Leader::getLeaderId, leaderId)
        );
        Integer teamId = leader.getTeamId();
        MatchMode matchMode = matchModeMapper.selectById(matchAPlayerDTO.getMatchModeId());
        if(teamId.equals(matchMode.getTeamAId())){
            if(!matchAPlayerDTO.getIsSubstitute()){
                if(matchMode.getTeamAPlayer1()!=null){
                    matchMode.setTeamAPlayer2(matchAPlayerDTO.getPlayerId());
                } else {
                    matchMode.setTeamAPlayer1(matchAPlayerDTO.getPlayerId());
                }
            }else{
                if(matchMode.getTeamASubstitutePlayer1()!=null){
                    matchMode.setTeamASubstitutePlayer2(matchAPlayerDTO.getPlayerId());
                } else {
                    matchMode.setTeamASubstitutePlayer1(matchAPlayerDTO.getPlayerId());
                }
            }
        }else {
            if(!matchAPlayerDTO.getIsSubstitute()){
                if(matchMode.getTeamBPlayer1()!=null){
                    matchMode.setTeamBPlayer2(matchAPlayerDTO.getPlayerId());
                } else {
                    matchMode.setTeamBPlayer1(matchAPlayerDTO.getPlayerId());
                }
            }else{
                if(matchMode.getTeamBSubstitutePlayer1()!=null){
                    matchMode.setTeamBSubstitutePlayer2(matchAPlayerDTO.getPlayerId());
                }
            }
        }
        matchModeMapper.updateById(matchMode);
    }

    /**
     * 删除MatchA参赛选手
     * @param clearMatchAPlayerDTO
     */
    @Override
    public void deleteMatchAPlayer(ClearMatchAPlayerDTO clearMatchAPlayerDTO) {
        Integer matchModeId = clearMatchAPlayerDTO.getMatchModeId();
        Integer teamId = clearMatchAPlayerDTO.getTeamId();
        MatchMode matchMode = matchModeMapper.selectById(matchModeId);
        if(teamId.equals(matchMode.getTeamAId())){
            matchMode.setTeamAPlayer1(null);
            matchMode.setTeamAPlayer2(null);
            matchMode.setTeamASubstitutePlayer1(null);
            matchMode.setTeamASubstitutePlayer2(null);
        }else{
            matchMode.setTeamBPlayer1(null);
            matchMode.setTeamBPlayer2(null);
            matchMode.setTeamBSubstitutePlayer1(null);
            matchMode.setTeamBSubstitutePlayer2(null);
        }
    }

    /**
     * 获取正在进行的某一MatchA详细比赛信息
     * @param matchModeId
     * @return
     */
    @Override
    public MatchMode getDoingMatchADetail(Integer matchModeId) {
        MatchMode matchMode = matchModeMapper.selectById(matchModeId);
        if(!matchMode.getStatus().equals(StatusConstant.DOING)){
            return null;
        }
        return matchMode;
    }

    /**
     * 获取所有未开始的比赛简略信息
     * @param pageQueryDTO
     * @return
     */
    @Override
    public PageResult getUnStartMatchABrief(PageQueryDTO pageQueryDTO) {
        Page<MatchMode> page = new Page<>(pageQueryDTO.getPage(),pageQueryDTO.getPageSize());
        page = matchModeMapper.selectPage(page,
                new LambdaQueryWrapper<MatchMode>()
                        .eq(MatchMode::getStatus, StatusConstant.UNSTART)
                        .ge(MatchMode::getBeginTime, LocalDateTime.now())
                        .orderByAsc(MatchMode::getBeginTime)
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
        Page<MatchMode> page = new Page<>(pageQueryDTO.getPage(),pageQueryDTO.getPageSize());
        page = matchModeMapper.selectPage(page,
                new LambdaQueryWrapper<MatchMode>()
                        .eq(MatchMode::getStatus, StatusConstant.DOING)
                        .ge(MatchMode::getBeginTime, LocalDateTime.now())
                        .orderByAsc(MatchMode::getBeginTime)
        );
        return new PageResult(page.getTotal(),page.getRecords());
    }

    /**
     * 判决matchMode结束
     * @param matchModeId
     */
    @Override
    public void endMatchA(Integer matchModeId) {
        MatchMode matchMode = matchModeMapper.selectById(matchModeId);
        matchMode.setStatus(StatusConstant.END);
        matchModeMapper.updateById(matchMode);
        if(matchMode.getTeamAGameScore()>matchMode.getTeamBGameScore()) {
            matchMode.setModeWinnerTeamId(matchMode.getTeamAId());
        }else if(matchMode.getTeamAGameScore()<matchMode.getTeamBGameScore()) {
            matchMode.setModeWinnerTeamId(matchMode.getTeamBId());
        }
    }

    /**
     * 查看判分的matchMode信息
     * @param pageQueryDTO
     * @return
     */
    @Override
    public PageResult getRefereeMatchMode(PageQueryDTO pageQueryDTO) {
        Page<MatchMode> page = new Page<>(pageQueryDTO.getPage(),pageQueryDTO.getPageSize());
        // matchModeMapper筛选出refereeId为当前referee的id的matchMode
        // MatchMode的status字段为UNSTART或者DOING
        // 按早时间从近到远排序
        page = matchModeMapper.selectPage(page,
                new LambdaQueryWrapper<MatchMode>()
                        .eq(MatchMode::getRefereeId, BaseContext.getCurrentId())
                        .eq(MatchMode::getStatus, StatusConstant.UNSTART)
                        .or()
                        .eq(MatchMode::getStatus, StatusConstant.DOING)
                        .orderByAsc(MatchMode::getBeginTime)
        );
        return new PageResult(page.getTotal(),page.getRecords());
    }

    /**
     * 裁判开启MatchMode
     * @param matchModeId
     */
    @Override
    public void beginMatchA(Integer matchModeId) {
        MatchMode matchMode = matchModeMapper.selectById(matchModeId);
        matchMode.setCurrentGame(1);
        matchMode.setStatus(StatusConstant.DOING);
        // 局比分
        matchMode.setTeamAGameScore(0);
        matchMode.setTeamBGameScore(0);
        Integer matchAId = matchMode.getMatchAId();
        MatchA matchA = matchAMapper.selectById(matchAId);
        Integer gameCount = matchA.getGameCount();
        // 局内比分
        matchMode.setTeamARoundScore1(0);
        matchMode.setTeamBRoundScore1(0);
        if(gameCount == 3){
            matchMode.setTeamARoundScore2(0);
            matchMode.setTeamBRoundScore2(0);
            matchMode.setTeamARoundScore3(0);
            matchMode.setTeamBRoundScore3(0);
        }
        matchModeMapper.updateById(matchMode);
    }


    /**
     * 对matchMode某一局的某一回合进行判分
     *
     * @param scoreDTO
     */
    @Override
    public void matchAScore(MatchAScoreDTO scoreDTO) {
        Integer matchModeId = scoreDTO.getMatchModeId();
        Integer matchAId = scoreDTO.getMatchAId();
        Integer teamId = scoreDTO.getTeamId();
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
        if (currentGame == 1) {
            Integer teamARoundScore1 = matchMode.getTeamARoundScore1();
            Integer teamBRoundScore1 = matchMode.getTeamBRoundScore1();
            // 有一队伍的分数达到winScore并且两个队伍的差距大于1就是本局结束
            if (teamARoundScore1 >= winScore || teamBRoundScore1 >= winScore) {
                if (teamARoundScore1 - teamBRoundScore1 > 1) {
                    matchMode.setTeamAGameScore(1);
                } else {
                    matchMode.setTeamBGameScore(1);
                }
            }
        } else if (currentGame == 2) {
            Integer teamARoundScore2 = matchMode.getTeamARoundScore2();
            Integer teamBRoundScore2 = matchMode.getTeamBRoundScore2();
            if (teamARoundScore2 >= winScore || teamBRoundScore2 >= winScore) {
                if (teamARoundScore2 - teamBRoundScore2 > 1) {
                    matchMode.setTeamAGameScore(matchMode.getTeamAGameScore() + 1);
                } else {
                    matchMode.setTeamBGameScore(matchMode.getTeamBGameScore() + 1);
                }
            }
        } else if (currentGame == 3) {
            Integer teamARoundScore3 = matchMode.getTeamARoundScore3();
            Integer teamBRoundScore3 = matchMode.getTeamBRoundScore3();
            if (teamARoundScore3 >= winScore || teamBRoundScore3 >= winScore) {
                if (teamARoundScore3 - teamBRoundScore3 > 1) {
                    matchMode.setTeamAGameScore(matchMode.getTeamAGameScore() + 1);
                } else {
                    matchMode.setTeamBGameScore(matchMode.getTeamBGameScore() + 1);
                }
            }
        }
        // 判断整个比赛是否结束
        if (matchMode.getTeamAGameScore() == matchA.getGameCount() / 2 + 1) {
            matchA.setWinnerTeamId(matchA.getTeamAId());
        }
        if (matchMode.getTeamBGameScore() == matchA.getGameCount() / 2 + 1) {
            matchA.setWinnerTeamId(matchA.getTeamBId());
        }
    }

    /**
     * 创建一个matchA的模式
     *
     * @param matchAModeDTO
     */
    @Override
    public void saveMode(MatchAModeDTO matchAModeDTO) {
        Integer matchAId = matchAModeDTO.getMatchAId();
        String mode = matchAModeDTO.getMode();
        LocalDateTime beginTime = matchAModeDTO.getBeginTime();
        Integer venueNumber = matchAModeDTO.getVenueNumber();
        MatchA matchA = matchAMapper.selectById(matchAId);
        if (matchA == null) {
            throw new MatchCreateException("matchA不存在");
        }
        MatchMode matchMode = MatchMode.builder()
                .mode(mode)
                .matchAId(matchAId)
                .status(StatusConstant.UNSTART)
                .beginTime(beginTime)
                .venueNumber(venueNumber)
                .teamAId(matchA.getTeamAId())
                .teamBId(matchA.getTeamBId())
                .build();
        matchModeMapper.insert(matchMode);
    }

    /**
     * 比赛A模式修改
     * @param matchAModeDTO
     */
    @Override
    public void updateMode(MatchAModeDTO matchAModeDTO) {
        Integer matchModeId = matchAModeDTO.getMatchModeId();
        MatchMode matchMode = matchModeMapper.selectById(matchModeId);
        if(matchMode == null){
            throw new MatchCreateException("matchMode不存在");
        }
        MatchA matchA = matchAMapper.selectById(matchMode.getMatchAId());
        if(matchA == null){
            throw new MatchCreateException("matchA不存在");
        }
        if(matchAModeDTO.getMode()!=null){
            matchMode.setMode(matchAModeDTO.getMode());
        }
        if(matchAModeDTO.getBeginTime()!=null){
            matchMode.setBeginTime(matchAModeDTO.getBeginTime());
        }
        if(matchAModeDTO.getVenueNumber()!=null){
            matchMode.setVenueNumber(matchAModeDTO.getVenueNumber());
        }
        matchModeMapper.updateById(matchMode);
    }

    /**
     * 分页查询比赛A模式
     * @param pageQueryDTO
     * @return
     */
    @Override
    public PageResult pageMatchAMode(PageQueryDTO pageQueryDTO) {
        Page<MatchMode> page = new Page<>(pageQueryDTO.getPage(),pageQueryDTO.getPageSize());
        QueryWrapper<MatchMode> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("match_a_id",matchAModeDTO.getMatchAId());
        matchModeMapper.selectPage(page,queryWrapper);
        return new PageResult(page.getTotal(),page.getRecords());
    }

    /**
     * 删除一个matchA的模式
     *
     * @param matchModeId
     */
    @Override
    public void deleteMode(Integer matchModeId) {
        matchModeMapper.deleteById(matchModeId);
    }

    /**
     * 分页条件查询matchA
     * @param matchQueryDTO
     * @return
     */
    @Override
    public PageResult queryPage(MatchQueryDTO matchQueryDTO) {
        Page<MatchA> page = new Page<>(matchQueryDTO.getPage(),matchQueryDTO.getPageSize());
        QueryWrapper<MatchA> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("event_id",matchQueryDTO.getEventId());
        if (matchQueryDTO.getDepartment() != null) {
            queryWrapper
                    .eq("team_a_department",matchQueryDTO.getDepartment())
                    .or()
                    .eq("team_b_department",matchQueryDTO.getDepartment());
        }
        matchAMapper.selectPage(page,queryWrapper);
        return new PageResult(page.getTotal(),page.getRecords());
    }
}
