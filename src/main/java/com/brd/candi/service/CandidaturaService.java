package com.brd.candi.service;

import com.brd.candi.repository.CandidaturaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CandidaturaService {
    final CandidaturaRepository candidaturaRepository;
}
