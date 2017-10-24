package rsound.core.tag;

import java.awt.Image;

public abstract class Picture
{

    public abstract String getName();

    public abstract String getDescription();

    public abstract Image getImage();

    public String toString()
    {

        return getName() + " - " + getDescription();

    }

}
