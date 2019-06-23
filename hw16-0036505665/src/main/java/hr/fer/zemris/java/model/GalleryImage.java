package hr.fer.zemris.java.model;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class representing a gallery image. It consists of an image description, tags,
 * and of an underlying {@link Image} object.
 *
 * @author Bruna Dujmović
 *
 */
public class GalleryImage {

    /**
     * The path to the image descriptions file.
     */
    private static final Path DESCRIPTIONS = Paths.get("WEB-INF/opisnik.txt");

    /**
     * An {@link Image} object representing this gallery image.
     */
    private Image image;

    /**
     * The description of this gallery image.
     */
    private String description;

    /**
     * A list of tags of this gallery image.
     */
    private List<String> tags;

    /**
     * Constructs a {@link GalleryImage} of the given parameters.
     *
     * @param image an {@link Image} object representing this gallery image
     * @param description the description of this gallery image
     * @param tags a list of tags of this gallery image
     */
    public GalleryImage(Image image, String description, List<String> tags) {
        this.image = image;
        this.description = description;
        this.tags = tags;
    }

    /**
     * Returns an {@link Image} object representing this gallery image.
     *
     * @return an {@link Image} object representing this gallery image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Returns the description of this gallery image.
     *
     * @return the description of this gallery image
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a list of tags of this gallery image
     *
     * @return a list of tags of this gallery image
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Returns a {@link GalleryImage} object for the given image by reading its
     * description and tags from the {@link #DESCRIPTIONS} file.
     *
     * @param name the name of the image
     * @param image an {@link Image} object representing the image
     * @return a {@link GalleryImage} object for the given image
     * @throws IOException if there was an issue with the image descriptions
     */
    public static GalleryImage toGalleryImage(String name, Image image)
            throws IOException {

        List<String> tags = new ArrayList<>();
        String description = null;

        try (BufferedReader br = Files.newBufferedReader(DESCRIPTIONS)) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.equals(name)) {
                    description = br.readLine();
                    tags = Arrays.asList(br.readLine().split(", "));
                }
            }
        }

        return new GalleryImage(image, description, tags);
    }
}
