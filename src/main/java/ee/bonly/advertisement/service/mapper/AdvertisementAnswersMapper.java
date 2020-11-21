package ee.bonly.advertisement.service.mapper;


import ee.bonly.advertisement.domain.*;
import ee.bonly.advertisement.service.dto.AdvertisementAnswersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdvertisementAnswers} and its DTO {@link AdvertisementAnswersDTO}.
 */
@Mapper(componentModel = "spring", uses = {AdvertisementMapper.class})
public interface AdvertisementAnswersMapper extends EntityMapper<AdvertisementAnswersDTO, AdvertisementAnswers> {

    @Mapping(source = "advertisement.id", target = "advertisementId")
    AdvertisementAnswersDTO toDto(AdvertisementAnswers advertisementAnswers);

    @Mapping(source = "advertisementId", target = "advertisement")
    AdvertisementAnswers toEntity(AdvertisementAnswersDTO advertisementAnswersDTO);

    default AdvertisementAnswers fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdvertisementAnswers advertisementAnswers = new AdvertisementAnswers();
        advertisementAnswers.setId(id);
        return advertisementAnswers;
    }
}
