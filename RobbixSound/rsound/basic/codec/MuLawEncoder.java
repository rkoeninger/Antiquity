package rsound.basic.codec;

import java.io.IOException;
import rsound.core.codec.Encoder;
import rsound.core.io.AudioOutput;
import rsound.core.io.DataOutput;
import rsound.core.io.DecodedFormat;
import rsound.core.io.EncodedFormat;
import rsound.basic.io.MuLawFormat;

public class MuLawEncoder implements Encoder
{

    public boolean isFormatSupported(EncodedFormat format)
    {

        return new MuLawFormat.MuLawEncoding().matches(format.getEncoding());

    }

    public AudioOutput encode(DataOutput output) throws IOException
    {

        final EncodedFormat format = output.getFormat();

        if (! isFormatSupported(format) | ! format.isFullySpecified())
            throw new IllegalArgumentException();

        return new MuLawEncoderStream(new DecodedFormat(
        (int) format.getFrameRate(), format.getChannelCount()), output);

    }

}