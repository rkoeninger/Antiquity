package rsound.basic.codec;

import java.nio.ByteOrder;
import rsound.core.Utilities;
import rsound.core.io.BitBuffer;
import rsound.core.io.DataInput;
import rsound.core.io.DecodedFormat;
import rsound.core.io.SampleBuffer;

class PCMSignedDecoderStream extends AbstractDecoderStream
{

    private final int sampleSize;

    public PCMSignedDecoderStream(DecodedFormat format, DataInput input)
    {

        super(format, input);
        this.sampleSize = getEncodedFormat().getFrameSize() /
        getEncodedFormat().getChannelCount();

    }

    protected void toSamples(BitBuffer bits, SampleBuffer samples)
    {

        bits.setDoSignExtend(true);
        final long max = Utilities.getMax(sampleSize);

        while (samples.hasRemaining() & (bits.remaining() >= sampleSize))
        {

            samples.put(Utilities.normalize((sampleSize % 8 == 0 ?
            bits.getWordBytes(sampleSize) : bits.getWordBits(sampleSize)) /
            max));

        }

    }

}