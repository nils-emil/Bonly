package ee.bonly.advertisement.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdvertisementAnswersMapperTest {

    private AdvertisementAnswersMapper advertisementAnswersMapper;

    @BeforeEach
    public void setUp() {
        advertisementAnswersMapper = new AdvertisementAnswersMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(advertisementAnswersMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(advertisementAnswersMapper.fromId(null)).isNull();
    }
}
