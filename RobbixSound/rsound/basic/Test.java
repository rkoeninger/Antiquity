package rsound.basic;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.*;
import java.util.*;
import javax.sound.sampled.*;
import rsound.core.*;
import rsound.core.codec.*;
import rsound.core.io.*;
import rsound.basic.codec.*;
import rsound.basic.io.*;

class Test
{

    public static void main(String[] args) throws Throwable
    {

        File file = new File("C:\\spacemusic.au");
        AudioInput ain = new MuLawDecoder().decode(
        new AuFileInput(file, MuLawFormat.getFormat()));
        AudioOutput aout = new PCMEncoder().encode(
        new SDLAudioOutput(PCMFormat.getSignedFormat(
        8000, 16, 1, ByteOrder.BIG_ENDIAN)));
        SampleBuffer buffer = SampleBuffer.allocate(998);

        while (ain.read(buffer.clear()) >= 0) aout.write(buffer.flip());

        System.exit(0);

    }

}

class AuFileInput implements DataInput{
    final RandomAccessFile raf;
    final EncodedFormat format;
    final long dataOffset, dataLength;
    boolean open;
    AuFileInput(File file, EncodedFormat encodedFormat) throws IOException{
        raf = new RandomAccessFile(file, "r");
        format = encodedFormat;
        int magic = raf.readInt();
        dataOffset = raf.readInt();
        dataLength = raf.readInt();
        long enc = raf.readInt();
        long sampleRate = raf.readInt();
        long channels = raf.readInt();

        if (magic != 0x2e736e64 | enc != 0x00000001 |
        sampleRate != 8000 | channels != 1)
            throw new Error();

        open = true;

    }
    public EncodedFormat getFormat(){
        return format;
    }
    public boolean isOpen(){
        return open;
    }
    public synchronized void close() throws IOException{
        open = false;
        raf.close();
    }
    public boolean canSeek(){
        return true;
    }
    public long seek(long position) throws IOException{
        raf.seek(dataOffset + Math.min(position / 8, dataLength));
        return getPosition();
    }
    public long getPosition() throws IOException{
        return (raf.getFilePointer() - dataOffset) * 8;
    }
    public long getLength() throws IOException{
        return dataLength * 8;
    }
    byte[] bbuff = new byte[1024];
    public synchronized int read(BitBuffer buffer) throws IOException{
        int b = -1;
        int bitsRead=0;
        buffer.setBitOrder(Utilities.getNativeByteOrder());
        while (((raf.length() - raf.getFilePointer()) >= 0) &
        (buffer.remaining() >= 8)){
            final int len = Math.min(buffer.remaining() / 8, 1024);
            final int readBYTES = raf.read(bbuff, 0,len);
            if (readBYTES < 0)
                return -1;
            buffer.put(bbuff, 0, readBYTES);
            bitsRead += 8*readBYTES;
        }
        return  bitsRead == 0 ? -1 : bitsRead;
    }
}

class SDLAudioOutput implements DataOutput{
    final SourceDataLine sdl;
    final EncodedFormat format;
    SDLAudioOutput(EncodedFormat fmt) throws Exception{
        format = fmt;
        final AudioFormat jsaf = new AudioFormat((float) fmt.getFrameRate(),
        fmt.getFrameSize()/fmt.getChannelCount(),fmt.getChannelCount(),
        true,
        fmt.getByteOrder() == ByteOrder.BIG_ENDIAN);
        sdl = (SourceDataLine) AudioSystem.getLine(
        new DataLine.Info(SourceDataLine.class, jsaf));
        sdl.open(jsaf);
        sdl.start();
    }
    public EncodedFormat getFormat(){
        return format;
    }
    public boolean isOpen(){
        return sdl.isOpen();
    }
    public void close() throws IOException{
        sdl.stop();
        sdl.flush();
        sdl.close();
    }
    public long getAvailable() throws IOException{
        return sdl.available() * 8;
    }
    public long getLength() throws IOException{
        return sdl.getLongFramePosition() * format.getFrameSize();
    }
    byte[] bbuff = new byte[1024];
    public int write(BitBuffer buffer) throws IOException{
        int bitsWritten = 0;
        buffer.setBitOrder(Utilities.getNativeByteOrder());
        while ((buffer.remaining() >= 8)){
            final int len = Math.min(buffer.remaining() / 8, 1024);
            buffer.get(bbuff, 0, len);
            sdl.write(bbuff, 0, len);
            bitsWritten += 8 * len;
        }
        return bitsWritten == 0 ? -1 : bitsWritten;
    }
}