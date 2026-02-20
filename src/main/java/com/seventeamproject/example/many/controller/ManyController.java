package com.seventeamproject.example.many.controller;

import com.seventeamproject.common.dto.ApiResponse;
import com.seventeamproject.common.security.principal.PrincipalUser;
import com.seventeamproject.example.many.dto.ManyRequest;
import com.seventeamproject.example.many.service.ManyService;
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
public class ManyController {
    private final ManyService manyService;

    @PostMapping("/example/{id}/manys")
    public ResponseEntity<ApiResponse> save(Authentication authentication,
                                            @PathVariable Long id,
                                            @RequestBody ManyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(manyService.save(id, request)));
    }

    @GetMapping("/example/{id}/manys")
    public ResponseEntity<ApiResponse> getAll(Authentication authentication,
                                              Pageable pageable,
                                              @PathVariable Long id,
                                              @RequestParam(required = false) String content
                                              ) {
        PrincipalUser user = (PrincipalUser) authentication.getPrincipal();
        log.info(user.getId().toString());
        log.info(user.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(manyService.getAll(pageable, id, content)));
    }

    @GetMapping("/example/manys/{id}")
    public ResponseEntity<ApiResponse> get(Authentication authentication,
                                           @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(manyService.getOne(id)));
    }

    @PatchMapping("/example/manys/{id}")
    public ResponseEntity<ApiResponse> update(Authentication authentication,
                                              @PathVariable Long id,
                                              @RequestBody ManyRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(manyService.update(id, request)));
    }

    @DeleteMapping("/example/manys/{id}")
    public ResponseEntity<Void> delelte(Authentication authentication,
                                        @PathVariable Long id) {
        manyService.delete(id, ((PrincipalUser) authentication.getPrincipal()).getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}