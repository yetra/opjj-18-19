/**
 * Funkcija preuzeta sa Stack Overflow-a:
 *
 * http://stackoverflow.com/questions/1219860/html-encoding-in-javascript-jquery
 */

function htmlEscape(str) {
    return String(str)
        .replace(/&/g, '&amp;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#39;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;');
}

/**
 * Displays the original version of an image specified by name.
 *
 * @param name the name of the original image to display
 */
function getOriginalImage(name) {
    $.ajax(
        {
            url: "rest/gallery/image/" + name,
            data: {
                dummy: Math.random()
            },
            dataType: "json",
            success: function (data) {
                var original = data;

                var html = "<img src='image?name="
                    + htmlEscape(original.fileName) + "' alt='"
                    + htmlEscape(original.description) + "'>";

                html += "<p>Description: " + htmlEscape(original.description) + "</p>";
                html += "<p>Tags: " + htmlEscape(original.tags) + "</p>";

                $("#original_image").html(html);
            }
        }
    );
}

/**
 * Displays all thumbnails for the specified tag.
 *
 * @param tag the tag of the thumbnails to display
 */
function getThumbnailsFor(tag) {
    $.ajax(
        {
            url: "rest/gallery/tag/" + tag,
            data: {
                dummy: Math.random()
            },
            dataType: "json",
            success: function (data) {
                var imagesData = data;
                var html = "";

                for (var i = 0; i < imagesData.length; i++) {
                    html += "<img class='thumbnail' src='thumbnail?name="
                        + htmlEscape(imagesData[i].fileName)
                        + "' onclick='getOriginalImage(\""
                        + htmlEscape(imagesData[i].fileName) + "\")' alt='"
                        + htmlEscape(imagesData[i].description) + "'>";
                }

                $("#thumbnails").html(html);
                $("#original_image").html(" ");
            }
        }
    );
}

/**
 * Displays all image tag buttons when DOM becomes ready for JavaScript code to
 * execute.
 */
$(document).ready(
    function getTagButtons() {
        $.ajax(
            {
                url: "rest/gallery",
                data: {
                    dummy: Math.random()
                },
                dataType: "json",
                success: function (data) {
                    var tags = data;
                    var html = "";

                    for (var i = 0; i < tags.length; i++) {
                        html += "<button class='tag_button' "
                            + "onclick='getThumbnailsFor(\""
                            + htmlEscape(tags[i]) + "\")'>"
                            + htmlEscape(tags[i])
                            + "</button>";
                    }

                    $("#tag_buttons").html(html);
                }
            }
        );
    }
);