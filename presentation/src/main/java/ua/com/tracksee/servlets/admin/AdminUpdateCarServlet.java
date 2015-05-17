package ua.com.tracksee.servlets.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import ua.com.tracksee.entities.CarEntity;
import ua.com.tracksee.logic.admin.AdministratorBean;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Katia Stetsiuk
 */
@WebServlet("/admin/updatecar")
public class AdminUpdateCarServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private static String warning = "Cannot get json from post /admin/updatecar";
    private static String carNumber = "carNumber";
    private static String car = "car";
    private String carNumb;
    @EJB
    private AdministratorBean administratorBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        carNumb = req.getParameter(carNumber);
        req.setAttribute(car, administratorBean.getCarByNumber(carNumb));
        req.getRequestDispatcher("/WEB-INF/admin/adminUpdateCar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String data = getData(req);
        ObjectMapper mapper = new ObjectMapper();
        CarEntity car = mapper.readValue(data, CarEntity.class);
        car.setCarNumber(carNumb);
        administratorBean.updateCar(car);
    }

    private String getData(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = req.getReader();
            String line;
            do {
                line = reader.readLine();
                sb.append(line).append("\n");
            } while (line != null);
        } catch (IOException e) {
            logger.warn(warning);

        }
        return sb.toString();
    }
}
