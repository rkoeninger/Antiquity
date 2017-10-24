package rsound.core.codec;

public interface Codebook
{

    public Number getCode(int index);

    public Number getCode(int... indexes);

}