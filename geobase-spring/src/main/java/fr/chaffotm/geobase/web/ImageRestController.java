package fr.chaffotm.geobase.web;

import fr.chaffotm.geobase.service.ImageService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.io.InputStream;
import java.util.UUID;

@RestController
@RequestMapping("/api/images")
public class ImageRestController {

    private final ImageService imageService;

    public ImageRestController(final ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = "{imageUuid}")
    public ResponseEntity<InputStreamResource> get(@PathVariable("imageUuid") final UUID imageUuid) {
        final InputStream stream = imageService.getImageStream(imageUuid);
        if (stream == null) {
            throw new EntityNotFoundException("The uuid does not refer to any existing image: '" + imageUuid + "'");
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + imageUuid + "\"");
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream, "unknown image"));
    }

}
