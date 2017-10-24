package rsound.core.io;

public class FileFormat
{

    public Type getType()
    {
    }

    public static interface Type
    {

        public String getName();

        public String[] getExtensions();

    }

}