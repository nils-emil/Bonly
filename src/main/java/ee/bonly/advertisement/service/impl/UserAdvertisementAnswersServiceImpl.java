package ee.bonly.advertisement.service.impl;

import ee.bonly.advertisement.service.UserAdvertisementAnswersService;
import ee.bonly.advertisement.domain.UserAdvertisementAnswers;
import ee.bonly.advertisement.repository.UserAdvertisementAnswersRepository;
import ee.bonly.advertisement.service.dto.UserAdvertisementAnswersDTO;
import ee.bonly.advertisement.service.mapper.UserAdvertisementAnswersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UserAdvertisementAnswers}.
 */
@Service
@Transactional
public class UserAdvertisementAnswersServiceImpl implements UserAdvertisementAnswersService {

    private final Logger log = LoggerFactory.getLogger(UserAdvertisementAnswersServiceImpl.class);

    private final UserAdvertisementAnswersRepository userAdvertisementAnswersRepository;

    private final UserAdvertisementAnswersMapper userAdvertisementAnswersMapper;

    public UserAdvertisementAnswersServiceImpl(UserAdvertisementAnswersRepository userAdvertisementAnswersRepository, UserAdvertisementAnswersMapper userAdvertisementAnswersMapper) {
        this.userAdvertisementAnswersRepository = userAdvertisementAnswersRepository;
        this.userAdvertisementAnswersMapper = userAdvertisementAnswersMapper;
    }

    @Override
    public UserAdvertisementAnswersDTO save(UserAdvertisementAnswersDTO userAdvertisementAnswersDTO) {
        log.debug("Request to save UserAdvertisementAnswers : {}", userAdvertisementAnswersDTO);
        UserAdvertisementAnswers userAdvertisementAnswers = userAdvertisementAnswersMapper.toEntity(userAdvertisementAnswersDTO);
        userAdvertisementAnswers = userAdvertisementAnswersRepository.save(userAdvertisementAnswers);
        return userAdvertisementAnswersMapper.toDto(userAdvertisementAnswers);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAdvertisementAnswersDTO> findAll() {
        log.debug("Request to get all UserAdvertisementAnswers");
        return userAdvertisementAnswersRepository.findAll().stream()
            .map(userAdvertisementAnswersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserAdvertisementAnswersDTO> findOne(Long id) {
        log.debug("Request to get UserAdvertisementAnswers : {}", id);
        return userAdvertisementAnswersRepository.findById(id)
            .map(userAdvertisementAnswersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserAdvertisementAnswers : {}", id);
        userAdvertisementAnswersRepository.deleteById(id);
    }
}
