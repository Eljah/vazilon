package tatar.tourism.web.security;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import org.apache.log4j.Logger;
import tatar.tourism.dao.DaoFactory;
import tatar.tourism.dao.UserDao;
import tatar.tourism.pojo.Sightseer;
import tatar.tourism.pojo.User;

@WebServlet("/callback")
public class CallbackServlet extends HttpServlet {
    private static final long serialVersionUID = 6305643034487441839L;
    static Logger log = Logger.getLogger(CallbackServlet.class);
    static UserDao userDao = DaoFactory.getDAOFactory(1).getUserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
        String oauthCode = request.getParameter("code");
        try {
            facebook.getOAuthAccessToken(oauthCode);
            User sessionUser = new Sightseer();
            User facebookUser = userDao.read(facebook.getMe().getId());
            if (facebookUser != null) {
                sessionUser = facebookUser;
                sessionUser.setLocale(facebook.getMe().getLocale().getLanguage());
                sessionUser.setUsername(facebook.getMe().getId());
                sessionUser.setEmail(facebook.getMe().getEmail());
                sessionUser.setPassword("facebook");
                sessionUser.setFirstname(facebook.getMe().getFirstName());
                sessionUser.setLastname(facebook.getMe().getLastName());
                userDao.update(sessionUser);

            } else {
                sessionUser.setLocale(facebook.getMe().getLocale().getLanguage());
                sessionUser.setConfirmed(true);
                sessionUser.setActive(true);
                sessionUser.setUsername(facebook.getMe().getId());
                sessionUser.setEmail(facebook.getMe().getEmail());
                sessionUser.setPassword("facebook");
                sessionUser.setFirstname(facebook.getMe().getFirstName());
                sessionUser.setLastname(facebook.getMe().getLastName());
                userDao.create(sessionUser);
            }
            request.getSession().setAttribute("language", new Locale(facebook.getMe().getLocale().getLanguage()));
            request.getSession().setAttribute("user", sessionUser);
        } catch (FacebookException e) {
            throw new ServletException(e);
        }

        response.sendRedirect(request.getContextPath() + "/");
    }
}