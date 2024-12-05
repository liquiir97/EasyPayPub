package easypay.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import easypay.IntegrationTest;
import easypay.domain.Uplatnica;
import easypay.repository.UplatnicaRepository;
import easypay.service.dto.UplatnicaDTO;
import easypay.service.mapper.UplatnicaMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UplatnicaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UplatnicaResourceIT {

    private static final String DEFAULT_UPLATILAC = "AAAAAAAAAA";
    private static final String UPDATED_UPLATILAC = "BBBBBBBBBB";

    private static final String DEFAULT_SVRHA_UPLATE = "AAAAAAAAAA";
    private static final String UPDATED_SVRHA_UPLATE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMALAC = "AAAAAAAAAA";
    private static final String UPDATED_PRIMALAC = "BBBBBBBBBB";

    private static final String DEFAULT_SIFRA_PLACANJA = "AAAAAAAAAA";
    private static final String UPDATED_SIFRA_PLACANJA = "BBBBBBBBBB";

    private static final String DEFAULT_VALUTA = "AAAAAAAAAA";
    private static final String UPDATED_VALUTA = "BBBBBBBBBB";

    private static final Long DEFAULT_IZNOS = 1L;
    private static final Long UPDATED_IZNOS = 2L;

    private static final String DEFAULT_RACUN_PRIMAOCA = "AAAAAAAAAA";
    private static final String UPDATED_RACUN_PRIMAOCA = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_POZIV_NA_BROJ = "AAAAAAAAAA";
    private static final String UPDATED_POZIV_NA_BROJ = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUM_KREIRANJA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM_KREIRANJA = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/uplatnicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UplatnicaRepository uplatnicaRepository;

    @Autowired
    private UplatnicaMapper uplatnicaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUplatnicaMockMvc;

    private Uplatnica uplatnica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uplatnica createEntity(EntityManager em) {
        Uplatnica uplatnica = new Uplatnica()
            .uplatilac(DEFAULT_UPLATILAC)
            .svrhaUplate(DEFAULT_SVRHA_UPLATE)
            .primalac(DEFAULT_PRIMALAC)
            .sifraPlacanja(DEFAULT_SIFRA_PLACANJA)
            .valuta(DEFAULT_VALUTA)
            .iznos(DEFAULT_IZNOS)
            .racunPrimaoca(DEFAULT_RACUN_PRIMAOCA)
            .model(DEFAULT_MODEL)
            .pozivNaBroj(DEFAULT_POZIV_NA_BROJ)
            .datumKreiranja(DEFAULT_DATUM_KREIRANJA);
        return uplatnica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uplatnica createUpdatedEntity(EntityManager em) {
        Uplatnica uplatnica = new Uplatnica()
            .uplatilac(UPDATED_UPLATILAC)
            .svrhaUplate(UPDATED_SVRHA_UPLATE)
            .primalac(UPDATED_PRIMALAC)
            .sifraPlacanja(UPDATED_SIFRA_PLACANJA)
            .valuta(UPDATED_VALUTA)
            .iznos(UPDATED_IZNOS)
            .racunPrimaoca(UPDATED_RACUN_PRIMAOCA)
            .model(UPDATED_MODEL)
            .pozivNaBroj(UPDATED_POZIV_NA_BROJ)
            .datumKreiranja(UPDATED_DATUM_KREIRANJA);
        return uplatnica;
    }

    @BeforeEach
    public void initTest() {
        uplatnica = createEntity(em);
    }

    @Test
    @Transactional
    void createUplatnica() throws Exception {
        int databaseSizeBeforeCreate = uplatnicaRepository.findAll().size();
        // Create the Uplatnica
        UplatnicaDTO uplatnicaDTO = uplatnicaMapper.toDto(uplatnica);
        restUplatnicaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(uplatnicaDTO)))
            .andExpect(status().isCreated());

        // Validate the Uplatnica in the database
        List<Uplatnica> uplatnicaList = uplatnicaRepository.findAll();
        assertThat(uplatnicaList).hasSize(databaseSizeBeforeCreate + 1);
        Uplatnica testUplatnica = uplatnicaList.get(uplatnicaList.size() - 1);
        assertThat(testUplatnica.getUplatilac()).isEqualTo(DEFAULT_UPLATILAC);
        assertThat(testUplatnica.getSvrhaUplate()).isEqualTo(DEFAULT_SVRHA_UPLATE);
        assertThat(testUplatnica.getPrimalac()).isEqualTo(DEFAULT_PRIMALAC);
        assertThat(testUplatnica.getSifraPlacanja()).isEqualTo(DEFAULT_SIFRA_PLACANJA);
        assertThat(testUplatnica.getValuta()).isEqualTo(DEFAULT_VALUTA);
        assertThat(testUplatnica.getIznos()).isEqualTo(DEFAULT_IZNOS);
        assertThat(testUplatnica.getRacunPrimaoca()).isEqualTo(DEFAULT_RACUN_PRIMAOCA);
        assertThat(testUplatnica.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testUplatnica.getPozivNaBroj()).isEqualTo(DEFAULT_POZIV_NA_BROJ);
        assertThat(testUplatnica.getDatumKreiranja()).isEqualTo(DEFAULT_DATUM_KREIRANJA);
    }

    @Test
    @Transactional
    void createUplatnicaWithExistingId() throws Exception {
        // Create the Uplatnica with an existing ID
        uplatnica.setId(1L);
        UplatnicaDTO uplatnicaDTO = uplatnicaMapper.toDto(uplatnica);

        int databaseSizeBeforeCreate = uplatnicaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUplatnicaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(uplatnicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Uplatnica in the database
        List<Uplatnica> uplatnicaList = uplatnicaRepository.findAll();
        assertThat(uplatnicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUplatnicas() throws Exception {
        // Initialize the database
        uplatnicaRepository.saveAndFlush(uplatnica);

        // Get all the uplatnicaList
        restUplatnicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uplatnica.getId().intValue())))
            .andExpect(jsonPath("$.[*].uplatilac").value(hasItem(DEFAULT_UPLATILAC)))
            .andExpect(jsonPath("$.[*].svrhaUplate").value(hasItem(DEFAULT_SVRHA_UPLATE)))
            .andExpect(jsonPath("$.[*].primalac").value(hasItem(DEFAULT_PRIMALAC)))
            .andExpect(jsonPath("$.[*].sifraPlacanja").value(hasItem(DEFAULT_SIFRA_PLACANJA)))
            .andExpect(jsonPath("$.[*].valuta").value(hasItem(DEFAULT_VALUTA)))
            .andExpect(jsonPath("$.[*].iznos").value(hasItem(DEFAULT_IZNOS.intValue())))
            .andExpect(jsonPath("$.[*].racunPrimaoca").value(hasItem(DEFAULT_RACUN_PRIMAOCA)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].pozivNaBroj").value(hasItem(DEFAULT_POZIV_NA_BROJ)))
            .andExpect(jsonPath("$.[*].datumKreiranja").value(hasItem(DEFAULT_DATUM_KREIRANJA.toString())));
    }

    @Test
    @Transactional
    void getUplatnica() throws Exception {
        // Initialize the database
        uplatnicaRepository.saveAndFlush(uplatnica);

        // Get the uplatnica
        restUplatnicaMockMvc
            .perform(get(ENTITY_API_URL_ID, uplatnica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uplatnica.getId().intValue()))
            .andExpect(jsonPath("$.uplatilac").value(DEFAULT_UPLATILAC))
            .andExpect(jsonPath("$.svrhaUplate").value(DEFAULT_SVRHA_UPLATE))
            .andExpect(jsonPath("$.primalac").value(DEFAULT_PRIMALAC))
            .andExpect(jsonPath("$.sifraPlacanja").value(DEFAULT_SIFRA_PLACANJA))
            .andExpect(jsonPath("$.valuta").value(DEFAULT_VALUTA))
            .andExpect(jsonPath("$.iznos").value(DEFAULT_IZNOS.intValue()))
            .andExpect(jsonPath("$.racunPrimaoca").value(DEFAULT_RACUN_PRIMAOCA))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.pozivNaBroj").value(DEFAULT_POZIV_NA_BROJ))
            .andExpect(jsonPath("$.datumKreiranja").value(DEFAULT_DATUM_KREIRANJA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUplatnica() throws Exception {
        // Get the uplatnica
        restUplatnicaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUplatnica() throws Exception {
        // Initialize the database
        uplatnicaRepository.saveAndFlush(uplatnica);

        int databaseSizeBeforeUpdate = uplatnicaRepository.findAll().size();

        // Update the uplatnica
        Uplatnica updatedUplatnica = uplatnicaRepository.findById(uplatnica.getId()).get();
        // Disconnect from session so that the updates on updatedUplatnica are not directly saved in db
        em.detach(updatedUplatnica);
        updatedUplatnica
            .uplatilac(UPDATED_UPLATILAC)
            .svrhaUplate(UPDATED_SVRHA_UPLATE)
            .primalac(UPDATED_PRIMALAC)
            .sifraPlacanja(UPDATED_SIFRA_PLACANJA)
            .valuta(UPDATED_VALUTA)
            .iznos(UPDATED_IZNOS)
            .racunPrimaoca(UPDATED_RACUN_PRIMAOCA)
            .model(UPDATED_MODEL)
            .pozivNaBroj(UPDATED_POZIV_NA_BROJ)
            .datumKreiranja(UPDATED_DATUM_KREIRANJA);
        UplatnicaDTO uplatnicaDTO = uplatnicaMapper.toDto(updatedUplatnica);

        restUplatnicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, uplatnicaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(uplatnicaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Uplatnica in the database
        List<Uplatnica> uplatnicaList = uplatnicaRepository.findAll();
        assertThat(uplatnicaList).hasSize(databaseSizeBeforeUpdate);
        Uplatnica testUplatnica = uplatnicaList.get(uplatnicaList.size() - 1);
        assertThat(testUplatnica.getUplatilac()).isEqualTo(UPDATED_UPLATILAC);
        assertThat(testUplatnica.getSvrhaUplate()).isEqualTo(UPDATED_SVRHA_UPLATE);
        assertThat(testUplatnica.getPrimalac()).isEqualTo(UPDATED_PRIMALAC);
        assertThat(testUplatnica.getSifraPlacanja()).isEqualTo(UPDATED_SIFRA_PLACANJA);
        assertThat(testUplatnica.getValuta()).isEqualTo(UPDATED_VALUTA);
        assertThat(testUplatnica.getIznos()).isEqualTo(UPDATED_IZNOS);
        assertThat(testUplatnica.getRacunPrimaoca()).isEqualTo(UPDATED_RACUN_PRIMAOCA);
        assertThat(testUplatnica.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testUplatnica.getPozivNaBroj()).isEqualTo(UPDATED_POZIV_NA_BROJ);
        assertThat(testUplatnica.getDatumKreiranja()).isEqualTo(UPDATED_DATUM_KREIRANJA);
    }

    @Test
    @Transactional
    void putNonExistingUplatnica() throws Exception {
        int databaseSizeBeforeUpdate = uplatnicaRepository.findAll().size();
        uplatnica.setId(count.incrementAndGet());

        // Create the Uplatnica
        UplatnicaDTO uplatnicaDTO = uplatnicaMapper.toDto(uplatnica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUplatnicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, uplatnicaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(uplatnicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uplatnica in the database
        List<Uplatnica> uplatnicaList = uplatnicaRepository.findAll();
        assertThat(uplatnicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUplatnica() throws Exception {
        int databaseSizeBeforeUpdate = uplatnicaRepository.findAll().size();
        uplatnica.setId(count.incrementAndGet());

        // Create the Uplatnica
        UplatnicaDTO uplatnicaDTO = uplatnicaMapper.toDto(uplatnica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUplatnicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(uplatnicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uplatnica in the database
        List<Uplatnica> uplatnicaList = uplatnicaRepository.findAll();
        assertThat(uplatnicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUplatnica() throws Exception {
        int databaseSizeBeforeUpdate = uplatnicaRepository.findAll().size();
        uplatnica.setId(count.incrementAndGet());

        // Create the Uplatnica
        UplatnicaDTO uplatnicaDTO = uplatnicaMapper.toDto(uplatnica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUplatnicaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(uplatnicaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Uplatnica in the database
        List<Uplatnica> uplatnicaList = uplatnicaRepository.findAll();
        assertThat(uplatnicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUplatnicaWithPatch() throws Exception {
        // Initialize the database
        uplatnicaRepository.saveAndFlush(uplatnica);

        int databaseSizeBeforeUpdate = uplatnicaRepository.findAll().size();

        // Update the uplatnica using partial update
        Uplatnica partialUpdatedUplatnica = new Uplatnica();
        partialUpdatedUplatnica.setId(uplatnica.getId());

        partialUpdatedUplatnica.primalac(UPDATED_PRIMALAC).iznos(UPDATED_IZNOS).racunPrimaoca(UPDATED_RACUN_PRIMAOCA).model(UPDATED_MODEL);

        restUplatnicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUplatnica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUplatnica))
            )
            .andExpect(status().isOk());

        // Validate the Uplatnica in the database
        List<Uplatnica> uplatnicaList = uplatnicaRepository.findAll();
        assertThat(uplatnicaList).hasSize(databaseSizeBeforeUpdate);
        Uplatnica testUplatnica = uplatnicaList.get(uplatnicaList.size() - 1);
        assertThat(testUplatnica.getUplatilac()).isEqualTo(DEFAULT_UPLATILAC);
        assertThat(testUplatnica.getSvrhaUplate()).isEqualTo(DEFAULT_SVRHA_UPLATE);
        assertThat(testUplatnica.getPrimalac()).isEqualTo(UPDATED_PRIMALAC);
        assertThat(testUplatnica.getSifraPlacanja()).isEqualTo(DEFAULT_SIFRA_PLACANJA);
        assertThat(testUplatnica.getValuta()).isEqualTo(DEFAULT_VALUTA);
        assertThat(testUplatnica.getIznos()).isEqualTo(UPDATED_IZNOS);
        assertThat(testUplatnica.getRacunPrimaoca()).isEqualTo(UPDATED_RACUN_PRIMAOCA);
        assertThat(testUplatnica.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testUplatnica.getPozivNaBroj()).isEqualTo(DEFAULT_POZIV_NA_BROJ);
        assertThat(testUplatnica.getDatumKreiranja()).isEqualTo(DEFAULT_DATUM_KREIRANJA);
    }

    @Test
    @Transactional
    void fullUpdateUplatnicaWithPatch() throws Exception {
        // Initialize the database
        uplatnicaRepository.saveAndFlush(uplatnica);

        int databaseSizeBeforeUpdate = uplatnicaRepository.findAll().size();

        // Update the uplatnica using partial update
        Uplatnica partialUpdatedUplatnica = new Uplatnica();
        partialUpdatedUplatnica.setId(uplatnica.getId());

        partialUpdatedUplatnica
            .uplatilac(UPDATED_UPLATILAC)
            .svrhaUplate(UPDATED_SVRHA_UPLATE)
            .primalac(UPDATED_PRIMALAC)
            .sifraPlacanja(UPDATED_SIFRA_PLACANJA)
            .valuta(UPDATED_VALUTA)
            .iznos(UPDATED_IZNOS)
            .racunPrimaoca(UPDATED_RACUN_PRIMAOCA)
            .model(UPDATED_MODEL)
            .pozivNaBroj(UPDATED_POZIV_NA_BROJ)
            .datumKreiranja(UPDATED_DATUM_KREIRANJA);

        restUplatnicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUplatnica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUplatnica))
            )
            .andExpect(status().isOk());

        // Validate the Uplatnica in the database
        List<Uplatnica> uplatnicaList = uplatnicaRepository.findAll();
        assertThat(uplatnicaList).hasSize(databaseSizeBeforeUpdate);
        Uplatnica testUplatnica = uplatnicaList.get(uplatnicaList.size() - 1);
        assertThat(testUplatnica.getUplatilac()).isEqualTo(UPDATED_UPLATILAC);
        assertThat(testUplatnica.getSvrhaUplate()).isEqualTo(UPDATED_SVRHA_UPLATE);
        assertThat(testUplatnica.getPrimalac()).isEqualTo(UPDATED_PRIMALAC);
        assertThat(testUplatnica.getSifraPlacanja()).isEqualTo(UPDATED_SIFRA_PLACANJA);
        assertThat(testUplatnica.getValuta()).isEqualTo(UPDATED_VALUTA);
        assertThat(testUplatnica.getIznos()).isEqualTo(UPDATED_IZNOS);
        assertThat(testUplatnica.getRacunPrimaoca()).isEqualTo(UPDATED_RACUN_PRIMAOCA);
        assertThat(testUplatnica.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testUplatnica.getPozivNaBroj()).isEqualTo(UPDATED_POZIV_NA_BROJ);
        assertThat(testUplatnica.getDatumKreiranja()).isEqualTo(UPDATED_DATUM_KREIRANJA);
    }

    @Test
    @Transactional
    void patchNonExistingUplatnica() throws Exception {
        int databaseSizeBeforeUpdate = uplatnicaRepository.findAll().size();
        uplatnica.setId(count.incrementAndGet());

        // Create the Uplatnica
        UplatnicaDTO uplatnicaDTO = uplatnicaMapper.toDto(uplatnica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUplatnicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, uplatnicaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(uplatnicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uplatnica in the database
        List<Uplatnica> uplatnicaList = uplatnicaRepository.findAll();
        assertThat(uplatnicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUplatnica() throws Exception {
        int databaseSizeBeforeUpdate = uplatnicaRepository.findAll().size();
        uplatnica.setId(count.incrementAndGet());

        // Create the Uplatnica
        UplatnicaDTO uplatnicaDTO = uplatnicaMapper.toDto(uplatnica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUplatnicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(uplatnicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Uplatnica in the database
        List<Uplatnica> uplatnicaList = uplatnicaRepository.findAll();
        assertThat(uplatnicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUplatnica() throws Exception {
        int databaseSizeBeforeUpdate = uplatnicaRepository.findAll().size();
        uplatnica.setId(count.incrementAndGet());

        // Create the Uplatnica
        UplatnicaDTO uplatnicaDTO = uplatnicaMapper.toDto(uplatnica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUplatnicaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(uplatnicaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Uplatnica in the database
        List<Uplatnica> uplatnicaList = uplatnicaRepository.findAll();
        assertThat(uplatnicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUplatnica() throws Exception {
        // Initialize the database
        uplatnicaRepository.saveAndFlush(uplatnica);

        int databaseSizeBeforeDelete = uplatnicaRepository.findAll().size();

        // Delete the uplatnica
        restUplatnicaMockMvc
            .perform(delete(ENTITY_API_URL_ID, uplatnica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Uplatnica> uplatnicaList = uplatnicaRepository.findAll();
        assertThat(uplatnicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
