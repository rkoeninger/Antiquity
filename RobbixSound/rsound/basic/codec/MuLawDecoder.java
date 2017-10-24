package rsound.basic.codec;

import java.io.IOException;
import rsound.core.codec.Decoder;
import rsound.core.io.AudioInput;
import rsound.core.io.DataInput;
import rsound.core.io.DecodedFormat;
import rsound.core.io.EncodedFormat;
import rsound.basic.io.MuLawFormat;

public class MuLawDecoder implements Decoder
{

    public boolean isFormatSupported(EncodedFormat format)
    {

        return new MuLawFormat.MuLawEncoding().matches(format.getEncoding());

    }

    public AudioInput decode(DataInput input) throws IOException
    {

        final EncodedFormat format = input.getFormat();

        if (! isFormatSupported(format) | ! format.isFullySpecified())
            throw new IllegalArgumentException();

        return new MuLawDecoderStream(new DecodedFormat(
        (int) format.getFrameRate(), format.getChannelCount()), input);

    }

}