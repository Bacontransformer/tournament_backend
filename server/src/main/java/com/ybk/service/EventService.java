package com.ybk.service;

import com.ybk.dto.EventDTO;
import com.ybk.dto.PageQueryDTO;
import com.ybk.result.PageResult;

public interface EventService {
    void save(EventDTO eventDTO);

    void update(EventDTO eventDTO);

    void delete(Integer id);

    PageResult pageQuery(PageQueryDTO pageQueryDTO);
}
