package com.seventeamproject.example.one.service;

import com.seventeamproject.common.dto.PageResponse;
import com.seventeamproject.example.many.service.ManyService;
import com.seventeamproject.example.one.dto.OneRequest;
import com.seventeamproject.example.one.dto.OneResponse;
import com.seventeamproject.example.one.dto.OnesResponse;
import com.seventeamproject.example.one.entity.One;
import com.seventeamproject.example.one.repository.OneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OneService {

    private final OneRepository oneRepository;
    private final ManyService manyService;

    @Transactional
    public OneResponse save(OneRequest request) {
        return new OneResponse(oneRepository.save(new One(request.title(), request.content())));
    }

    public PageResponse<OnesResponse> getAll(Pageable pageable, String title) {
        return new PageResponse<>(oneRepository.search(pageable, title));
    }

    public OneResponse getOne(Pageable pageable, Long id) {
        return new OneResponse(
                oneRepository.findById(id).orElseThrow(() -> new IllegalStateException("적절한 에러 메세지")),
                manyService.getAll(pageable, id, null));
    }

    @Transactional
    public OneResponse update(Long id, OneRequest request) {
        return new OneResponse(oneRepository.findById(id).orElseThrow(() -> new IllegalStateException("적절한 에러 메세지")).update(request.title(), request.content()));

    }

    @Transactional
    public void delete(Long id, Long userId) {
        oneRepository.findById(id).orElseThrow(() -> new IllegalStateException("적절한 에러 메세지")).delete(userId);
    }
}
