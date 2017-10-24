package rsound.core.effect;

public interface CompoundControl extends Control
{

    public Control[] getMemberControls();

    public Object[] setValue(Object[] values);

    public Object[] getValue();

}