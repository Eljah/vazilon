package tatar.tourism.web;

import org.apache.log4j.Logger;
import tatar.tourism.dao.*;
import tatar.tourism.pojo.AverageCountPair;
import tatar.tourism.pojo.Excursion;
import tatar.tourism.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Ilya Evlampiev on 06.11.2015.
 */
@WebServlet("/rating")
public class RatingServlet extends HttpServlet {
    static Logger log = Logger.getLogger(RatingServlet.class);
    static ExcursionDao excursionDao = DaoFactory.getDAOFactory(1).getExcursionDao();
    static ExcursionTripDao excursionTripDao = DaoFactory.getDAOFactory(1).getExcursionTripDao();
    static ExcursionPlanDao excursionPlanDao = DaoFactory.getDAOFactory(1).getExcursionPlanDao();
    static UserDao userDao = DaoFactory.getDAOFactory(1).getUserDao();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doProcess(req, resp);
    }

    // Переопределим стандартные методы
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        String voteId = req.getParameter("vote-id");
        final Excursion excursion = new Excursion();
        try {
            excursion.setDatabaseId(Integer.parseInt(voteId));
            String catId = req.getParameter("cat_id");
            Integer stars = Integer.parseInt(req.getParameter("score"));

            User sessionUser = (User) req.getSession().getAttribute("user");
            excursion.setUser(sessionUser);
            switch (catId) {
                case "1":
                    excursion.setGuideStars(stars);
                    excursionDao.updateVoteGuide(excursion);
                    resp.getWriter().print("{\"status\": \"OK\",\"msg\": \"Your vote counted\"}");
                    break;
                case "2":
                    excursion.setTripStars(stars);
                    excursionDao.updateVoteTrip(excursion);
                    resp.getWriter().print("{\"status\": \"OK\",\"msg\": \"Your vote counted\"}");
                    break;
                case "3":
                    excursion.setPlanStars(stars);
                    excursionDao.updateVotePlan(excursion);
                    resp.getWriter().print("{\"status\": \"OK\",\"msg\": \"Your vote counted\"}");
                    break;
                default: {
                    resp.getWriter().print("{\"status\": \"ERR\",\"msg\": \"Wrong parameter passed! Category id is not set up for the vote!\"}");
                }
            }

            Thread asyncVoteUpdater = new Thread() {

                @Override
                public void run() {
                    Excursion excursionAsyn = excursionDao.read(excursion);
                    AverageCountPair avTrip = excursionDao.averageVote(excursionAsyn.getExcursionTrip());
                    AverageCountPair avPlan = excursionDao.averageVote(excursionAsyn.getExcursionPlan());
                    AverageCountPair avGuide = excursionDao.averageVote(excursionAsyn.getGuide());
                    excursionTripDao.updateVote(excursionAsyn.getExcursionTrip(), avTrip);
                    excursionPlanDao.updateVote(excursionAsyn.getExcursionPlan(), avPlan);
                    userDao.updateVote(excursionAsyn.getExcursionTrip().getLeadingGuide(), avGuide);
                }
            };
            asyncVoteUpdater.start();
        } catch (NumberFormatException numb) {
            resp.getWriter().print("{\"status\": \"ERR\",\"msg\": \"Wrong parameter passed! Only integer votes and vote ids are accepted!\"}");
        }
    }
}
