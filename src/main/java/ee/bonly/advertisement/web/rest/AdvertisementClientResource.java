package ee.bonly.advertisement.web.rest;

import ee.bonly.advertisement.service.AdvertisementService;
import ee.bonly.advertisement.service.dto.AdvertisementDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link ee.bonly.advertisement.domain.Advertisement}.
 */
@RestController
@RequestMapping("/api/client")
public class AdvertisementClientResource {

    private final Logger log = LoggerFactory.getLogger(AdvertisementClientResource.class);

    private static final String ENTITY_NAME = "advertisement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdvertisementService advertisementService;

    public AdvertisementClientResource(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }


    @GetMapping("/content/find-one")
    public AdvertisementDTO getOneRandomUnseenAdvertisement() {
        log.debug("REST request to get all AdvertisementAnswers");
        return advertisementService.findOneUnansweredAdvertisementByUserid();
    }

    @PostMapping("/content/answer/{questionId}/{answerId}")
    public ResponseEntity<AdvertisementDTO> createAdvertisementAnswer(
        @PathVariable Long questionId,
        @PathVariable Long answerId) {
        log.debug("REST request to save Advertisement answer for {}: {}",questionId , answerId);
        advertisementService.saveAnswerToQuestion(questionId, answerId);
        return ResponseEntity.noContent().build();
    }

}
