package ee.bonly.advertisement.web.rest;

import ee.bonly.advertisement.repository.ImageRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class Image {

    private final ImageRepository imageRepository;

    public Image(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @GetMapping("/image/{id}")
    public void getImage(HttpServletResponse response,
                           @PathVariable Long id) throws IOException {
        // get the image
        Optional<ee.bonly.advertisement.domain.Image> byId = imageRepository.findById(id);
        byte[] image = byId.get().getContent();

        // get MIME type of the file
        String mimeType = "application/octet-stream";

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength(image.length);

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=image.jpeg");
        response.setHeader(headerKey, headerValue);

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();

        // get input stream and a fixed size buffer
        InputStream in = new ByteArrayInputStream(image);
        byte[] buffer = new byte[4096];

        // write data into output stream
        int read = -1;
        while((read = in.read(buffer)) != -1) {
            outStream.write(buffer, 0, read);
        }

        // close output stream
        outStream.flush();
        outStream.close();
    }

}
