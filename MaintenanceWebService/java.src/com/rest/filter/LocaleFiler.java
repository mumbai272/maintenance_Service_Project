package com.rest.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.cxf.common.util.StringUtils;

/**
 * Servlet Filter implementation class LocaleFiler
 */
@WebFilter("/*")
public class LocaleFiler implements Filter {

    /**
     * Default constructor. 
     */
    public LocaleFiler() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String locale=request.getParameter("locale");	
		System.out.println("Locale:" + locale);
		if(!StringUtils.isEmpty(locale)){			
			Locale.setDefault(Locale.FRANCE);
		}
		System.out.println(Locale.getDefault());
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
