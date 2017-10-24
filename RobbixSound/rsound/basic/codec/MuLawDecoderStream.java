package rsound.basic.codec;

import rsound.core.codec.Codebook;
import rsound.core.io.AudioInput;
import rsound.core.io.BitBuffer;
import rsound.core.io.DataInput;
import rsound.core.io.DecodedFormat;
import rsound.core.io.SampleBuffer;

class MuLawDecoderStream extends AbstractDecoderStream
{

    private static final Codebook codebook = new MuLawToPCMCodebook();

    public MuLawDecoderStream(DecodedFormat format, DataInput input)
    {

        super(format, input);

    }

    public void toSamples(BitBuffer bits, SampleBuffer samples)
    {

        while (bits.hasRemaining() & samples.hasRemaining())
        {

            samples.put(codebook.getCode(
            bits.getByte() & 0xff).shortValue() / 32767.0f);

        }

    }

}