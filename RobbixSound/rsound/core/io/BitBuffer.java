package rsound.core.io;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.nio.InvalidMarkException;
import rsound.core.Utilities;

public class BitBuffer implements Cloneable
{

    private static final int segmentSize = Long.SIZE;

    public static BitBuffer allocate(int size)
    {

        if (size < 0)
            throw new IllegalArgumentException();

        return new BitBuffer(new long[(int)
        Math.ceil(size / (double) segmentSize)], 0, size, 0, size, -1);

    }

    public static BitBuffer wrap(long[] bits)
    {

        return wrap(bits, 0, bits.length * segmentSize);

    }

    public static BitBuffer wrap(long[] bits, int offset, int length)
    {

        if ((offset | length) < 0 |
        (offset + length) > (bits.length * segmentSize))
            throw new IllegalArgumentException();

        return new BitBuffer(bits, offset, length, 0, length, -1);

    }

    private final long[] segments;
    private final int bitOffset;

    private int capacity, position, limit, mark;

    private boolean byteOrder, bitOrder, signExtend;

    protected BitBuffer(long[] segments, int bitOffset,
    int capacity, int position, int limit, int mark)
    {

        this.segments = segments;
        this.bitOffset = bitOffset;
        this.capacity = capacity;
        this.position = position;
        this.limit = limit;
        this.mark = mark;
        this.byteOrder = false;
        this.bitOrder = false;
        this.signExtend = false;

    }

    public BitBuffer position(int newPosition)
    {

        if (newPosition < 0 | newPosition > limit())
            throw new IllegalArgumentException();

        position = newPosition;

        if (position > mark)
            mark = -1;

        return this;

    }

    public BitBuffer limit(int newLimit)
    {

        if (newLimit < 0 | newLimit > capacity())
            throw new IllegalArgumentException();

        limit = newLimit;

        if (position > limit)
            position = limit;
        else if (mark > limit)
            mark = -1;

        return this;

    }

    public int position()
    {

        return position;

    }

    public int limit()
    {

        return limit;

    }

    public int capacity()
    {

        return capacity;

    }

    public boolean hasRemaining()
    {

        return position < limit;

    }

    public int remaining()
    {

        return limit - position;

    }

    public BitBuffer mark()
    {

        mark = position;
        return this;

    }

    public BitBuffer reset()
    {

        if (mark < 0 | mark > position)
            throw new InvalidMarkException();

        position = mark;
        return this;

    }

    public BitBuffer rewind()
    {

        position = 0;
        mark = -1;
        return this;

    }

    public BitBuffer clear()
    {

        position = 0;
        limit = capacity;
        mark = -1;
        return this;

    }

    public BitBuffer flip()
    {

        limit = position;
        position = 0;
        mark = -1;
        return this;

    }

    public BitBuffer clone()
    {

        return new BitBuffer((long[]) segments.clone(),
        bitOffset, capacity, position, limit, mark);

    }

    public BitBuffer slice()
    {

        return new BitBuffer(segments, bitOffset + position,
        remaining(), 0, remaining(), -1);

    }

    public BitBuffer duplicate()
    {

        return new BitBuffer(segments, bitOffset,
        capacity, position, limit, mark);

    }

    public BitBuffer compact()
    {

        for (int x = 0; x < remaining(); ++x)
            put(x, get(position + x));

        limit = remaining();
        position = 0;
        mark = -1;
        return this;

    }

    public BitBuffer setDoSignExtend(boolean doSignExtend)
    {

        signExtend = doSignExtend;
        return this;

    }

    public BitBuffer setBitOrder(boolean bigEndian)
    {

        bitOrder = bigEndian;
        return this;

    }

    public BitBuffer setByteOrder(boolean bigEndian)
    {

        byteOrder = bigEndian;
        return this;

    }

    public boolean getDoSignExtend()
    {

        return signExtend;

    }

    public boolean getBitOrder()
    {

        return bitOrder;

    }

    public boolean getByteOrder()
    {

        return byteOrder;

    }

