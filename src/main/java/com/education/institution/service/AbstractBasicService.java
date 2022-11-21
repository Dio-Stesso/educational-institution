package com.education.institution.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface AbstractBasicService<M> {
    M save(M model);

    void deleteById(Long id);

    M findById(Long id);

    List<M> findAll(PageRequest pageRequest);

    List<M> findAllByName(String firstName, String lastName, PageRequest pageRequest);
}
