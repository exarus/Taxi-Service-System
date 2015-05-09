package ua.com.tracksee.servlets.admin;

/**
 * Created by Katia Stetsiuk
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import ua.com.tracksee.entities.ServiceUserEntity;
import ua.com.tracksee.logic.admin.AdministratorBean;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/admin/searchdriver")
public class SearchDriverServlet extends HttpServlet {
    private String drivers = "drivers";
    private String email= "email";

    @EJB
    private AdministratorBean administratorBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/admin/searchDriver.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String driverEmail = req.getParameter(email);
        req.setAttribute(email, driverEmail);
        List<ServiceUserEntity> driverList = administratorBean.getDriverByEmail(driverEmail);
        req.setAttribute(drivers, driverList);
        resp.getWriter().write(getJsonFromList(driverList));
    }

    /**
     *
     * @param driverList list od drivers to convert into JSON
     * @return String of JSON
     */
    private String getJsonFromList(List<ServiceUserEntity> driverList) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(driverList);
        } catch (IOException e) {
            json = "";
        }
        return json;
    }


}
