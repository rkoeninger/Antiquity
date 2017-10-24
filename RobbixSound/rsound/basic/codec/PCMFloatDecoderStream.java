package rsound.basic.codec;

import java.nio.ByteOrder;
import rsound.core.io.BitBuffer;
import rsound.core.io.DataInput;
import rsound.core.io.DecodedFormat;
import rsound.core.io.SampleBuffer;

class PCMFloatDecoderStream extends AbstractDecoderStream
{

    private final int sampleSize;

    public PCMFloatDecoderStream(DecodedFormat format, DataInput input)
    {

        super(format, input);
        this.sampleSize = getEncodedFormat().getFrameSize() /
        getEncodedFormat().getChannelCount();

    }

    public void toSamples(BitBuffer bits, SampleBuffer samples)
    {

        while (samples.hasRemaining() & (bits.remaining() >= sampleSize))
        {

            switch (sampleSize)
            {

                case 32 :
                    samples.put(bits.getFloat());
                    break;

                case 64 :
                    samples.put((float) bits.getDouble());
                    break;

                default :
                    throw new Error("PCM Float sample size illegal");

            }

        }

    }

}