package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.Role;
import com.escalabram.escalabram.model.enumeration.EnumRole;
import com.escalabram.escalabram.repository.UserRoleRepository;
import com.escalabram.escalabram.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    @Override
    public Optional<Role> findByRoleName(EnumRole roleName) {
        return userRoleRepository.findByRoleName(roleName);
    }
}
