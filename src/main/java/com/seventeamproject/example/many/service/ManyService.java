package com.seventeamproject.example.many.service;

import com.seventeamproject.common.dto.PageResponse;
import com.seventeamproject.example.many.dto.ManyRequest;
import com.seventeamproject.example.many.dto.ManyResponse;
import com.seventeamproject.example.many.entity.Many;
import com.seventeamproject.example.many.repository.ManyRepository;
import com.seventeamproject.example.one.service.OneReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManyService {

    private final ManyRepository manyRepository;
    private final OneReader oneReader;

    @Transactional
    public ManyResponse save(Long id, ManyRequest request) {
        return new ManyResponse(manyRepository.save(new Many(
                request.content(),
                request.value(),
                oneReader.getEntity(id)
        )));
    }

    public PageResponse<ManyResponse> getAll(Pageable pageable, Long id, String content) {
        return new PageResponse<>(manyRepository.search(pageable, id, content));
    }

    public ManyResponse getOne(Long id) {
        return new ManyResponse(manyRepository.findById(id).orElseThrow(() -> new IllegalStateException("적절한 에러 메세지")));
    }

    @Transactional
    public ManyResponse update(Long id, ManyRequest request) {
        return new ManyResponse(manyRepository.findById(id).orElseThrow(() -> new IllegalStateException("적절한 에러 메세지")).update(request.content(), request.value()));

    }

    @Transactional
    public void delete(Long id, Long userId) {
        manyRepository.findById(id).orElseThrow(() -> new IllegalStateException("적절한 에러 메세지")).delete(userId);
    }
}