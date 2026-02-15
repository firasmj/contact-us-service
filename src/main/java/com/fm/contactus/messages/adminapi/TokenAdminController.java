package com.fm.contactus.messages.adminapi;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fm.contactus.messages.application.service.TokenLifecycleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tokens")
public class TokenAdminController {

    private final TokenLifecycleService tokenLifecycleService;

    public TokenAdminController(TokenLifecycleService tokenLifecycleService) {
        this.tokenLifecycleService = tokenLifecycleService;
    }

    @PostMapping
    public TokenAdminResponse createToken(@Valid @RequestBody CreateTokenRequest request) {
        return TokenAdminMapper.toResponse(
            tokenLifecycleService.createToken(request.projectId(), request.expiresInDays())
        );
    }

    @PostMapping("/rotate")
    public TokenAdminResponse rotateToken(@Valid @RequestBody RotateTokenRequest request) {
        return TokenAdminMapper.toResponse(
            tokenLifecycleService.rotateToken(request.tokenEncoded(), request.expiresInDays())
        );
    }

    @PostMapping("/revoke")
    public TokenAdminResponse revokeToken(@Valid @RequestBody RevokeTokenRequest request) {
        return TokenAdminMapper.toResponse(
            tokenLifecycleService.revokeToken(request.tokenEncoded())
        );
    }
}
