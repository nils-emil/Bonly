package ee.bonly.advertisement.service.impl;

import ee.bonly.advertisement.service.PrizeRegistrationService;
import ee.bonly.advertisement.domain.PrizeRegistration;
import ee.bonly.advertisement.repository.PrizeRegistrationRepository;
import ee.bonly.advertisement.service.dto.PrizeRegistrationDTO;
import ee.bonly.advertisement.service.mapper.PrizeRegistrationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public PrizeRegistrationServiceImpl(PrizeRegistrationRepository prizeRegistrationRepository, PrizeRegistrationMapper prizeRegistrationMapper) {
        this.prizeRegistrationRepository = prizeRegistrationRepository;
        this.prizeRegistrationMapper = prizeRegistrationMapper;
    }

    @Override
    public PrizeRegistrationDTO save(PrizeRegistrationDTO prizeRegistrationDTO) {
        log.debug("Request to save PrizeRegistration : {}", prizeRegistrationDTO);
        PrizeRegistration prizeRegistration = prizeRegistrationMapper.toEntity(prizeRegistrationDTO);
        prizeRegistration = prizeRegistrationRepository.save(prizeRegistration);
        return prizeRegistrationMapper.toDto(prizeRegistration);
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
