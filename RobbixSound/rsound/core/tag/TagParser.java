package rsound.core.tag;

import rsound.core.io.BitBuffer;

public interface TagParser
{

    public Tag parse(BitBuffer buffer) throws TagParseException;

}
