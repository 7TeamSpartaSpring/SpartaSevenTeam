package com.seventeamproject.example.one.service;

import com.seventeamproject.example.one.entity.One;
import com.seventeamproject.example.one.repository.OneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OneReader {
    private final OneRepository oneRepository;


    public One getEntity(Long id) {
        return oneRepository.findById(id).orElseThrow(() -> new IllegalStateException("적절한 에러 메세지"));
    }
}
