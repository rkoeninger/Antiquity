package rsound.core;

import java.nio.ByteOrder;
import java.util.HashSet;
import java.util.Set;

public class Utilities
{

    public static <T> Set<T> asSet(T... array)
    {

        final Set<T> set = new HashSet<T>(array.length);

        for (T item : array)
            set.add(item);

        return set;

    }

    public static boolean getNativeByteOrder()
    {

        return ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;

    }

    public static long roundSamplesDown(long samples, int channelCount)
    {

        return samples - (samples % channelCount);

    }

    public static long roundSamplesUp(long samples, int channelCount)
    {

        return samples + (channelCount - (samples % channelCount));

    }

    public static float normalize(float sample)
    {

        return Math.min(1.0f, Math.max(-1.0f, sample));

    }

    public static long normalize(long word, int wordSize)
    {

        final long max = getMax(wordSize);
        return Math.min(max, Math.max(~max, word));

    }

    public static long getMax(int wordSize)
    {

        if (wordSize < 0 | wordSize > Long.SIZE)
            throw new IllegalArgumentException();

        return (1L << (wordSize  - 1)) - 1;

    }

    public static long getMin(int wordSize)
    {

        if (wordSize < 0 | wordSize > Long.SIZE)
            throw new IllegalArgumentException();

        return wordSize == Long.SIZE ? Long.MIN_VALUE : -1L << (wordSize - 1);

    }

    public static long signExtend(long word, int wordSize)
    {

        //2's complement only

        final long signMask = 1L << (wordSize - 1);
        return (word & signMask) != 0 & (wordSize < Long.SIZE) ?
        word | (-1L << wordSize) : word;

    }

    public static long signMagnitudeToTwos(long word, int wordSize)
    {

        final long mag = word & ~(-1L << (wordSize - 1));
        final boolean sign = (word & (1L << (wordSize - 1))) != 0;
        return sign ? -mag : mag;

    }

    public static long twosToSignMagnitude(long word, int wordSize)
    {

        final boolean sign = word < 0;
        final long mag = (sign ? -word : word) & ~(-1L << (wordSize - 1));
        return sign ? mag | (1L << wordSize) : mag;

    }

    private Utilities()
    {

        throw new Error("Do not instantiate this class!");

    }

}