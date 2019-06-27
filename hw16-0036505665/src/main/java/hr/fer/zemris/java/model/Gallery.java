package hr.fer.zemris.java.model;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class that models a gallery of images. On webapp initialization, it reads all the
 * info from the image {@link #DESCRIPTIONS} file and stores them as {@link GalleryImage}
 * object in a list.
 *
 * @author Bruna Dujmović
 */
public class Gallery implements ServletContextListener {

    /**
     * The path to the image descriptions file.
     */
    private static final Path DESCRIPTIONS = Paths.get("WEB-INF/opisnik.txt");

    /**
     * A list of {@link GalleryImage} data obtained from the {@link #DESCRIPTIONS} file.
     */
    private static final List<GalleryImage> galleryImages = new ArrayList<>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            List<String> lines = Files.readAllLines(DESCRIPTIONS);

            for (int i = 0, size = lines.size(); i < size; i += 3) {
                String fileName = lines.get(i);
                String description = lines.get(i + 1);
                Set<String> tags = Set.of(lines.get(i + 2).split(", "));

                galleryImages.add(
                        new GalleryImage(fileName, description, tags)
                );
            }

        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Issue with reading descriptions file!");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}


}
