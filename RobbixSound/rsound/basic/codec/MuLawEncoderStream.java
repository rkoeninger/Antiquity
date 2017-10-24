package rsound.basic.codec;

import rsound.core.codec.Codebook;
import rsound.core.io.AudioOutput;
import rsound.core.io.BitBuffer;
import rsound.core.io.DataOutput;
import rsound.core.io.DecodedFormat;
import rsound.core.io.SampleBuffer;

class MuLawEncoderStream extends AbstractEncoderStream
{

    private static final Codebook exponentBook = new MuLawExponentCodebook();

    public MuLawEncoderStream(DecodedFormat format, DataOutput output)
    {

        super(format, output);

    }

    public void toBits(SampleBuffer samples, BitBuffer bits)
    {

        while (samples.hasRemaining() & bits.hasRemaining())
        {

            short sample = (short) (samples.get() * 32767.0f);
            final int sign = (sample >> 8) & 0x80;
            sample = (short) (Math.min(Math.abs(sample), 32635) + 0x84);
            final int exponent = exponentBook.getCode(
            (sample >> 7) & 0xff).intValue();
            final int mantissa = (sample >> (exponent + 3)) & 0x0f;
            final int ulawbyte = ~(sign | (exponent << 4) | mantissa);
            bits.putByte((byte) (ulawbyte == 0 ? 0x02 : ulawbyte));

        }

    }

}