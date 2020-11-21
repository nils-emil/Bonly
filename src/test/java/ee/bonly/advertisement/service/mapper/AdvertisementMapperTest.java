package ee.bonly.advertisement.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdvertisementMapperTest {

    private AdvertisementMapper advertisementMapper;

    @BeforeEach
    public void setUp() {
        advertisementMapper = new AdvertisementMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(advertisementMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(advertisementMapper.fromId(null)).isNull();
    }
}
