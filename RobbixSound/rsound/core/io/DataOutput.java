package rsound.core.io;

import java.io.Closeable;
import java.io.IOException;

public interface DataOutput extends Closeable
{

    public EncodedFormat getFormat();

    public boolean isOpen();

    public void close() throws IOException;

    public long getAvailable() throws IOException;

    public long getLength() throws IOException;

    public int write(BitBuffer buffer) throws IOException;

}