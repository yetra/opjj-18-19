package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * A web servlet for displaying, creating or updating an user's blog entries and
 * comments.
 *
 * @author Bruna Dujmović
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String[] pathInfo = req.getPathInfo().substring(1).split("/");

        String nick = pathInfo[0];
        if (DAOProvider.getDAO().getBlogUser(nick) == null) {
            req.setAttribute("errorMessage", "The user " + nick + " does not exist!");
            req.getRequestDispatcher("/WEB-INF/pages/ErrorPage.jsp").forward(req, resp);
            return;
        }
        req.setAttribute("nick", nick);

        if (pathInfo.length == 1) {
            // TODO use entries attribute of user
            req.setAttribute("entries", DAOProvider.getDAO().getBlogEntriesFor(nick));
            req.getRequestDispatcher("/WEB-INF/pages/BlogEntriesListPage.jsp").forward(req, resp);

        } else if (pathInfo.length == 2) {
            if (pathInfo[1].equals("new")) {
                checkSessionNick(nick, req, resp);

                req.setAttribute("form", new BlogEntryForm());
                req.setAttribute("formAction", req.getContextPath() + "/servleti/author/" + nick + "/new");
                req.getRequestDispatcher("/WEB-INF/pages/NewEditBlogEntryPage.jsp").forward(req, resp);

            } else if (pathInfo[1].equals("edit")) {
                checkSessionNick(nick, req, resp);

                BlogEntryForm form = new BlogEntryForm();
                String id = req.getParameter("id");
                BlogEntry entry = getEntryFromParameter(id, req, resp);

                form.fromBlogEntry(entry);
                req.setAttribute("form", form);
                req.setAttribute("formAction", req.getContextPath() + "/servleti/author/" + nick + "/edit?id=" + id);
                req.getRequestDispatcher("/WEB-INF/pages/NewEditBlogEntryPage.jsp").forward(req, resp);

            } else {
                BlogEntry entry = getEntryFromParameter(pathInfo[1], req, resp);

                req.setAttribute("entry", entry);
                req.getRequestDispatcher("/WEB-INF/pages/BlogEntryPage.jsp").forward(req, resp);
            }

        } else {
            req.setAttribute("errorMessage", "Invalid URL!");
            req.getRequestDispatcher("/WEB-INF/pages/ErrorPage.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String[] pathInfo = req.getPathInfo().substring(1).split("/");

        String nick = pathInfo[0];
        if (DAOProvider.getDAO().getBlogUser(nick) == null) {
            req.setAttribute("errorMessage", "The user " + nick + " does not exist!");
            req.getRequestDispatcher("/WEB-INF/pages/ErrorPage.jsp").forward(req, resp);
            return;
        }
        req.setAttribute("nick", nick);

        if (pathInfo.length == 2) {
            if (pathInfo[1].equals("new") || pathInfo[1].equals("edit")) {
                newEditBlogEntry(pathInfo[1].equals("edit"), nick, req, resp);

            } else {
                addBlogComment(pathInfo[1], nick, req, resp);
            }

        } else {
            req.setAttribute("errorMessage", "Invalid URL!");
            req.getRequestDispatcher("/WEB-INF/pages/ErrorPage.jsp").forward(req, resp);
        }
    }

    /**
     * Adds a blog comment for the specified entry or shows an error message.
     *
     * @param entryID the ID of the entry
     * @param nick the nickname of the user from the URL
     * @param req the {@link HttpServletRequest}
     * @param resp the {@link HttpServletResponse}
     * @throws ServletException if there was an issue with adding the comment
     * @throws IOException if there was an issue with adding the comment
     */
    private static void addBlogComment(String entryID, String nick, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

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

        BlogEntry entry = getEntryFromParameter(entryID, req, resp);
        comment.setBlogEntry(entry);
        comment.setPostedOn(new Date());

        DAOProvider.getDAO().addBlogComment(comment);

        req.setAttribute("entry", entry);
        resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nick + "/" + entryID);
    }

    /**
     * Adds or edits a new blog entry or shows an error message.
     *
     * @param isEdit {@code true} if edit should be performed
     * @param nick the nickname of the user from the URL
     * @param req the {@link HttpServletRequest}
     * @param resp the {@link HttpServletResponse}
     * @throws ServletException if there was an issue with adding/editing the entry
     * @throws IOException if there was an issue with adding/editing the entry
     */
    private static void newEditBlogEntry(boolean isEdit, String nick, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        BlogEntryForm form = new BlogEntryForm();
        form.fromHttpRequest(req);
        form.validate();

        if (form.hasErrors()) {
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/pages/NewEditBlogEntryPage.jsp").forward(req, resp);
            return;
        }

        BlogEntry entry = new BlogEntry();
        form.toBlogEntry(entry);

        if (isEdit) {
            String entryID = req.getParameter("id");
            getEntryFromParameter(entryID, req, resp);
            entry.setId(Long.parseLong(entryID));

        } else {
            BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
            if (user == null) {
                req.setAttribute("errorMessage", "Unknown user " + nick + "!");
                req.getRequestDispatcher("/WEB-INF/pages/ErrorPage.jsp").forward(req, resp);
                return;
            }
            entry.setCreator(user);

            entry.setCreatedAt(new Date());
            entry.setLastModifiedAt(new Date());
        }

        DAOProvider.getDAO().addBlogEntry(entry);
        resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nick);
    }

    /**
     * Checks if the session nickname matches the given URL nickname. If they are not
     * equal, an error page will be displayed.
     *
     * @param nick the URL nickname to compare
     * @param req the {@link HttpServletRequest}
     * @param resp the {@link HttpServletResponse}
     * @throws IOException if there was an issue with displaying the error page
     * @throws ServletException if there was an issue with displaying the error page
     */
    private static void checkSessionNick(String nick, HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException{
        Object sessionNick = req.getSession().getAttribute("current.user.nick");
        if (sessionNick == null || !sessionNick.equals(nick)) {
            req.setAttribute("errorMessage", "Forbidden...");
            req.getRequestDispatcher("/WEB-INF/pages/ErrorPage.jsp").forward(req, resp);
        }
    }

    /**
     * Returns a blog entry of the specified ID or displays an error page if the ID
     * is invalid.
     *
     * @param id the ID of the blog entry
     * @param req the {@link HttpServletRequest}
     * @param resp the {@link HttpServletResponse}
     * @return a blog entry of the specified ID
     * @throws IOException if there was an issue with displaying the error page
     * @throws ServletException if there was an issue with displaying the error page
     */
    private static BlogEntry getEntryFromParameter(String id, HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        try {
            Long entryID = Long.parseLong(id);
            BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryID);

            if (entry == null) {
                throw new IllegalArgumentException();
            }
            return entry;

        } catch (IllegalArgumentException e) {
            req.setAttribute("errorMessage", "Invalid entry ID!");
            req.getRequestDispatcher("/WEB-INF/pages/ErrorPage.jsp").forward(req, resp);
            return new BlogEntry();
        }
    }
}
