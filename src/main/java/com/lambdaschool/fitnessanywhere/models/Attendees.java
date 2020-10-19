package com.lambdaschool.fitnessanywhere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

//should be called "sessionusers"
@Entity
@Table(name = "attendees")
@IdClass(AttendeesId.class)
public class Attendees implements Serializable
{
    @Id
    @ManyToOne
    @JoinColumn(name = "sessionid")
    @JsonIgnoreProperties({"user"})
    private Session sessions;

    @Id
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = "sessions")
    private User user;

    private boolean instructor;

    public void setSessions(Session sessions)
    {
        this.sessions = sessions;
    }

//    public Attendees(Session sessions, User user)
//    {
//        this.sessions = sessions;
//        this.user = user;
//        this.instructor = "random stuff";
//    }
    public Attendees(Session sessions, User user, boolean instructor)
    {
        this.sessions = sessions;
        this.user = user;
        this.instructor = instructor;
    }

    public Attendees()
    {
    }

    public Session getSessions()
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

    public boolean isInstructor()
    {
        return instructor;
    }

    public void setInstructor(boolean instructor)
    {
        this.instructor = instructor;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}

