package com.escalabram.escalabram.service;

import com.escalabram.escalabram.model.Role;
import com.escalabram.escalabram.model.enumeration.EnumRole;

import java.util.Optional;

public interface UserRoleService {

    Optional<Role> findByRoleName(EnumRole roleName);
}
