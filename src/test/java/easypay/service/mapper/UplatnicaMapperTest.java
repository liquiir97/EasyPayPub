package easypay.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UplatnicaMapperTest {

    private UplatnicaMapper uplatnicaMapper;

    @BeforeEach
    public void setUp() {
        uplatnicaMapper = new UplatnicaMapperImpl();
    }
}
