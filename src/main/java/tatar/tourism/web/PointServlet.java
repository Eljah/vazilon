package tatar.tourism.web;

import org.apache.log4j.Logger;
import tatar.tourism.dao.DaoFactory;
import tatar.tourism.dao.ExcursionPlanDao;
import tatar.tourism.dao.ExcursionTripDao;
import tatar.tourism.dao.PointDao;
import tatar.tourism.pojo.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Ilya Evlampiev on 07.11.2015.
 */
@WebServlet("/point")
public class PointServlet extends HttpServlet {

    static Logger log = Logger.getLogger(ExcursionsServlet.class);
    static ExcursionPlanDao excursionPlanDao = DaoFactory.getDAOFactory(1).getExcursionPlanDao();
    static ExcursionTripDao excursionTripDao = DaoFactory.getDAOFactory(1).getExcursionTripDao();
    static PointDao pointDao = DaoFactory.getDAOFactory(1).getPointDao();


    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String id = req.getParameter("id");

        Sightseer sessionUser = (Sightseer) req.getSession().getAttribute("user");
        if (id != null) {
            int idInt = Integer.parseInt(id);
            Point point = pointDao.readPoint(idInt);
            Route route = pointDao.readRoute(point);
            ExcursionPlan excursionPlan = route.getPlan();

            log.info("Plan is got from the db");
            if (sessionUser != null && point.getAuthor().isYou(sessionUser)) {
                req.setAttribute("point", point);
                req.setAttribute("excursionPlan", excursionPlan);

            } else {
                if (sessionUser != null) {
                    log.error("Wrong user " + sessionUser.getUsername() + " tries to access the plan it not belongs to");
                } else log.error("Not logged tries to access the plan it not belongs to");
                resp.setStatus(500, "Only the author can edit the plan");
            }
            if (sessionUser != null) {
                req.setAttribute("sessionUser", sessionUser);
            }
            ;
            getServletContext().getRequestDispatcher("/point.jsp").forward(req, resp);
        } else {
            String route = req.getParameter("route");
            if (route != null) {
                if (sessionUser != null) {
                    req.setAttribute("route", route);
                    ExcursionPlan excursionPlan = excursionPlanDao.read(Integer.parseInt(route));
                    req.setAttribute("excursionPlan", excursionPlan);
                } else {
                    if (sessionUser != null) {
                        log.error("Wrong user " + sessionUser.getUsername() + " tries to access the plan it not belongs to");
                    } else log.error("Not logged tries to access the plan it not belongs to");
                    resp.setStatus(500, "Only the author can edit the plan");
                }
                if (sessionUser != null) {
                    req.setAttribute("sessionUser", sessionUser);
                }
                getServletContext().getRequestDispatcher("/point.jsp").forward(req, resp);
            }
        }
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String id = req.getParameter("id");
        String latitude = req.getParameter("latitude");
        String longitude = req.getParameter("longitude");
        String name = req.getParameter("name");
        String description = req.getParameter("description");


        Sightseer sessionUser = (Sightseer) req.getSession().getAttribute("user");
        String pointForwardId = "";
        if (id != null) {
            int idInt = Integer.parseInt(id);
            Point point = pointDao.readPoint(idInt);
            point.setLongitude(Float.parseFloat(longitude));
            point.setLatitude(Float.parseFloat(latitude));
            point.setName(name);
            point.setDescription(description);
            pointDao.updatePoint(point, sessionUser);
            pointForwardId = point.getId() + "";
            log.info("Plan is got from the db");
            if (sessionUser != null && point.getAuthor().isYou(sessionUser)) {
                req.setAttribute("point", point);
            } else {
                if (sessionUser != null) {
                    log.error("Wrong user " + sessionUser.getUsername() + " tries to access the plan it not belongs to");
                } else log.error("Not logged tries to access the plan it not belongs to");
                resp.setStatus(500, "Only the author can edit the plan");
            }
            if (sessionUser != null) {
                req.setAttribute("sessionUser", sessionUser);
            }
        } else {
            String route = req.getParameter("route");
            if (route != null) {
                ExcursionPlan ex = new ExcursionPlan();
                ex.setDatabaseId(Integer.parseInt(route));
                Point p = new Point();
                Route r = pointDao.readRoute(ex);
                LinkedList<Point> a = new LinkedList<Point>();
                a.add(p);
                r.setPoints(a);
                p.setLongitude(Float.parseFloat(longitude));
                p.setLatitude(Float.parseFloat(latitude));
                p.setName(name);
                p.setDescription(description);
                p.setAuthor((Guide) sessionUser);
                r = pointDao.create(r);
                p = r.getPoints().getLast();
                pointForwardId = p.getId() + "";
                log.info("Plan is got from the db");
                req.setAttribute("point", p);
                req.setAttribute("sessionUser", sessionUser);
            }
        }
        getServletContext().getRequestDispatcher("/point.jsp").forward(req, resp);
        resp.sendRedirect("point?id=" + pointForwardId);
    }

}
