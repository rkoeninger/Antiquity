package rsound.basic.io;

import java.nio.ByteOrder;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import rsound.core.Utilities;
import rsound.core.io.EncodedFormat;

public class PCMFormat extends EncodedFormat
{

    public static PCMFormat getSignedFormat(int sampleRate,
    int sampleSize, int channelCount, ByteOrder order)
    {

        return new PCMFormat(sampleRate, sampleSize,
        channelCount, true, false, order);

    }

    public static PCMFormat getUnsignedFormat(int sampleRate,
    int channelCount, ByteOrder order)
    {

        return new PCMFormat(sampleRate, 8,
        channelCount, false, false, order);

    }

    public static PCMFormat getFloatFormat(int sampleRate,
    int sampleSize, int channelCount, ByteOrder order)
    {

        return new PCMFormat(sampleRate, sampleSize,
        channelCount, true, true, order);

    }

    protected PCMFormat(int sampleRate, int sampleSize, int channelCount,
    boolean signed, boolean floatingPoint, ByteOrder order)
    {

        super(new PCMEncoding(), sampleRate,
        sampleSize * channelCount, channelCount, order);

        if (floatingPoint)
            signed = true;

        setProperty("signed", Boolean.valueOf(signed));
        setProperty("float", Boolean.valueOf(floatingPoint));

    }

    public int getSampleRate()
    {

        return (int) getFrameRate();

    }

    public int getSampleSize()
    {

        return getFrameSize() / getChannelCount();

    }

    public boolean isSigned()
    {

        return getBooleanProperty("signed").booleanValue();

    }

    public boolean isFloat()
    {

        return getBooleanProperty("float").booleanValue();

    }

    public String toString()
    {

        return getEncoding() + " " + getSampleSize() + "-bit" +
        (isFloat() ? " float" : (isSigned() ? " signed" : " unsigned")) +
        (getFrameRate() / 1000.0) + " kHz" + " " +
        (getChannelCount() == 1 ? "mono" : (getChannelCount() == 2 ? "stereo" :
        getChannelCount() + "-channel"));

    }

    public static class PCMEncoding extends EncodedFormat.Encoding
    {

        private final Set<String> baseProperties;

        public PCMEncoding()
        {

            super("PCM");
            this.baseProperties = Utilities.asSet("signed", "float");
            this.baseProperties.addAll(super.getBaseProperties());

        }

        public Set<String> getBaseProperties()
        {

            return Collections.unmodifiableSet(baseProperties);

        }

        public boolean matches(Encoding encoding)
        {

            final String name = encoding.getName();
            return name.contains("pcm") | name.contains("PCM") |
            name.contains("pulse") | name.contains("Pulse") |
            name.contains("PULSE") | (encoding instanceof PCMEncoding);

        }

    }

}
