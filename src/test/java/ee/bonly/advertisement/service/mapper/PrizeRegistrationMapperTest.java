package ee.bonly.advertisement.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PrizeRegistrationMapperTest {

    private PrizeRegistrationMapper prizeRegistrationMapper;

    @BeforeEach
    public void setUp() {
        prizeRegistrationMapper = new PrizeRegistrationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(prizeRegistrationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(prizeRegistrationMapper.fromId(null)).isNull();
    }
}
