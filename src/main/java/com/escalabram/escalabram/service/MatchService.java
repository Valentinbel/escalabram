package com.escalabram.escalabram.service;

import com.escalabram.escalabram.model.Match;

import java.util.Set;

public interface MatchService {

    Set<Match> createMatchesIfFit(Long searchId);
}