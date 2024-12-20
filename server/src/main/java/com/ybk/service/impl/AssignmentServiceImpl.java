package com.ybk.service.impl;

import com.ybk.mapper.AssignmentMapper;
import com.ybk.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    @Autowired
    private AssignmentMapper assignmentMapper;
}
