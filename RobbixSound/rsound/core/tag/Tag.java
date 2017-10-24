package rsound.core.tag;

import java.util.HashSet;
import java.util.Set;

public abstract class Tag
{

    public String getTitle()
    {

        return null;

    }

    public String getArtist()
    {

        return null;

    }

    public String getAlbum()
    {

        return null;

    }

    public Genre getGenre()
    {

        return null;

    }

    public Mood getMood()
    {

        return null;

    }

    public Set<Comment> getComments()
    {

        return new HashSet<Comment>();

    }

    public Set<Lyrics> getLyrics()
    {

        return new HashSet<Lyrics>();

    }

    public Set<Picture> getPictures()
    {

        return new HashSet<Picture>();

    }

    public Set<Website> getWebsite()
    {

        return new HashSet<Website>();

    }

}