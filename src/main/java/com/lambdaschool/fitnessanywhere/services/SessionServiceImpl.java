package com.lambdaschool.fitnessanywhere.services;

import com.lambdaschool.fitnessanywhere.exceptions.ResourceNotFoundException;
import com.lambdaschool.fitnessanywhere.models.Attendees;
import com.lambdaschool.fitnessanywhere.models.Location;
import com.lambdaschool.fitnessanywhere.models.Session;
import com.lambdaschool.fitnessanywhere.models.User;
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

    @Autowired
    LocationRepository locrepos;

    @Override
    public Session findSessionById(long id)
    {
        return sessrepos.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Session id " + id + " not found")
        );
    }

    @Override
    public List<Session> findAll()
    {
        List<Session> rlist = new ArrayList<>();
        sessrepos.findAll().iterator().forEachRemaining(rlist::add);
        return rlist;
    }
    // post / put
    @Transactional
    @Override
    public Session save(Session session)
    {
        Session newSession = new Session();

        if (session.getSessionid() != 0)
        {
            sessrepos.findById(session.getSessionid())
                .orElseThrow(() -> new ResourceNotFoundException("Session id " + session.getSessionid() + " not found!"));
            newSession.setSessionid(session.getSessionid());
            newSession.getUsers().clear();
            for(Attendees ai : session.getUsers())
            {
                Attendees a = new Attendees(newSession,
                        ai.getUser(),
                        ai.isInstructor());
                newSession.getUsers().add(a);
            }
        }
        if (session.getSessionid() == 0 && session.getUsers().size() > 0)
        {
            throw new ResourceNotFoundException("Users are not added through " +
                    "Sessions");
        }
        newSession.setName(session.getName());
        newSession.setType(session.getType());
        newSession.setTime(session.getTime());
        newSession.setDuration(session.getDuration());
        newSession.setIntensity(session.getIntensity());
        newSession.setMaxsize(session.getMaxsize());
        newSession.setLocations(session.getLocations());
        return sessrepos.save(newSession);
    }

    @Transactional
    @Override
    public Session save(Session session, User user, boolean instructor)
    {
        return null;
    }

    // patch
    @Transactional
    @Override
    public Session update(Session session, Long id)
    {
        Session currentSession = findSessionById(id);
        if (session.getUsers().size() > 0)
        {
            currentSession.getUsers().clear();
            for(Attendees ai : session.getUsers())
            {
                Attendees a = new Attendees(currentSession,
                        ai.getUser(),
                        ai.isInstructor());
                currentSession.getUsers().add(a);
            }
        }
        if (session.getName() != null)
        {
            currentSession.setName(session.getName());
        }
        if (session.getType() != null)
        {
            currentSession.setType(session.getType());
        }
        if (session.getTime() != null)
        {
            currentSession.setTime(session.getTime());
        }
        if (session.getDuration() != null)
        {
            currentSession.setDuration(session.getDuration());
        }
        if (session.getIntensity() != null)
        {
            currentSession.setIntensity(session.getIntensity());
        }
        if (session.getMaxsize() != null)
        {
            currentSession.setMaxsize(session.getMaxsize());
        }
        if (session.getLocations() != null)
        {
            currentSession.setLocations(session.getLocations());
        }
        return sessrepos.save(currentSession);
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        sessrepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Session" +
                " id " + id + " Not Found"));
        sessrepos.deleteById(id);
    }

}
