package com.robbix.test;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.DataInput;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class AnimatedCursor
{
	public static void main(String[] args) throws IOException
	{
		long time;
		AnimatedCursor aCur;
		
		time = System.nanoTime();
		dbg=true;
		
		bw=false;
		aCur = load(new File("test.ani"));
		
		preview(aCur);
		
		out("\nFile 1 ----------------------------------------");
		readHeader(new File("test.ani"));
		out("\nFile 2 ----------------------------------------");
		readHeader(new File("test2.ani"));
		out("\nFile 3 ----------------------------------------");
		readHeader(new File("test3.ani"));
		out("\nFile 4 ----------------------------------------");
		readHeader(new File("move.ani"));
		
		dbg=false;
		out((System.nanoTime()-time)/1000000.0);
		dbg=false;
		
		System.exit(0);
	}
	
	private static int RIFF = 0x52494646;
	private static int ACON = 0x41434f4e;
	private static int anih = 0x616e6968;
	private static int rate = 0x72617465;
	private static int seq_ = 0x73657120;
	private static int icon = 0x69636f6e;
	private static int LIST = 0x4C495354;
	private static int fram = 0x6672616D;
	
	public static void readHeader(File file) throws IOException
	{
		RandomAccessFile input = new RandomAccessFile(file, "r");
		
		out("file length: " + input.length());
		
		int riffRead = input.readInt();
		if (riffRead != RIFF)
		{
			out("RIFF expected - got " + String.format("0x%x", riffRead));
			return;
		}
		
		int fileLength = reverseEndian(input.readInt());
		out("marked file length: " + fileLength);
		
		int aconRead = input.readInt();
		if (aconRead != ACON)
		{
			out("ACON expected - got " + String.format("0x%x", aconRead));
			return;
		}
		
		int anihRead = input.readInt();
		if (anihRead != anih)
		{
			out("anih expected - got " + String.format("0x%x", anihRead));
			return;
		}
		
		int headerLength = reverseEndian(input.readInt());
//		if (headerLength != 32)
//		{
//			out("header length expected to be 32 - got : " + headerLength);
//			return;
//		}
		out("marked header length: " + headerLength);
		out("total header length (including title): " + (headerLength + 8));
		
		// ^ these are equal becuase anih is a header and so it's header is it's entire length
		
		int subHeaderLength = reverseEndian(input.readInt());
		out("marked sub header length: " + subHeaderLength);
		out("total sub header length (including title): " + (subHeaderLength + 8));
		
		int frameCount = reverseEndian(input.readInt());
		out("frame count: " + frameCount);

		int stepCount = reverseEndian(input.readInt());
		out("step count: " + stepCount);

		int frameWidth = reverseEndian(input.readInt());
		out("frame width: " + frameWidth);

		int frameHeight = reverseEndian(input.readInt());
		out("frame height: " + frameHeight);
		
		int bitsPerPixel = reverseEndian(input.readInt());
		out("bits per pixel: " + bitsPerPixel);
		
		int planeCount = reverseEndian(input.readInt());
		out("plane count: " + planeCount);
		
		// ^ what does it mean to have more than one?

		int displayRate = reverseEndian(input.readInt());
		out("display rate: " + displayRate + " jiffies per frame");
		
		int flags = reverseEndian(input.readInt());
		if (flags != 1)
		{
			out("expected flags to be 0x0001 - got : " + toBinaryString(flags));
			return;
		}
		out("flags: 0b" + toBinaryString(flags));
		
//		int readLIST = input.readInt();
//		if (readLIST != LIST)
//		{
//			out("LIST expected - got " + String.format("0x%x", readLIST));
//			return;
//		}
//		
//		if (flags == 1)
//		{
//			
//		}
//		else if (flags == 2)
//		{
//			
//		}
	}
	
	
	
	
	
	
	public static AnimatedCursor load(File file) throws IOException
	{
		RandomAccessFile input = new RandomAccessFile(file, "r");
		
		if (input.readInt() != RIFF)                                        // 32 bits
			throw new IOException("Expected RIFF magic at start of file");
		
		out("fLength " + reverseEndian(input.readInt())); // Ignore file length    // 32 bits
		
		if (input.readInt() != ACON)                                             // 32 bits
			throw new IOException("Expected ACON magic at start of table");
		
		seekTo(input, anih);                                                // find "anih"
		
		int aniHeaderLength = reverseEndian(input.readInt());                 // 32 bits
		out("aniHeaderLength (num bytes following this word)" + aniHeaderLength);
		
		int frameCount = reverseEndian(input.readInt());                      // 32 bits
		out("frameCount " + frameCount);
		
		int cSteps = reverseEndian(input.readInt());                          // 32 bits
		out("cSteps " + cSteps);                                            //!! actually, number of frames
		
		out("cx " + reverseEndian(input.readInt())); // Ignore cx             // 32 bits!!also the number of frames
		out("cy " + reverseEndian(input.readInt())); // Ignore cy               //32bits!! actually, width/height
		out("cBitCount " + reverseEndian(input.readInt())); // Ignore cBitCount //32bits!! actually, width/height
		out("cPlanes " + reverseEndian(input.readInt())); // Ignore cPlanes     //32bits!! color depth
		
		int jifRate = reverseEndian(input.readInt());                       //32bits!!integer factor of jifrate
		out("jifRate " + jifRate);
		double jifRateFactor = (1.0 / 60.0) * jifRate;
		out("jifRateFactor " + jifRateFactor);
		
		int flags = reverseEndian(input.readInt()); //dont know what to do with flags
		out("flags " + flags);
		
		if (seekTo(input, rate)){                                           // find "rate"
		
			int rate = reverseEndian(input.readInt());                     // wasnt present for monochrome
			out("rate " + rate);
		
		}
		
		if (seekTo(input, seq_))                                            // find "seq_"
		{
		
			int seq = reverseEndian(input.readInt());                      // wasnt present for monochrome
			out("seq " + seq);
		
		}
		
		
		
		
		AnimatedCursor aCur = new AnimatedCursor(cSteps);
		
		long start = 0;
		
		for (int x = 0; x < cSteps; ++x)
		{
			seekTo(input, start, icon);
			start = input.getFilePointer();
			out("Frame: " + x);
			
//			append = true;
			Image img = loadImage(input);
//			append = false;
			
//			FileWriter w = new FileWriter(new File("dbg" + x + ".txt"));
//			w.write(buffer.toString());
//			w.flush();
//			w.close();
//			buffer = new StringBuffer();
			
			preview(img, 8);
			
			Cursor cur = Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(2, 2), "wand");
			
			aCur.frames[x] = new CursorFrame(cur, 100);
			//preview(cur);
		}
		
		input.close();
		
		return aCur;
	}
	
	
	
	
	
	/*
	 * Instance methods
	 */
	
	private CursorFrame[] frames;
	
	private AnimatedCursor(int frameLength)
	{
		frames = new CursorFrame[frameLength];
	}
	
	public int getFrameLength()
	{
		return frames.length;
	}
	
	public Cursor getFrame(int frame)
	{
		return frames[frame].cursor;
	}
	
	/**
	 * Duration cursor frame should be shown, in milliseconds
	 */
	public int getDuration(int frame)
	{
		return frames[frame].duration;
	}
	
	
	
	/*
	 * Static helper methods / classes
	 */
	
	private static void preview(final Image img, final int scale)
	{
		final JFrame frame = new JFrame("Preview");
		final JPanel panel = new JPanel()
		{
			private static final long serialVersionUID = 1L;
			{
				setPreferredSize(new Dimension(
					img.getWidth(null) * scale,
					img.getHeight(null) * scale
				));
				addMouseMotionListener(new MouseMotionAdapter()
				{
					public void mouseMoved(MouseEvent e)
					{
						frame.setTitle(String.format("(%1$d, %2$d)", e.getX() / scale, e.getY() / scale));
					}
				});
			}
			public void paint(Graphics g)
			{
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, getWidth(), getHeight());
				
				AffineTransform transform = new AffineTransform();
				transform.setToScale(scale, scale);
				Graphics2D g2d = ((Graphics2D) g);
				g2d.setTransform(transform);
				g2d.drawImage(img, 0, 0, null);
			}
		};
		frame.add(panel);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		
		while (frame.isVisible())
		{
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
//	private static void preview(final Cursor cur)
//	{
//		final JFrame frame = new JFrame("Preview");
//		final JPanel panel = new JPanel()
//		{
//			private static final long serialVersionUID = 1L;
//			{
//				{
//					setCursor(cur);
//				}
//				setPreferredSize(new Dimension(
//					128, 128
//				));
//			}
//			public void paint(Graphics g)
//			{
//				g.setColor(Color.WHITE);
//				g.fillRect(0, 0, getWidth(), getHeight());
//			}
//		};
//		frame.add(panel);
//		frame.setResizable(false);
//		frame.pack();
//		frame.setLocationRelativeTo(null);
//		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		frame.setVisible(true);
//		
//		while (frame.isVisible())
//		{
//			try
//			{
//				Thread.sleep(100);
//			}
//			catch (InterruptedException e)
//			{
//				e.printStackTrace();
//			}
//		}
//	}
	
	private static void preview(final AnimatedCursor aCur)
	{
		final JFrame frame = new JFrame("Preview");
		final JPanel panel = new JPanel()
		{
			private static final long serialVersionUID = 1L;
			{
				setPreferredSize(new Dimension(
					128, 128
				));
			}
			public void paint(Graphics g)
			{
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		final Timer timer = new Timer(aCur.getDuration(0), new ActionListener()
		{
			long cursorFrame = 0;
			public void actionPerformed(ActionEvent e)
			{
				panel.setCursor(aCur.getFrame((int)(cursorFrame++ % aCur.getFrameLength())));
			}
		});
		timer.start();
		frame.add(panel);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		
		while (frame.isVisible())
		{
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		timer.stop();
	}
	
	private static Image loadImage(RandomAccessFile input) throws IOException
	{
		long start = input.getFilePointer();
		
		// First short is little-endian negative of how many bytes are in frame header
		if (!bw){
			int headerSize = -reverseEndian(input.readShort());
			out("header size: " + headerSize);
			input.seek(start + headerSize);
		}else{
			out("header size? " + -reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out("img size? " + reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			out(reverseEndian(input.readShort()));
			System.out.flush();
		}
//		out("header size? " + -reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
//		out(reverseEndian(input.readShort()));
		
		out("Skipped block size (bytes): " + (input.getFilePointer() - start));
		
		BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		WritableRaster raster = img.getRaster();
		
		int h = raster.getHeight();
		int w = raster.getWidth();
		
		for (int y = 0; y < h; ++y)
			for (int x = 0; x < w; ++x)
				raster.setPixel(x, h - 1 - y, readColor(input));
		
		return img;
	}
	
	private static boolean bw = false;
	
	private static int[] readColor(DataInput input) throws IOException
	{
		Color c = bw ? readMono(input) : readRGBQuadLittleEndian(input);
		return new int[]{c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()};
	}
	
	private static Color readRGBQuadLittleEndian(DataInput input) throws IOException
	{
		int b = input.readByte() & 0xff;
		int g = input.readByte() & 0xff;
		int r = input.readByte() & 0xff;
		int a = input.readByte() & 0xff;
		return new Color(r, g, b, a);
	}
	
	private static Color readMono(DataInput input) throws IOException
	{
		int m = input.readByte();
		return m == 0 ? Color.WHITE : Color.BLACK;
	}
	
	private static boolean seekTo(RandomAccessFile input, int seq) throws IOException
	{
		return seekTo(input, 0, seq);
	}
	
	private static boolean seekTo(RandomAccessFile input, long start, int seq) throws IOException
	{
		input.seek(start);
		
		int magic = seq;
		int fourBytes = 0;
		
		do
		{
			fourBytes = input.readInt();
			
			if (fourBytes == magic) return true;
			
			input.seek(input.getFilePointer() - 3);
		}
		while ((fourBytes != magic) && (input.getFilePointer() <= (input.length() - 4)));
		
		//throw new IOException("Expected " + seq);
		return false;
	}
	
//	private static void seekTo(RandomAccessFile input, String seq) throws IOException
//	{
//		seekTo(input, 0, seq);
//	}
	
//	private static void seekTo(RandomAccessFile input, long start, String seq) throws IOException
//	{
//		input.seek(start);
//		
//		int magic = getMagicNumber(seq);
//		int fourBytes = 0;
//		
//		do
//		{
//			fourBytes = input.readInt();
//			
//			if (fourBytes == magic) return;
//			
//			input.seek(input.getFilePointer() - 3);
//		}
//		while (fourBytes != magic);
//		
//		throw new IOException("Expected " + seq);
//	}
	
	private static String toBinaryString(int word)
	{
		StringBuilder out = new StringBuilder();
		
		for (int b = 31; b >= 0; --b)
		{
			out.append(((word >> b) & 1) != 0 ? "1" : "0");
		}
		
		return out.toString();
	}
	
	private static int reverseEndian(int word)
	{
		byte b0 = (byte)((word >> 24) & 0xff);
		byte b1 = (byte)((word >> 16) & 0xff);
		byte b2 = (byte)((word >> 8) & 0xff);
		byte b3 = (byte)(word & 0xff);
		
		return (b3 << 24) | (b2 << 16) | (b1 << 8) | (b0);
	}
	
	private static short reverseEndian(short word)
	{
		byte b0 = (byte)((word >> 8) & 0xff);
		byte b1 = (byte)(word & 0xff);
		
		return (short)((b1 << 8) | (b0));
	}
	
//	private static int getMagicNumber(String chars)
//	{
//		return (chars.charAt(0) << 24)
//			 | (chars.charAt(1) << 16)
//			 | (chars.charAt(2) << 8)
//			 | chars.charAt(3);
//	}
	
	private static void out(Object o)
	{
		if (dbg)
			System.out.println(o.toString());
		
		if (append)
			buffer.append(o.toString());
	}
	
	private static void out(short s)
	{
		if (dbg)
			System.out.println(Short.toString(s));
		
		if (append)
			buffer.append(Short.toString(s));
	}
	
	static StringBuffer buffer = new StringBuffer();
	static boolean dbg = true;
	static boolean append = false;
	
	private static class CursorFrame
	{
		public Cursor cursor;
		public int duration;
		
		public CursorFrame(Cursor cursor, int duration)
		{
			this.cursor = cursor;
			this.duration = duration;
		}
	}
}
