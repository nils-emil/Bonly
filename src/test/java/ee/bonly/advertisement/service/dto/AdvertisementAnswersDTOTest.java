package ee.bonly.advertisement.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ee.bonly.advertisement.web.rest.TestUtil;

public class AdvertisementAnswersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdvertisementAnswersDTO.class);
        AdvertisementAnswersDTO advertisementAnswersDTO1 = new AdvertisementAnswersDTO();
        advertisementAnswersDTO1.setId(1L);
        AdvertisementAnswersDTO advertisementAnswersDTO2 = new AdvertisementAnswersDTO();
        assertThat(advertisementAnswersDTO1).isNotEqualTo(advertisementAnswersDTO2);
        advertisementAnswersDTO2.setId(advertisementAnswersDTO1.getId());
        assertThat(advertisementAnswersDTO1).isEqualTo(advertisementAnswersDTO2);
        advertisementAnswersDTO2.setId(2L);
        assertThat(advertisementAnswersDTO1).isNotEqualTo(advertisementAnswersDTO2);
        advertisementAnswersDTO1.setId(null);
        assertThat(advertisementAnswersDTO1).isNotEqualTo(advertisementAnswersDTO2);
    }
}
