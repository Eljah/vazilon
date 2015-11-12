package tatar.tourism.web;

import org.apache.log4j.Logger;
import tatar.tourism.dao.*;
import tatar.tourism.pojo.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ilya Evlampiev on 07.11.2015.
 */
@WebServlet("/excursionPlanDetails")
public class ExcursionPlanDetailsServlet  extends HttpServlet {

    static Logger log = Logger.getLogger(ExcursionPlanDetailsServlet.class);
    static ExcursionPlanDao excursionPlanDao = DaoFactory.getDAOFactory(1).getExcursionPlanDao();
    static PointDao pointDao = DaoFactory.getDAOFactory(1).getPointDao();


    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String id = req.getParameter("id");

        Sightseer sessionUser = (Sightseer) req.getSession().getAttribute("user");
        if (id != null) {
            int idInt = Integer.parseInt(id);
            //Excursion ex=excursionDao.read(idInt);
            ExcursionPlan exPlan = excursionPlanDao.read(idInt);
            Route route=pointDao.readRoute(exPlan);

            log.info("Plan is got from the db");
            if (sessionUser != null&&exPlan.getAuthor().isYou(sessionUser)) {
                req.setAttribute("excursionPlanDetails", exPlan);
                if (route!=null&&route.getPoints()!=null)
                {
                    req.setAttribute("points", route.getPoints());
                    req.setAttribute("id", id);
                }
            }
            else
            {
                if (sessionUser != null) {
                    log.error("Wrong user " + sessionUser.getUsername() + " tries to access the plan it not belongs to");
                }
                else     log.error("Not logged tries to access the plan it not belongs to");
                resp.setStatus(500, "Only the author can edit the plan");
            }
            if (sessionUser != null) {
                req.setAttribute("sessionUser", sessionUser);
                req.setAttribute("id", id);
            }
        }
        getServletContext().getRequestDispatcher("/excursionPlanDetails.jsp").forward(req, resp);

    }

}
