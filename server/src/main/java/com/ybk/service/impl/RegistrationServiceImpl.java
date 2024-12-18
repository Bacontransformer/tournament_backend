package com.ybk.service.impl;

import com.ybk.mapper.RegistrationMapper;
import com.ybk.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private RegistrationMapper registrationMapper;
}
