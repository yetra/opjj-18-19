package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogCommentForm;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogEntryForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String[] pathInfo = req.getPathInfo().substring(1).split("/");
        String nick = pathInfo[0];

        req.setAttribute("nick", nick);

        if (pathInfo.length == 1) {
            req.setAttribute("entries", DAOProvider.getDAO().getBlogUser(nick).getEntires());
            req.getRequestDispatcher("/WEB-INF/pages/BlogEntriesListPage.jsp").forward(req, resp);

        } else if (pathInfo.length == 2) {
            if (pathInfo[1].equals("new")) {
                req.setAttribute("form", new BlogEntryForm());
                req.getRequestDispatcher("/WEB-INF/pages/NewEditBlogEntryPage.jsp").forward(req, resp);

            } else if (pathInfo[1].equals("edit")) {
                Long entryID = Long.parseLong(req.getParameter("id"));
                // TODO validate

                BlogEntryForm form = new BlogEntryForm();
                BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryID);
                // TODO check if null
                form.fromBlogEntry(entry);

                req.setAttribute("form", form);
                req.getRequestDispatcher("/WEB-INF/pages/NewEditBlogEntryPage.jsp").forward(req, resp);

            } else {
                Long entryID = Long.parseLong(pathInfo[1]);
                // TODO validate

                BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryID);
                // TODO check if null
                req.setAttribute("entry", entry);
                req.getRequestDispatcher("/WEB-INF/pages/BlogEntryPage.jsp").forward(req, resp);
            }

        } else {
            //invalid URL
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String[] pathInfo = req.getPathInfo().substring(1).split("/");
        String nick = pathInfo[0];

        req.setAttribute("nick", nick);

        if (pathInfo.length == 2) {
            if (!pathInfo[1].equals("new") && pathInfo[1].equals("edit")) {
                Long entryID = Long.parseLong(pathInfo[1]);
                // TODO validate

                BlogCommentForm form = new BlogCommentForm();
                form.fromHttpRequest(req);
                form.validate();

                if (form.hasErrors()) {
                    req.setAttribute("form", form);
                    req.getRequestDispatcher("/WEB-INF/pages/BlogEntryPage.jsp").forward(req, resp);
                    return;
                }

                BlogComment comment = new BlogComment();
                form.toBlogComment(comment);

                BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryID);
                // TODO check
                comment.setBlogEntry(entry);
                comment.setPostedOn(new Date());

                DAOProvider.getDAO().addBlogComment(comment);

                req.setAttribute("entry", entry);
                resp.sendRedirect("author/" + nick + "/" + entryID);

            } else {
                BlogEntryForm form = new BlogEntryForm();
                form.fromHttpRequest(req);
                form.validate();

                if (form.hasErrors()) {
                    req.setAttribute("form", form);
                    req.getRequestDispatcher("/WEB-INF/pages/NewEditBlogEntry.jsp").forward(req, resp);
                    return;
                }

                BlogEntry entry = new BlogEntry();
                form.toBlogEntry(entry);

                if (pathInfo[1].equals("edit")) {
                    Long entryID = Long.parseLong(req.getParameter("id"));
                    // TODO validate
                    entry.setId(entryID);
                }

                DAOProvider.getDAO().addBlogEntry(entry);
                resp.sendRedirect("author/" + nick);
            }

        } else {
            //invalid URL
        }
    }
}
