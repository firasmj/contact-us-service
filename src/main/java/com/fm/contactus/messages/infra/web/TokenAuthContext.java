package com.fm.contactus.messages.infra.web;

public record TokenAuthContext(Long tokenId, Long projectId, String tokenEncoded) {
    
}
