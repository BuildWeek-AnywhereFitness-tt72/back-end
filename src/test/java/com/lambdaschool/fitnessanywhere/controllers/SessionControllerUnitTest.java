package com.lambdaschool.fitnessanywhere.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.fitnessanywhere.FitnessAnywhereApplication;
import com.lambdaschool.fitnessanywhere.models.Location;
import com.lambdaschool.fitnessanywhere.models.Session;
import com.lambdaschool.fitnessanywhere.services.SessionService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = FitnessAnywhereApplication.class)
@AutoConfigureMockMvc
@WithMockUser(username = "admin",
    roles = {"USER", "ADMIN"})
public class SessionControllerUnitTest
{

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    private List<Session> sessionList;

    @Before
    public void setUp() throws
        Exception
    {
        sessionList = new ArrayList<>();
        LocalDateTime date = LocalDateTime.now();
        Location l = new Location("Sesame Street", "New York", "New York", "000000");

        Session s1 = new Session("Test Name", "test yoga", date, "30 minutes",
            "intense", (long) 15);
        // making time null, because Jackson autoconverts it
        // ...I think
        s1.setTime(null);
        s1.setLocations(l);
        s1.setSessionid(10);
        sessionList.add(s1);


        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
    }


    @Test
    public void getSessionById() throws
        Exception
    {
        String apiUrl = "/sessions/session/10";

        Mockito.when(sessionService.findSessionById(10))
            .thenReturn(sessionList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn(); // this could throw an exception
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(sessionList.get(0));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List",
            er,
            tr);
    }

    @Test
    public void getSessionByIdNotFound() throws
        Exception
    {
        String apiUrl = "/sessions/session/77";

        Mockito.when(sessionService.findSessionById(77))
            .thenReturn(null);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn(); // this could throw an exception
        String tr = r.getResponse()
            .getContentAsString();

        String er = "";

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List",
            er,
            tr);
    }

    @Test
    public void listAllSessions() throws
        Exception
    {
        String apiUrl = "/sessions/sessions";

        Mockito.when(sessionService.findAll())
            .thenReturn(sessionList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);

        // the following actually performs a real controller call
        MvcResult r = mockMvc.perform(rb)
            .andReturn(); // this could throw an exception
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(sessionList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List",
            er,
            tr);
    }

}