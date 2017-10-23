
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class MouseTest {

	static JPanel panel;
	
	static PaintFunction painter;
	
	static MouseHandler currentHandler;

	//red mode
	static LinkedList<Rectangle> rects = new LinkedList<Rectangle>();
	static Rectangle selectRect;
	
	//blue mode
	static Rectangle unit;
	static boolean unitSelected = false;
	static int mouseX, mouseY;
	
	//green mode
	static Rectangle[] ovals;
	static double scale;
	static Point down;
	
	private static interface PaintFunction{
		public void paint(Graphics g);
	}
	
	public static void main(String[] args){
		
		//final LinkedList<MouseHandler> handlers = new LinkedList<MouseHandler>();
		
		final JFrame frame = new JFrame("Mouse Hander Test");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				
				for (int y = 0; y < getHeight()/16; ++y){
					for (int x = 0; x < getWidth()/16; ++ x){
						g.setColor((((x >> 1) ^ (y >> 1)) & 1) == 1 ? Color.black : Color.WHITE);
						g.fillRect(x * 16, y * 16, 16, 16);
					}
				}
				
				if (painter != null) painter.paint(g);
			}
		};
		panel.setPreferredSize(new Dimension(512,512));
		
		frame.getContentPane().add(panel);
		frame.pack();
		
		
		JButton button1 = new JButton("1");
		JButton button2 = new JButton("2");
		JButton button3 = new JButton("3");
		
		JFrame buttonPanel = new JFrame("buttons");
		buttonPanel.setLocation(600, 100);
		buttonPanel.getContentPane().setLayout(new GridLayout(3, 0));
		buttonPanel.getContentPane().add(button1);
		buttonPanel.getContentPane().add(button2);
		buttonPanel.getContentPane().add(button3);
		buttonPanel.pack();
		buttonPanel.setVisible(true);
		
		button1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				MouseHandler handler = new RedMouseHandler();
				
				panel.addMouseListener(handler);
				panel.addMouseMotionListener(handler);
				panel.addMouseWheelListener(handler);

				if (currentHandler != null){
					panel.removeMouseListener(currentHandler);
					panel.removeMouseMotionListener(currentHandler);
					panel.removeMouseWheelListener(currentHandler);
					
				}currentHandler = handler;

				rects.clear();
				selectRect = null;
				
				painter = new PaintFunction(){
					
					public void paint(Graphics g){
					
						if (!rects.isEmpty()){
							g.setColor(Color.RED.darker());
							for (Rectangle r : rects){
								int x = Math.min(r.x, r.x + r.width);
								int y = Math.min(r.y, r.y + r.height);
								int w = Math.abs(r.width);
								int h = Math.abs(r.height);
								g.fillRect(x, y, w, h);
							}
						}
						
						if (selectRect != null){
							int x = Math.min(selectRect.x, selectRect.x + selectRect.width);
							int y = Math.min(selectRect.y, selectRect.y + selectRect.height);
							int w = Math.abs(selectRect.width);
							int h = Math.abs(selectRect.height);
							g.setColor(new Color(255,0,33,127));
							g.fillRect(x, y, w, h);
							g.setColor(new Color(255,0,33,255));
							g.drawRect(x, y, w, h);
						}
				
					}};
					
				panel.repaint();
			}
		});

		button2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				MouseHandler handler = new BlueMouseHandler();
				
				panel.addMouseListener(handler);
				panel.addMouseMotionListener(handler);
				panel.addMouseWheelListener(handler);

				unit = new Rectangle(64, 64, 64, 64);
				unitSelected = false;
				mouseX = mouseY = -100;
				
				if (currentHandler != null){
					panel.removeMouseListener(currentHandler);
					panel.removeMouseMotionListener(currentHandler);
					panel.removeMouseWheelListener(currentHandler);
					
				}currentHandler = handler;
				

				painter = new PaintFunction(){
					
					public void paint(Graphics g){
					
						if (unitSelected){
							g.setColor(Color.CYAN);
						}else{
							g.setColor(Color.BLUE);
						}
						
						
						g.fillRect(unit.x, unit.y, unit.width, unit.height);
						
						g.setColor(new Color(128, 128, 255, 90));
						g.fillRect((mouseX/64)*64,(mouseY/64)*64,64,64);
						
						if (unitSelected){
							int nwX = unit.x - 5;
							int nwY = unit.y - 5;
							int seX = unit.x + unit.width + 5;
							int seY = unit.y + unit.height + 5;
							g.setColor(Color.BLACK);
							g.drawLine(nwX, nwY, nwX + 25, nwY);
							g.drawLine(nwX, nwY, nwX, nwY + 25);
							g.drawLine(seX, seY, seX - 25, seY);
							g.drawLine(seX, seY, seX, seY - 25);
						}
						
					}};

					panel.repaint();
			}
		});

		button3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				MouseHandler handler = new GreenMouseHandler();
				
				panel.addMouseListener(handler);
				panel.addMouseMotionListener(handler);
				panel.addMouseWheelListener(handler);

				if (currentHandler != null){
					panel.removeMouseListener(currentHandler);
					panel.removeMouseMotionListener(currentHandler);
					panel.removeMouseWheelListener(currentHandler);

				}					currentHandler = handler;
				
				ovals = new Rectangle[]{new Rectangle(60, 56, 123, 90),
										new Rectangle(356, 78, 45, 100),
										new Rectangle(234, 280, 167, 213)};
				scale = 1.0;
				down = null;
				
				painter = new PaintFunction(){
					
					public void paint(Graphics g){
					
						int centerX = panel.getWidth() / 2;
						int centerY = panel.getHeight() / 2;
//						int centerX = mouseX;
//						int centerY = mouseY;
						
						for (Rectangle oval : ovals){
							double centerD = distance(oval.x, oval.y, centerX, centerY);
							double centerA = angle(oval.x, oval.y, centerX, centerY);
							
							centerD *= scale;
							
							int dx = (int) Math.abs(centerD * Math.cos(centerA));
							int dy = (int) Math.abs(centerD * Math.sin(centerA));
							
							if (oval.x < centerX) dx = -dx;
							if (oval.y < centerY) dy = -dy;
							
							int paintX = centerX + dx;// + (mouseX - panel.getWidth() / 2);
							int paintY = centerY + dy;// + (mouseY - panel.getHeight() / 2);
							int paintW = (int)(oval.width * scale);
							int paintH = (int)(oval.height * scale);
							
							//oval.x += ();
							//oval.y += ();
//							oval.height = paintH;
//							oval.width = paintW;
							
//							int paintX = oval.x;
//							int paintY = oval.y;
//							int paintW = oval.width;
//							int paintH = oval.height;
							
							g.setColor(Color.GREEN);
							g.fillOval(paintX, paintY, paintW, paintH);
							g.setColor(Color.green.darker());
							g.fillOval(paintX, paintY, paintW, paintH);
						}
						
					}
					private double distance(int x1, int y1, int x2, int y2){
						return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
					}
					private double angle(int x1, int y1, int x2, int y2){
						return Math.atan2(y2 - y1, x2 - x1);
					}
				};

				panel.repaint();
			}
		});
		
		Robot rob = null;
		try {
			rob = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
		rob.mouseMove(buttonPanel.getLocation().x+10, buttonPanel.getLocation().y+30);
		rob.mousePress(InputEvent.BUTTON1_MASK);
		rob.mouseRelease(InputEvent.BUTTON1_MASK);
		
		
	}
	
	private static interface MouseHandler extends MouseListener, MouseMotionListener, MouseWheelListener{}
		
	private static class RedMouseHandler implements MouseHandler{

		public void mouseWheelMoved(MouseWheelEvent arg0) {

		}

		public void mouseDragged(MouseEvent arg0) {
			//System.out.println("dragged");
			selectRect.width  = arg0.getX() - selectRect.x;
			selectRect.height = arg0.getY() - selectRect.y;
			panel.repaint();
		}

		public void mouseMoved(MouseEvent arg0) {

		}

		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3){
				rects.clear();
				panel.repaint();
			}
			
		}

		public void mouseEntered(MouseEvent e) {

		}

		public void mouseExited(MouseEvent e) {

		}

		public void mousePressed(MouseEvent e) {
			//System.out.println("pressed");
			//rects.clear();
			selectRect = new Rectangle(e.getX(), e.getY(), 0, 0);
			panel.repaint();
		}

		public void mouseReleased(MouseEvent e) {
			//System.out.println("released");
			rects.add(selectRect);
			selectRect = null;
			panel.repaint();
		}
		
	}
	
	private static class BlueMouseHandler implements MouseHandler{

		public void mouseWheelMoved(MouseWheelEvent arg0) {

		}

		public void mouseDragged(MouseEvent arg0) {

		}

		public void mouseMoved(MouseEvent arg0) {
			mouseX = arg0.getX();
			mouseY = arg0.getY();
			panel.repaint();
		}

		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1){
				final int x = e.getX();
				final int y = e.getY();
			
				unitSelected = (!unitSelected) &&
						(x >= unit.x && x <= (unit.x + unit.width) &&
						y >= unit.y && y <= (unit.y + unit.height));

			}else if (e.getButton() == MouseEvent.BUTTON3){
				if (unitSelected){
					unit.x=(e.getX()/64)*64;
					unit.y=(e.getY()/64)*64;
				}
			}
			
			panel.repaint();

		}

		public void mouseEntered(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			panel.repaint();
		}

		public void mouseExited(MouseEvent e) {
			mouseX = mouseY = -100;
			panel.repaint();
		}

		public void mousePressed(MouseEvent e) {

		}

		public void mouseReleased(MouseEvent e) {

		}
		
	}
	
	private static class GreenMouseHandler implements MouseHandler{

		public void mouseWheelMoved(MouseWheelEvent arg0) {
			if (arg0.getWheelRotation() > 0){
				scale *= 1.1;
				mouseX = arg0.getX();
				mouseY = arg0.getY();
				panel.repaint();
			}else if (arg0.getWheelRotation() < 0){
				scale *= 1/1.1;
				mouseX = arg0.getX();
				mouseY = arg0.getY();
				panel.repaint();
			}
		}

		public void mouseDragged(MouseEvent arg0) {
			double d = Math.sqrt(Math.pow(down.x - arg0.getX(), 2) + Math.pow(down.y - arg0.getY(), 2));
			scale = (d+100) / 100;
			panel.repaint();
		}

		public void mouseMoved(MouseEvent arg0) {

		}

		public void mouseClicked(MouseEvent e) {

		}

		public void mouseEntered(MouseEvent e) {

		}

		public void mouseExited(MouseEvent e) {

		}

		public void mousePressed(MouseEvent e) {
			down = new Point(e.getX(), e.getY());
		}

		public void mouseReleased(MouseEvent e) {
			down = null;
		}
		
	}
}
