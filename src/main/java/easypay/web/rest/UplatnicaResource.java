package easypay.web.rest;

import easypay.repository.UplatnicaRepository;
import easypay.service.PdfGeneratorService;
import easypay.service.UplatnicaService;
import easypay.service.dto.UplatnicaDTO;
import easypay.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link easypay.domain.Uplatnica}.
 */
@RestController
@RequestMapping("/api")
public class UplatnicaResource {

    @Autowired
    PdfGeneratorService pdfGeneratorService;

    private final Logger log = LoggerFactory.getLogger(UplatnicaResource.class);

    private static final String ENTITY_NAME = "uplatnica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UplatnicaService uplatnicaService;

    private final UplatnicaRepository uplatnicaRepository;

    public UplatnicaResource(UplatnicaService uplatnicaService, UplatnicaRepository uplatnicaRepository) {
        this.uplatnicaService = uplatnicaService;
        this.uplatnicaRepository = uplatnicaRepository;
    }

    /**
     * {@code POST  /uplatnicas} : Create a new uplatnica.
     *
     * @param uplatnicaDTO the uplatnicaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uplatnicaDTO, or with status {@code 400 (Bad Request)} if the uplatnica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/uplatnicas")
    public ResponseEntity<UplatnicaDTO> createUplatnica(@RequestBody UplatnicaDTO uplatnicaDTO) throws URISyntaxException {
        log.debug("REST request to save Uplatnica : {}", uplatnicaDTO);
        if (uplatnicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new uplatnica cannot already have an ID", ENTITY_NAME, "idexists");
            //uplatnicaDTO.setId(null);
        }

        UplatnicaDTO result = uplatnicaService.save(uplatnicaDTO);
        if (uplatnicaDTO.isGeneratePdf()) {
            DateTimeFormatter dateTimeForPdf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
            LocalDateTime now = LocalDateTime.now();

            String outputPath = "C:/Users/Nidza/Documents/uplatnica_" + dateTimeForPdf.format(now) + ".pdf";
            //pdfGeneratorService.generateSimplePdf(outputPath, uplatnicaDTO);
        }
        return ResponseEntity
            .created(new URI("/api/uplatnicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /uplatnicas/:id} : Updates an existing uplatnica.
     *
     * @param id the id of the uplatnicaDTO to save.
     * @param uplatnicaDTO the uplatnicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uplatnicaDTO,
     * or with status {@code 400 (Bad Request)} if the uplatnicaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uplatnicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/uplatnicas/{id}")
    public ResponseEntity<UplatnicaDTO> updateUplatnica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UplatnicaDTO uplatnicaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Uplatnica : {}, {}", id, uplatnicaDTO);
        if (uplatnicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uplatnicaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uplatnicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UplatnicaDTO result = uplatnicaService.update(uplatnicaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, uplatnicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /uplatnicas/:id} : Partial updates given fields of an existing uplatnica, field will ignore if it is null
     *
     * @param id the id of the uplatnicaDTO to save.
     * @param uplatnicaDTO the uplatnicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uplatnicaDTO,
     * or with status {@code 400 (Bad Request)} if the uplatnicaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the uplatnicaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the uplatnicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/uplatnicas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UplatnicaDTO> partialUpdateUplatnica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UplatnicaDTO uplatnicaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Uplatnica partially : {}, {}", id, uplatnicaDTO);
        if (uplatnicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uplatnicaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uplatnicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UplatnicaDTO> result = uplatnicaService.partialUpdate(uplatnicaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, uplatnicaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /uplatnicas} : get all the uplatnicas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uplatnicas in body.
     */
    /*@GetMapping("/uplatnicas")
    public ResponseEntity<List<UplatnicaDTO>> getAllUplatnicas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Uplatnicas");
        Page<UplatnicaDTO> page = uplatnicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }*/

    @GetMapping("/uplatnicas")
    @CrossOrigin(origins = { "http://localhost:9000", "http://localhost:8080" })
    public ResponseEntity<List<UplatnicaDTO>> getAllUplatnicasByUser(@ParameterObject Pageable pageable) { //@org.springdoc.api.annotations.ParameterObject Pageable pageable
        log.debug("REST request to get a page of Uplatnicas");
        Page<UplatnicaDTO> page = uplatnicaService.findAllByUserId(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /uplatnicas/:id} : get the "id" uplatnica.
     *
     * @param id the id of the uplatnicaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uplatnicaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/uplatnicas/{id}")
    public ResponseEntity<UplatnicaDTO> getUplatnica(@PathVariable Long id) {
        log.debug("REST request to get Uplatnica : {}", id);
        Optional<UplatnicaDTO> uplatnicaDTO = uplatnicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uplatnicaDTO);
    }

    /**
     * {@code DELETE  /uplatnicas/:id} : delete the "id" uplatnica.
     *
     * @param id the id of the uplatnicaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/uplatnicas/{id}")
    public ResponseEntity<Void> deleteUplatnica(@PathVariable Long id) {
        log.debug("REST request to delete Uplatnica : {}", id);
        uplatnicaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @CrossOrigin(origins = "http://localhost:9000")
    @GetMapping("/find-all-payments-by-user/{user}")
    public ResponseEntity<List<UplatnicaDTO>> getAllUplatnicasByUser(@PathVariable("user") String user) {
        log.info("/find-all-payments-by-user");
        List<UplatnicaDTO> listUplatnicaDtoByUser = this.uplatnicaService.findAllPaymentsByUser(user);
        List<UplatnicaDTO> liss = listUplatnicaDtoByUser.stream().filter(e -> e.getIznos() < 5).collect(Collectors.toList());
        int count = liss.size();
        return ResponseEntity.ok(listUplatnicaDtoByUser);
    }

    @CrossOrigin(origins = "http://localhost:9000")
    @PostMapping("/create-from-template")
    public ResponseEntity<UplatnicaDTO> createUplatnicaFromTemplate(@RequestBody UplatnicaDTO uplatnicaDTO) throws URISyntaxException {
        log.info("/create-from-template");
        if (uplatnicaDTO.getId() != null) {
            uplatnicaDTO.setId(null);
        }

        UplatnicaDTO result = this.uplatnicaService.save(uplatnicaDTO);

        if (uplatnicaDTO.isGeneratePdf()) {
            DateTimeFormatter dateTimeForPdf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
            LocalDateTime now = LocalDateTime.now();

            String outputPath = "C:/Users/Nidza/Documents/uplatnica_" + dateTimeForPdf.format(now) + ".pdf";
            //pdfGeneratorService.generateSimplePdf(outputPath, uplatnicaDTO);
        }

        return ResponseEntity
            .created(new URI("/api/uplatnicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}
