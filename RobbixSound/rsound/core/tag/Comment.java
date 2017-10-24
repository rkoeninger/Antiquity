package rsound.core.tag;

import java.util.Locale;

public abstract class Comment
{

    public abstract Locale getLocale();

    public abstract String getText();

    public String toString()
    {

        return getText();

    }

}