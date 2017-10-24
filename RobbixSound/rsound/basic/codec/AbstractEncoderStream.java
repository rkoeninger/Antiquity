package rsound.basic.codec;

import java.io.IOException;
import java.nio.ByteOrder;
import rsound.core.Utilities;
import rsound.core.io.AudioOutput;
import rsound.core.io.BitBuffer;
import rsound.core.io.DataOutput;
import rsound.core.io.DecodedFormat;
import rsound.core.io.EncodedFormat;
import rsound.core.io.SampleBuffer;

abstract class AbstractEncoderStream implements AudioOutput
{

    private final DecodedFormat format;
    private final DataOutput output;
    private final BitBuffer bitBuffer;

    private final int frameSize, channelCount;

    private final boolean bitOrder, byteOrder;

    protected AbstractEncoderStream(DecodedFormat format, DataOutput output)
    {

        if (format == null | output == null)
            throw new Error();

        this.format = format;
        this.output = output;
        this.bitBuffer = BitBuffer.allocate(1024 *
        getEncodedFormat().getFrameSize());
        this.frameSize = getEncodedFormat().getFrameSize();
        this.channelCount = getEncodedFormat().getChannelCount();
        this.bitOrder = output.getFormat(
        ).getBitOrder() == ByteOrder.BIG_ENDIAN;
        this.byteOrder = output.getFormat(
        ).getByteOrder() == ByteOrder.BIG_ENDIAN;

    }

    public DecodedFormat getFormat()
    {

        return format;

    }

    public EncodedFormat getEncodedFormat()
    {

        return output.getFormat();

    }

    public boolean isOpen()
    {

        return output.isOpen();

    }

    public void close() throws IOException
    {

        output.close();

    }

    public long getAvailable() throws IOException
    {

        return output.getAvailable() / getEncodedFormat().getFrameSize();

    }

    public long getLength() throws IOException
    {

        return output.getLength() / getEncodedFormat().getFrameSize();

    }

    public int write(SampleBuffer buffer) throws IOException
    {

        final int initialRemainingSamples = buffer.remaining();
        int totalFramesWritten = 0;

        while (buffer.hasRemaining())
        {

            bitBuffer.clear();
            toBits(buffer, bitBuffer.setBitOrder(
            bitOrder).setByteOrder(byteOrder));
            bitBuffer.flip();

            while (bitBuffer.hasRemaining())
            {

                final int bitsWritten = output.write(bitBuffer);

                if (bitsWritten < 0)
                {

                    buffer.position(buffer.position() -
                    (initialRemainingSamples -
                    (totalFramesWritten * channelCount)));
                    return totalFramesWritten == 0 ? -1 : totalFramesWritten;

                }

                totalFramesWritten += bitsWritten / frameSize;

            }

        }

        return totalFramesWritten;

    }

    protected abstract void toBits(SampleBuffer samples, BitBuffer bits);

}