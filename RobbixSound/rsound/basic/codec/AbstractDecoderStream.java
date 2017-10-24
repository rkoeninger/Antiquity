package rsound.basic.codec;

import java.io.IOException;
import java.nio.ByteOrder;
import rsound.core.Utilities;
import rsound.core.io.AudioInput;
import rsound.core.io.BitBuffer;
import rsound.core.io.DataInput;
import rsound.core.io.DecodedFormat;
import rsound.core.io.EncodedFormat;
import rsound.core.io.SampleBuffer;

abstract class AbstractDecoderStream implements AudioInput
{

    private final DecodedFormat format;
    private final DataInput input;
    private final BitBuffer bitBuffer;

    private final int frameSize, channelCount;

    private final boolean bitOrder, byteOrder;

    protected AbstractDecoderStream(DecodedFormat format, DataInput input)
    {

        if (format == null | input == null)
            throw new Error();

        this.format = format;
        this.input = input;
        this.bitBuffer = BitBuffer.allocate(1024 *
        getEncodedFormat().getFrameSize());
        this.frameSize = getEncodedFormat().getFrameSize();
        this.channelCount = getEncodedFormat().getChannelCount();
        this.bitOrder = input.getFormat(
        ).getBitOrder() == ByteOrder.BIG_ENDIAN;
        this.byteOrder = input.getFormat(
        ).getByteOrder() == ByteOrder.BIG_ENDIAN;

    }

    public DecodedFormat getFormat()
    {

        return format;

    }

    public EncodedFormat getEncodedFormat()
    {

        return input.getFormat();

    }

    public boolean isOpen()
    {

        return input.isOpen();

    }

    public void close() throws IOException
    {

        input.close();

    }

    public boolean canSeek()
    {

        return input.canSeek();

    }

    public long seek(long position) throws IOException
    {

        return input.seek(position * frameSize) / frameSize;

    }

    public long getPosition() throws IOException
    {

        return input.getPosition() / frameSize;

    }

    public long getLength() throws IOException
    {

        return input.getLength() / frameSize;

    }

    public int read(SampleBuffer buffer) throws IOException
    {

        int totalFramesRead = 0;

        while (buffer.hasRemaining())
        {

            bitBuffer.clear().limit((int) Math.min(
            Utilities.roundSamplesDown(buffer.remaining(),
            channelCount) * channelCount / frameSize, bitBuffer.capacity()));
            final int bitsRead = input.read(
            bitBuffer.setBitOrder(bitOrder).setByteOrder(byteOrder));

            if (bitsRead < 0)
                return totalFramesRead == 0 ? -1 : totalFramesRead;

            bitBuffer.flip();
            toSamples(bitBuffer, buffer);
            totalFramesRead += bitsRead / frameSize;

        }

        return totalFramesRead;

    }

    protected abstract void toSamples(BitBuffer bits, SampleBuffer samples);

}