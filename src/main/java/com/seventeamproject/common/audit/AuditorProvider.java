package com.seventeamproject.common.audit;

import com.seventeamproject.common.security.principal.PrincipalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorProvider implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated())
            return null;

        PrincipalUser user = (PrincipalUser) auth.getPrincipal();
        return Optional.of(user.getId());
    }
}
