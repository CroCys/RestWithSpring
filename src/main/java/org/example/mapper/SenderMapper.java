package org.example.mapper;

import org.example.dto.SenderDto;
import org.example.entity.Sender;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SenderMapper {
    SenderDto toDto(Sender sender);

    Sender toEntity(SenderDto senderDto);
}
