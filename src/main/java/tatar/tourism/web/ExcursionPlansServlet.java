package tatar.tourism.web;

import tatar.tourism.dao.DaoFactory;
import tatar.tourism.dao.ExcursionPlanDao;
import tatar.tourism.dao.UserDao;
import org.apache.log4j.Logger;
import tatar.tourism.pojo.ExcursionPlan;
import tatar.tourism.pojo.Guide;
import tatar.tourism.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya Evlampiev on 27.10.2015.
 */
@WebServlet("/excursionPlans")
public class ExcursionPlansServlet extends HttpServlet {
    static Logger log = Logger.getLogger(ExcursionPlansServlet.class);
    static ExcursionPlanDao excursionPlanDao = DaoFactory.getDAOFactory(1).getExcursionPlanDao();

   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        List<ExcursionPlan> planList= null;
        try {
            Guide sessionUser= (Guide) req.getSession().getAttribute("user");
            if (sessionUser!=null) {
                planList = excursionPlanDao.getForGuide(sessionUser);
            }
            else planList=new ArrayList<ExcursionPlan>();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.debug("Plan list is got from the db");
        req.setAttribute("plans",planList);
        req.setAttribute("sessionUser",req.getSession().getAttribute("user"));
        getServletContext().getRequestDispatcher("/excursionPlans.jsp").forward(req, resp);    }

    // Переопределим стандартные методы
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String dbId=req.getParameter("dbId");
        String explanation=req.getParameter("explanation");
        String shortExplanation=req.getParameter("shortExplanation");

        List<ExcursionPlan> planList = null;

        if (dbId!=null) {
            ExcursionPlan excursionPlanToBeUpdated = excursionPlanDao.read(Integer.parseInt(dbId));
            if (explanation != null) excursionPlanToBeUpdated.setExplanation(explanation);
            if (shortExplanation != null) excursionPlanToBeUpdated.setShortExplanation(shortExplanation);

            excursionPlanDao.update(excursionPlanToBeUpdated);
            try {
                planList = excursionPlanDao.getForGuide((Guide) req.getSession().getAttribute("user"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
        {
            ExcursionPlan excursionPlanToBeUpdated = new ExcursionPlan();
            if (explanation != null) excursionPlanToBeUpdated.setExplanation(explanation);
            if (shortExplanation != null) excursionPlanToBeUpdated.setShortExplanation(shortExplanation);
            excursionPlanToBeUpdated.setAuthor((Guide) req.getSession().getAttribute("user"));
            excursionPlanDao.create(excursionPlanToBeUpdated);
            try {
                planList = excursionPlanDao.getForGuide((Guide) req.getSession().getAttribute("user"));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        log.debug("Plans list is got from the db");
        req.setAttribute("plans",planList);
        req.setAttribute("sessionUser",req.getSession().getAttribute("user"));
        getServletContext().getRequestDispatcher("/excursionPlans.jsp").forward(req, resp);
    }


}
