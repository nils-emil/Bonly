package ee.bonly.advertisement.service.impl;

import ee.bonly.advertisement.domain.*;
import ee.bonly.advertisement.repository.AdvertisementRepository;
import ee.bonly.advertisement.repository.ImageRepository;
import ee.bonly.advertisement.repository.UserAdvertisementAnswersRepository;
import ee.bonly.advertisement.repository.UserRepository;
import ee.bonly.advertisement.security.SecurityUtils;
import ee.bonly.advertisement.service.AdvertisementService;
import ee.bonly.advertisement.service.UserService;
import ee.bonly.advertisement.service.dto.AdvertisementDTO;
import ee.bonly.advertisement.service.mapper.AdvertisementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Advertisement}.
 */
@Service
@Transactional
public class AdvertisementServiceImpl implements AdvertisementService {

    private final Logger log = LoggerFactory.getLogger(AdvertisementServiceImpl.class);

    private final AdvertisementRepository advertisementRepository;

    private final ImageRepository imageRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    private final UserAdvertisementAnswersRepository userAdvertisementAnswersRepository;

    private final AdvertisementMapper advertisementMapper;

    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository,
                                    UserRepository userRepository,
                                    UserService userService,
                                    ImageRepository imageRepository,
                                    UserAdvertisementAnswersRepository userAdvertisementAnswersRepository,
                                    AdvertisementMapper advertisementMapper) {
        this.advertisementRepository = advertisementRepository;
        this.advertisementMapper = advertisementMapper;
        this.imageRepository = imageRepository;
        this.userAdvertisementAnswersRepository = userAdvertisementAnswersRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public AdvertisementDTO save(AdvertisementDTO advertisementDTO) {
        log.debug("Request to save Advertisement : {}", advertisementDTO);
        Advertisement advertisement = advertisementMapper.toEntity(advertisementDTO);
        String base64Image = advertisementDTO.getImage().split(",")[1];
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
        Image image = new Image();
        image.setContent(imageBytes);
        image.setId(advertisementDTO.getImageId());
        Image save = imageRepository.save(image);
        advertisement.setImageId(save.getId());
        advertisement = advertisementRepository.save(advertisement);
        return advertisementMapper.toDto(advertisement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdvertisementDTO> findAll() {
        log.debug("Request to get all Advertisements");
        return advertisementRepository.findAll().stream()
            .map(advertisementMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public AdvertisementDTO findOneUnansweredAdvertisementByUserid() {
        log.debug("Request to get all Advertisements");
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if (currentUserLogin.isPresent()) {
            String currentLogin = currentUserLogin.get();
            Optional<User> oneByLogin = userRepository.findOneByLogin(currentLogin);
            if (oneByLogin.isPresent()) {
                Long id = oneByLogin.get().getId();
                Advertisement randomUnseenAdvertisement = advertisementRepository
                    .findOneUnansweredAdvertisementByUserid(id);
                return advertisementMapper.toDto(randomUnseenAdvertisement);
            }
        }
        return null;
    }

    @Override
    public void saveAnswerToQuestion(Long questionId, Long answerId) {
        UserAdvertisementAnswers userAdvertisementAnswers = new UserAdvertisementAnswers();
        Advertisement advertisement = new Advertisement();
        advertisement.setId(questionId);
        AdvertisementAnswers advertisementAnswers = new AdvertisementAnswers();
        advertisementAnswers.setId(answerId);
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if (currentUserLogin.isPresent()) {
            String currentLogin = currentUserLogin.get();
            Optional<User> oneByLogin = userRepository.findOneByLogin(currentLogin);
            if (oneByLogin.isPresent()) {
                Optional<Advertisement> byId = advertisementRepository.findById(questionId);
                User user = oneByLogin.get();
                userAdvertisementAnswers.setUser(user);
                userAdvertisementAnswers.setAdvertisement(advertisement);
                userAdvertisementAnswers.setAdvertisementAnswer(advertisementAnswers);
                userAdvertisementAnswersRepository.save(userAdvertisementAnswers);
                if (byId.isPresent()) {
                    long newAmount = user.getCreditsCount() + byId.get().getCreditCount();
                    userService.updateUserCreditCount(newAmount);
                }
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdvertisementDTO> findOne(Long id) {
        log.debug("Request to get Advertisement : {}", id);
        return advertisementRepository.findById(id)
            .map(advertisementMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Advertisement : {}", id);
        advertisementRepository.deleteById(id);
    }
}
