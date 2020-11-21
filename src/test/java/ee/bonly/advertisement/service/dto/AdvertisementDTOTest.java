package ee.bonly.advertisement.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ee.bonly.advertisement.web.rest.TestUtil;

public class AdvertisementDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdvertisementDTO.class);
        AdvertisementDTO advertisementDTO1 = new AdvertisementDTO();
        advertisementDTO1.setId(1L);
        AdvertisementDTO advertisementDTO2 = new AdvertisementDTO();
        assertThat(advertisementDTO1).isNotEqualTo(advertisementDTO2);
        advertisementDTO2.setId(advertisementDTO1.getId());
        assertThat(advertisementDTO1).isEqualTo(advertisementDTO2);
        advertisementDTO2.setId(2L);
        assertThat(advertisementDTO1).isNotEqualTo(advertisementDTO2);
        advertisementDTO1.setId(null);
        assertThat(advertisementDTO1).isNotEqualTo(advertisementDTO2);
    }
}
