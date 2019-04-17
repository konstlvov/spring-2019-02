/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring10.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring10.domain.Author;

/**
 *
 * @author lvov_k
 */
public interface AuthorRepository extends CrudRepository<Author, Long> {
  
}
