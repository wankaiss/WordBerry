package com.youbanban.wordberry.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Default Error Controller for MVC site.
 * 
 * Related template should be created as well. In the template, one could use
 * thymeleaf predefined variables such as param, session and application
 * 
 * @author allenzhao
 *
 */
@Controller
public class DefaultErrorController implements ErrorController {
    public static final String UNAUTHORIZED_ERROR_VIEW = "401";
    public static final String FORBIDDEN_ERROR_VIEW = "403";
    public static final String NOT_FOUND_ERROR_VIEW = "404";
    public static final String INTERNAL_SERVER_ERROR_VIEW = "error";
    public static final String JSON_EXT = ".json";
    public static final String ERROR_TEMPLATE_PATH = "error/";
    protected Logger log = LoggerFactory.getLogger(DefaultErrorController.class);
    @Autowired
    private ErrorAttributes errorAttributes;

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @RequestMapping(value = "/401", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Map<String, Object> unauthorized(HttpServletRequest request, HttpServletResponse response) {
        return getErrorAttributes(request, false);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @RequestMapping(value = "/403", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Map<String, Object> forbidden(HttpServletRequest request, HttpServletResponse response) {
        return getErrorAttributes(request, false);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @RequestMapping(value = "/404", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Map<String, Object> notFound(HttpServletRequest request, HttpServletResponse response) {
        return getErrorAttributes(request, false);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @RequestMapping(value = "/error", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Map<String, Object> error(HttpServletRequest request, HttpServletResponse response) {
        return getErrorAttributes(request, false);
    }
    
    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        }
        catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
    
    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }
    

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
