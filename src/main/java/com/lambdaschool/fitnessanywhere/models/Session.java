package com.lambdaschool.fitnessanywhere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "sessions")
public class Session
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sessionid;

    private String name;

    private String type;

    private String time;

    private String duration;

    private String intensity;

    private long maxsize;
    // this will need to be another model
    // many to one

    @ManyToOne
    @JoinColumn(name = "locationid") // , nullable = false)
    @JsonIgnoreProperties(value = "sessions")
    private Location locations;


    // this will need to link to users?
    // many-to-manY??
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = "sessions")
    private User instructor;

    @OneToMany(mappedBy = "sessions", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "sessions", allowSetters = true)
    private Set<Attendees> users = new HashSet<>();

    public User getInstructor()
    {
        return instructor;
    }

    public void setInstructor(User instructor)
    {
        this.instructor = instructor;
    }

    public Set<Attendees> getUsers()
    {
        return users;
    }

    public Session()
    {
        // required by jpa
    }

    public Session(String name, String type, String time, String duration,
                   String intensity, long maxsize)
    {
        this.name = name;
        this.type = type;
        this.time = time;
        this.duration = duration;
        this.intensity = intensity;
        this.maxsize = maxsize;
    }

    public long getSessionid()
    {
        return sessionid;
    }

    public void setSessionid(long sessionid)
    {
        this.sessionid = sessionid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getDuration()
    {
        return duration;
    }

    public void setDuration(String duration)
    {
        this.duration = duration;
    }

    public String getIntensity()
    {
        return intensity;
    }

    public void setIntensity(String intensity)
    {
        this.intensity = intensity;
    }

    public long getMaxsize()
    {
        return maxsize;
    }

    public void setMaxsize(long maxsize)
    {
        this.maxsize = maxsize;
    }

    public Location getLocation()
    {
        return locations;
    }

    public void setLocation(Location locations)
    {
        this.locations = locations;
    }

//    public List<Attendees> getAttendees()
//    {
//        return attendees;
//    }
//
//    public void setAttendees(List<Attendees> attendees)
//    {
//        this.attendees = attendees;
//    }
}
