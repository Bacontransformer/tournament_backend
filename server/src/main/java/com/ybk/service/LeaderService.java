package com.ybk.service;

import com.ybk.dto.PageQueryDTO;
import com.ybk.dto.role.LeaderDTO;
import com.ybk.dto.role.LeaderLoginDTO;
import com.ybk.entity.Leader;
import com.ybk.result.PageResult;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LeaderService {
    void save(LeaderDTO leaderDto);

    Leader login(LeaderLoginDTO leaderLoginDTO);

    void udpate(LeaderDTO leaderDTO);

    PageResult pageQuery(PageQueryDTO pageQueryDTO);

    void passLeader(List<Integer> ids);

}
