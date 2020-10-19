package com.lambdaschool.fitnessanywhere.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AttendeesId implements Serializable
{
    private long user;

    private long sessions;

    public AttendeesId()
    {
    }

    public long getUser()
    {
        return user;
    }

    public void setUser(long user)
    {
        this.user = user;
    }

    public long getSession()
    {
        return sessions;
    }

    public void setSession(long sessions)
    {
        this.sessions = sessions;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        // boolean temp = (o.getClass() instanceof Class);
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        AttendeesId that = (AttendeesId) o;
        return user == that.user &&
                sessions == that.sessions;
    }

    @Override
    public int hashCode()
    {
        return 16798;
    }
}
