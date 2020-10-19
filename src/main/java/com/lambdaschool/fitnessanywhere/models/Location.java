package com.lambdaschool.fitnessanywhere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "locations")
public class Location
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long locationid;

    private String address;

    private String city;


    private String state;

    private String zip;

    @OneToMany(mappedBy = "locations", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "locations", allowSetters = true)
    private List<Session> sessions = new ArrayList<>();

    public Location(String address, String city, String state, String zip)
    {
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public Location()
    {
    }

    public long getLocationid()
    {
        return locationid;
    }

    public void setLocationid(long locationid)
    {
        this.locationid = locationid;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }
    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getZip()
    {
        return zip;
    }

    public void setZip(String zip)
    {
        this.zip = zip;
    }

    public List<Session> getSessions()
    {
        return sessions;
    }

    public void setSessions(List<Session> sessions)
    {
        this.sessions = sessions;
    }
}
