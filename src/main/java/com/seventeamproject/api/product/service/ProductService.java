package com.seventeamproject.api.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    @Transactional
    public boolean adjustStock(Long id, Long qty) {
        return true;
    }
}
