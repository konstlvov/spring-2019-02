/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring10.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ru.otus.spring10.domain.Comment;

/**
 *
 * @author lvov_k
 */
public interface CommentRepository extends CrudRepository<Comment, Long> {
  @Override
  List<Comment> findAll();
}
