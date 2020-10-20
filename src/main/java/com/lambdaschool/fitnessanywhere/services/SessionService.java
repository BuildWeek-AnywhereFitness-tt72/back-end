package com.lambdaschool.fitnessanywhere.services;

import com.lambdaschool.fitnessanywhere.models.Attendees;
import com.lambdaschool.fitnessanywhere.models.Session;
import com.lambdaschool.fitnessanywhere.models.User;

import java.util.List;

public interface SessionService
{
    List<Session> findAll();

    Session findSessionById(long id);

    Session save(Session session);

    // add user to Attendeees
    Session save(Session session, User user, boolean instructor);

    Session update(Session session, Long id);

    void delete(long id);


}
