package com.seventeamproject.api.review.controller;

import com.seventeamproject.common.dto.ApiResponse;
import com.seventeamproject.common.security.principal.PrincipalUser;
import com.seventeamproject.example.many.dto.ManyRequest;
import com.seventeamproject.api.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

}
