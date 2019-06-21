package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.LoginForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A web servlet for rendering the main page of the blog webapp. It handles user login
 * and retrieves a list of existing blog users from the database.
 *
 * @author Bruna Dujmović
 * 
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("form", new LoginForm());
        req.setAttribute("users", DAOProvider.getDAO().getBlogUsers());
        req.getRequestDispatcher("/WEB-INF/pages/MainPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        LoginForm form = new LoginForm();
        form.fromHttpRequest(req);
        form.validate();

        if (form.hasErrors()) {
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/pages/MainPage.jsp").forward(req, resp);
            return;
        }

        BlogUser loginUser = new BlogUser();
        form.toBlogUser(loginUser);

        BlogUser dbUser = DAOProvider.getDAO().getBlogUser(loginUser.getNick());

        if (dbUser == null || !dbUser.getPasswordHash().equals(loginUser.getPasswordHash())) {
            form.setError("nick", "Invalid username or password!");
            form.setError("password", "Invalid username or password!");
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/pages/MainPage.jsp").forward(req, resp);

        } else {
            req.getSession().setAttribute("current.user.id", dbUser.getId());
            req.getSession().setAttribute("current.user.fn", dbUser.getFirstName());
            req.getSession().setAttribute("current.user.ln", dbUser.getLastName());
            req.getSession().setAttribute("current.user.nick", dbUser.getNick());
            req.getSession().setAttribute("current.user.email", dbUser.getEmail());
            resp.sendRedirect("main");
        }
    }
}
