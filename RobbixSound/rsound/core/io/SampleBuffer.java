package rsound.core.io;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.InvalidMarkException;

public class SampleBuffer
{

    public static SampleBuffer allocate(int size)
    {

        if (size < 0)
            throw new IllegalArgumentException();

        return new SampleBuffer(new float[size], 0, 1, size, 0, size, -1);

    }

    public static SampleBuffer wrap(float[] samples)
    {

        return wrap(samples, 0, samples.length);

    }

    public static SampleBuffer wrap(float[] samples, int offset, int length)
    {

        if ((offset | length) < 0 | (offset + length) > samples.length)
            throw new IllegalArgumentException();

        return new SampleBuffer(samples, offset, 1, length, 0, length, -1);

    }

    private final float[] elements;
    private final int sampleOffset, channelFactor;

    private int capacity, position, limit, mark;

    protected SampleBuffer(float[] elements, int sampleOffset,
    int channelFactor, int capacity, int position, int limit, int mark)
    {

        this.elements = elements;
        this.sampleOffset = sampleOffset;
        this.channelFactor = channelFactor;
        this.capacity = capacity;
        this.position = position;
        this.limit = limit;
        this.mark = mark;

    }

    public SampleBuffer position(int newPosition)
    {

        if (newPosition < 0 | newPosition > limit())
            throw new IllegalArgumentException();

        position = newPosition;

        if (position > mark)
            mark = -1;

        return this;

    }

    public SampleBuffer limit(int newLimit)
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

        return (position * channelFactor) < limit;

    }

    public int remaining()
    {

        return limit - (position * channelFactor);

    }

    public SampleBuffer mark()
    {

        mark = position;
        return this;

    }

    public SampleBuffer reset()
    {

        if (mark < 0)
            throw new InvalidMarkException();

        position = mark;
        return this;

    }

    public SampleBuffer rewind()
    {

        position = 0;
        mark = -1;
        return this;

    }

    public SampleBuffer clear()
    {

        position = 0;
        limit = capacity;
        mark = -1;
        return this;

    }

    public SampleBuffer flip()
    {

        limit = position;
        position = 0;
        mark = -1;
        return this;

    }

    public SampleBuffer clone()
    {

        return new SampleBuffer((float[]) elements.clone(),
        sampleOffset, channelFactor, capacity, position, limit, mark);

    }

    public SampleBuffer slice()
    {

        return new SampleBuffer(elements, sampleOffset + position,
        channelFactor, remaining(), 0, remaining(), -1);

    }

    public SampleBuffer duplicate()
    {

        return new SampleBuffer(elements, sampleOffset,
        channelFactor, capacity, position, limit, mark);

    }

    public SampleBuffer compact()
    {

        System.arraycopy(elements, position, elements, 0, remaining());
        limit = remaining();
        position = 0;
        mark = -1;
        return this;

    }

    public SampleBuffer getChannel(int channel, int channelCount)
    {

        return new SampleBuffer(elements, sampleOffset + channel,
        channelCount, capacity, position, limit, mark);

    }

    public SampleBuffer put(int index, float sample)
    {

        if (index < 0 | index >= capacity)
            throw new IllegalArgumentException();

        elements[sampleOffset + (index * channelFactor)] = sample;
        return this;

    }

    public SampleBuffer put(float sample)
    {

        if (! hasRemaining())
            throw new BufferOverflowException();

        return put(position++, sample);

    }

    public SampleBuffer put(float[] samples)
    {

        return put(samples, 0, samples.length);

    }

    public SampleBuffer put(float[] samples, int offset, int length)
    {

        if ((length | offset) < 0 | (offset + length) > samples.length)
            throw new IllegalArgumentException();
        else if (length > remaining())
            throw new BufferOverflowException();

        if (channelFactor == 1)
        {

            System.arraycopy(samples, offset, elements,
            sampleOffset + position, length);
            position += length;

        }
        else
        {

            for (int x = offset; x < (offset + length); ++x)
                put(samples[x]);

        }

        return this;

    }

    public float get(int index)
    {

        if (index < 0 | index >= capacity)
            throw new IllegalArgumentException();

        return elements[sampleOffset + (index * channelFactor)];

    }

    public float get()
    {

        if (! hasRemaining())
            throw new BufferUnderflowException();

        return get(position++);

    }

    public SampleBuffer get(float[] samples)
    {

        return get(samples, 0, samples.length);

    }

    public SampleBuffer get(float[] samples, int offset, int length)
    {

        if ((length | offset) < 0 | (offset + length) > samples.length)
            throw new IllegalArgumentException();
        else if (length > remaining())
            throw new BufferUnderflowException();

        if (channelFactor == 1)
        {

            System.arraycopy(elements, sampleOffset + position,
            samples, offset, length);
            position += length;

        }
        else
        {

            for (int x = offset; x < (offset + length); ++x)
                samples[x] = get();

        }

        return this;

    }

    public String toString()
    {

        return "SampleBuffer: position=" + position() + " limit=" + limit() +
        " capacity=" + capacity() + " mark=" + mark;

    }

}