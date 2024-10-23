package pet.proj.todo.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        StringBuilder requestLog = new StringBuilder();
        requestLog.append(String.format("REQUEST URI: %s\n", request.getRequestURI()));
        requestLog.append(String.format("REQUEST METHOD: %s\n\nHEADERS:\n", request.getMethod()));
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            requestLog.append(String.format("Header: %s = %s\n", headerName, headerValue));
        }

        logger.info("\n\nREQUEST LOG:\n{}\n\n", requestLog);

        filterChain.doFilter(request, response);
    }
}