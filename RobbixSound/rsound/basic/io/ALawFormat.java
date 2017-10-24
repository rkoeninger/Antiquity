package rsound.basic.io;

import java.nio.ByteOrder;
import rsound.core.io.EncodedFormat;

public class ALawFormat extends EncodedFormat
{

    public static ALawFormat getFormat()
    {

        return new ALawFormat();

    }

    protected ALawFormat()
    {

        super(new ALawEncoding(), 8000.0, 8, 1, ByteOrder.BIG_ENDIAN);

    }

    public String toString()
    {

        return getEncoding().toString();

    }

    public static class ALawEncoding extends EncodedFormat.Encoding
    {

        public ALawEncoding()
        {

            super("A-Law");

        }

        public boolean matches(Encoding encoding)
        {

            final String name = encoding.getName();
            return ((name.contains("a") | name.contains("A")) &
            (name.contains("law") | name.contains("Law") |
            name.contains("LAW"))) | (encoding instanceof ALawEncoding);

        }

    }

}