/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import account.AccountDTO;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.MultipartConfig;
import truongtn.utils.MyToys;

/**
 *
 * @author truongtn
 */

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)
public class RoleFilter implements Filter {

    private static final boolean debug = true;

    private final static Logger LOGGER = Logger.getLogger(RoleFilter.class.getName());

    private final static String HOME_PAGE = "home.jsp";
    private final static String LOGIN_PAGE = "login.html";

    private final static String START_APPLICATION = "StartApplication";

    private final String ADMIN_AUTHORITY_PATH = "truongtn.authority.Admin";
    private final String GUEST_AUTHORITY_PATH = "truongtn.authority.Guest";
    private final String USER_AUTHORITY_PATH = "truongtn.authority.User";

    private final List<String> admin;
    private final List<String> guest;
    private final List<String> user;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public RoleFilter() {
        MyToys mt = new MyToys();
        admin = mt.getResourceBundleList(ADMIN_AUTHORITY_PATH);
        guest = mt.getResourceBundleList(GUEST_AUTHORITY_PATH);
        user = mt.getResourceBundleList(USER_AUTHORITY_PATH);

    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("CacheFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
	/*
         for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
         String name = (String)en.nextElement();
         String values[] = request.getParameterValues(name);
         int n = values.length;
         StringBuffer buf = new StringBuffer();
         buf.append(name);
         buf.append("=");
         for(int i=0; i < n; i++) {
         buf.append(values[i]);
         if (i < n-1)
         buf.append(",");
         }
         log(buf.toString());
         }
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("CacheFilter:DoAfterProcessing");
        }

	// Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
	/*
         for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
         String name = (String)en.nextElement();
         Object value = request.getAttribute(name);
         log("attribute: " + name + "=" + value.toString());

         }
         */
        // For example, a filter might append something to the response.
	/*
         PrintWriter respOut = new PrintWriter(response.getWriter());
         respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        System.out.println("URI ROLE: " + uri);
        int index = uri.lastIndexOf("/");
        String resource = uri.substring(index + 1);
        String productNamme = request.getParameter("txtProductName");
        try {
            System.out.println("PRODUCTNAME: " + productNamme);
            if(MyToys.isNullOrEmpty(resource)){
                System.out.println("Resource empty");
                chain.doFilter(request, response);
                return;
            }
            if ((uri.contains(".js") || uri.contains(".jpg") || uri.contains(".png")
                    || uri.contains(".gif") || uri.contains("jpeg") || uri.contains(".css")
                    || uri.contains(".woff") || uri.contains(".ttf")) && !uri.contains(".jsp") && !uri.contains(".html")) {
                chain.doFilter(request, response);
            } else {
                String action = req.getParameter("btnAction");

                HttpSession session = req.getSession(false);
                System.out.println("RESOURCE ROLE: " + resource);
                if (session == null || session.getAttribute("USER") == null) {
                    if (guest.contains(resource)) {
                        System.out.println("1");
                        chain.doFilter(request, response);
//                        return;
                    } else {
                        System.out.println("2");
                        RequestDispatcher rd = req.getRequestDispatcher(LOGIN_PAGE);
                        rd.forward(request, response);
                    }
                } else {

                    AccountDTO acc = (AccountDTO) session.getAttribute("USER");
                    int role = acc.getRoleId();
                    if (role == 1) {
                        if (user.contains(resource) || user.contains(action)) {
                            chain.doFilter(request, response);
                        } else {
                            res.sendRedirect(HOME_PAGE);
                        }
                    } else if (role == 2) {
                        if (admin.contains(resource) || admin.contains(action)) {
                            chain.doFilter(request, response);
                        } else {
                            res.sendRedirect(HOME_PAGE);
                        }
                    } else {
                        res.sendRedirect(HOME_PAGE);
                    }
                }
            }
        } catch (IOException | ServletException t) {
            LOGGER.error("Error at RoleFilter: " + t.getMessage());
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("CacheFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("CacheFilter()");
        }
        StringBuffer sb = new StringBuffer("CacheFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
