package com.ybk.service;

import com.ybk.dto.PageQueryDTO;
import com.ybk.dto.role.PlayerDTO;
import com.ybk.result.PageResult;

import java.util.List;

public interface PlayerService {
    void save(PlayerDTO playerDTO);

    void update(PlayerDTO playerDTO);

    void delete(List<Long> ids);


    PageResult pageQuery(PageQueryDTO pageQueryDTO);
}
