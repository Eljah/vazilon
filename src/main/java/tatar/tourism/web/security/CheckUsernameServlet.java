package tatar.tourism.web.security;

import tatar.tourism.dao.DaoFactory;
import tatar.tourism.dao.UserDao;
import org.apache.log4j.Logger;
import tatar.tourism.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ilya Evlampiev on 19.10.2015.
 */
@WebServlet("/checkusername")
public class CheckUsernameServlet extends HttpServlet {
    static Logger log = Logger.getLogger(LoginServlet.class);
    static UserDao userDao = DaoFactory.getDAOFactory(1).getUserDao();

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException, ServletException {
        doProcess(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException, ServletException {
        doProcess(request, response);
    }

    public void doProcess(HttpServletRequest request,
                          HttpServletResponse response
    )
            throws IOException, ServletException

    {
        String username=request.getParameter("username");
            User result=userDao.read(username);
            if (result==null) {      response.setStatus(200);
                response.getWriter().write("true"); //json format );

            }
            else
            {
                response.setStatus(200);
                //response.getWriter().write("\"User already exists\""); //json format );
                response.getWriter().write("false"); //json format );
            }

     /*
        response.getWriter().write("{\n" +
             "    \"isError\": \"true\",\n" +
             "    \"errorMessage\": \"The User Name you chose is already in use. Please enter another name.\"\n" +
             "}");
             */
        //response.getWriter().write("{ result: 'false' }"); //json format );

    }
}
