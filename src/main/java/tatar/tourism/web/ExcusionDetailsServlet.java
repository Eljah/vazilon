package tatar.tourism.web;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tatar.tourism.dao.*;
import tatar.tourism.pojo.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ilya Evlampiev on 06.11.2015.
 */

@WebServlet("/excursionDetails")
public class ExcusionDetailsServlet extends HttpServlet {

    static Logger log = Logger.getLogger(ExcursionsServlet.class);
    static ExcursionTripDao excursionTripDao = DaoFactory.getDAOFactory(1).getExcursionTripDao();
    static ExcursionPlanDao excursionPlanDao = DaoFactory.getDAOFactory(1).getExcursionPlanDao();
    static ExcursionDao excursionDao = DaoFactory.getDAOFactory(1).getExcursionDao();
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
            ExcursionTrip exTrip = excursionTripDao.read(idInt);
            if (sessionUser != null) {
                Excursion ex = excursionDao.read(exTrip, sessionUser);
                req.setAttribute("excursion", ex);
                Route route = pointDao.readRoute(exTrip.getOrigin());
                req.setAttribute("points", route.getPoints());

                //todo remove
                JSONObject featureCollection = new JSONObject();
                try {
                    featureCollection.put("type", "FeatureCollection");
                    JSONArray featureList = new JSONArray();
                    // iterate through your list
                    for (Point obj : route.getPoints()) {
                        // {"geometry": {"type": "Point", "coordinates": [-94.149, 36.33]}
                        JSONObject point = new JSONObject();
                        point.put("type", "Point");
                        // construct a JSONArray from a string; can also use an array or list
                        JSONArray coord = new JSONArray("["+obj.getLongitude()+","+obj.getLatitude()+"]");
                        point.put("coordinates", coord);
                        JSONObject feature = new JSONObject();
                        feature.put("geometry", point);
                        feature.put("type", "Feature");
                        featureList.put(feature);
                        JSONObject properties = new JSONObject();
                        feature.put("properties", properties);
                        properties.put("name",obj.getName());
                        featureCollection.put("features", featureList);
                    }
                    log.info(featureCollection.toString());
                } catch (JSONException e) {
                    log.error("can't save json object: "+e.toString());
                }
                // output the result
                //System.out.println("featureCollection="+featureCollection.toString());
                req.setAttribute("jsonPoints", featureCollection.toString());


            }
            log.info("Trip is got from the db");
            req.setAttribute("excursionDetails", exTrip);
            req.setAttribute("sessionUser", sessionUser);
            if (sessionUser != null) {
                req.setAttribute("sessionUser", sessionUser);
            }
        }
        getServletContext().getRequestDispatcher("/excursionDetails.jsp").forward(req, resp);

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
        excursionTrip = excursionTripDao.read(excursion.getExcursionTrip().getDatabaseId());
        new Notification(excursionTrip.getLeadingGuide(), excursion.getUser().getFirstname() + " " + excursion.getUser().getLastname() + " signed for your excursion " + excursionTrip.getOrigin().getShortExplanation() + " on " + excursionTrip.getDate());

        List<ExcursionTrip> tripList = null;
        List<ExcursionPlan> planList = null;
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
        ;
        getServletContext().getRequestDispatcher("/excursions.jsp").forward(req, resp);
    }

}
