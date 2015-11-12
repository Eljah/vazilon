package tatar.tourism.web;

import tatar.tourism.dao.DaoFactory;
import tatar.tourism.dao.ExcursionPlanDao;
import tatar.tourism.dao.ExcursionTripDao;
import org.apache.log4j.Logger;
import tatar.tourism.pojo.ExcursionPlan;
import tatar.tourism.pojo.ExcursionTrip;
import tatar.tourism.pojo.Guide;
import tatar.tourism.pojo.Notification;

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
 * Created by Ilya Evlampiev on 27.10.2015.
 */
@WebServlet("/excursionTrips")
public class ExcursionTripsServlet  extends HttpServlet {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    static Logger log = Logger.getLogger(ExcursionTripsServlet.class);
    static ExcursionTripDao excursionTripDao = DaoFactory.getDAOFactory(1).getExcursionTripDao();
    static ExcursionPlanDao excursionPlanDao = DaoFactory.getDAOFactory(1).getExcursionPlanDao();


   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        List<ExcursionTrip> tripList = null;
        try {
            Guide sessionUser = (Guide) req.getSession().getAttribute("user");
            ExcursionPlan excusionPlan=(ExcursionPlan)req.getSession().getAttribute("plan");
            if (excusionPlan != null) {
                tripList = excursionTripDao.getForExcusionPlan(excusionPlan);
            } else tripList = excursionTripDao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.debug("Trips list is got from the db");


        List<ExcursionPlan> planList= null;
        try {
            planList = excursionPlanDao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("excplans", planList);

        req.setAttribute("trips", tripList);
        req.setAttribute("sessionUser",req.getSession().getAttribute("user"));
        getServletContext().getRequestDispatcher("/excursionTrips.jsp").forward(req, resp);
    }

    // Переопределим стандартные методы
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String dbId = req.getParameter("dbId");
        String date = req.getParameter("date");
        String planId = req.getParameter("planId");


        List<ExcursionTrip> tripList = null;

        if (dbId != null) {
            ExcursionTrip excursionTripToBeUpdated = excursionTripDao.read(Integer.parseInt(dbId));
            if (date != null) try {
                excursionTripToBeUpdated.setDate(sdf.parse(req.getParameter("date").trim()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ExcursionPlan ep=excursionPlanDao.read(Integer.parseInt(planId));
            if (planId!=null) excursionTripToBeUpdated.setOrigin(ep);
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
            ExcursionPlan ep=excursionPlanDao.read(Integer.parseInt(planId));
            if (planId!=null) excursionTripToBeUpdated.setOrigin(ep);
            excursionTripToBeUpdated.setLeadingGuide((Guide) req.getSession().getAttribute("user"));
            excursionTripDao.create(excursionTripToBeUpdated);
            new Notification(excursionTripToBeUpdated.getOrigin().getAuthor(),excursionTripToBeUpdated.getLeadingGuide().getFirstname()+" "+excursionTripToBeUpdated.getLeadingGuide().getLastname()+" planned the excursion for your plan "+excursionTripToBeUpdated.getOrigin().getShortExplanation()+" on "+excursionTripToBeUpdated.getDate());

            try {
                tripList = excursionTripDao.getAll();
                //tripList = excursionTripDao.getForExcusionPlan(ep);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        List<ExcursionPlan> planList= null;
        try {
            planList = excursionPlanDao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("excplans", planList);

        log.debug("Plans list is got from the db");
        req.setAttribute("trips", tripList);
        req.setAttribute("sessionUser",req.getSession().getAttribute("user"));
        getServletContext().getRequestDispatcher("/excursionTrips.jsp").forward(req, resp);
    }

}
