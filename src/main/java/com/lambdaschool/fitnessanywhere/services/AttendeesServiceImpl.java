//package com.lambdaschool.fitnessanywhere.services;
//
//import com.lambdaschool.fitnessanywhere.models.Attendees;
//import com.lambdaschool.fitnessanywhere.models.User;
//import com.lambdaschool.fitnessanywhere.repository.AttendeesRepository;
//import com.lambdaschool.fitnessanywhere.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//@Transactional
//@Service(value = "attendeesService")
//public class AttendeesServiceImpl implements AttendeesService
//{
//    @Autowired
//    AttendeesRepository attrepos;
//
//    @Autowired
//    UserRepository userrepos;
//
//    @Override
//    public List<Attendees> findall()
//    {
//        List<Attendees> rlist = new ArrayList<>();
//        attrepos.findAll().iterator().forEachRemaining(rlist::add);
//        return rlist;
//    }
//    //will this one work?
//    @Override
//    public List<User> findAttendees(long sessionid)
//    {
//        List<User> rlist = new ArrayList<>();
//        return rlist;
//    }
//}
