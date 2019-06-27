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

    /**
     * Returns a set of all image tags found in this gallery.
     *
     * @return a set of all image tags found in this gallery
     */
    public static Set<String> getTags() {
        Set<String> tags = new HashSet<>();

        for (GalleryImage image : galleryImages) {
            tags.addAll(image.getTags());
        }

        return tags;
    }

    /**
     * Returns a list of {@link GalleryImage} objects that contain a specified tag.
     *
     * @param tag the tag that the images should contain
     * @return a list of {@link GalleryImage} objects that contain the specified tag
     */
    public static List<GalleryImage> getImagesWithTag(String tag) {
        List<GalleryImage> imagesWithTag = new ArrayList<>();

        for (GalleryImage image : galleryImages) {
            if (image.getTags().contains(tag)) {
                imagesWithTag.add(image);
            }
        }

        return imagesWithTag;
    }

    /**
     * Returns a {@link GalleryImage} object from the {@link #galleryImages} array that
     * has the specified file name. If there is no such object, this method will return
     * {@code null}.
     *
     * @param fileName the file name of the image
     * @return a {@link GalleryImage} object of the specified file name, or {@code null}
     *         if there is no such object
     */
    public static GalleryImage getGalleryImageFor(String fileName) {
        for (GalleryImage image : galleryImages) {
            if (image.getFileName().equals(fileName)) {
                return image;
            }
        }

        return null;
    }

}
