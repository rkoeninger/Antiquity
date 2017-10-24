package rsound.basic.codec;

import java.nio.ByteOrder;
import rsound.core.io.BitBuffer;
import rsound.core.io.DataOutput;
import rsound.core.io.DecodedFormat;
import rsound.core.io.SampleBuffer;

class PCMFloatEncoderStream extends AbstractEncoderStream
{

    private final int sampleSize;

    public PCMFloatEncoderStream(DecodedFormat format, DataOutput output)
    {

        super(format, output);
        this.sampleSize = getEncodedFormat().getFrameSize() /
        getEncodedFormat().getChannelCount();

    }

    public void toBits(SampleBuffer samples, BitBuffer bits)
    {

        while (samples.hasRemaining() & (bits.remaining() >= sampleSize))
        {

            switch (sampleSize)
            {

                case 32 :
                    bits.putFloat(samples.get());
                    break;

                case 64 :
                    bits.putDouble(samples.get());
                    break;

                default :
                    throw new Error("PCM Float sample size illegal");

            }

        }

    }

}