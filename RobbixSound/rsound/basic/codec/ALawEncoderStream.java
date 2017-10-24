package rsound.basic.codec;

import rsound.core.codec.Codebook;
import rsound.core.io.AudioOutput;
import rsound.core.io.BitBuffer;
import rsound.core.io.DataOutput;
import rsound.core.io.DecodedFormat;
import rsound.core.io.SampleBuffer;

class ALawEncoderStream extends AbstractEncoderStream
{

    private static final Codebook segmentBook = new ALawSegmentEndCodebook();

    public ALawEncoderStream(DecodedFormat format, DataOutput output)
    {

        super(format, output);

    }

    public void toBits(SampleBuffer samples, BitBuffer bits)
    {

        while (samples.hasRemaining() & bits.hasRemaining())
        {

            short sample = (short) (samples.get() * 32767.0f);

            byte mask, segment = 8;

            if (sample >= 0)
                mask = (byte) 0xd5;
            else
            {

                mask = (byte) 0x55;
                sample = (short) (-sample - 8);

            }


            for (int x = 0; x < 8; ++x)
                if (sample <= segmentBook.getCode(x).shortValue())
                {

                    segment = (byte) x;
                    break;

                }

            if (segment >= 8)
                bits.putByte((byte) ((0x7f ^ mask) & 0xff));
            else
            {

                byte aval = (byte) (segment << 4);

                if (segment < 2)
                    aval |= (sample >> 4) & 0x0f;
                else
                    aval |= (sample >> (segment + 3)) & 0x0f;

                bits.putByte((byte) ((aval ^ mask) & 0xff));

            }

        }

    }

}