package com.clevertec.checkrunner.service.mapper.list;

import com.clevertec.checkrunner.domain.Check;
import com.clevertec.checkrunner.dto.CheckDto;
import com.clevertec.checkrunner.service.mapper.CheckMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CheckMapper.class)
public interface CheckListMapper {

    Check dtoToDomain(CheckDto checkDto);

    CheckDto domainToDto(Check check);

}
