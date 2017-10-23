import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class LineIntersection{
	static Point p1=null,p2=null,p3=null,p4=null;
	static Point2D intersect=null;
	public static void main(String[] args)throws IOException{
		final InputPanel panel = new InputPanel();
		final JLabel label = new JLabel("Select Point 1");
		
		panel.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent event){
				if (p1==null){
					p1=new Point(event.getX(),event.getY());
					label.setText("Select Point 2");
					panel.repaint();
				}else if (p2==null){
					p2=new Point(event.getX(),event.getY());
					label.setText("Select Point 3");
					panel.repaint();
				}else if (p3==null){
					p3=new Point(event.getX(),event.getY());
					label.setText("Select Point 4");
					panel.repaint();
				}else if (p4==null){
					p4=new Point(event.getX(),event.getY());
					double a=p1.getX();
					double b=p1.getY();
					double c=p2.getX();
					double d=p2.getY();
					double e=p3.getX();
					double f=p3.getY();
					double g=p4.getX();
					double h=p4.getY();
					double x =
					((a*(d-b)*(g-e))-(e*(c-a)*(h-f))+((f-b)*(c-a)*(g-e)))/
					(((d-b)*(g-e))-((h-f)*(c-a)));
					double y =
					(((d-b)/(c-a))*(x-a))+b;
					//((b*c_a*h_f)-(f*g-e*d_b)+((e-a)*h_f*d_b))/
					//((c_a*h_f)-(g_e*d_b));
                    intersect = new Point2D.Double(
                    Math.floor(x+0.5),Math.floor(y+0.5));
					label.setText("Click to Clear");
					panel.repaint();
				}else{
					p1=null;
					p2=null;
					p3=null;
					p4=null;
					label.setText("Select Point 1");
					panel.repaint();
				}
			}
		});

		JFrame frame = new JFrame("Line Intersection");
		frame.setLayout(new BorderLayout());
		frame.add(panel,BorderLayout.CENTER);
		frame.add(label,BorderLayout.SOUTH);
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	static class InputPanel extends JPanel{
		public void paint(Graphics g){
			g.setColor(Color.WHITE);
			g.fillRect(0,0,1000,1000);
			if (p1!=null){
				g.setColor(Color.BLACK);
                g.drawLine((int)p1.getX(),(int)p1.getY(),
                (int)p1.getX(),(int)p1.getY());
				g.drawString("p1=("+p1.getX()+","+p1.getY()+")",
				(int)p1.getX()+5,(int)p1.getY()-5);
			}
			if (p2!=null){
				g.setColor(Color.BLACK);
                g.drawLine((int)p2.getX(),(int)p2.getY(),
                (int)p2.getX(),(int)p2.getY());
				g.drawString("p2=("+p2.getX()+","+p2.getY()+")",
				(int)p2.getX()+5,(int)p2.getY()-5);
				g.setColor(Color.RED);
				g.drawLine((int)p1.getX(),(int)p1.getY(),
				(int)p2.getX(),(int)p2.getY());
			}
			if (p3!=null){
				g.setColor(Color.BLACK);
                g.drawLine((int)p3.getX(),(int)p3.getY(),
                (int)p3.getX(),(int)p3.getY());
				g.drawString("p3=("+p3.getX()+","+p3.getY()+")",
				(int)p3.getX()+5,(int)p3.getY()-5);
			}
			if (p4!=null){
				g.setColor(Color.BLACK);
                g.drawLine((int)p4.getX(),(int)p4.getY(),
                (int)p4.getX(),(int)p4.getY());
				g.drawString("p4=("+p4.getX()+","+p4.getY()+")",
				(int)p4.getX()+5,(int)p4.getY()-5);
				g.setColor(Color.RED);
				g.drawLine((int)p3.getX(),(int)p3.getY(),
				(int)p4.getX(),(int)p4.getY());
				g.setColor(Color.BLACK);
				g.drawLine((int)intersect.getX(),(int)intersect.getY(),
                (int)intersect.getX(),(int)intersect.getY());
				g.setColor(Color.BLACK);
				g.drawString("("+intersect.getX()+","+intersect.getY()+")",
				(int)intersect.getX()+5,(int)intersect.getY()-5);
			}
		}
	}
}