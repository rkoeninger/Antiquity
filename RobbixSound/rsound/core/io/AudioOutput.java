package rsound.core.io;

import java.io.Closeable;
import java.io.IOException;

public interface AudioOutput extends Closeable
{

    public DecodedFormat getFormat();

    public boolean isOpen();

    public void close() throws IOException;

    public long getAvailable() throws IOException;

    public long getLength() throws IOException;

    public int write(SampleBuffer buffer) throws IOException;

}