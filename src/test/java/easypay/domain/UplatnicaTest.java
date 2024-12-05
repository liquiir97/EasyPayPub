package easypay.domain;

import static org.assertj.core.api.Assertions.assertThat;

import easypay.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UplatnicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Uplatnica.class);
        Uplatnica uplatnica1 = new Uplatnica();
        uplatnica1.setId(1L);
        Uplatnica uplatnica2 = new Uplatnica();
        uplatnica2.setId(uplatnica1.getId());
        assertThat(uplatnica1).isEqualTo(uplatnica2);
        uplatnica2.setId(2L);
        assertThat(uplatnica1).isNotEqualTo(uplatnica2);
        uplatnica1.setId(null);
        assertThat(uplatnica1).isNotEqualTo(uplatnica2);
    }
}
