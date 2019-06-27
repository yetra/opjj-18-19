package hr.fer.zemris.java.model;

import java.awt.*;
import java.util.Set;

/**
 * A class representing a gallery image. It consists of an image description, tags,
 * and of an underlying {@link Image} object.
 *
 * @author Bruna Dujmović
 *
 */
public class GalleryImage {

    /**
     * The name of the image file.
     */
    private String fileName;

    /**
     * The description of this gallery image.
     */
    private String description;

    /**
     * A set of tags of this gallery image.
     */
    private Set<String> tags;

    /**
     * Constructs a {@link GalleryImage} of the given parameters.
     *
     * @param fileName the name of the image file
     * @param description the description of this gallery image
     * @param tags a set of tags of this gallery image
     */
    public GalleryImage(String fileName, String description, Set<String> tags) {
        this.fileName = fileName;
        this.description = description;
        this.tags = tags;
    }

    /**
     * Returns the name of the image file.
     *
     * @return the name of the image file
     */
    public String getFileName() {
        return fileName;
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
     * Returns a set of tags of this gallery image
     *
     * @return a set of tags of this gallery image
     */
    public Set<String> getTags() {
        return tags;
    }
}
