package easypay.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import easypay.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UplatnicaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UplatnicaDTO.class);
        UplatnicaDTO uplatnicaDTO1 = new UplatnicaDTO();
        uplatnicaDTO1.setId(1L);
        UplatnicaDTO uplatnicaDTO2 = new UplatnicaDTO();
        assertThat(uplatnicaDTO1).isNotEqualTo(uplatnicaDTO2);
        uplatnicaDTO2.setId(uplatnicaDTO1.getId());
        assertThat(uplatnicaDTO1).isEqualTo(uplatnicaDTO2);
        uplatnicaDTO2.setId(2L);
        assertThat(uplatnicaDTO1).isNotEqualTo(uplatnicaDTO2);
        uplatnicaDTO1.setId(null);
        assertThat(uplatnicaDTO1).isNotEqualTo(uplatnicaDTO2);
    }
}
