package ua.com.tracksee.servlets.customer;

import ua.com.tracksee.entities.ServiceUserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Ruslan Gunavardana
 */
@WebServlet("/signin")
public class SignInServlet extends HttpServlet {
    private Logger logger;

    private static final int SESSION_MAX_INACTIVE_INTERVAL = 60 * 60;

    @Override
    public void init() throws ServletException {
        super.init();
        logger = LogManager.getLogger();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("pageName", "signIn");
        req.getRequestDispatcher("/WEB-INF/customer/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        ServiceUserEntity user = null;
        //TODO  checkAndGetUser(req.getParameter("email"), req.getParameter("password"));
        if (user == null) {
            resp.sendRedirect(req.getParameter("redirect_url"));
        } else {
            session = req.getSession(true);
            session.setMaxInactiveInterval(SESSION_MAX_INACTIVE_INTERVAL);
            session.setAttribute("user", user);
            resp.sendRedirect(req.getParameter("redirect_url"));
        }
    }
}
