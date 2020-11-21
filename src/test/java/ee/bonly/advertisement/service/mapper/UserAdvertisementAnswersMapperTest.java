package ee.bonly.advertisement.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserAdvertisementAnswersMapperTest {

    private UserAdvertisementAnswersMapper userAdvertisementAnswersMapper;

    @BeforeEach
    public void setUp() {
        userAdvertisementAnswersMapper = new UserAdvertisementAnswersMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userAdvertisementAnswersMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userAdvertisementAnswersMapper.fromId(null)).isNull();
    }
}
