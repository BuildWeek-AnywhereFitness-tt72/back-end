package com.lambdaschool.fitnessanywhere.services;

import com.lambdaschool.fitnessanywhere.FitnessAnywhereApplication;
import com.lambdaschool.fitnessanywhere.exceptions.ResourceNotFoundException;
import com.lambdaschool.fitnessanywhere.models.Location;
import com.lambdaschool.fitnessanywhere.models.Session;
import com.lambdaschool.fitnessanywhere.repository.SessionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FitnessAnywhereApplication.class)
public class SessionServiceImplTest
{
    @Autowired
    private SessionService sessionService;

    @MockBean
    private SessionRepository sessrepos;

    private List<Session> sessionList;

    @Before
    public void setUp() throws
        Exception
    {
//        sessionList = new ArrayList<>();
//        Date date = new GregorianCalendar(2020, 11, 11).getTime();
//        Location l = new Location("Sesame Street", "New York", "New York", "000000");
//
//        Session s1 = new Session("Test Name", "test yoga", date, "30 minutes",
//            "intense", (long) 15);
//        s1.setLocations(l);
//        sessionList.add(s1);
    }

    @Test
    public void findSessionById()
    {
        Mockito.when(sessrepos.findById(1L))
            .thenReturn(Optional.of(sessionList.get(0)));

        assertEquals("Test Name",
            sessionService.findSessionById(1L)
                .getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findSessionByIdNotFound()
    {
        Mockito.when(sessrepos.findById(10L))
            .thenReturn(Optional.empty());

        assertEquals("Test Name",
            sessionService.findSessionById(10L)
                .getName());
    }

    @Test
    public void findAll()
    {
        Mockito.when(sessrepos.findAll())
            .thenReturn(sessionList);

        assertEquals(1,
            sessionService.findAll()
                .size());
    }

    @Test
    public void delete()
    {
        Mockito.when(sessrepos.findById(1L))
            .thenReturn(Optional.of(sessionList.get(0)));

        Mockito.doNothing()
            .when(sessrepos)
            .deleteById(1L);

        sessionService.delete(1L);
        assertEquals(1,
            sessionList.size());
    }
}