package com.seventeamproject.example.one.controller;

import com.seventeamproject.common.dto.ApiResponse;
import com.seventeamproject.common.security.principal.PrincipalUser;
import com.seventeamproject.example.one.dto.OneRequest;
import com.seventeamproject.example.one.service.OneService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OneController {
    private final OneService oneService;

    @PostMapping("/example/ones")
    public ResponseEntity<ApiResponse> save(Authentication authentication,
                                            @RequestBody OneRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(oneService.save(request)));
    }

    @GetMapping("/example/ones")
    public ResponseEntity<ApiResponse> getAll(Authentication authentication,
                                              Pageable pageable,
                                              @RequestParam(required = false) String title) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(oneService.getAll(pageable, title)));
    }

    @GetMapping("/example/ones/{id}")
    public ResponseEntity<ApiResponse> get(Authentication authentication,
                                           Pageable pageable,
                                           @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(oneService.getOne(pageable, id)));
    }

    @PatchMapping("/example/ones/{id}")
    public ResponseEntity<ApiResponse> update(Authentication authentication,
                                              @PathVariable Long id,
                                              @RequestBody OneRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(oneService.update(id, request)));
    }

    @DeleteMapping("/example/ones/{id}")
    public ResponseEntity<Void> delete(Authentication authentication,
                                       @PathVariable Long id) {
        oneService.delete(id, ((PrincipalUser) authentication.getPrincipal()).getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}