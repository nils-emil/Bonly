package ee.bonly.advertisement.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ee.bonly.advertisement.web.rest.TestUtil;

public class PrizeRegistrationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrizeRegistrationDTO.class);
        PrizeRegistrationDTO prizeRegistrationDTO1 = new PrizeRegistrationDTO();
        prizeRegistrationDTO1.setId(1L);
        PrizeRegistrationDTO prizeRegistrationDTO2 = new PrizeRegistrationDTO();
        assertThat(prizeRegistrationDTO1).isNotEqualTo(prizeRegistrationDTO2);
        prizeRegistrationDTO2.setId(prizeRegistrationDTO1.getId());
        assertThat(prizeRegistrationDTO1).isEqualTo(prizeRegistrationDTO2);
        prizeRegistrationDTO2.setId(2L);
        assertThat(prizeRegistrationDTO1).isNotEqualTo(prizeRegistrationDTO2);
        prizeRegistrationDTO1.setId(null);
        assertThat(prizeRegistrationDTO1).isNotEqualTo(prizeRegistrationDTO2);
    }
}
