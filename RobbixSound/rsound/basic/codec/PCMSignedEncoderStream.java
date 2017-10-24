package rsound.basic.codec;

import java.nio.ByteOrder;
import rsound.core.Utilities;
import rsound.core.io.BitBuffer;
import rsound.core.io.DataOutput;
import rsound.core.io.DecodedFormat;
import rsound.core.io.SampleBuffer;

class PCMSignedEncoderStream extends AbstractEncoderStream
{

    private final int sampleSize;

    public PCMSignedEncoderStream(DecodedFormat format, DataOutput output)
    {

        super(format, output);
        this.sampleSize = getEncodedFormat().getFrameSize() /
        getEncodedFormat().getChannelCount();

    }

    protected void toBits(SampleBuffer samples, BitBuffer bits)
    {

        bits.setDoSignExtend(true);
        final long max = Utilities.getMax(sampleSize);

        while (samples.hasRemaining() & (bits.remaining() >= sampleSize))
        {

            final long integer = Utilities.normalize(
            (long) (samples.get() * max), sampleSize);

            if (sampleSize % 8 == 0)
                bits.putWordBytes(integer, sampleSize);
            else
                bits.putWordBits(integer, sampleSize);

        }

    }

}