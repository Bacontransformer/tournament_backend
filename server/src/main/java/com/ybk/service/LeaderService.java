package com.ybk.service;

import com.ybk.dto.AuthorizeQueryDTO;
import com.ybk.dto.LeaderDTO;
import com.ybk.dto.LeaderLoginDTO;
import com.ybk.entity.Leader;
import com.ybk.result.PageResult;

public interface LeaderService {
    void save(LeaderDTO leaderDto);

    Leader login(LeaderLoginDTO leaderLoginDTO);

    void udpate(LeaderDTO leaderDTO);

    PageResult pageQuery(AuthorizeQueryDTO authorizeQueryDTO);
}
