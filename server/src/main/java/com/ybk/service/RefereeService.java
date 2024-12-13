package com.ybk.service;

import com.ybk.dto.AuthorizeQueryDTO;
import com.ybk.dto.RefereeDTO;
import com.ybk.dto.RefereeLoginDTO;
import com.ybk.entity.Referee;
import com.ybk.result.PageResult;

import java.util.List;

public interface RefereeService {
    Referee login(RefereeLoginDTO refereeLoginDTO);

    PageResult pageQuery(AuthorizeQueryDTO authorizeQueryDTO);

    void passReferee(List<Integer> ids);

    void save(RefereeDTO refereeDTO);

    void udpate(RefereeDTO refereeDTO);
}
