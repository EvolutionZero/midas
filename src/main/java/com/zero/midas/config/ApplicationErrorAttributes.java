package com.zero.midas.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class ApplicationErrorAttributes
        extends DefaultErrorAttributes {
    private static final Logger log = LoggerFactory.getLogger(ApplicationErrorAttributes.class);

    @Autowired
    public ApplicationErrorAttributes(ServerProperties serverProperties) {
        super(serverProperties.getError().isIncludeException());
    }

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.error(ex.getMessage(), ex);
        return super.resolveException(request, response, handler, ex);
    }

    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> attributes = super.getErrorAttributes(webRequest, includeStackTrace);
        return attributes;
    }
}
