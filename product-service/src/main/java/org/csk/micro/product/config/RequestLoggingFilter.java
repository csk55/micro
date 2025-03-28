package org.csk.micro.product.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

public class RequestLoggingFilter extends OncePerRequestFilter {

    Logger logger = org.slf4j.LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String traceId = request.getHeader("X-Trace-Id"); // Check if traceId exists
        if (traceId == null || traceId.isEmpty()) {
            logger.info("PrODUCT SERVICE new traceID");
            traceId = UUID.randomUUID().toString(); // Generate if missing
        }

        MDC.put("traceId", traceId); // Set traceId in MDC for logging
        response.setHeader("X-Trace-Id", traceId); // Ensure response carries it

        // Log the request entry
        logger.info("Request PS: {} {} ", request.getMethod(), request.getRequestURI());
//        logger.info("Request: {} {} traceId={}", request.getMethod(), request.getRequestURI(), traceId);

        // Proceed with the request
        filterChain.doFilter(request, response);

        // Log the response status code
        logger.info("Response PS: {} - {} ", request.getMethod(), response.getStatus());
//        logger.info("Response: {} - {} traceId={}", request.getMethod(), response.getStatus(), traceId);

        // Clear MDC after the request is processed
        MDC.clear();
    }
}