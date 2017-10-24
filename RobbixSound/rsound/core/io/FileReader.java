package rsound.core.io;

import java.io.IOException;

public interface FileReader
{

    public BitBuffer[] getMetadata() throws IOException;

    public DataInput getInput() throws IOException;

}