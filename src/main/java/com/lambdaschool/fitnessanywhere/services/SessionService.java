package com.lambdaschool.fitnessanywhere.services;

import com.lambdaschool.fitnessanywhere.models.Session;

import java.util.List;

public interface SessionService
{
    List<Session> findAll();

    Session findSessionById(long id);


}
