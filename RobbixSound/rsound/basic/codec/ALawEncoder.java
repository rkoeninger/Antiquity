package rsound.basic.codec;

import java.io.IOException;
import rsound.core.codec.Encoder;
import rsound.core.io.AudioOutput;
import rsound.core.io.DataOutput;
import rsound.core.io.DecodedFormat;
import rsound.core.io.EncodedFormat;
import rsound.basic.io.ALawFormat;

public class ALawEncoder implements Encoder
{

    public boolean isFormatSupported(EncodedFormat format)
    {

        return new ALawFormat.ALawEncoding().matches(format.getEncoding());

    }

    public AudioOutput encode(DataOutput output) throws IOException
    {

        final EncodedFormat format = output.getFormat();

        if (! isFormatSupported(format) | ! format.isFullySpecified())
            throw new IllegalArgumentException();

        return new ALawEncoderStream(new DecodedFormat(
        (int) format.getFrameRate(), format.getChannelCount()), output);

    }

}