package rsound.core.io;

public final class DecodedFormat
{

    private final int sampleRate;
    private final int channelCount;

    public DecodedFormat(int sampleRate, int channelCount)
    {

        if (sampleRate <= 0 | channelCount <= 0)
            throw new IllegalArgumentException();

        this.sampleRate = sampleRate;
        this.channelCount = channelCount;

    }

    public int getSampleRate()
    {

        return sampleRate;

    }

    public int getChannelCount()
    {

        return channelCount;

    }

    public boolean equals(Object that)
    {

        if (! (that instanceof DecodedFormat))
            return false;

        final DecodedFormat format = (DecodedFormat) that;

        return (this.sampleRate == format.sampleRate) &
        (this.channelCount == channelCount);

    }

    public String toString()
    {

        final StringBuilder builder = new StringBuilder();
        builder.append(sampleRate / 1000.0).append("kHz ");
        builder.append(channelCount).append("-channel");
        return builder.toString();

    }

}
