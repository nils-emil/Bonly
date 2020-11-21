package ee.bonly.advertisement.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ee.bonly.advertisement.web.rest.TestUtil;

public class AdvertisementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Advertisement.class);
        Advertisement advertisement1 = new Advertisement();
        advertisement1.setId(1L);
        Advertisement advertisement2 = new Advertisement();
        advertisement2.setId(advertisement1.getId());
        assertThat(advertisement1).isEqualTo(advertisement2);
        advertisement2.setId(2L);
        assertThat(advertisement1).isNotEqualTo(advertisement2);
        advertisement1.setId(null);
        assertThat(advertisement1).isNotEqualTo(advertisement2);
    }
}
