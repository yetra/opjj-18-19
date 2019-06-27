package hr.fer.zemris.java.servlets;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A web servlet for displaying a gallery image in its original size.
 *
 * @author Bruna Dujmović
 */
@WebServlet("/image")
public class ImageServlet extends HttpServlet {

    /**
     * The base path of an original image.
     */
    private static final String BASE_PATH_ORIGINALS = "WEB-INF/slike";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("image/jpg");

        String imageName = req.getParameter("name");
        Path largeImagePath = Paths.get(BASE_PATH_ORIGINALS + "/" + imageName);
        BufferedImage largeImage = ImageIO.read(
                new BufferedInputStream(Files.newInputStream(largeImagePath)));

        ImageIO.write(largeImage, "jpg", resp.getOutputStream());
    }
}
