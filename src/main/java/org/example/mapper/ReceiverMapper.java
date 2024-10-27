package org.example.mapper;

import org.example.dto.ReceiverDto;
import org.example.entity.Receiver;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReceiverMapper {
    ReceiverDto toDto(Receiver receiver);

    Receiver toEntity(ReceiverDto receiverDto);
}
