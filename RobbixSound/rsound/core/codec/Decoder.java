package rsound.core.codec;

import java.io.IOException;
import rsound.core.io.AudioInput;
import rsound.core.io.DataInput;
import rsound.core.io.EncodedFormat;

public interface Decoder
{

    public boolean isFormatSupported(EncodedFormat format);

    public AudioInput decode(DataInput input) throws IOException;

}