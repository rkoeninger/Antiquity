package rsound.core.io;

import java.io.Closeable;
import java.io.IOException;

public interface AudioInput extends Closeable
{

    public DecodedFormat getFormat();

    public boolean isOpen();

    public void close() throws IOException;

    public boolean canSeek();

    public long seek(long position) throws IOException;

    public long getPosition() throws IOException;

    public long getLength() throws IOException;

    public int read(SampleBuffer buffer) throws IOException;

}