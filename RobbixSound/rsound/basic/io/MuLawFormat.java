package rsound.basic.io;

import java.nio.ByteOrder;
import rsound.core.io.EncodedFormat;

public class MuLawFormat extends EncodedFormat
{

    public static MuLawFormat getFormat()
    {

        return new MuLawFormat();

    }

    protected MuLawFormat()
    {

        super(new MuLawEncoding(), 8000.0, 8, 1, ByteOrder.BIG_ENDIAN);

    }

    public String toString()
    {

        return getEncoding().toString();

    }

    public static class MuLawEncoding extends EncodedFormat.Encoding
    {

        public MuLawEncoding()
        {

            super("Mu-Law");

        }

        public boolean matches(Encoding encoding)
        {

            final String name = encoding.getName();
            return ((name.contains("mu") | name.contains("u") |
            name.contains("Mu") | name.contains("MU") |
            name.contains("U")) &
            (name.contains("law") | name.contains("Law") |
            name.contains("LAW"))) | (encoding instanceof MuLawEncoding);

        }

    }

}