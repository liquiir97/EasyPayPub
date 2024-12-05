package easypay.service.impl;

import easypay.domain.Uplatnica;
import easypay.domain.User;
import easypay.repository.UplatnicaRepository;
import easypay.repository.UserRepository;
import easypay.service.UplatnicaService;
import easypay.service.dto.UplatnicaDTO;
import easypay.service.mapper.UplatnicaMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Uplatnica}.
 */
@Service
@Transactional
public class UplatnicaServiceImpl implements UplatnicaService {

    @Autowired
    UserRepository userRepository;

    private final Logger log = LoggerFactory.getLogger(UplatnicaServiceImpl.class);

    private final UplatnicaRepository uplatnicaRepository;

    private final UplatnicaMapper uplatnicaMapper;

    public UplatnicaServiceImpl(UplatnicaRepository uplatnicaRepository, UplatnicaMapper uplatnicaMapper) {
        this.uplatnicaRepository = uplatnicaRepository;
        this.uplatnicaMapper = uplatnicaMapper;
    }

    @Override
    public UplatnicaDTO save(UplatnicaDTO uplatnicaDTO) {
        log.debug("Request to save Uplatnica : {}", uplatnicaDTO);
        Uplatnica uplatnica = uplatnicaMapper.toEntity(uplatnicaDTO);
        uplatnica = uplatnicaRepository.save(uplatnica);
        return uplatnicaMapper.toDto(uplatnica);
    }

    @Override
    public UplatnicaDTO update(UplatnicaDTO uplatnicaDTO) {
        log.debug("Request to update Uplatnica : {}", uplatnicaDTO);
        Uplatnica uplatnica = uplatnicaMapper.toEntity(uplatnicaDTO);
        uplatnica = uplatnicaRepository.save(uplatnica);
        return uplatnicaMapper.toDto(uplatnica);
    }

    @Override
    public Optional<UplatnicaDTO> partialUpdate(UplatnicaDTO uplatnicaDTO) {
        log.debug("Request to partially update Uplatnica : {}", uplatnicaDTO);

        return uplatnicaRepository
            .findById(uplatnicaDTO.getId())
            .map(existingUplatnica -> {
                uplatnicaMapper.partialUpdate(existingUplatnica, uplatnicaDTO);

                return existingUplatnica;
            })
            .map(uplatnicaRepository::save)
            .map(uplatnicaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UplatnicaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Uplatnicas");
        Page<Uplatnica> s = uplatnicaRepository.findAll(pageable);
        return s.map(uplatnicaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UplatnicaDTO> findAllByUserId(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Long userId = this.userRepository.findOneByLogin(currentPrincipalName).get().getId();
        log.debug("aaa");
        Page<Uplatnica> s = uplatnicaRepository.findAllByUserId(pageable, userId);
        return s.map(uplatnicaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UplatnicaDTO> findOne(Long id) {
        log.debug("Request to get Uplatnica : {}", id);
        return uplatnicaRepository.findById(id).map(uplatnicaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Uplatnica : {}", id);
        uplatnicaRepository.deleteById(id);
    }

    @Override
    public List<UplatnicaDTO> findAllPaymentsByUser(String user) {
        List<UplatnicaDTO> listUplatnicaDto = new ArrayList<>();
        Optional<User> userOptional = this.userRepository.findOneByLogin(user);
        List<Uplatnica> uplatnicaList = this.uplatnicaRepository.findAllByUserId(userOptional.get().getId());
        listUplatnicaDto =
            uplatnicaList
                .stream()
                .map(ele -> {
                    UplatnicaDTO uplatnicaDTO = new UplatnicaDTO();
                    uplatnicaDTO.setSvrhaUplate(ele.getSvrhaUplate());
                    uplatnicaDTO.setPrimalac(ele.getPrimalac());
                    return uplatnicaDTO;
                })
                .collect(Collectors.toList());
        return listUplatnicaDto;
    }
}
