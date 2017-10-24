package rsound.basic.codec;

import java.nio.ByteOrder;
import rsound.core.io.BitBuffer;
import rsound.core.io.DataOutput;
import rsound.core.io.DecodedFormat;
import rsound.core.io.SampleBuffer;

class PCMUnsignedEncoderStream extends AbstractEncoderStream
{

    public PCMUnsignedEncoderStream(DecodedFormat format, DataOutput input)
    {

        super(format, input);

    }

    public void toBits(SampleBuffer samples, BitBuffer bits)
    {

    }

}