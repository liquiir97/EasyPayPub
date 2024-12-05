package easypay.service.mapper;

import easypay.domain.Uplatnica;
import easypay.service.dto.UplatnicaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Uplatnica} and its DTO {@link UplatnicaDTO}.
 */
@Mapper(componentModel = "spring")
public interface UplatnicaMapper extends EntityMapper<UplatnicaDTO, Uplatnica> {}
