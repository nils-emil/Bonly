package ee.bonly.advertisement.service.mapper;


import ee.bonly.advertisement.domain.*;
import ee.bonly.advertisement.service.dto.UserAdvertisementAnswersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserAdvertisementAnswers} and its DTO {@link UserAdvertisementAnswersDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, AdvertisementMapper.class})
public interface UserAdvertisementAnswersMapper extends EntityMapper<UserAdvertisementAnswersDTO, UserAdvertisementAnswers> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "advertisement.id", target = "advertisementId")
    UserAdvertisementAnswersDTO toDto(UserAdvertisementAnswers userAdvertisementAnswers);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "advertisementId", target = "advertisement")
    UserAdvertisementAnswers toEntity(UserAdvertisementAnswersDTO userAdvertisementAnswersDTO);

    default UserAdvertisementAnswers fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserAdvertisementAnswers userAdvertisementAnswers = new UserAdvertisementAnswers();
        userAdvertisementAnswers.setId(id);
        return userAdvertisementAnswers;
    }
}
