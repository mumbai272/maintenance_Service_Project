package com.rest.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.maintenance.Common.Constants;
import com.maintenance.Common.UserContextRetriver;
import com.rest.entity.SessionImpl;
import com.rest.repository.SessionRepository;
import com.rest.service.UserServiceImpl;

/**
 * Servlet Filter implementation class SessionValidationFilter
 */
@Component
public class SessionValidationFilter implements Filter {

    private static final Logger logger = Logger.getLogger(SessionValidationFilter.class);
    private String excludeUrl;
    private SessionRepository sessionRepository;
    private UserServiceImpl userServiceImpl;

    /**
     * Default constructor.
     */
    public SessionValidationFilter() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        try {
            if (!httpRequest.getRequestURI().contains(excludeUrl)) {

                String token = httpRequest.getHeader(Constants.TOKEN_HEADER);
                if (token == null) {
                    throw new RuntimeException("Invalid token is passed");
                }
                validateSession(token, httpRequest);
            }
            chain.doFilter(request, response);


        } catch (Exception e) {
//            throw new RuntimeException("Session validation failed", e);
            request.setAttribute("errorResponse",
                Response.status(500).entity(e.getMessage()).build());
            chain.doFilter(request, response);
        } finally {
            logger.info("removing the context");
            UserContextRetriver.remove();
        }
    }

    private void validateSession(String token, HttpServletRequest request) throws Exception {
        logger.info("validating the token " + token);
        SessionImpl session = sessionRepository.findByToken(token);
        HttpSession httpSession = request.getSession();
        if(httpSession==null && session!=null){
            sessionRepository.delete(session);
            throw new RuntimeException("Session got ended");
        }
        if (httpSession != null && session != null
            && session.getSessionId().equals(httpSession.getId())) {
           long lastAccessed=httpSession.getLastAccessedTime();
           Long userId=(Long) httpSession.getAttribute(Constants.USERID);
           logger.info("valid token, lastAccessed:"+lastAccessed);
           UserContextRetriver.setUsercontext(userServiceImpl.getUserContext(userId));           
        } else {
            throw new RuntimeException("Invalid Token");
        }

    }

    /**
     * @see Filter#init(FilterConfig)
     */
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        this.excludeUrl = fConfig.getInitParameter("excludeUrl");
        ApplicationContext ctx =
            WebApplicationContextUtils
                    .getRequiredWebApplicationContext(fConfig.getServletContext());
        this.sessionRepository = ctx.getBean(SessionRepository.class);
        this.userServiceImpl = ctx.getBean(UserServiceImpl.class);

    }

}
