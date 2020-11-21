package ee.bonly.advertisement.service.impl;

import ee.bonly.advertisement.service.AdvertisementAnswersService;
import ee.bonly.advertisement.domain.AdvertisementAnswers;
import ee.bonly.advertisement.repository.AdvertisementAnswersRepository;
import ee.bonly.advertisement.service.dto.AdvertisementAnswersDTO;
import ee.bonly.advertisement.service.mapper.AdvertisementAnswersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AdvertisementAnswers}.
 */
@Service
@Transactional
public class AdvertisementAnswersServiceImpl implements AdvertisementAnswersService {

    private final Logger log = LoggerFactory.getLogger(AdvertisementAnswersServiceImpl.class);

    private final AdvertisementAnswersRepository advertisementAnswersRepository;

    private final AdvertisementAnswersMapper advertisementAnswersMapper;

    public AdvertisementAnswersServiceImpl(AdvertisementAnswersRepository advertisementAnswersRepository, AdvertisementAnswersMapper advertisementAnswersMapper) {
        this.advertisementAnswersRepository = advertisementAnswersRepository;
        this.advertisementAnswersMapper = advertisementAnswersMapper;
    }

    @Override
    public AdvertisementAnswersDTO save(AdvertisementAnswersDTO advertisementAnswersDTO) {
        log.debug("Request to save AdvertisementAnswers : {}", advertisementAnswersDTO);
        AdvertisementAnswers advertisementAnswers = advertisementAnswersMapper.toEntity(advertisementAnswersDTO);
        advertisementAnswers = advertisementAnswersRepository.save(advertisementAnswers);
        return advertisementAnswersMapper.toDto(advertisementAnswers);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdvertisementAnswersDTO> findAll() {
        log.debug("Request to get all AdvertisementAnswers");
        return advertisementAnswersRepository.findAll().stream()
            .map(advertisementAnswersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AdvertisementAnswersDTO> findOne(Long id) {
        log.debug("Request to get AdvertisementAnswers : {}", id);
        return advertisementAnswersRepository.findById(id)
            .map(advertisementAnswersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdvertisementAnswers : {}", id);
        advertisementAnswersRepository.deleteById(id);
    }
}
