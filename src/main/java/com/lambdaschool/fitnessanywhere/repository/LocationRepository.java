package com.lambdaschool.fitnessanywhere.repository;

import com.lambdaschool.fitnessanywhere.models.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository
        extends CrudRepository<Location, Long>
{
}
