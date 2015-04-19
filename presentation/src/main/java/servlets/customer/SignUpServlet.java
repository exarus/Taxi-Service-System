package servlets.customer;

import com.netcracker.bootcamp.tracksee.entity.Role;
import com.netcracker.bootcamp.tracksee.entity.Sex;
import com.netcracker.bootcamp.tracksee.logic.RegistrationBean;
import com.netcracker.bootcamp.tracksee.logic.RegistrationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Ruslan Gunavardana.
 */
@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {

    private Logger logger;
    @EJB
    private RegistrationBean controller;

    @Override
    public void init() throws ServletException {
        super.init();
        logger = LogManager.getLogger();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/customer/SignUp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            Sex sex = Sex.valueOf(req.getParameter("sex"));
            Role role = Role.CUSTOMER_USER;
            logger.debug("REGISTRATION. User: " + email);

            //TODO email validation

            boolean emailIsInUse = !controller.registerUser(email, password, role, sex);
            if (emailIsInUse) {
                resp.sendRedirect("/registration?error=true");
            } else {
                req.getRequestDispatcher("/WEB-INF/customer/CheckEmail.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Database unavailable.");
        } catch (RegistrationException e) {
            logger.error(e.getMessage());
            resp.sendRedirect("/registration?error=true&code=email");
        }
    }
}