package ee.bonly.advertisement.service.impl;

import ee.bonly.advertisement.domain.Prize;
import ee.bonly.advertisement.domain.User;
import ee.bonly.advertisement.repository.PrizeRepository;
import ee.bonly.advertisement.repository.UserRepository;
import ee.bonly.advertisement.security.SecurityUtils;
import ee.bonly.advertisement.service.PrizeRegistrationService;
import ee.bonly.advertisement.domain.PrizeRegistration;
import ee.bonly.advertisement.repository.PrizeRegistrationRepository;
import ee.bonly.advertisement.service.UserService;
import ee.bonly.advertisement.service.dto.PrizeRegistrationDTO;
import ee.bonly.advertisement.service.mapper.PrizeRegistrationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PrizeRegistration}.
 */
@Service
@Transactional
public class PrizeRegistrationServiceImpl implements PrizeRegistrationService {

    private final Logger log = LoggerFactory.getLogger(PrizeRegistrationServiceImpl.class);

    private final PrizeRegistrationRepository prizeRegistrationRepository;

    private final PrizeRegistrationMapper prizeRegistrationMapper;

    private final UserRepository userRepository;

    private final UserService userService;

    private final PrizeRepository prizeRepository;

    public PrizeRegistrationServiceImpl(PrizeRegistrationRepository prizeRegistrationRepository,
                                        PrizeRegistrationMapper prizeRegistrationMapper,
                                        UserService userService,
                                        PrizeRepository prizeRepository,
                                        UserRepository userRepository) {
        this.prizeRegistrationRepository = prizeRegistrationRepository;
        this.prizeRegistrationMapper = prizeRegistrationMapper;
        this.userRepository = userRepository;
        this.userService = userService;
        this.prizeRepository = prizeRepository;
    }

    @Override
    public PrizeRegistrationDTO save(PrizeRegistrationDTO prizeRegistrationDTO) {
        log.debug("Request to save PrizeRegistration : {}", prizeRegistrationDTO);
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if (currentUserLogin.isPresent()) {
            String currentLogin = currentUserLogin.get();
            Optional<User> oneByLogin = userRepository.findOneByLogin(currentLogin);
            if (oneByLogin.isPresent()) {
                User user = oneByLogin.get();
                PrizeRegistration prizeRegistration = prizeRegistrationMapper.toEntity(prizeRegistrationDTO);
                prizeRegistration.setUser(user);
                prizeRegistration.setRegistation(Instant.now());
                Optional<Prize> one = prizeRepository.findById(prizeRegistrationDTO.getPrizeId());
                if (!one.isPresent()) {
                    throw new IllegalArgumentException("Did not find the prize user tryied to register for");
                }
                if (one.get().getCreditsRequired() > user.getCreditsCount()) {
                    throw new IllegalArgumentException("User does not have enough credit");
                }
                PrizeRegistration save = prizeRegistrationRepository.save(prizeRegistration);
                userService.updateUserCreditCount(user.getCreditsCount() - one.get().getCreditsRequired());
                return  prizeRegistrationMapper.toDto(save);
            }
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrizeRegistrationDTO> findAll() {
        log.debug("Request to get all PrizeRegistrations");
        return prizeRegistrationRepository.findAll().stream()
            .map(prizeRegistrationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PrizeRegistrationDTO> findOne(Long id) {
        log.debug("Request to get PrizeRegistration : {}", id);
        return prizeRegistrationRepository.findById(id)
            .map(prizeRegistrationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PrizeRegistration : {}", id);
        prizeRegistrationRepository.deleteById(id);
    }
}
