package ee.bonly.advertisement.service.impl;

import ee.bonly.advertisement.domain.Image;
import ee.bonly.advertisement.domain.Prize;
import ee.bonly.advertisement.repository.ImageRepository;
import ee.bonly.advertisement.repository.PrizeRepository;
import ee.bonly.advertisement.service.PrizeService;
import ee.bonly.advertisement.service.dto.PrizeDTO;
import ee.bonly.advertisement.service.mapper.PrizeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Prize}.
 */
@Service
@Transactional
public class PrizeServiceImpl implements PrizeService {

    private final Logger log = LoggerFactory.getLogger(PrizeServiceImpl.class);

    private final PrizeRepository prizeRepository;

    private final ImageRepository imageRepository;

    private final PrizeMapper prizeMapper;

    public PrizeServiceImpl(PrizeRepository prizeRepository,
                            ImageRepository imageRepository,
                            PrizeMapper prizeMapper) {
        this.prizeRepository = prizeRepository;
        this.prizeMapper = prizeMapper;
        this.imageRepository = imageRepository;
    }

    @Override
    public PrizeDTO save(PrizeDTO prizeDTO) {
        log.debug("Request to save Prize : {}", prizeDTO);
        Prize prize = prizeMapper.toEntity(prizeDTO);
        String base64Image = prizeDTO.getImage().split(",")[1];
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
        Image image = new Image();
        image.setId(prizeDTO.getImageId());
        image.setContent(imageBytes);
        Image save = imageRepository.save(image);
        prize.setImageId(save.getId());
        prize = prizeRepository.save(prize);
        return prizeMapper.toDto(prize);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrizeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Prizes");
        return prizeRepository.findAll(pageable)
            .map(prizeMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PrizeDTO> findOne(Long id) {
        log.debug("Request to get Prize : {}", id);
        return prizeRepository.findById(id)
            .map(prizeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prize : {}", id);
        prizeRepository.deleteById(id);
    }
}
