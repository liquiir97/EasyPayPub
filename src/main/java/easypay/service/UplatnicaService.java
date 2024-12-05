package easypay.service;

import easypay.service.dto.UplatnicaDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link easypay.domain.Uplatnica}.
 */
public interface UplatnicaService {
    /**
     * Save a uplatnica.
     *
     * @param uplatnicaDTO the entity to save.
     * @return the persisted entity.
     */
    UplatnicaDTO save(UplatnicaDTO uplatnicaDTO);

    /**
     * Updates a uplatnica.
     *
     * @param uplatnicaDTO the entity to update.
     * @return the persisted entity.
     */
    UplatnicaDTO update(UplatnicaDTO uplatnicaDTO);

    /**
     * Partially updates a uplatnica.
     *
     * @param uplatnicaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UplatnicaDTO> partialUpdate(UplatnicaDTO uplatnicaDTO);

    /**
     * Get all the uplatnicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UplatnicaDTO> findAll(Pageable pageable);

    Page<UplatnicaDTO> findAllByUserId(Pageable pageable);

    /**
     * Get the "id" uplatnica.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UplatnicaDTO> findOne(Long id);

    /**
     * Delete the "id" uplatnica.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<UplatnicaDTO> findAllPaymentsByUser(String user);
}
