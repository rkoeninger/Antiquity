package rsound.basic.codec;

import java.nio.ByteOrder;
import rsound.core.io.BitBuffer;
import rsound.core.io.DataInput;
import rsound.core.io.DecodedFormat;
import rsound.core.io.SampleBuffer;

class PCMUnsignedDecoderStream extends AbstractDecoderStream
{

    public PCMUnsignedDecoderStream(DecodedFormat format, DataInput input)
    {

        super(format, input);

    }

    public void toSamples(BitBuffer bits, SampleBuffer samples)
    {

    }

}