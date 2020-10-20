package com.lambdaschool.fitnessanywhere.controllers;

import com.lambdaschool.fitnessanywhere.models.Session;
import com.lambdaschool.fitnessanywhere.models.User;
import com.lambdaschool.fitnessanywhere.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
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

    @PostMapping(value = "/session", consumes = "application/json")
    public ResponseEntity<?> addNewSession(@Valid @RequestBody Session newSession) throws URISyntaxException
    {
        newSession.setSessionid(0);
        newSession = sessionService.save(newSession);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{sessionid}")
                .buildAndExpand(newSession.getSessionid())
                .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null,
                responseHeaders,
                HttpStatus.CREATED);
    }

    @PutMapping(value = "/session/{sessionid}",
            consumes = "application/json")
    public ResponseEntity<?> updateFullSession(
            @Valid
            @RequestBody
                    Session updateSession,
            @PathVariable
                    long sessionid)
    {
        updateSession.setSessionid(sessionid);
        sessionService.save(updateSession);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PatchMapping(value = "/session/{id}",
            consumes = "application/json")
    public ResponseEntity<?> updateSession(
            @Valid @RequestBody Session updateSession,
            @PathVariable long id)
    {
        sessionService.update(updateSession, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
