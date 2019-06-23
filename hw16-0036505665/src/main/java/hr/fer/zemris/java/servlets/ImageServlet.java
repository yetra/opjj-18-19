package hr.fer.zemris.java.servlets;

import hr.fer.zemris.java.model.GalleryImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A web servlet for resizing a given image to 150x150.
 *
 * @author Bruna Dujmović
 */
@WebServlet // TODO mapping
public class ImageServlet extends HttpServlet {

    /**
     * The base path of a thumbnail.
     */
    private static final String BASE_PATH_THUMBNAILS = "WEB-INF/thumbnails/";

    /**
     * The base path of an original image.
     */
    private static final String BASE_PATH_ORIGINALS = "WEB-INF/slike/";

    /**
     * The size (both width and height) of the image after resizing.
     */
    private static final int SMALL_SIZE = 150;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String imageName = req.getParameter("name");
        Path smallImagePath = Paths.get(BASE_PATH_THUMBNAILS + imageName);

        // check if 150x150 exists in WEB-INF/thumbnails
        if (Files.exists(smallImagePath)) {
            // TODO if yes - return it
            return;
        }

        // else - read big picture
        Path largeImagePath = Paths.get(BASE_PATH_ORIGINALS + imageName);
        BufferedImage largeImage = ImageIO.read(new BufferedInputStream(Files.newInputStream(largeImagePath)));

        // resize to 150x150
        BufferedImage smallImage = new BufferedImage(SMALL_SIZE, SMALL_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics g = smallImage.createGraphics();
        g.drawImage(largeImage, 0, 0, SMALL_SIZE, SMALL_SIZE, null);
        g.dispose();

        // save to thumbnails
        ImageIO.write(smallImage, "jpg", Files.newOutputStream(smallImagePath));

        // TODO return it
        GalleryImage.toGalleryImage(imageName, smallImage);
    }

}
