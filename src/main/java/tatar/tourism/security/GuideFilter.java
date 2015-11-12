package tatar.tourism.security;

import tatar.tourism.pojo.User;
import tatar.tourism.pojo.UserTypes;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ilya Evlampiev on 27.10.2015.
 */
@WebFilter(filterName = "guideFilter", urlPatterns = {"/executionPlans/*"})
public class GuideFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (((HttpServletRequest) request).getSession().getAttribute("user") != null) {


            if (((User) ((HttpServletRequest) request).getSession().getAttribute("user")).getRole().equals(UserTypes.GUIDE.toString())) {
                chain.doFilter(request, response);
            } else {
                ((HttpServletRequest) request).getServletContext().getRequestDispatcher("/login.jsp").forward(httpRequest, httpResponse);
            }

        } else {
            ((HttpServletRequest) request).getServletContext().getRequestDispatcher("/login.jsp").forward(httpRequest, httpResponse);
        }
    }
}
