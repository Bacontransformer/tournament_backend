package com.ybk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ybk.dto.EventDTO;
import com.ybk.dto.PageQueryDTO;
import com.ybk.entity.Event;
import com.ybk.mapper.EventMapper;
import com.ybk.result.PageResult;
import com.ybk.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventMapper eventMapper;

    /**
     * 新建活动
     *
     * @param eventDTO
     */
    @Override
    public void save(EventDTO eventDTO) {
        // 新建活动
        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setIntroduction(eventDTO.getIntroduction());
        event.setStadium(eventDTO.getStadium());
        event.setRequiredAreaCount(eventDTO.getRequiredAreaCount());
        event.setRequiredRefereeCount(eventDTO.getRequiredRefereeCount());
        event.setBeginTime(eventDTO.getBeginTime());
        event.setEndTime(eventDTO.getEndTime());
        event.setMatchType(eventDTO.getMatchType());
        event.setCreateTime(LocalDateTime.now());
        event.setUpdateTime(LocalDateTime.now());
        eventMapper.insert(event);
    }

    /**
     * 修改活动
     *
     * @param eventDTO
     */
    @Override
    public void update(EventDTO eventDTO) {
        Event event = new Event();
        event.setEventId(eventDTO.getEventId());
        if (eventDTO.getName() != null && !eventDTO.getName().isEmpty()) {
            event.setName(eventDTO.getName());
        }
        if (eventDTO.getIntroduction() != null && !eventDTO.getIntroduction().isEmpty()) {
            event.setIntroduction(eventDTO.getIntroduction());
        }
        if (eventDTO.getStadium() != null && !eventDTO.getStadium().isEmpty()) {
            event.setStadium(eventDTO.getStadium());
        }
        if (eventDTO.getRequiredAreaCount() != null) {
            event.setRequiredAreaCount(eventDTO.getRequiredAreaCount());
        }
        if (eventDTO.getRequiredRefereeCount() != null) {
            event.setRequiredRefereeCount(eventDTO.getRequiredRefereeCount());
        }
        if (eventDTO.getBeginTime() != null) {
            event.setBeginTime(eventDTO.getBeginTime());
        }
        if (eventDTO.getEndTime() != null) {
            event.setEndTime(eventDTO.getEndTime());
        }
        if (eventDTO.getMatchType() != null && !eventDTO.getMatchType().isEmpty()) {
            event.setMatchType(eventDTO.getMatchType());
        }
        event.setUpdateTime(LocalDateTime.now());
        eventMapper.updateById(event);
    }

    /**
     * 删除活动
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        eventMapper.deleteById(id);
    }

    @Override
    public PageResult pageQuery(PageQueryDTO pageQueryDTO) {
        // 分页查询尚未结束的活动，也就是活动endTime大于等于当前日期的活动
        Page<Event> page = new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getPageSize());
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("end_time", LocalDateTime.now());
        Page<Event> eventPage = eventMapper.selectPage(page, queryWrapper);
        PageResult pageResult = new PageResult(eventPage.getTotal(), eventPage.getRecords());
        return pageResult;
    }
}
