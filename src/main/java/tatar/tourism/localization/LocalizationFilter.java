package tatar.tourism.localization;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by Ilya Evlampiev on 27.10.2015.
 */
@WebFilter(filterName = "localizationFilter", urlPatterns = {"/*","/"})
public class LocalizationFilter implements Filter {

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

        ((HttpServletRequest) request).setCharacterEncoding("UTF-8");
        if (((HttpServletRequest) request).getParameter("language") != null) {
            ((HttpServletRequest) request).getSession().setAttribute("language",new Locale(((HttpServletRequest) request).getParameter("language")));
            ((HttpServletRequest) request).setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        }
        else
        {
            ((HttpServletRequest) request).setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        }
    }
}
