package ee.bonly.advertisement.service.impl;

import ee.bonly.advertisement.service.AdvertisementService;
import ee.bonly.advertisement.domain.Advertisement;
import ee.bonly.advertisement.repository.AdvertisementRepository;
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

    private final AdvertisementMapper advertisementMapper;

    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository, AdvertisementMapper advertisementMapper) {
        this.advertisementRepository = advertisementRepository;
        this.advertisementMapper = advertisementMapper;
    }

    @Override
    public AdvertisementDTO save(AdvertisementDTO advertisementDTO) {
        log.debug("Request to save Advertisement : {}", advertisementDTO);
        Advertisement advertisement = advertisementMapper.toEntity(advertisementDTO);
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
