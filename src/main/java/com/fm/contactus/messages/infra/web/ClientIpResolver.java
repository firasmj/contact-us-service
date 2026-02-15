package com.fm.contactus.messages.infra.web;

import jakarta.servlet.http.HttpServletRequest;

public class ClientIpResolver {

    private static final String[] CANDIDATE_HEADERS = {
        "X-Forwarded-For",
        "X-Real-IP",
        "CF-Connecting-IP"
    };

    private ClientIpResolver() {
    }

    public static String resolve(HttpServletRequest request) {
        for (String header : CANDIDATE_HEADERS) {
            String value = request.getHeader(header);
            if (value != null && !value.isBlank()) {
                return value.split(",")[0].trim();
            }
        }

        return request.getRemoteAddr();
    }
}
