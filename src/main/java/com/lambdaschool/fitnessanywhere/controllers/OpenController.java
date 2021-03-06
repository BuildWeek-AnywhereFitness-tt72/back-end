package com.lambdaschool.fitnessanywhere.controllers;

import com.lambdaschool.fitnessanywhere.exceptions.ResourceNotFoundException;
import com.lambdaschool.fitnessanywhere.models.User;
import com.lambdaschool.fitnessanywhere.models.UserMinimum;
import com.lambdaschool.fitnessanywhere.models.UserRoles;
import com.lambdaschool.fitnessanywhere.repository.UserRepository;
import com.lambdaschool.fitnessanywhere.services.RoleService;
import com.lambdaschool.fitnessanywhere.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * The class allows access to endpoints that are open to all users regardless of authentication status.
 * Its most important function is to allow a person to create their own username
 */
@RestController
public class OpenController
{
    @Autowired
    UserRepository userrepos;
    /**
     * A method in this controller adds a new user to the application so needs access to User Services to do this.
     */
    @Autowired
    private UserService userService;

    /**
     * A method in this controller adds a new user to the application with the role User so needs access to Role Services to do this.
     */
    @Autowired
    private RoleService roleService;

    /**
     * This endpoint always anyone to create an account with the default role of USER. That role is hardcoded in this method.
     *
     * @param httpServletRequest the request that comes in for creating the new user
     * @param newminuser         A special minimum set of data that is needed to create a new user
     * @return The token access and other relevent data to token access. Status of CREATED. The location header to look up the new user.
     * @throws URISyntaxException we create some URIs during this method. If anything goes wrong with that creation, an exception is thrown.
     */
    @PostMapping(value = "/createnewuser",
        consumes = {"application/json"},
        produces = {"application/json"})
    public ResponseEntity<?> addSelf(
        HttpServletRequest httpServletRequest,
        @Valid
        @RequestBody
            UserMinimum newminuser)
        throws
        URISyntaxException
    {
        System.out.println("attempting to add user: " + newminuser);
        // Create the user
        User newuser = new User();

        if (StreamSupport.stream(userrepos.findAll().spliterator(), false).filter(u -> u.getUsername().equals(newminuser.getUsername().toLowerCase())).collect(Collectors.toList()).size() > 0)
        {
            System.out.println("I EXPLODED BUT NOBODY CARES!");
            throw new ResourceNotFoundException("Username " + newminuser.getUsername() +
                " " +
                "already exists!");
        }
        newuser.setUsername(newminuser.getUsername());
        newuser.setPassword(newminuser.getPassword());
//        newuser.setPrimaryemail(newminuser.getPrimaryemail());

        // add the default role of user
        Set<UserRoles> newRoles = new HashSet<>();
        newRoles.add(new UserRoles(newuser,
            roleService.findByName("user")));
        String role = newminuser.getRole();
        System.out.println(role);
        newRoles.add(new UserRoles(newuser, roleService.findByName(role)));
        newuser.setRoles(newRoles);

        newuser = userService.save(newuser);

        // set the location header for the newly created resource
        // The location comes from a different controller!
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromUriString(httpServletRequest.getServerName() + ":" + httpServletRequest.getLocalPort() + "/users/user/{userId}")
            .buildAndExpand(newuser.getUserid())
            .toUri();
        responseHeaders.setLocation(newUserURI);

        // return the access token
        // To get the access token, surf to the endpoint /login (which is always on the server where this is running)
        // just as if a client had done this.
        RestTemplate restTemplate = new RestTemplate();
        String requestURI = "http://localhost" + ":" + httpServletRequest.getLocalPort() + "/login";

        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(acceptableMediaTypes);
        headers.setBasicAuth(System.getenv("OAUTHCLIENTID"),
            System.getenv("OAUTHCLIENTSECRET"));

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type",
            "password");
        map.add("scope",
            "read write trust");
        map.add("username",
            newminuser.getUsername());
        map.add("password",
            newminuser.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map,
            headers);

        String theToken = restTemplate.postForObject(requestURI,
            request,
            String.class);

        return new ResponseEntity<>(theToken,
            responseHeaders,
            HttpStatus.CREATED);
    }


    /**
     * Prevents no favicon.ico warning from appearing in the logs. @ApiIgnore tells Swagger to ignore documenting this as an endpoint.
     */
    @ApiIgnore
    @GetMapping("favicon.ico")
    public void returnNoFavicon()
    {

    }
}
