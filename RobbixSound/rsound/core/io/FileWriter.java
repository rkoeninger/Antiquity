package rsound.core.io;

import java.io.IOException;

public interface FileWriter
{

    public FileWriter setMetadata(BitBuffer[] metadata) throws IOException;

    public DataOutput getOutput() throws IOException;

}