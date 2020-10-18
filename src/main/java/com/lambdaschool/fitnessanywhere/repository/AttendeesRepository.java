package com.lambdaschool.fitnessanywhere.repository;

import com.lambdaschool.fitnessanywhere.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface AttendeesRepository
        extends CrudRepository<Role, Long>
{
}
