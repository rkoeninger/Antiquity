package rsound.basic.codec;

import rsound.core.codec.Codebook;

public final class ALawSegmentEndCodebook implements Codebook
{

    private static final short[] segmentEnds = new short[]
    {
        
        0xff, 0x1ff, 0x3ff, 0x7ff, 0xfff, 0x1fff, 0x3fff, 0x7fff
        
    };

    public ALawSegmentEndCodebook()
    {
    }

    public Number getCode(int index)
    {

        return Short.valueOf(segmentEnds[index]);

    }

    public Number getCode(int... indexes)
    {

        return getCode(indexes[0]);

    }

}