package ee.bonly.advertisement.service.mapper;


import ee.bonly.advertisement.domain.*;
import ee.bonly.advertisement.service.dto.AdvertisementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Advertisement} and its DTO {@link AdvertisementDTO}.
 */
@Mapper(componentModel = "spring", uses = {AdvertisementAnswersMapper.class})
public interface AdvertisementMapper extends EntityMapper<AdvertisementDTO, Advertisement> {

    default Advertisement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Advertisement advertisement = new Advertisement();
        advertisement.setId(id);
        return advertisement;
    }
}
