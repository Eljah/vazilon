package tatar.tourism.web.security;

import tatar.tourism.dao.DaoFactory;
import tatar.tourism.dao.TokenDao;
import tatar.tourism.dao.UserDao;
import tatar.tourism.mail.GreetingEmail;
import org.apache.log4j.Logger;
import tatar.tourism.pojo.Sightseer;
import tatar.tourism.pojo.Token;
import tatar.tourism.pojo.User;
import tatar.tourism.pojo.UserTypes;
import tatar.tourism.web.security.LoginServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Ilya Evlampiev on 31.10.2015.
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    static Logger log = Logger.getLogger(LoginServlet.class);
    static UserDao userDao = DaoFactory.getDAOFactory(1).getUserDao();
    static TokenDao tokenDao = DaoFactory.getDAOFactory(1).getTokenDao();

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //processRequest(req, resp);
        log.info("Starting registering user");
        User newUser=new Sightseer();
        log.debug("Retrieving user name from session");
        newUser.setRole(UserTypes.SIGHTSEER.toString());
        newUser.setUsername(req.getParameter("username"));
        newUser.setFirstname(req.getParameter("firstname"));
        newUser.setLastname(req.getParameter("lastname"));
        //try {
            newUser.setPassword(req.getParameter("password"));
            log.debug("Calculating and setting password for the user");
        //} catch (NoSuchAlgorithmException e) {
        //    log.error("MD5 algorithm not fount");
        //    e.printStackTrace();
       // }
        newUser.setEmail(req.getParameter("email"));
        log.debug("Retrieving user email from request");
        newUser.setLocale(req.getSession().getAttribute("language").toString());

        /*  //jquery submit disable demonstration
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */

        log.debug("Saving user "+newUser.getUsername());
        try {
            userDao.create(newUser);
            newUser=userDao.read(newUser.getUsername());
            log.info("Saving user "+newUser.getUsername()+" succeed");
            Token tk=new Token();
            tk.setUuid(java.util.UUID.randomUUID().toString());
            Calendar c = Calendar.getInstance();
            c.setTime(new java.util.Date()); // Now use today date.
            c.add(Calendar.DATE, 5);
            java.util.Date now_plus_5_days=c.getTime();
            tk.setDeleteDate(now_plus_5_days);
            tk.setUser(newUser);
            tokenDao.create(tk);
            //req.setAttribute("passwordhash", newUser.getPasswordHash());
            getServletContext().getRequestDispatcher("/userCreated.jsp").forward(req, resp);
            //getServletContext().getRequestDispatcher("/logon.jsp").forward(req, resp);
            GreetingEmail ge=new GreetingEmail(req.getSession().getAttribute("language").toString(),newUser.getEmail(), newUser.getUsername(), req.getParameter("password"),tk.getUuid());
            ge.send();
            //MailUtil mu=new MailUtil(newUser.getEmail(), newUser.getUsername(), req.getParameter("password"));
            //mu.start();
            log.info("Sending email to " + newUser.getEmail());
        }
        catch (Exception ex)
        {
            log.error("Saving user "+newUser.getUsername()+" failed");
            getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
        }


    }
    // Переопределим стандартные методы
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
    }
}
