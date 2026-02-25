package com.seventeamproject.api.admin.repository;

import com.seventeamproject.api.admin.dto.AdminResponse;
import com.seventeamproject.api.admin.enums.AdminRoleEnum;
import com.seventeamproject.api.admin.enums.AdminStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminRepositoryCustom {
    Page<AdminResponse> search(Pageable pageable, String keyword, AdminRoleEnum role, AdminStatusEnum status);
}
