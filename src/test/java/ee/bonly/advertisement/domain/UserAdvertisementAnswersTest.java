package ee.bonly.advertisement.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ee.bonly.advertisement.web.rest.TestUtil;

public class UserAdvertisementAnswersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAdvertisementAnswers.class);
        UserAdvertisementAnswers userAdvertisementAnswers1 = new UserAdvertisementAnswers();
        userAdvertisementAnswers1.setId(1L);
        UserAdvertisementAnswers userAdvertisementAnswers2 = new UserAdvertisementAnswers();
        userAdvertisementAnswers2.setId(userAdvertisementAnswers1.getId());
        assertThat(userAdvertisementAnswers1).isEqualTo(userAdvertisementAnswers2);
        userAdvertisementAnswers2.setId(2L);
        assertThat(userAdvertisementAnswers1).isNotEqualTo(userAdvertisementAnswers2);
        userAdvertisementAnswers1.setId(null);
        assertThat(userAdvertisementAnswers1).isNotEqualTo(userAdvertisementAnswers2);
    }
}
