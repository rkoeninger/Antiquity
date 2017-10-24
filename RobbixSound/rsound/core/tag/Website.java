package rsound.core.tag;

import java.net.URL;

public abstract class Website
{

    public abstract String getName();

    public abstract URL getURL();

    public String toString()
    {

        return getName() + " - " + getURL().toExternalForm();

    }

}
