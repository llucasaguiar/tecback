package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.GeneroDTO;
import br.uniesp.si.techback.model.Genero;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GeneroMapper {

    private final ModelMapper modelMapper;

    public GeneroMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Genero toEntity(GeneroDTO dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, Genero.class);
    }

    public GeneroDTO toDTO(Genero entity) {
        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, GeneroDTO.class);
    }

}
