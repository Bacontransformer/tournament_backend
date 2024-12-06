package com.ybk.service;

import com.ybk.dto.RefereeLoginDTO;
import com.ybk.entity.Referee;

public interface RefereeService {
    Referee login(RefereeLoginDTO refereeLoginDTO);
}
