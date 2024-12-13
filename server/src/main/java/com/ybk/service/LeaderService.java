package com.ybk.service;

import com.ybk.dto.PageQueryDTO;
import com.ybk.dto.LeaderDTO;
import com.ybk.dto.LeaderLoginDTO;
import com.ybk.entity.Leader;
import com.ybk.result.PageResult;

import java.util.List;

public interface LeaderService {
    void save(LeaderDTO leaderDto);

    Leader login(LeaderLoginDTO leaderLoginDTO);

    void udpate(LeaderDTO leaderDTO);

    PageResult pageQuery(PageQueryDTO pageQueryDTO);

    void passLeader(List<Integer> ids);
}
