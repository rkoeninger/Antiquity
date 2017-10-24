package rsound.core.codec;

import java.io.IOException;
import rsound.core.io.AudioOutput;
import rsound.core.io.DataOutput;
import rsound.core.io.EncodedFormat;

public interface Encoder
{

    public boolean isFormatSupported(EncodedFormat format);

    public AudioOutput encode(DataOutput output) throws IOException;

}