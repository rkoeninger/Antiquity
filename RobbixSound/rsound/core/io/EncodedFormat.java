package rsound.core.io;

import java.nio.ByteOrder;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import rsound.core.Utilities;

public class EncodedFormat
{

    private final Map<String, Object> properties;

    public EncodedFormat(Encoding encoding, double frameRate,
    int frameSize, int channelCount, ByteOrder byteOrder)
    {

        this();

        if (encoding == null)
            throw new IllegalArgumentException();

        final int bitrate = (int) (frameRate * frameSize);
        properties.put("encoding", encoding);
        properties.put("frameRate", frameRate);
        properties.put("frameSize", frameSize);
        properties.put("channelCount", channelCount);
        properties.put("bitrate.nominal", bitrate);
        properties.put("bitrate.average", bitrate);
        properties.put("bitrate.minimum", bitrate);
        properties.put("bitrate.maximum", bitrate);
        properties.put("bitrate.variable", false);
        properties.put("bitOrder", ByteOrder.BIG_ENDIAN);
        properties.put("byteOrder", byteOrder);

    }

    public EncodedFormat(Map<String, Object> initialProperties)
    {

        this();

        if (! initialProperties.containsKey("encoding"))
            throw new IllegalArgumentException();

        properties.putAll(initialProperties);
        fillKeys(getEncoding().getBaseProperties());

    }

    private EncodedFormat()
    {

        this.properties = Collections.synchronizedMap(
        new HashMap<String, Object>());

    }

    public Encoding getEncoding()
    {

        return getEncodingProperty("encoding");

    }

    public double getFrameRate()
    {

        return getNumberProperty("frameRate").doubleValue();

    }

    public int getFrameSize()
    {

        return getNumberProperty("frameSize").intValue();

    }

    public int getBitrate()
    {

        return getNumberProperty("bitrate.nominal").intValue();

    }

    public int getAverageBitrate()
    {

        return getNumberProperty("bitrate.average").intValue();

    }

    public int getMinimumBitrate()
    {

        return getNumberProperty("bitrate.minimum").intValue();

    }

    public int getMaximumBitrate()
    {

        return getNumberProperty("bitrate.maximum").intValue();

    }

    public boolean isVariableBitrate()
    {

        return getBooleanProperty("bitrate.varible").booleanValue();

    }

    public int getChannelCount()
    {

        return getNumberProperty("channelCount").intValue();

    }

    public ByteOrder getBitOrder()
    {

        return getByteOrderProperty("bitOrder");

    }

    public ByteOrder getByteOrder()
    {

        return getByteOrderProperty("byteOrder");

    }

    protected Object setProperty(String key, Object value)
    {

        for (char c : key.toCharArray())
            if (! (Character.isLetterOrDigit(c) | c == '.'))
                throw new IllegalArgumentException();

        return properties.put(key, value);

    }

    public Object getProperty(String key)
    {

        if (! properties.containsKey(key))
            throw new IllegalArgumentException();

        return properties.get(key);

    }

    protected Encoding getEncodingProperty(String key)
    {

        final Object value = getProperty(key);

        if (! (value instanceof Encoding) | (value == null))
            throw new IllegalArgumentException();

        return (Encoding) value;

    }

    public Boolean getBooleanProperty(String key)
    {

        final Object value = getProperty(key);

        if (! (value instanceof Boolean))
            throw new IllegalArgumentException();

        return value == null ? Boolean.FALSE : (Boolean) value;

    }

    public Number getNumberProperty(String key)
    {

        final Object value = getProperty(key);

        if (! (value instanceof Number))
            throw new IllegalArgumentException();

        return value == null ? Integer.valueOf(-1) : (Number) value;

    }

    public ByteOrder getByteOrderProperty(String key)
    {

        final Object value = getProperty(key);

        if (! (value instanceof ByteOrder) | (value == null))
            throw new IllegalArgumentException();

        return (ByteOrder) value;

    }

    public Map<String, Object> getProperties()
    {

        return Collections.unmodifiableMap(properties);

    }

    protected Map<String, Object> getSpecifiedProperties()
    {

        final Map<String, Object> specified = new HashMap<String, Object>();
        final Set<String> keys = properties.keySet();

        for (String key : keys)
            if (properties.get(key) != null)
                specified.put(key, properties.get(key));

        return specified;

    }

    protected void fillKeys(Set<String> keys)
    {

        for (String key : keys)
            if (! properties.containsKey(key))
                properties.put(key, null);

    }

    public boolean isFullySpecified()
    {

        final Set<String> keys = properties.keySet();

        for (String key : keys)
            if (properties.get(key) == null)
                return false;

        return true;

    }

    public boolean matches(EncodedFormat that)
    {

        final Map<String, Object> thisProperties =
        this.getSpecifiedProperties();
        final Map<String, Object> thatProperties =
        that.getSpecifiedProperties();
        final Set<String> sharedProperties = new HashSet<String>();
        sharedProperties.addAll(thisProperties.keySet());
        sharedProperties.addAll(thatProperties.keySet());

        for (String key : sharedProperties)
            if (! thisProperties.get(key).equals(thatProperties.get(key)))
                return false;

        return true;

    }

    public static abstract class Encoding
    {

        private final String name;

        private final Set<String> baseProperties;

        public Encoding(String name)
        {

            if (name.trim().length() == 0)
                throw new IllegalArgumentException();

            this.name = name;
            this.baseProperties = Utilities.asSet(
            "encoding", "frameRate", "frameSize", "bitrate.nominal",
            "bitrate.average", "bitrate.minimum", "bitrate.maximum",
            "bitrate.varible", "channelCount", "bitOrder", "byteOrder");

        }

        public boolean matches(Encoding encoding)
        {

            return getName().trim().equalsIgnoreCase(encoding.getName().trim());

        }

        public String getName()
        {

            return name;

        }

        public Set<String> getBaseProperties()
        {

            return Collections.unmodifiableSet(baseProperties);

        }

        public String toString()
        {

            return getName();

        }

    }

}