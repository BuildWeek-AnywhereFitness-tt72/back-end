package com.lambdaschool.fitnessanywhere.services;

import com.lambdaschool.fitnessanywhere.exceptions.ResourceNotFoundException;
import com.lambdaschool.fitnessanywhere.models.Location;
import com.lambdaschool.fitnessanywhere.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "locationService")
public class LocationServiceImpl implements LocationService
{

    @Autowired
    LocationRepository locrepos;

    @Override
    public List<Location> findall()
    {
        List<Location> rlist = new ArrayList<>();
        locrepos.findAll().iterator().forEachRemaining(rlist::add);
        return rlist;
    }

    @Override
    public Location findLocationById(long id)
    {
        return locrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location id " + id +
                        "not found"));
    }
}
