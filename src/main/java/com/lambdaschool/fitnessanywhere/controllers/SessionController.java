package com.lambdaschool.fitnessanywhere.controllers;

import com.lambdaschool.fitnessanywhere.models.Session;
import com.lambdaschool.fitnessanywhere.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sessions")
public class SessionController
{
    @Autowired
    private SessionService sessionService;

    @GetMapping(value = "/sessions",
            produces = "application/json")
    public ResponseEntity<?> listAllSessions()
    {
        List<Session> rlist = sessionService.findAll();
        return new ResponseEntity<>(rlist, HttpStatus.OK);
    }

    @GetMapping(value = "/session/{id}",
            produces = "application/json")
    public ResponseEntity<?> findSessionById(@PathVariable long id)
    {
        Session s = sessionService.findSessionById(id);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }
}
