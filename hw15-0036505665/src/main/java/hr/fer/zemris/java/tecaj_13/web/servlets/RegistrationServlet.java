package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.RegistrationForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/servleti/register")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("form", new RegistrationForm());
        req.getRequestDispatcher("/WEB-INF/pages/RegistrationPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        RegistrationForm form = new RegistrationForm();
        form.fromHttpRequest(req);
        form.validate();

        if (form.hasErrors()) {
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/pages/RegistrationPage.jsp").forward(req, resp);
            return;
        }

        BlogUser newUser = new BlogUser();
        form.toBlogUser(newUser);

        BlogUser dbUser = DAOProvider.getDAO().getBlogUser(newUser.getNick());

        if (dbUser == null) {
            DAOProvider.getDAO().addBlogUser(newUser);

            req.getSession().setAttribute("current.user.id", newUser.getId());
            req.getSession().setAttribute("current.user.fn", newUser.getFirstName());
            req.getSession().setAttribute("current.user.ln", newUser.getLastName());
            req.getSession().setAttribute("current.user.nick", newUser.getNick());
            resp.sendRedirect("RegistrationPage.jsp");

        } else {
            form.setError("nick", "A user with the nickname " + newUser.getNick()
                    + " already exists!");
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/pages/RegistrationPage.jsp").forward(req, resp);
        }
    }
}
