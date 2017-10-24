import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class AngleCalc {

	static int precision = 16;
	
	public static void main(String[] args){
		
		final Map<Integer, String> angleNames = new HashMap<Integer, String>();
		
		angleNames.put(0,  "E");
		angleNames.put(1,  "ENE");
		angleNames.put(2,  "NE");
		angleNames.put(3,  "NNE");
		angleNames.put(4,  "N");
		angleNames.put(5,  "NNW");
		angleNames.put(6,  "NW");
		angleNames.put(7,  "WNW");
		angleNames.put(8,  "W");
		angleNames.put(9,  "WSW");
		angleNames.put(10, "SW");
		angleNames.put(11, "SSW");
		angleNames.put(12, "S");
		angleNames.put(13, "SSE");
		angleNames.put(14, "SE");
		angleNames.put(15, "ESE");

		final JFrame frame = new JFrame("angle calc");
		final Point p1 = new Point(-1, -1), p2 = new Point(-1, -1);

		final DecimalFormat format = new DecimalFormat("0.000");
		
		final JPanel inputPanel = new JPanel(){
			private static final long serialVersionUID = 1L;
			public void paint(Graphics g)
			{
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				
				g.setColor(Color.LIGHT_GRAY);
				g.drawOval((getWidth()/2)-200, (getHeight()/2)-200, 400, 400);
				g.drawLine((getWidth()/2), (getHeight()/2), (getWidth()/2)-200, (getHeight()/2));
				g.drawLine((getWidth()/2), (getHeight()/2), (getWidth()/2)+200, (getHeight()/2));
				g.drawLine((getWidth()/2), (getHeight()/2), (getWidth()/2), (getHeight()/2)-200);
				g.drawLine((getWidth()/2), (getHeight()/2), (getWidth()/2), (getHeight()/2)+200);

				g.drawLine((getWidth()/2), (getHeight()/2), (getWidth()/2)-(int)(Math.sqrt(2)*100), (getHeight()/2)-(int)(Math.sqrt(2)*100));
				g.drawLine((getWidth()/2), (getHeight()/2), (getWidth()/2)-(int)(Math.sqrt(2)*100), (getHeight()/2)+(int)(Math.sqrt(2)*100));
				g.drawLine((getWidth()/2), (getHeight()/2), (getWidth()/2)+(int)(Math.sqrt(2)*100), (getHeight()/2)-(int)(Math.sqrt(2)*100));
				g.drawLine((getWidth()/2), (getHeight()/2), (getWidth()/2)+(int)(Math.sqrt(2)*100), (getHeight()/2)+(int)(Math.sqrt(2)*100));
				
				g.setColor(getForeground());
				
				if (p1.x == -1) return;
				
				g.fillOval(p1.x-2, p1.y-2, 4, 4);
				
				if (p2.x == -1){
					frame.setTitle("");
					return;
				}

				g.fillOval(p2.x-2, p2.y-2, 4, 4);
				g.drawLine(p1.x, p1.y, p2.x, p2.y);
				
				int xDiff = p2.x - p1.x;
				int yDiff = p2.y - p1.y;
				double angle = Math.atan2(-yDiff, xDiff);
				
				if (angle < 0) angle = (2 * Math.PI) + angle;

				// Get absolute steps (steps from East) in the integer range [0, precision)
				int steps = (int)Math.round(angle / (Math.PI * (2.0 / precision)));
				steps %= precision;                // normalize steps
				if (steps < 0) steps += precision; // normalize steps

				// Get the rounded angle from the step value in the range [0, 2pi)
				double snappedAngle = steps * Math.PI / (precision / 2.0);
				
				// Get coordinates of point at end of rounded angle !!! remember y is inverted !!!
				int x3 = (int)(200.0 * Math.cos(snappedAngle));
				int y3 = -(int)(200.0 * Math.sin(snappedAngle));
				
				g.setColor(Color.RED);
				g.drawLine(p1.x, p1.y, p1.x + x3, p1.y + y3);
				
				g.setColor(new Color(255, 0, 0, 63));
				g.fillArc(p1.x - 200, p1.y - 200, 400, 400,
						(int) ((snappedAngle - (Math.PI / precision)) * 180.0 / Math.PI),
						(int) ((Math.PI / (precision / 2.0)) * 180.0 / Math.PI));
			}
		};
		
		inputPanel.setBackground(Color.WHITE);
		
		inputPanel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (p1.x == -1){
					p1.x = e.getX();
					p1.y = e.getY();
				}else{
					p1.x = p1.y = p2.x = p2.y = -1;
					p1.x = inputPanel.getWidth()/2;
					p1.y = inputPanel.getHeight()/2;
				}
				
				inputPanel.repaint();
			}
			
			public void mouseExited(MouseEvent e){
				p2.x = p2.y = -1;
				inputPanel.repaint();
			}
			
//			public void mousePressed(MouseEvent e){
//				if (p1.x != -1){
//					p2.x = e.getX();
//					p2.y = e.getY();
//					
//					int xDiff = p2.x - p1.x;
//					int yDiff = p2.y - p1.y;
//					double angle = Math.atan2(-yDiff, xDiff);
//					
//					if (angle < 0) angle = (2 * Math.PI) + angle;
//					
//					int steps = (int)Math.round(angle / (Math.PI * (2.0 / precision)));
//					steps %= precision;
//					if (steps < 0) steps += precision;
//					
//					double snappedAngle = steps * Math.PI / (precision / 2.0);
//					
//					frame.setTitle(
//							 format.format(angle/Math.PI) + " pi  "
//							+ steps + "  "
//							+ format.format(snappedAngle/Math.PI) + " pi"
//							+ "  " + angleNames.get(format.format(snappedAngle/Math.PI)));
//				}
//				inputPanel.repaint();
//			}
			
		});
		
		inputPanel.addMouseMotionListener(new MouseMotionAdapter(){
//			public void mouseDragged(MouseEvent e){
			public void mouseMoved(MouseEvent e){
				if (p1.x != -1){
					p2.x = e.getX();
					p2.y = e.getY();
					
					int xDiff = p2.x - p1.x;
					int yDiff = p2.y - p1.y;
					double angle = Math.atan2(-yDiff, xDiff);
					
					if (angle < 0) angle = (2 * Math.PI) + angle;

					// Get absolute steps (steps from East) in the integer range [0, precision)
					int steps = (int)Math.round(angle / (Math.PI * (2.0 / precision)));
					steps %= precision;
					if (steps < 0) steps += precision;

					// Get the rounded angle from the step value in the range [0, 2pi)
					double snappedAngle = steps * Math.PI / (precision / 2.0);
					
					frame.setTitle(
							 format.format(angle/Math.PI) + " pi  "
							+ steps + "  "
							+ format.format(snappedAngle/Math.PI) + " pi"
							+ "  " + angleNames.get(steps)
							+ " (" + (p2.x-p1.x) + ", " + (p2.y-p1.y) + ")");
				}
				inputPanel.repaint();
			}
		});

		frame.getContentPane().add(inputPanel);
		
		frame.setSize(500, 500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		p1.x = inputPanel.getWidth()/2;
		p1.y = inputPanel.getHeight()/2;
		
		final JFrame otherFrame = new JFrame();
		final JTextField aBox = new JTextField();
		final JTextField pBox = new JTextField(precision + "");
		
		otherFrame.add(aBox);
		otherFrame.add(pBox);
		
		otherFrame.setSize(100, 50);
		
		ActionListener a = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				precision = Integer.parseInt(pBox.getText());
				
				// Convert radian angle in the range (-inf, inf) to the range [0, 2pi)
				double angle = Double.parseDouble(aBox.getText());
				angle %= (2*Math.PI);
				if (angle < 0) angle+= (2*Math.PI);
				
				// Get coordinates (!!!remember that y is inverted!!!)
				
				// ???If y is inverted, maybe angles should be reversed as well???
				// ???so that angle=0 is still East but value increases positively
				//             clockwise instead of counter-clockwise???
				p2.x = (int)(200.0*Math.cos(angle));
				p2.y = -(int)(200.0*Math.sin(angle));
				otherFrame.setTitle(p2.x + ", " + p2.y);
				
				p2.x += inputPanel.getWidth()/2;
				p2.y += inputPanel.getHeight()/2;
				inputPanel.repaint();
				

				// Get absolute steps (steps from East) in the integer range [0, precision)
				int steps = (int)Math.round(angle / (Math.PI * (2.0 / precision)));
				steps %= precision;
				if (steps < 0) steps += precision;
				
				// Get the rounded angle from the step value in the range [0, 2pi)
				double snappedAngle = steps * Math.PI / (precision / 2.0);
				
				frame.setTitle(
						 format.format(angle/Math.PI) + " pi  "
						+ steps + "  "
						+ format.format(snappedAngle/Math.PI) + " pi"
						+ "  " + angleNames.get(steps)
						+ " (" + (p2.x-p1.x) + ", " + (p2.y-p1.y) + ")");
			}
		};
		
		aBox.addActionListener(a);
		pBox.addActionListener(a);
		otherFrame.setLocation(500, 50);
		otherFrame.setVisible(true);
	}
	
}
