package com.ybk.service;

import com.ybk.dto.match.AssignmentDTO;
import com.ybk.dto.match.MatchBDTO;

public interface MatchBService {
    void save(MatchBDTO matchBDTO);

    void update(MatchBDTO matchBDTO);

    void delete(Long matchId);

    void setMatchBPlayer(AssignmentDTO assignmentDTO);

    void updateMatchBPlayer(AssignmentDTO assignmentDTO);

    void deleteMatchBPlayer(Long assignmentId);
}
