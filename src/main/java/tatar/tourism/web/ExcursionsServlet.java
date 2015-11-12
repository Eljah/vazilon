package tatar.tourism.web;

import org.apache.log4j.Logger;
import tatar.tourism.dao.DaoFactory;
import tatar.tourism.dao.ExcursionDao;
import tatar.tourism.dao.ExcursionPlanDao;
import tatar.tourism.dao.ExcursionTripDao;
import tatar.tourism.pojo.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Ilya Evlampiev on 02.11.2015.
 */
@WebServlet("/excursions")
public class ExcursionsServlet extends HttpServlet {

    static Logger log = Logger.getLogger(ExcursionsServlet.class);
    static ExcursionTripDao excursionTripDao = DaoFactory.getDAOFactory(1).getExcursionTripDao();
    static ExcursionPlanDao excursionPlanDao = DaoFactory.getDAOFactory(1).getExcursionPlanDao();
    static ExcursionDao excursionDao = DaoFactory.getDAOFactory(1).getExcursionDao();


    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        List<ExcursionTrip> tripList = null;
        List<ExcursionPlan> planList = null;
        List<ExcursionTrip> currenUserPlanTripList = null;

        Sightseer sessionUser = (Sightseer) req.getSession().getAttribute("user");
        try {
            planList = excursionPlanDao.getAll();
            tripList = excursionTripDao.getAllInFuture();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.debug("Trips and plans list is got from the db");
        req.setAttribute("excplans", planList);
        req.setAttribute("trips", tripList);
        if (sessionUser != null) {
            currenUserPlanTripList=excursionDao.getExcursionTripsIds(sessionUser);
            sessionUser.setPlannedExcursions(currenUserPlanTripList);
            req.setAttribute("sessionUser", sessionUser);
        }
        ;
        getServletContext().getRequestDispatcher("/excursions.jsp").forward(req, resp);
    }

    // Переопределим стандартные методы
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        Sightseer sessionUser = (Sightseer) req.getSession().getAttribute("user");

        String dbId = req.getParameter("dbId");
        String planId = req.getParameter("planId");
        Excursion excursion = new Excursion();
        excursion.setUser(sessionUser);
        ExcursionTrip excursionTrip = new ExcursionTrip();
        excursionTrip.setDatabaseId(Integer.parseInt(dbId));
        excursion.setExcursionTrip(excursionTrip);
        ExcursionPlan excursionPlan = new ExcursionPlan();
        excursionPlan.setDatabaseId(Integer.parseInt(planId));
        excursionTrip.setOrigin(excursionPlan);
        excursion.setExcursionPlan(excursionPlan);
        excursionDao.create(excursion);
        sessionUser.addPlannedExcursion(excursion.getExcursionTrip());
        excursionTrip=excursionTripDao.read(excursion.getExcursionTrip().getDatabaseId());
        new Notification(excursionTrip.getLeadingGuide(),excursion.getUser().getFirstname()+" "+excursion.getUser().getLastname()+" signed for your excursion "+excursionTrip.getOrigin().getShortExplanation()+" on "+excursionTrip.getDate());

        List<ExcursionTrip> tripList = null;
        List<ExcursionPlan> planList = null;

        /*
        List<ExcursionTrip> tripList = null;

        if (dbId != null) {
            ExcursionTrip excursionTripToBeUpdated = excursionTripDao.read(Integer.parseInt(dbId));
            if (date != null) try {
                excursionTripToBeUpdated.setDate(sdf.parse(req.getParameter("date").trim()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ExcursionPlan ep = excursionPlanDao.read(Integer.parseInt(planId));
            if (planId != null) excursionTripToBeUpdated.setOrigin(ep);
            excursionTripDao.update(excursionTripToBeUpdated);
            try {
                tripList = excursionTripDao.getAll();
                //tripList = excursionTripDao.getForExcusionPlan(ep);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            ExcursionTrip excursionTripToBeUpdated = new ExcursionTrip();
            if (date != null) try {
                excursionTripToBeUpdated.setDate(sdf.parse(req.getParameter("date").trim()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ExcursionPlan ep = excursionPlanDao.read(Integer.parseInt(planId));
            if (planId != null) excursionTripToBeUpdated.setOrigin(ep);
            excursionTripToBeUpdated.setLeadingGuide((Guide) req.getSession().getAttribute("user"));
            excursionTripDao.create(excursionTripToBeUpdated);
            try {
                tripList = excursionTripDao.getAll();
                //tripList = excursionTripDao.getForExcusionPlan(ep);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        */
        try {
            planList = excursionPlanDao.getAll();
            tripList = excursionTripDao.getAllInFuture();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("excplans", planList);

        log.debug("Plans list is got from the db");
        req.setAttribute("excplans", planList);
        req.setAttribute("trips", tripList);
        if (sessionUser != null) {
            req.setAttribute("sessionUser", sessionUser);
        }
        getServletContext().getRequestDispatcher("/excursions.jsp").forward(req, resp);
    }

}
