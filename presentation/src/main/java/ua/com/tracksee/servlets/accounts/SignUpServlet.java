package ua.com.tracksee.servlets.accounts;

import ua.com.tracksee.logic.RegistrationBean;
import ua.com.tracksee.logic.exception.RegistrationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ruslan Gunavardana
 */
@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    private @EJB RegistrationBean controller;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("pageName", "signUp");
        req.getRequestDispatcher("/WEB-INF/customer/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phoneNumber = req.getParameter("phone-number");

        try {
            controller.registerCustomerUser(email, password, phoneNumber);
        } catch (RegistrationException e) {
            logger.warn(e.getMessage());
            resp.getWriter().append(e.getErrorType());
            return;
        }

        logger.info("Successful sign up. User: {}", email);
        req.getRequestDispatcher("/WEB-INF/customer/checkEmail.jsp").forward(req, resp);
    }
}
