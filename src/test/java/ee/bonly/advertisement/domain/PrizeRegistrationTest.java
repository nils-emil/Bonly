package ee.bonly.advertisement.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ee.bonly.advertisement.web.rest.TestUtil;

public class PrizeRegistrationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrizeRegistration.class);
        PrizeRegistration prizeRegistration1 = new PrizeRegistration();
        prizeRegistration1.setId(1L);
        PrizeRegistration prizeRegistration2 = new PrizeRegistration();
        prizeRegistration2.setId(prizeRegistration1.getId());
        assertThat(prizeRegistration1).isEqualTo(prizeRegistration2);
        prizeRegistration2.setId(2L);
        assertThat(prizeRegistration1).isNotEqualTo(prizeRegistration2);
        prizeRegistration1.setId(null);
        assertThat(prizeRegistration1).isNotEqualTo(prizeRegistration2);
    }
}
