package com.lambdaschool.fitnessanywhere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "attendees")
public class Attendees implements Serializable
{
    @Id
    @ManyToOne
    @JoinColumn(name = "sessionid")
    @JsonIgnoreProperties(value = "userid")
    private Session sessions;

    @Id
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = "sessions")
    private User user;

    private boolean instructor;

    public Attendees()
    {
    }

    public Session getSession()
    {
        return sessions;
    }

    public void setSession(Session sessions)
    {
        this.sessions = sessions;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public boolean isInstructor()
    {
        return instructor;
    }

    public void setInstructor(boolean instructor)
    {
        this.instructor = instructor;
    }
}
