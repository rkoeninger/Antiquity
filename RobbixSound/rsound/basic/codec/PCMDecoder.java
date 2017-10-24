package rsound.basic.codec;

import java.io.IOException;
import rsound.core.codec.Decoder;
import rsound.core.io.AudioInput;
import rsound.core.io.DataInput;
import rsound.core.io.DecodedFormat;
import rsound.core.io.EncodedFormat;
import rsound.basic.io.PCMFormat;

public class PCMDecoder implements Decoder
{

    public boolean isFormatSupported(EncodedFormat format)
    {

        return new PCMFormat.PCMEncoding().matches(format.getEncoding());

    }

    public AudioInput decode(DataInput input) throws IOException
    {

        final EncodedFormat format = input.getFormat();

        if (! isFormatSupported(format) | ! format.isFullySpecified())
            throw new IllegalArgumentException();

        final DecodedFormat decodedFormat = new DecodedFormat(
        (int) format.getFrameRate(), format.getChannelCount());
        final Boolean signed = format.getBooleanProperty("signed");
        final Boolean fp = format.getBooleanProperty("float");

        if (fp.booleanValue())
            return new PCMFloatDecoderStream(decodedFormat, input);
        else if (signed.booleanValue())
            return new PCMSignedDecoderStream(decodedFormat, input);
        else if (! signed.booleanValue())
            return new PCMUnsignedDecoderStream(decodedFormat, input);

        throw new IllegalArgumentException();

    }

}