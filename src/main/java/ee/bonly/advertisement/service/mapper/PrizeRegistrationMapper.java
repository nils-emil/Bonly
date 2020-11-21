package ee.bonly.advertisement.service.mapper;


import ee.bonly.advertisement.domain.*;
import ee.bonly.advertisement.service.dto.PrizeRegistrationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PrizeRegistration} and its DTO {@link PrizeRegistrationDTO}.
 */
@Mapper(componentModel = "spring", uses = {PrizeMapper.class, UserMapper.class})
public interface PrizeRegistrationMapper extends EntityMapper<PrizeRegistrationDTO, PrizeRegistration> {

    @Mapping(source = "prize.id", target = "prizeId")
    @Mapping(source = "user.id", target = "userId")
    PrizeRegistrationDTO toDto(PrizeRegistration prizeRegistration);

    @Mapping(source = "prizeId", target = "prize")
    @Mapping(source = "userId", target = "user")
    PrizeRegistration toEntity(PrizeRegistrationDTO prizeRegistrationDTO);

    default PrizeRegistration fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrizeRegistration prizeRegistration = new PrizeRegistration();
        prizeRegistration.setId(id);
        return prizeRegistration;
    }
}
