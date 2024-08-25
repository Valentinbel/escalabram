package com.escalabram.escalabram.service;

import com.escalabram.escalabram.model.Match;
import com.escalabram.escalabram.model.Search;

import java.util.List;

public interface MatchService {

    List<Match> createMatchesIfFit(Search search);
}
