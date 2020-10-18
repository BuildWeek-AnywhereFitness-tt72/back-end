package com.lambdaschool.fitnessanywhere.repository;

import com.lambdaschool.fitnessanywhere.models.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository
        extends CrudRepository<Session, Long>
{
}
