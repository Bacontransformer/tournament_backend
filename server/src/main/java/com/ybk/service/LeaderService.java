package com.ybk.service;

import com.ybk.dto.LeaderDTO;
import com.ybk.dto.LeaderLoginDTO;
import com.ybk.entity.Leader;

public interface LeaderService {
    void save(LeaderDTO leaderDto);

    Leader login(LeaderLoginDTO leaderLoginDTO);
}
