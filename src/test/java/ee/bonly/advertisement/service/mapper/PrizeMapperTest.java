package ee.bonly.advertisement.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PrizeMapperTest {

    private PrizeMapper prizeMapper;

    @BeforeEach
    public void setUp() {
        prizeMapper = new PrizeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(prizeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(prizeMapper.fromId(null)).isNull();
    }
}
