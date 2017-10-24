package rsound.core.io;

import java.io.IOException;
import rsound.core.io.AudioOutput;

public interface LineOut extends AudioOutput
{

    public long getAvailable();

    public long getBufferSize();

    public LineOut start();

    public LineOut stop();

}
