package ee.bonly.advertisement.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ee.bonly.advertisement.web.rest.TestUtil;

public class PrizeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prize.class);
        Prize prize1 = new Prize();
        prize1.setId(1L);
        Prize prize2 = new Prize();
        prize2.setId(prize1.getId());
        assertThat(prize1).isEqualTo(prize2);
        prize2.setId(2L);
        assertThat(prize1).isNotEqualTo(prize2);
        prize1.setId(null);
        assertThat(prize1).isNotEqualTo(prize2);
    }
}
