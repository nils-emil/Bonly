package ee.bonly.advertisement.service.mapper;


import ee.bonly.advertisement.domain.*;
import ee.bonly.advertisement.service.dto.PrizeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Prize} and its DTO {@link PrizeDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PrizeMapper extends EntityMapper<PrizeDTO, Prize> {

    @Mapping(source = "winner.id", target = "winnerId")
    PrizeDTO toDto(Prize prize);

    @Mapping(source = "winnerId", target = "winner")
    Prize toEntity(PrizeDTO prizeDTO);

    default Prize fromId(Long id) {
        if (id == null) {
            return null;
        }
        Prize prize = new Prize();
        prize.setId(id);
        return prize;
    }
}
