package tatar.tourism.web.security;

import org.apache.log4j.Logger;
import tatar.tourism.dao.DaoFactory;
import tatar.tourism.dao.TokenDao;
import tatar.tourism.dao.UserDao;
import tatar.tourism.mail.ConfirmationEmail;
import tatar.tourism.pojo.Token;
import tatar.tourism.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by Ilya Evlampiev on 02.11.2015.
 */
@WebServlet("/confirm")
public class ConfirmServlet extends HttpServlet {
    static Logger log = Logger.getLogger(ConfirmServlet.class);
    static UserDao userDao = DaoFactory.getDAOFactory(1).getUserDao();
    static TokenDao tokenDao = DaoFactory.getDAOFactory(1).getTokenDao();


    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String uuid = req.getParameter("uuid");
        String referer = req.getHeader("referer");
        log.trace("User has accessed the confirmation service from " + referer);
        if (uuid != null) {
            Token tk = tokenDao.readToken(uuid);
            if (tk != null) {
                User sessionUser = tk.getUser();
                if (sessionUser == null) {
                    req.setAttribute("error", "Yor confirmation link has expired");
                    getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
                } else {
                    if (!sessionUser.isConfirmed()) {
                        req.getSession().setAttribute("language", new Locale(sessionUser.getLocale()));
                        req.getSession().setAttribute("user", sessionUser);
                        tokenDao.deleteToken(tk);
                        sessionUser.setConfirmed(true);
                        userDao.update(sessionUser);
                        ConfirmationEmail ce = new ConfirmationEmail(sessionUser.getLocale(), sessionUser.getEmail(), sessionUser.getUsername());
                        getServletContext().getRequestDispatcher("/confirmed.jsp").forward(req, resp);
                        ce.send();
                    } else {
                        req.setAttribute("error", "User was already confirmed before");
                        getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
                    }
                }
            } else {
                req.setAttribute("error", "Yor confirmation link has expired");
                getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error", "Yor confirmation link has expired");
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
