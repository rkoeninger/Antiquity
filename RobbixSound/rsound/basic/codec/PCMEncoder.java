package rsound.basic.codec;

import java.io.IOException;
import rsound.core.codec.Encoder;
import rsound.core.io.AudioOutput;
import rsound.core.io.DataOutput;
import rsound.core.io.DecodedFormat;
import rsound.core.io.EncodedFormat;
import rsound.basic.io.PCMFormat;

public class PCMEncoder implements Encoder
{

    public boolean isFormatSupported(EncodedFormat format)
    {

        return new PCMFormat.PCMEncoding().matches(format.getEncoding());

    }

    public AudioOutput encode(DataOutput output) throws IOException
    {

        final EncodedFormat format = output.getFormat();

        if (! isFormatSupported(format) | ! format.isFullySpecified())
            throw new IllegalArgumentException();

        final DecodedFormat decodedFormat = new DecodedFormat(
        (int) format.getFrameRate(), format.getChannelCount());
        final Boolean signed = format.getBooleanProperty("signed");
        final Boolean fp = format.getBooleanProperty("float");

        if (fp.booleanValue())
            return new PCMFloatEncoderStream(decodedFormat, output);
        else if (signed.booleanValue())
            return new PCMSignedEncoderStream(decodedFormat, output);
        else if (! signed.booleanValue())
            return new PCMUnsignedEncoderStream(decodedFormat, output);

        throw new IllegalArgumentException();

    }

}