package tatar.tourism.security;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ilya Evlampiev on 26.10.2015.
 */
@WebFilter(filterName = "userFilter",urlPatterns = {"/users","/users/*","/excursionPlans","/excursionTrips","/deleteExcursion"})
public class UserFilter implements Filter {

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
            chain.doFilter(request, response);
        } else {
            ((HttpServletRequest) request).getServletContext().getRequestDispatcher("/login.jsp").forward(httpRequest, httpResponse);
        }
    }

}