    public BitBuffer put(int index, boolean bit)
    {

        if (index < 0 | index >= capacity)
            throw new IllegalArgumentException();

        final int seg = (bitOffset + index) / segmentSize;
        final long mask = 1L << ((bitOffset + index) % segmentSize);
        segments[seg] = bit ? segments[seg] | mask : segments[seg] & ~mask;
        return this;

    }

    public BitBuffer put(boolean bit)
    {

        if (! hasRemaining())
            throw new BufferOverflowException();

        put(position++, bit);
        return this;

    }

    public BitBuffer put(boolean[] bits)
    {

        return put(bits, 0, bits.length);

    }

    public BitBuffer put(boolean[] bits, int offset, int length)
    {

        if ((length | offset) < 0 | (offset + length) > bits.length)
            throw new IllegalArgumentException();
        else if (length > remaining())
            throw new BufferOverflowException();

        for (int x = 0; x < length; ++x)
            put(position++, bits[offset + x]);

        return this;

    }

    public BitBuffer put(byte[] bytes)
    {

        return put(bytes, 0, bytes.length);

    }

    public BitBuffer put(byte[] bytes, int offset, int length)
    {

        if ((offset | length) < 0 | (offset + length) > bytes.length)
            throw new IllegalArgumentException();
        else if (length * Byte.SIZE > remaining())
            throw new BufferOverflowException();

        for (int x = offset; x < (offset + length); ++x)
            putByte(bytes[x]);

        return this;

    }

    public BitBuffer put(BitBuffer buffer)
    {

        if (buffer == null | buffer == this)
            throw new IllegalArgumentException();
        else if (buffer.remaining() > remaining())
            throw new BufferOverflowException();

        while (buffer.hasRemaining() & hasRemaining())
            put(buffer.get());

        return this;

    }

    public BitBuffer putWordBits(long bits, int length)
    {

        if (length < 0 | length > Long.SIZE)
            throw new IllegalArgumentException();
        else if (length > remaining())
            throw new BufferOverflowException();

        if (bitOrder)
            bits = Long.rotateRight(Long.reverse(bits), Long.SIZE - length);

        int offset = 0, bitsLeft = length;

        while (bitsLeft > 0)
        {

            final int seg = (bitOffset + position) / segmentSize;
            final int positionInSegment = (bitOffset + position) % segmentSize;
            final int remainingInSegment = segmentSize - positionInSegment;
            final int lengthToPut = Math.min(bitsLeft, remainingInSegment);

            long mask = ~(-1L << positionInSegment);

            if (lengthToPut != remainingInSegment)
                mask |= -1L << (positionInSegment + lengthToPut);

            segments[seg] &= mask;
            segments[seg] |= (~(-1L << lengthToPut) &
            (bits >> offset)) << positionInSegment;

            bitsLeft -= lengthToPut;
            offset += lengthToPut;
            position += lengthToPut;

        }

        return this;

    }

    public BitBuffer putNibble(byte value)
    {

        putWordBits(value, 4);

    }

    public BitBuffer putByte(byte value)
    {

        return putWordBits(value, Byte.SIZE);

    }

    public BitBuffer putWordBytes(long bytes, int length)
    {

        if (length < 0 | length > Long.SIZE | (length % Byte.SIZE != 0))
            throw new IllegalArgumentException();
        else if (length > remaining())
            throw new BufferOverflowException();

        if (bitOrder == byteOrder)
            return putWordBits(bytes, length);

        for (int x = 0; x < (length / 8); ++x)
            putByte((byte) (bytes >>> ((byteOrder ?
            (length / 8) - 1 - x : x) * 8)));

        return this;

    }

    public BitBuffer putShort(short value)
    {

        return putWordBytes(value, Short.SIZE);

    }

    public BitBuffer putInt(int value)
    {

        return putWordBytes(value, Integer.SIZE);

    }

    public BitBuffer putLong(long value)
    {

        return putWordBytes(value, Long.SIZE);

    }

    public BitBuffer putFloat(float value)
    {

        return putWordBytes(Float.floatToIntBits(value), Float.SIZE);

    }

    public BitBuffer putDouble(double value)
    {

        return putWordBytes(Double.doubleToLongBits(value), Double.SIZE);

    }

