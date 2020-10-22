package com.lambdaschool.fitnessanywhere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    private LocalDateTime time;

    private String duration;

    private String intensity;

    private Long maxsize;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "locationid") // , nullable = false)
    @JsonIgnoreProperties(value = "sessions")
    private Location locations;


    @OneToMany(mappedBy = "sessions", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "sessions", allowSetters = true)
    private Set<Attendees> users = new HashSet<>();

    public void setUsers(Set<Attendees> users)
    {
        this.users = users;
    }

    public Set<Attendees> getUsers()
    {
        return users;
    }

    public Session()
    {
        // required by jpa
    }

    public Session(String name, String type, LocalDateTime time, String duration,
                   String intensity, Long maxsize)
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

    public LocalDateTime getTime()
    {
        return time;
    }

    public void setTime(LocalDateTime time)
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

    public Long getMaxsize()
    {
        return maxsize;
    }

    public void setMaxsize(Long maxsize)
    {
        this.maxsize = maxsize;
    }

    public Location getLocations()
    {
        return locations;
    }

    public void setLocations(Location locations)
    {
        this.locations = locations;
    }
}
