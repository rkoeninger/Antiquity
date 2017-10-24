package rsound.core.effect;

import java.io.IOException;
import rsound.core.io.AudioInput;

public interface Effect
{

    public AudioInput apply(AudioInput input) throws IOException;

}