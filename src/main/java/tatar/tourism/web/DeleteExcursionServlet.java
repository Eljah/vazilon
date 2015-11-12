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
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Ilya Evlampiev on 03.11.2015.
 */
@WebServlet("/deleteExcursion")
public class DeleteExcursionServlet extends HttpServlet {

    static Logger log = Logger.getLogger(DeleteExcursionServlet.class);
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

        String parameterId=null;
        parameterId= req.getParameter("id");
        if (parameterId!=null) {

            ExcursionTrip excursionTrip=new ExcursionTrip();
            excursionTrip.setDatabaseId(Integer.parseInt(parameterId));
            Sightseer sessionUser = (Sightseer) req.getSession().getAttribute("user");
            try {
                sessionUser.removePlannedExcursion(excursionTrip);
                excursionTrip=excursionTripDao.read(excursionTrip.getDatabaseId());
                excursionDao.delete(excursionTrip,sessionUser);
                new Notification(excursionTrip.getLeadingGuide(),sessionUser.getFirstname()+" "+sessionUser.getLastname()+" left for excursion "+excursionTrip.getOrigin().getShortExplanation()+" on "+excursionTrip.getDate());
                planList = excursionPlanDao.getAll();
                tripList = excursionTripDao.getAllInFuture();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            log.debug("Trips and plans list is got from the db");
            req.setAttribute("excplans", planList);
            req.setAttribute("trips", tripList);
            if (sessionUser != null) {
                currenUserPlanTripList = excursionDao.getExcursionTripsIds(sessionUser);
                sessionUser.setPlannedExcursions(currenUserPlanTripList);
                req.setAttribute("sessionUser", sessionUser);
            }
            ;
            //getServletContext().getRequestDispatcher("/excursions.jsp").forward(req, resp);
            resp.sendRedirect("excursions");
        }
        else
        {
            resp.setStatus(500);
            resp.setHeader("Error","The mandatory parameter 'id' was not set");
            new ServletException("The mandatory parameter 'id' was not set");
        }
    }

}
