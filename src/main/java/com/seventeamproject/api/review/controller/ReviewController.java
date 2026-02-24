package com.seventeamproject.api.review.controller;

import com.seventeamproject.api.review.dto.ReviewResponse;
import com.seventeamproject.api.review.dto.ReviewUpdateRequest;
import com.seventeamproject.common.dto.ApiResponse;
import com.seventeamproject.common.dto.PageResponse;
import com.seventeamproject.api.review.service.ReviewService;
import com.seventeamproject.common.security.principal.PrincipalUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/v1/reviews")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN', 'CS_ADMIN')")
    public ResponseEntity<ApiResponse> getAll(
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            Authentication authentication,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer rating
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(reviewService.getAll(pageable, keyword, rating)));
    }

    @GetMapping("/v1/reviews/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN', 'CS_ADMIN')")
    public ResponseEntity<ApiResponse> getOne(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(reviewService.getOne(id)));
    }


    @PutMapping("/v1/reviews/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN')")
    public ResponseEntity<ApiResponse> update(
            @PathVariable Long id,
            Authentication authentication,
            @RequestBody ReviewUpdateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(reviewService.update(id, request)));
    }

    @DeleteMapping("/v1/reviews/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN')")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable Long id,
            Authentication authentication
    ) {
        PrincipalUser user = (PrincipalUser) authentication.getPrincipal();
        log.info(user.getId().toString());
        log.info(user.getEmail());

        reviewService.delete(id, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/v1/reviews/{id}/restore")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN')")
    public ResponseEntity<ApiResponse> restore(
            @PathVariable Long id,
            Authentication authentication
    ) {
        reviewService.restore(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}