    public boolean get(int index)
    {

        if (index < 0 | index >= capacity)
            throw new IllegalArgumentException();

        final int segment = (bitOffset + index) / segmentSize;
        final long mask = 1L << ((bitOffset + index) % segmentSize);
        return (segments[segment] & mask) != 0;

    }

    public boolean get()
    {

        if (! hasRemaining())
            throw new BufferUnderflowException();

        return get(position++);

    }

    public BitBuffer get(boolean[] bits)
    {

        return get(bits, 0, bits.length);

    }

    public BitBuffer get(boolean[] bits, int offset, int length)
    {

        if ((length | offset) < 0 | (offset + length) > bits.length)
            throw new IllegalArgumentException();
        else if (length > remaining())
            throw new BufferUnderflowException();

        for (int x = 0; x < length; ++x)
            bits[offset + x] = get(position++);

        return this;

    }

    public BitBuffer get(byte[] bytes)
    {

        return get(bytes, 0, bytes.length);

    }

    public BitBuffer get(byte[] bytes, int offset, int length)
    {

        if ((offset | length) < 0 | (offset + length) > bytes.length)
            throw new IllegalArgumentException();
        else if (length * Byte.SIZE > remaining())
            throw new BufferUnderflowException();

        for (int x = offset; x < (offset + length); ++x)
            bytes[x] = getByte();

        return this;

    }

    public BitBuffer get(BitBuffer buffer)
    {

        if (buffer == null | buffer == this)
            throw new IllegalArgumentException();
        else if (buffer.remaining() > remaining())
            throw new BufferUnderflowException();

        while (buffer.hasRemaining() & hasRemaining())
            buffer.put(get());

        return this;

    }

    public long getWordBits(int length)
    {

        if (length < 0 | length > Long.SIZE)
            throw new IllegalArgumentException();
        else if (length > remaining())
            throw new BufferUnderflowException();

        long bits = 0L;
        int offset = 0, bitsLeft = length;

        while (bitsLeft > 0)
        {

            final int seg = (bitOffset + position) / segmentSize;
            final int positionInSegment = (bitOffset + position) % segmentSize;
            final int remainingInSegment = segmentSize - positionInSegment;
            final int lengthToGet = Math.min(bitsLeft, remainingInSegment);

            long mask = ~(-1L << lengthToGet);

            bits |= ((segments[seg] >> positionInSegment) & mask) << offset;

            bitsLeft -= lengthToGet;
            offset += lengthToGet;
            position += lengthToGet;

        }

        if (bitOrder)
            bits = Long.rotateRight(Long.reverse(bits), Long.SIZE - length);

        return signExtend ? Utilities.signExtend(bits, length) : bits;

    }

    public byte getNibble()
    {

        return getWordBits(4);

    }

    public byte getByte()
    {

        return (byte) getWordBits(Byte.SIZE);

    }

    public long getWordBytes(int length)
    {

        if (length < 0 | length > Long.SIZE | (length % Byte.SIZE != 0))
            throw new IllegalArgumentException();
        else if (length > remaining())
            throw new BufferUnderflowException();

        long word = 0;

        if (bitOrder == byteOrder)
        {

            word = getWordBits(length);

        }
        else
        {

            for (int x = 0; x < (length / 8); ++x)
                word |= (getByte() & 0xffL) <<
                ((byteOrder ? (length / 8) - 1 - x : x) * 8);

        }

        return signExtend ? Utilities.signExtend(word, length) : word;

    }

    public short getShort()
    {

        return (short) getWordBytes(Short.SIZE);

    }

    public int getInt()
    {

        return (int) getWordBytes(Integer.SIZE);

    }

    public long getLong()
    {

        return getWordBytes(Long.SIZE);

    }

    public float getFloat()
    {

        return Float.intBitsToFloat((int) getWordBytes(Float.SIZE));

    }

    public double getDouble()
    {

        return Double.longBitsToDouble(getWordBytes(Double.SIZE));

    }

    public String toString()
    {

        return "BitBuffer: position=" + position() + " limit=" + limit() +
        " capacity=" + capacity() + " mark=" + mark + " bitOrder=" +
        (bitOrder ? "MSb" : "LSb") + " byteOrder=" +
        (byteOrder ? "MSB" : "LSB") + " doSignExtend=" + signExtend;

    }

}