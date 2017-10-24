package rsound.core.io;

import java.io.IOException;
import rsound.core.io.AudioInput;

public interface LineIn extends AudioInput
{

    public long getAvailable();

    public long getBufferSize();

    public LineIn start();

    public LineIn stop();

}
