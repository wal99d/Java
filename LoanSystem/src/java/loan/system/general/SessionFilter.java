/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loan.system.general;

import java.io.IOException;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author me
 */
public class SessionFilter implements Filter {
   
    private FilterConfig filterConfig = null;
    
    public SessionFilter() {
    }    
    
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session =  req.getSession(false);
        LoginManager lm = new LoginManager();
        
        
        //if(session == null || session.getAttribute("user") == null || session.getAttribute("role") == null || session.getAttribute("deptId") == null || session.getAttribute("userId") == null){
          if(session == null && lm.isLogged() == false && req.getAttribute("userName") == null 
                  && req.getAttribute("deptId")==null && req.getAttribute("userId")==null 
                  && req.getAttribute("role")==null ){   
            if ("partial/ajax".equals(req.getHeader("Faces-Request"))) {
                res.setContentType("text/xml");
                res.getWriter()
                    .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                    .printf("<partial-response><redirect url=\"%s\"></redirect></partial-response>", "/LOAN_SYSTEM/faces/login.xhtml");
            }
            else {
                res.sendRedirect("/LOAN_SYSTEM/faces/login.xhtml");
            }
//            res.sendRedirect("/DArch/faces/login.xhtml");
        } else {
            if (!req.getRequestURI().startsWith(req.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) { // Skip JSF resources (CSS/JS/Images/etc)
                res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
                res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
                res.setDateHeader("Expires", 0); // Proxies.
                
            }
           chain.doFilter(request, response); 
        }
        
    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }
    
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {        
    }

    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        
    }
  
}
