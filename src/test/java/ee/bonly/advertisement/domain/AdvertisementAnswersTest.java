package ee.bonly.advertisement.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ee.bonly.advertisement.web.rest.TestUtil;

public class AdvertisementAnswersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdvertisementAnswers.class);
        AdvertisementAnswers advertisementAnswers1 = new AdvertisementAnswers();
        advertisementAnswers1.setId(1L);
        AdvertisementAnswers advertisementAnswers2 = new AdvertisementAnswers();
        advertisementAnswers2.setId(advertisementAnswers1.getId());
        assertThat(advertisementAnswers1).isEqualTo(advertisementAnswers2);
        advertisementAnswers2.setId(2L);
        assertThat(advertisementAnswers1).isNotEqualTo(advertisementAnswers2);
        advertisementAnswers1.setId(null);
        assertThat(advertisementAnswers1).isNotEqualTo(advertisementAnswers2);
    }
}
