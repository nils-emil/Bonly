package ee.bonly.advertisement.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ee.bonly.advertisement.web.rest.TestUtil;

public class PrizeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrizeDTO.class);
        PrizeDTO prizeDTO1 = new PrizeDTO();
        prizeDTO1.setId(1L);
        PrizeDTO prizeDTO2 = new PrizeDTO();
        assertThat(prizeDTO1).isNotEqualTo(prizeDTO2);
        prizeDTO2.setId(prizeDTO1.getId());
        assertThat(prizeDTO1).isEqualTo(prizeDTO2);
        prizeDTO2.setId(2L);
        assertThat(prizeDTO1).isNotEqualTo(prizeDTO2);
        prizeDTO1.setId(null);
        assertThat(prizeDTO1).isNotEqualTo(prizeDTO2);
    }
}
