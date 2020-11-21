package ee.bonly.advertisement.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ee.bonly.advertisement.web.rest.TestUtil;

public class UserAdvertisementAnswersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAdvertisementAnswersDTO.class);
        UserAdvertisementAnswersDTO userAdvertisementAnswersDTO1 = new UserAdvertisementAnswersDTO();
        userAdvertisementAnswersDTO1.setId(1L);
        UserAdvertisementAnswersDTO userAdvertisementAnswersDTO2 = new UserAdvertisementAnswersDTO();
        assertThat(userAdvertisementAnswersDTO1).isNotEqualTo(userAdvertisementAnswersDTO2);
        userAdvertisementAnswersDTO2.setId(userAdvertisementAnswersDTO1.getId());
        assertThat(userAdvertisementAnswersDTO1).isEqualTo(userAdvertisementAnswersDTO2);
        userAdvertisementAnswersDTO2.setId(2L);
        assertThat(userAdvertisementAnswersDTO1).isNotEqualTo(userAdvertisementAnswersDTO2);
        userAdvertisementAnswersDTO1.setId(null);
        assertThat(userAdvertisementAnswersDTO1).isNotEqualTo(userAdvertisementAnswersDTO2);
    }
}
