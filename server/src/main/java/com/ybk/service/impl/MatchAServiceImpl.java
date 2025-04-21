package com.ybk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import com.ybk.service.MatchAService;
import com.ybk.vo.LeaderMatchModeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.stream.Collectors;

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

    @Autowired
    private RefereeMapper refereeMapper;

    /**
     * 校验matchA编辑请求体
     *
     * @param matchADTO
     */
    private void validateMatchADTO(MatchADTO matchADTO) {
        if (matchADTO.getTeamAId() == null || matchADTO.getTeamBId() == null) {
            throw new MatchCreateException("请选择两支球队");
        }
        if (matchADTO.getGameCount() == null) {
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
     *
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
     *
     * @param matchADTO
     */
    @Override
    public void update(MatchADTO matchADTO) {
        MatchA matchA = new MatchA();
        matchA.setMatchAId(matchADTO.getMatchAId());
        if (matchADTO.getEventId() != null) {
            matchA.setEventId(matchADTO.getEventId());
        }
        if (matchADTO.getTeamAId() != null) {
            matchA.setTeamAId(matchADTO.getTeamAId());
        }
        if (matchADTO.getTeamBId() != null) {
            matchA.setTeamBId(matchADTO.getTeamBId());
        }
        if (matchADTO.getMaxParticipationTimes() != null) {
            matchA.setMaxParticipationTimes(matchADTO.getMaxParticipationTimes());
        }
        if (matchADTO.getMinTeamAgeSum() != null) {
            matchA.setMinTeamAgeSum(matchADTO.getMinTeamAgeSum());
        }
        if (matchADTO.getMaxTeamAgeSum() != null) {
            matchA.setMaxTeamAgeSum(matchADTO.getMaxTeamAgeSum());
        }
        if (matchADTO.getMaxSubstitutePlayer() != null) {
            matchA.setMaxSubstitutePlayer(matchADTO.getMaxSubstitutePlayer());
        }
        if (matchADTO.getGameCount() != null) {
            matchA.setGameCount(matchADTO.getGameCount());
        }
        if (matchADTO.getWinScore() != null) {
            matchA.setWinScore(matchADTO.getWinScore());
        }
        matchA.setUpdateTime(LocalDateTime.now());
        matchAMapper.updateById(matchA);
    }

    /**
     * 删除matchA
     *
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
     *
     * @param matchAPlayerDTO
     */
    @Override
    public void setMatchAPlayer(MatchAPlayerDTO matchAPlayerDTO) {
        MatchA matchA = matchAMapper.selectById(matchAPlayerDTO.getMatchAId());
        if (matchA == null) {
            throw new MatchCreateException("比赛不存在");
        }
        MatchMode matchMode = matchModeMapper.selectById(matchAPlayerDTO.getMatchModeId());
        if (matchMode == null) {
            throw new MatchCreateException("比赛模式不存在");
        }
        if (matchAPlayerDTO.getIsTeamA()) {
            Integer teamAPlayer1 = matchAPlayerDTO.getTeamAPlayer1();
            Integer teamAPlayer2 = matchAPlayerDTO.getTeamAPlayer2();
            Integer teamASubstitutePlayer1 = matchAPlayerDTO.getTeamASubstitutePlayer1();
            Integer teamASubstitutePlayer2 = matchAPlayerDTO.getTeamASubstitutePlayer2();
            if (teamAPlayer1 != null) {
                Player player = playerMapper.selectById(teamAPlayer1);
                if (player == null) {
                    throw new MatchCreateException("参赛选手不存在");
                }
                if (player.getTeamId().equals(matchMode.getTeamAId())) {
                    matchMode.setTeamAPlayer1(teamAPlayer1);
                    matchMode.setTeamAPlayer1Name(player.getName());
                }
            }
            if (teamAPlayer2 != null) {
                Player player = playerMapper.selectById(teamAPlayer2);
                if (player == null) {
                    throw new MatchCreateException("参赛选手不存在");
                }
                if (player.getTeamId().equals(matchMode.getTeamAId())) {
                    matchMode.setTeamAPlayer2(teamAPlayer2);
                    matchMode.setTeamAPlayer2Name(player.getName());
                }
            }
            if (teamASubstitutePlayer1 != null) {
                Player player = playerMapper.selectById(teamASubstitutePlayer1);
                if (player == null) {
                    throw new MatchCreateException("参赛选手不存在");
                }
                if (player.getTeamId().equals(matchMode.getTeamAId())) {
                    matchMode.setTeamASubstitutePlayer1(teamASubstitutePlayer1);
                    matchMode.setTeamASubstitutePlayer1Name(player.getName());
                }
            }
            if (teamASubstitutePlayer2 != null) {
                Player player = playerMapper.selectById(teamASubstitutePlayer2);
                if (player == null) {
                    throw new MatchCreateException("参赛选手不存在");
                }
                if (player.getTeamId().equals(matchMode.getTeamAId())) {
                    matchMode.setTeamASubstitutePlayer2(teamASubstitutePlayer2);
                    matchMode.setTeamASubstitutePlayer2Name(player.getName());
                }
            }
        } else {
            Integer teamBPlayer1 = matchAPlayerDTO.getTeamBPlayer1();
            Integer teamBPlayer2 = matchAPlayerDTO.getTeamBPlayer2();
            Integer teamBSubstitutePlayer1 = matchAPlayerDTO.getTeamBSubstitutePlayer1();
            Integer teamBSubstitutePlayer2 = matchAPlayerDTO.getTeamBSubstitutePlayer2();
            if (teamBPlayer1 != null) {
                Player player = playerMapper.selectById(teamBPlayer1);
                if (player == null) {
                    throw new MatchCreateException("参赛选手不存在");
                }
                if (player.getTeamId().equals(matchMode.getTeamBId())) {
                    matchMode.setTeamBPlayer1(teamBPlayer1);
                    matchMode.setTeamBPlayer1Name(player.getName());
                }
            }
            if (teamBPlayer2 != null) {
                Player player = playerMapper.selectById(teamBPlayer2);
                if (player == null) {
                    throw new MatchCreateException("参赛选手不存在");
                }
                if (player.getTeamId().equals(matchMode.getTeamBId())) {
                    matchMode.setTeamBPlayer2(teamBPlayer2);
                    matchMode.setTeamBPlayer2Name(player.getName());
                }
            }
            if (teamBSubstitutePlayer1 != null) {
                Player player = playerMapper.selectById(teamBSubstitutePlayer1);
                if (player == null) {
                    throw new MatchCreateException("参赛选手不存在");
                }
                if (player.getTeamId().equals(matchMode.getTeamBId())) {
                    matchMode.setTeamBSubstitutePlayer1(teamBSubstitutePlayer1);
                    matchMode.setTeamBSubstitutePlayer1Name(player.getName());
                }
            }
            if (teamBSubstitutePlayer2 != null) {
                Player player = playerMapper.selectById(teamBSubstitutePlayer2);
                if (player == null) {
                    throw new MatchCreateException("参赛选手不存在");
                }
                if (player.getTeamId().equals(matchMode.getTeamBId())) {
                    matchMode.setTeamBSubstitutePlayer2(teamBSubstitutePlayer2);
                    matchMode.setTeamBSubstitutePlayer2Name(player.getName());
                }
            }
        }
        matchModeMapper.updateById(matchMode);
    }

    /**
     * 清空MatchA参赛选手
     *
     * @param clearMatchAPlayerDTO
     */
    @Override
    public void deleteMatchAPlayer(ClearMatchAPlayerDTO clearMatchAPlayerDTO) {
        Integer matchModeId = clearMatchAPlayerDTO.getMatchModeId();
        Boolean isTeamA = clearMatchAPlayerDTO.getIsTeamA();
        MatchMode matchMode = matchModeMapper.selectById(matchModeId);
        if (isTeamA) {
            matchMode.setTeamAPlayer1(null);
            matchMode.setTeamAPlayer1Name(null);
            matchMode.setTeamAPlayer2(null);
            matchMode.setTeamAPlayer2Name(null);
            matchMode.setTeamASubstitutePlayer1(null);
            matchMode.setTeamASubstitutePlayer1Name(null);
            matchMode.setTeamASubstitutePlayer2(null);
            matchMode.setTeamASubstitutePlayer2Name(null);
        } else {
            matchMode.setTeamBPlayer1(null);
            matchMode.setTeamBPlayer1Name(null);
            matchMode.setTeamBPlayer2(null);
            matchMode.setTeamBPlayer2Name(null);
            matchMode.setTeamBSubstitutePlayer1(null);
            matchMode.setTeamBSubstitutePlayer1Name(null);
            matchMode.setTeamBSubstitutePlayer2(null);
            matchMode.setTeamBSubstitutePlayer2Name(null);
        }
        matchModeMapper.updateById(matchMode);
    }

    /**
     * 获取所有未开始的比赛简略信息
     *
     * @param visitorQueryDTO
     * @return
     */
    @Override
    public PageResult getUnStartMatchABrief(VisitorQueryDTO visitorQueryDTO) {
        if (visitorQueryDTO == null) {
            throw new BaseException("visitorQueryDTO cannot be null");
        }
        Page<MatchMode> page = new Page<>(visitorQueryDTO.getPage(), visitorQueryDTO.getPageSize());
        String name = visitorQueryDTO.getName();
        String department = visitorQueryDTO.getDepartment();
        LambdaQueryWrapper<MatchMode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MatchMode::getStatus, StatusConstant.UNSTART)
                //.ge(MatchMode::getBeginTime, LocalDateTime.now())
                .orderByAsc(MatchMode::getBeginTime);
        if (name != null) {
            queryWrapper.and(wrapper -> wrapper
                    .like(MatchMode::getTeamAPlayer1Name, name)
                    .or()
                    .like(MatchMode::getTeamAPlayer2Name, name)
                    .or()
                    .like(MatchMode::getTeamASubstitutePlayer1Name, name)
                    .or()
                    .like(MatchMode::getTeamASubstitutePlayer2Name, name)
                    .or()
                    .like(MatchMode::getTeamBPlayer1Name, name)
                    .or()
                    .like(MatchMode::getTeamBPlayer2Name, name)
                    .or()
                    .like(MatchMode::getTeamBSubstitutePlayer1Name, name)
                    .or()
                    .like(MatchMode::getTeamBSubstitutePlayer2Name, name)
            );
        }
        if (department != null){
            queryWrapper.and(wrapper -> wrapper
                    .like(MatchMode::getTeamADepartment, department)
                    .or()
                    .like(MatchMode::getTeamBDepartment, department)
            );
        }
        page = matchModeMapper.selectPage(page, queryWrapper);
        return new PageResult(page.getTotal(), page.getRecords());
    }


    /**
     * 获取所有进行中的比赛简略信息
     *
     * @param visitorQueryDTO
     * @return
     */
    @Override
    public PageResult getDoingMatchABrief(VisitorQueryDTO visitorQueryDTO) {
        if (visitorQueryDTO == null) {
            throw new BaseException("visitorQueryDTO cannot be null");
        }
        Page<MatchMode> page = new Page<>(visitorQueryDTO.getPage(), visitorQueryDTO.getPageSize());
        String name = visitorQueryDTO.getName();
        String department = visitorQueryDTO.getDepartment();
        LambdaQueryWrapper<MatchMode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MatchMode::getStatus, StatusConstant.DOING)
                //.ge(MatchMode::getBeginTime, LocalDateTime.now())
                .orderByAsc(MatchMode::getBeginTime);
        if (name != null) {
            queryWrapper.and(wrapper -> wrapper
                    .like(MatchMode::getTeamAPlayer1Name, name)
                    .or()
                    .like(MatchMode::getTeamAPlayer2Name, name)
                    .or()
                    .like(MatchMode::getTeamASubstitutePlayer1Name, name)
                    .or()
                    .like(MatchMode::getTeamASubstitutePlayer2Name, name)
                    .or()
                    .like(MatchMode::getTeamBPlayer1Name, name)
                    .or()
                    .like(MatchMode::getTeamBPlayer2Name, name)
                    .or()
                    .like(MatchMode::getTeamBSubstitutePlayer1Name, name)
                    .or()
                    .like(MatchMode::getTeamBSubstitutePlayer2Name, name)
            );
        }
        if (department != null){
            queryWrapper.and(wrapper -> wrapper
                    .like(MatchMode::getTeamADepartment, department)
                    .or()
                    .like(MatchMode::getTeamBDepartment, department)
            );
        }
        page = matchModeMapper.selectPage(page, queryWrapper);
        return new PageResult(page.getTotal(), page.getRecords());
    }

    /**
     * 判决matchMode结束
     *
     * @param matchModeId
     */
    @Override
    public void endMatchA(Integer matchModeId) {
        MatchMode matchMode = matchModeMapper.selectById(matchModeId);
        matchMode.setStatus(StatusConstant.END);
        matchModeMapper.updateById(matchMode);
    }

    /**
     * 查看判分的matchMode信息
     *
     * @param pageQueryDTO
     * @return
     */
    @Override
    public PageResult getRefereeMatchMode(PageQueryDTO pageQueryDTO) {
        Page<MatchMode> page = new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getPageSize());
        // matchModeMapper筛选出refereeId为当前referee的id的matchMode
        // MatchMode的status字段为UNSTART或者DOING
        // 按早时间从近到远排序
        Integer refereeId = BaseContext.getCurrentId();
        QueryWrapper<MatchMode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("referee_id", refereeId).or().eq("substitute_referee_id", refereeId);
        // queryWrapper.eq("status", StatusConstant.UNSTART).or().eq("status", StatusConstant.DOING);
        queryWrapper.orderByAsc("status","begin_time");
        page = matchModeMapper.selectPage(page, queryWrapper);
        return new PageResult(page.getTotal(), page.getRecords());
    }

    /**
     * 裁判开启MatchMode
     *
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
        if (gameCount == 3) {
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
        Integer teamARoundScore1 = scoreDTO.getTeamARoundScore1();
        Integer teamBRoundScore1 = scoreDTO.getTeamBRoundScore1();
        Integer teamARoundScore2 = scoreDTO.getTeamARoundScore2();
        Integer teamBRoundScore2 = scoreDTO.getTeamBRoundScore2();
        Integer teamARoundScore3 = scoreDTO.getTeamARoundScore3();
        Integer teamBRoundScore3 = scoreDTO.getTeamBRoundScore3();

        MatchA matchA = matchAMapper.selectById(matchAId);
        MatchMode matchMode = matchModeMapper.selectById(matchModeId);
        if (matchMode == null) {
            throw new MatchCreateException("matchMode不存在");
        }

        Integer gameCount = matchA.getGameCount();

        matchMode.setTeamARoundScore1(teamARoundScore1);
        matchMode.setTeamBRoundScore1(teamBRoundScore1);

        int teamAGameScore = 0;
        int teamBGameScore = 0;

        if (teamARoundScore1 >= 21 && teamBRoundScore1 >= 21
                && Math.abs(teamARoundScore1 - teamBRoundScore1) >= 2) {
            if (teamARoundScore1 > teamBRoundScore1) {
                teamAGameScore++;
            } else {
                teamBGameScore++;
            }
        }

        if (gameCount == 3) {
            matchMode.setTeamARoundScore2(teamARoundScore2);
            matchMode.setTeamBRoundScore2(teamBRoundScore2);
            matchMode.setTeamARoundScore3(teamARoundScore3);
            matchMode.setTeamBRoundScore3(teamBRoundScore3);

            if (teamARoundScore2 >= 21 && teamBRoundScore2 >= 21
                    && Math.abs(teamARoundScore2 - teamBRoundScore2) >= 2) {
                if (teamARoundScore2 > teamBRoundScore2) {
                    teamAGameScore++;
                } else {
                    teamBGameScore++;
                }
            }
            if (teamARoundScore3 >= 21 && teamBRoundScore3 >= 21
                    && Math.abs(teamARoundScore3 - teamBRoundScore3) >= 2) {
                if (teamARoundScore3 > teamBRoundScore3) {
                    teamAGameScore++;
                } else {
                    teamBGameScore++;
                }
            }
        }

        matchMode.setTeamAGameScore(teamAGameScore);
        matchMode.setTeamBGameScore(teamBGameScore);

        if (teamAGameScore == 2 || teamBGameScore == 2) {
            if (teamAGameScore > teamBGameScore) {
                matchMode.setModeWinnerTeamId(matchA.getTeamAId());
            } else {
                matchMode.setModeWinnerTeamId(matchA.getTeamBId());
            }
            matchMode.setStatus(StatusConstant.END);
        }

        matchModeMapper.updateById(matchMode);
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
        Integer refereeId = matchAModeDTO.getRefereeId();
        Integer substituteRefereeId = matchAModeDTO.getSubstituteRefereeId();
        if (refereeId == null) {
            throw new MatchCreateException("裁判或替补裁判不能为空");
        }
        String refereeName = refereeMapper.selectById(refereeId).getName();
        String substituteRefereeName = refereeMapper.selectById(substituteRefereeId).getName();
        if (matchA == null) {
            throw new MatchCreateException("matchA不存在");
        }
        MatchMode matchMode = MatchMode.builder()
                .mode(mode)
                .matchAId(matchAId)
                .status(StatusConstant.UNSTART)
                .beginTime(beginTime)
                .venueNumber(venueNumber)
                .gameCount(matchA.getGameCount())
                .teamAId(matchA.getTeamAId())
                .teamBId(matchA.getTeamBId())
                .teamADepartment(matchA.getTeamADepartment())
                .teamBDepartment(matchA.getTeamBDepartment())
                .refereeId(refereeId)
                .substituteRefereeId(substituteRefereeId)
                .refereeName(refereeName)
                .substituteRefereeName(substituteRefereeName)
                .build();
        matchModeMapper.insert(matchMode);
    }

    /**
     * 比赛A模式修改
     *
     * @param matchAModeDTO
     */
    @Override
    public void updateMode(MatchAModeDTO matchAModeDTO) {
        Integer matchModeId = matchAModeDTO.getMatchModeId();
        MatchMode matchMode = matchModeMapper.selectById(matchModeId);
        if (matchMode == null) {
            throw new MatchCreateException("matchMode不存在");
        }
        MatchA matchA = matchAMapper.selectById(matchMode.getMatchAId());
        if (matchA == null) {
            throw new MatchCreateException("matchA不存在");
        }
        if (matchAModeDTO.getMode() != null) {
            matchMode.setMode(matchAModeDTO.getMode());
        }
        if (matchAModeDTO.getBeginTime() != null) {
            matchMode.setBeginTime(matchAModeDTO.getBeginTime());
        }
        if (matchAModeDTO.getVenueNumber() != null) {
            matchMode.setVenueNumber(matchAModeDTO.getVenueNumber());
        }
        if (matchAModeDTO.getRefereeId() != null) {
            matchMode.setRefereeId(matchAModeDTO.getRefereeId());
            matchMode.setRefereeName(refereeMapper.selectById(matchAModeDTO.getRefereeId()).getName());
        }
        if (matchAModeDTO.getSubstituteRefereeId() != null) {
            matchMode.setSubstituteRefereeId(matchAModeDTO.getSubstituteRefereeId());
            matchMode.setSubstituteRefereeName(refereeMapper.selectById(matchAModeDTO.getSubstituteRefereeId()).getName());
        }
        matchModeMapper.updateById(matchMode);
    }

    /**
     * 分页查询比赛A模式
     *
     * @param matchModeQueryDTO
     * @return
     */
    @Override
    public PageResult pageMatchAMode(MatchModeQueryDTO matchModeQueryDTO) {
        Page<MatchMode> page = new Page<>(matchModeQueryDTO.getPage(), matchModeQueryDTO.getPageSize());
        QueryWrapper<MatchMode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("match_a_id", matchModeQueryDTO.getMatchAId());
        matchModeMapper.selectPage(page, queryWrapper);
        return new PageResult(page.getTotal(), page.getRecords());
    }

    /**
     * 领队查询负责的matchMode
     *
     * @param pageQueryDTO
     * @return
     */
    @Override
    public PageResult leaderPageMatchMode(PageQueryDTO pageQueryDTO) {
        Page<MatchMode> page = new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getPageSize());
        Integer leaderId = BaseContext.getCurrentId();
        Team team = teamMapper.selectOne(new QueryWrapper<Team>().eq("leader_id", leaderId));

        if (team == null) {
            throw new MatchCreateException("队伍不存在");
        }

        Integer teamId = team.getTeamId();

        QueryWrapper<MatchMode> matchModeQueryWrapper = new QueryWrapper<>();
        matchModeQueryWrapper
                .eq("team_a_id", teamId)
                .or()
                .eq("team_b_id", teamId);

        page = matchModeMapper.selectPage(page, matchModeQueryWrapper);

        Page<LeaderMatchModeVO> pageVO = new Page<>();
        pageVO.setTotal(page.getTotal());
        pageVO.setRecords(page.getRecords().stream().map(matchMode -> {
            LeaderMatchModeVO.LeaderMatchModeVOBuilder builder = LeaderMatchModeVO.builder()
                    .matchModeId(matchMode.getMatchModeId())
                    .matchAId(matchMode.getMatchAId())
                    .mode(matchMode.getMode())
                    .venueNumber(matchMode.getVenueNumber())
                    .beginTime(matchMode.getBeginTime())
                    .refereeId(matchMode.getRefereeId())
                    .refereeName(matchMode.getRefereeName())
                    .substituteRefereeId(matchMode.getSubstituteRefereeId())
                    .teamAId(matchMode.getTeamAId())
                    .teamBId(matchMode.getTeamBId())
                    .teamADepartment(teamMapper.selectById(matchMode.getTeamAId()).getDepartment())
                    .teamBDepartment(teamMapper.selectById(matchMode.getTeamBId()).getDepartment())
                    .modeWinnerTeamId(matchMode.getModeWinnerTeamId())
                    .status(matchMode.getStatus())
                    .substituteRefereeName(matchMode.getSubstituteRefereeName())
                    .teamAPlayer1(matchMode.getTeamAPlayer1())
                    .teamAPlayer1Name(matchMode.getTeamAPlayer1Name())
                    .teamAPlayer2(matchMode.getTeamAPlayer2())
                    .teamAPlayer2Name(matchMode.getTeamAPlayer2Name())
                    .teamASubstitutePlayer1(matchMode.getTeamASubstitutePlayer1())
                    .teamASubstitutePlayer1Name(matchMode.getTeamASubstitutePlayer1Name())
                    .teamASubstitutePlayer2(matchMode.getTeamASubstitutePlayer2())
                    .teamASubstitutePlayer2Name(matchMode.getTeamASubstitutePlayer2Name())
                    .teamBPlayer1(matchMode.getTeamBPlayer1())
                    .teamBPlayer1Name(matchMode.getTeamBPlayer1Name())
                    .teamBPlayer2(matchMode.getTeamBPlayer2())
                    .teamBPlayer2Name(matchMode.getTeamBPlayer2Name())
                    .teamBSubstitutePlayer1(matchMode.getTeamBSubstitutePlayer1())
                    .teamBSubstitutePlayer1Name(matchMode.getTeamBSubstitutePlayer1Name())
                    .teamBSubstitutePlayer2(matchMode.getTeamBSubstitutePlayer2())
                    .teamBSubstitutePlayer2Name(matchMode.getTeamBSubstitutePlayer2Name());
            if (teamId.equals(matchMode.getTeamAId())) {
                builder.isTeamA(true);
            } else if (teamId.equals(matchMode.getTeamBId())) {
                builder.isTeamA(false);
            }
            return builder.build();
        }).collect(Collectors.toList()));

        return new PageResult(pageVO.getTotal(), pageVO.getRecords());
    }

    /**
     * 领队获取matchMode详情
     *
     * @param matchModeId
     * @return
     */
    @Override
    public LeaderMatchModeVO LeaderGetMatchMode(Integer matchModeId) {
        Integer leaderId = BaseContext.getCurrentId();
        Team team = teamMapper.selectOne(new QueryWrapper<Team>().eq("leader_id", leaderId));
        MatchMode matchMode = matchModeMapper.selectById(matchModeId);
        LeaderMatchModeVO leaderMatchModeVO = LeaderMatchModeVO.builder()
                .matchModeId(matchMode.getMatchModeId())
                .matchAId(matchMode.getMatchAId())
                .mode(matchMode.getMode())
                .venueNumber(matchMode.getVenueNumber())
                .refereeId(matchMode.getRefereeId())
                .refereeName(matchMode.getRefereeName())
                .substituteRefereeId(matchMode.getSubstituteRefereeId()).build();
        if (team.getTeamId().equals(matchMode.getTeamAId())) {
            leaderMatchModeVO.setIsTeamA(true);
            leaderMatchModeVO.setTeamAPlayer1(matchMode.getTeamAPlayer1());
            leaderMatchModeVO.setTeamAPlayer1Name(matchMode.getTeamAPlayer1Name());
            leaderMatchModeVO.setTeamAPlayer2(matchMode.getTeamAPlayer2());
            leaderMatchModeVO.setTeamAPlayer2Name(matchMode.getTeamAPlayer2Name());
            leaderMatchModeVO.setTeamASubstitutePlayer1(matchMode.getTeamASubstitutePlayer1());
            leaderMatchModeVO.setTeamASubstitutePlayer1Name(matchMode.getTeamASubstitutePlayer1Name());
            leaderMatchModeVO.setTeamASubstitutePlayer2(matchMode.getTeamASubstitutePlayer2());
            leaderMatchModeVO.setTeamASubstitutePlayer2Name(matchMode.getTeamASubstitutePlayer2Name());
        } else if (team.getTeamId().equals(matchMode.getTeamBId())) {
            leaderMatchModeVO.setIsTeamA(false);
            leaderMatchModeVO.setTeamBPlayer1(matchMode.getTeamBPlayer1());
            leaderMatchModeVO.setTeamBPlayer1Name(matchMode.getTeamBPlayer1Name());
            leaderMatchModeVO.setTeamBPlayer2(matchMode.getTeamBPlayer2());
            leaderMatchModeVO.setTeamBPlayer2Name(matchMode.getTeamBPlayer2Name());
            leaderMatchModeVO.setTeamBSubstitutePlayer1(matchMode.getTeamBSubstitutePlayer1());
            leaderMatchModeVO.setTeamBSubstitutePlayer1Name(matchMode.getTeamBSubstitutePlayer1Name());
            leaderMatchModeVO.setTeamBSubstitutePlayer2(matchMode.getTeamBSubstitutePlayer2());
            leaderMatchModeVO.setTeamBSubstitutePlayer2Name(matchMode.getTeamBSubstitutePlayer2Name());
        }
        return leaderMatchModeVO;
    }

    @Override
    public PageResult getEndMatchABrief(VisitorQueryDTO visitorQueryDTO) {
        if (visitorQueryDTO == null) {
            throw new BaseException("visitorQueryDTO cannot be null");
        }
        Page<MatchMode> page = new Page<>(visitorQueryDTO.getPage(), visitorQueryDTO.getPageSize());
        String name = visitorQueryDTO.getName();
        String department = visitorQueryDTO.getDepartment();
        LambdaQueryWrapper<MatchMode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MatchMode::getStatus, StatusConstant.END)
                //.ge(MatchMode::getBeginTime, LocalDateTime.now())
                .orderByAsc(MatchMode::getBeginTime);
        if (name != null) {
            queryWrapper.and(wrapper -> wrapper
                    .like(MatchMode::getTeamAPlayer1Name, name)
                    .or()
                    .like(MatchMode::getTeamAPlayer2Name, name)
                    .or()
                    .like(MatchMode::getTeamASubstitutePlayer1Name, name)
                    .or()
                    .like(MatchMode::getTeamASubstitutePlayer2Name, name)
                    .or()
                    .like(MatchMode::getTeamBPlayer1Name, name)
                    .or()
                    .like(MatchMode::getTeamBPlayer2Name, name)
                    .or()
                    .like(MatchMode::getTeamBSubstitutePlayer1Name, name)
                    .or()
                    .like(MatchMode::getTeamBSubstitutePlayer2Name, name)
            );
        }
        if (department != null){
            queryWrapper.and(wrapper -> wrapper
                    .like(MatchMode::getTeamADepartment, department)
                    .or()
                    .like(MatchMode::getTeamBDepartment, department)
            );
        }
        page = matchModeMapper.selectPage(page, queryWrapper);
        return new PageResult(page.getTotal(), page.getRecords());
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
     *
     * @param matchQueryDTO
     * @return
     */
    @Override
    public PageResult queryPage(MatchQueryDTO matchQueryDTO) {
        Page<MatchA> page = new Page<>(matchQueryDTO.getPage(), matchQueryDTO.getPageSize());
        QueryWrapper<MatchA> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("event_id", matchQueryDTO.getEventId());
//        if (matchQueryDTO.getDepartment() != null) {
//            queryWrapper
//                    .eq("team_a_department", matchQueryDTO.getDepartment())
//                    .or()
//                    .eq("team_b_department", matchQueryDTO.getDepartment());
//        }
        matchAMapper.selectPage(page, queryWrapper);
        return new PageResult(page.getTotal(), page.getRecords());
    }
}
