package com.escalabram.escalabram.service;

import com.escalabram.escalabram.model.ClimberUser;

public interface ClimberUSerService {

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);

    ClimberUser save(ClimberUser user);

}
