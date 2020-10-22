package com.lambdaschool.fitnessanywhere.controllers;

import com.lambdaschool.fitnessanywhere.exceptions.ResourceNotFoundException;
import com.lambdaschool.fitnessanywhere.models.Session;
import com.lambdaschool.fitnessanywhere.models.User;
import com.lambdaschool.fitnessanywhere.services.SessionService;
import com.lambdaschool.fitnessanywhere.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sessions")
public class SessionController
{
    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

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
    public ResponseEntity<?> addNewSession(Authentication authentication,
                                           @Valid @RequestBody Session newSession) throws URISyntaxException
    {
        User u = userService.findByName(authentication.getName());
        System.out.println("Sessions endpoint hit");
        newSession.setSessionid(0);
        // convert date string to Date object
        //date = formatter.parse(newSession.getTime().toString());

        newSession = sessionService.save(newSession, u);

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
    public ResponseEntity<?> updateFullSession(Authentication authentication,
            @Valid
            @RequestBody
                    Session updateSession,
            @PathVariable
                    long sessionid)
    {
        User u = userService.findByName(authentication.getName());
        // I need to check if user is an instructor for this session.
        // so that would be
        updateSession.setSessionid(sessionid);
        if (u.getSessions().stream().filter(a -> a.isInstructor() == true && a.getUser().getUserid() == u.getUserid()).collect(Collectors.toList()).size() == 0)
        {
            throw new ResourceNotFoundException("User " + u.getUsername() + " is not an" +
                " instructor of Session " + sessionid);
        }
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

    @DeleteMapping(value = "/session/{id}")
    public ResponseEntity<?> deleteSessionById(
            @PathVariable
                    long id)
    {
        sessionService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
