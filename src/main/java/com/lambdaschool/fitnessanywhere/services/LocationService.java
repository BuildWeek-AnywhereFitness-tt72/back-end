package com.lambdaschool.fitnessanywhere.services;


import com.lambdaschool.fitnessanywhere.models.Location;

import java.util.List;

public interface LocationService
{
    List<Location> findall();

    Location findLocationById(long id);

}
