package ru.otus.spring12.repostory;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring12.domain.Email;

import java.util.List;
import ru.otus.spring12.domain.Email;

public interface EmailRepository extends CrudRepository<Email, Integer> {

    List<Email> findAll();
}
