package tatar.tourism.web.security;

import org.apache.log4j.Logger;
import tatar.tourism.web.security.LoginServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ilya Evlampiev on 26.10.2015.
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    static Logger log = Logger.getLogger(LoginServlet.class);

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.getSession().removeAttribute("user");
        req.getSession().invalidate();
        resp.sendRedirect("index.jsp");
    }

    // Переопределим стандартные методы
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
