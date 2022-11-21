package com.education.institution.service.mapper;

public interface RequestDtoMapper<D, M> {
    M toModel(D dto);
}
