package tatar.tourism.web.security;

import tatar.tourism.dao.DaoFactory;
import tatar.tourism.dao.UserDao;
import org.apache.log4j.Logger;
import tatar.tourism.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by Ilya Evlampiev on 26.10.2015.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    static Logger log = Logger.getLogger(LoginServlet.class);
    static UserDao userDao = DaoFactory.getDAOFactory(1).getUserDao();

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String username = req.getParameter("j_username");
        String password = req.getParameter("j_password");
        String referer = req.getHeader("Referer");

        if (username != null) {
            if (password != null) {
                User sessionUser = userDao.read(username, password);
                if (sessionUser != null) {
                    if (sessionUser.isConfirmed()) {
                        req.getSession().setAttribute("language", new Locale(sessionUser.getLocale()));
                        if (sessionUser == null) {
                            req.setAttribute("error", "Incorrect username or password");
                            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
                        } else {
                            req.getSession().setAttribute("user", sessionUser);
                            if (referer.contains("login")) {
                                resp.sendRedirect(referer.replace("login", ""));
                                log.info("The referer is passed to redirect here: " + referer.replace("login", ""));
                            } else {
                                req.getSession().setAttribute("url", referer);
                                resp.sendRedirect(referer.replace("login", ""));
                                log.info("The referer is passed to redirect here: " + referer);
                            }
                        }

                    } else {
                        req.setAttribute("error", "User is not confirmed yet");
                        getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
                    }
                } else {
                    req.setAttribute("error", "Incorrect username or password");
                    getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
                }

            } else {
                req.setAttribute("error", "Username or password was not passed");
                getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error", "Username or password was not passed");
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    // Переопределим стандартные методы
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
