package hr.fer.zemris.java.rest;

import com.google.gson.Gson;
import hr.fer.zemris.java.model.Gallery;
import hr.fer.zemris.java.model.GalleryImage;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

/**
 * A Jersey resource class representing the image gallery.
 *
 * @author Bruna Dujmović
 *
 */
@Path("/gallery")
public class GalleryJSON {

    /**
     * Returns a {@link Response} containing a set of all image tags.
     *
     * @return a {@link Response} containing a set of all image tags
     */
    @GET
    @Produces("application/json")
    public Response getTags() {
        String result = new Gson().toJson(Gallery.getTags());

        return Response.status(Status.OK).entity(result).build();
    }

    /**
     * Returns a {@link Response} containing a list of {@link GalleryImage}s containing
     * the specified tag.
     *
     * @param tag the tag of the {@link GalleryImage}s
     * @return a {@link Response} containing a list of {@link GalleryImage}s for the
     *         given tag
     */
    @GET
    @Path("tag/{tag}")
    @Produces("application/json")
    public Response getThumbnailsWithTag(@PathParam("tag") String tag) {
        List<GalleryImage> imagesWithTag = Gallery.getImagesWithTag(tag);

        if (imagesWithTag == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        String result = new Gson().toJson(imagesWithTag);

        return Response.status(Status.OK).entity(result).build();
    }

    /**
     * Returns a {@link Response} containing a {@link GalleryImage} object representing
     * the specified image.
     *
     * @param fileName the file name of the image
     * @return a {@link Response} containing a {@link GalleryImage} object representing
     *         the specified image
     */
    @GET
    @Path("image/{fileName}")
    @Produces("application/json")
    public Response getImageDataFor(@PathParam("fileName") String fileName) {
        GalleryImage image = Gallery.getGalleryImageFor(fileName);

        if (image == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        String result = new Gson().toJson(image);

        return Response.status(Status.OK).entity(result).build();
    }

}
