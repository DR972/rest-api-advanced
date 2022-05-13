package com.epam.esm.dto.mapper;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    TagDto convertToDto(Tag tag);

    Tag convertToEntity(TagDto dto);
}
