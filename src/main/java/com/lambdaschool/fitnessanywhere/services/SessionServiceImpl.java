package com.lambdaschool.fitnessanywhere.services;

import com.lambdaschool.fitnessanywhere.exceptions.ResourceNotFoundException;
import com.lambdaschool.fitnessanywhere.models.Session;
import com.lambdaschool.fitnessanywhere.repository.LocationRepository;
import com.lambdaschool.fitnessanywhere.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "sessionService")
public class SessionServiceImpl implements SessionService
{
    @Autowired
    SessionRepository sessrepos;


    @Override
    public List<Session> findAll()
    {
        List<Session> rlist = new ArrayList<>();
        sessrepos.findAll().iterator().forEachRemaining(rlist::add);
        return rlist;
    }

    @Override
    public Session findSessionById(long id)
    {
        return sessrepos.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Session id " + id + " not found")
        );
    }
}
