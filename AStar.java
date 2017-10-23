
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.*;

public class AStar {

	public static void main(String[] args){

		try {
			int[][] grid = loadTerrian(new File("terrian.bmp"));
			
//			for (int y = 0; y < grid.length; ++y){
//				for (int x = 0; x < grid[y].length; ++x){
//					if (grid[y][x] < 0){
//						System.out.print("* ");
//					} else if ((y == startY) && (x == startX)){
//						System.out.print("S ");
//						
//					} else if ((y == destY) && (x == destX)){
//						System.out.print("D ");
//					
//					} else {
//						System.out.print("  ");
//						
//					}
//				}
//				System.out.println();
//			}
			
			long timeN = System.nanoTime();
			long timeM = System.currentTimeMillis();
			LinkedList<Point> path = retracePath(findPath(grid));
			System.out.println((System.nanoTime() - timeN) + " nanos");
			System.out.println((System.currentTimeMillis() - timeM) + " millis");
//			for (Point p : path){
//				System.out.println(p);
//			}
			System.out.println(path.size() + " steps");
			
//			for (int y = 0; y < grid.length; ++y){
//				for (int x = 0; x < grid[y].length; ++x){
//					if (grid[y][x] < 0){
//						System.out.print("* ");
//					} else if ((y == startY) && (x == startX)){
//						System.out.print("S ");
//					} else if ((y == destY) && (x == destX)){
//						System.out.print("D ");
//					} else if (path.contains(new Point(x, y))){
//						System.out.print("+ ");
//					} else {
//						System.out.print("  ");			
//					}
//				}
//				System.out.println();
//			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static LinkedList<PointPair> findPath(int[][] grid){
		LinkedList<PointAndValue> openSet = new LinkedList<PointAndValue>();
		LinkedList<PointAndValue> closedSet = new LinkedList<PointAndValue>();
		LinkedList<PointPair> path = new LinkedList<PointPair>();
		
		int startToGoalEstimate = Math.abs(destX - startX) + Math.abs(destY - startY);
		openSet.add(new PointAndValue(startX, startY, startToGoalEstimate, 0, startToGoalEstimate));
		
		while (!openSet.isEmpty()){
			
			PointAndValue lowestF = findLowestF(openSet);
			
			if ((lowestF.x == destX) && (lowestF.y == destY)){
				System.out.println("Goal found");
				return path;
			}
			
			openSet.remove(lowestF);
			closedSet.add(lowestF);
			
			int x = lowestF.x, y = lowestF.y;
			
			inspectNeighbor(lowestF, new PointAndValue(x+0,y+1,0,0,0), openSet, closedSet, grid, path);
			inspectNeighbor(lowestF, new PointAndValue(x+0,y-1,0,0,0), openSet, closedSet, grid, path);
			inspectNeighbor(lowestF, new PointAndValue(x+1,y+0,0,0,0), openSet, closedSet, grid, path);
			inspectNeighbor(lowestF, new PointAndValue(x-1,y+0,0,0,0), openSet, closedSet, grid, path);
			inspectNeighbor(lowestF, new PointAndValue(x+1,y+1,0,0,0), openSet, closedSet, grid, path);
			inspectNeighbor(lowestF, new PointAndValue(x+1,y-1,0,0,0), openSet, closedSet, grid, path);
			inspectNeighbor(lowestF, new PointAndValue(x-1,y+1,0,0,0), openSet, closedSet, grid, path);
			inspectNeighbor(lowestF, new PointAndValue(x-1,y-1,0,0,0), openSet, closedSet, grid, path);
			
		}
		
		throw new Error("path untraceable");
	}
	
	private static void inspectNeighbor(PointAndValue current, PointAndValue p, LinkedList<PointAndValue> openSet, LinkedList<PointAndValue> closedSet, int[][] grid, LinkedList<PointPair> path){
		if ((p.x < 0) || (p.x >= grid[0].length) || (p.y < 0) || (p.y >= grid.length)){
			return;
		}
		if (grid[p.y][p.x] <= 0){
			return;
		}
		if (closedSet.contains(p)){
			return;
		}
		
		int tentative_g_score = current.gvalue + (int)Math.sqrt(Math.pow(p.y-current.y,2) + Math.pow(p.x-current.x,2));
		boolean tentative_is_better = false;
		
		if (!openSet.contains(p)){
			openSet.add(p);
			tentative_is_better = true;
		}else if (tentative_g_score < p.gvalue){
			tentative_is_better = true;
		}
		
		if (tentative_is_better){
//			System.out.println(p + "\tcame from\t" + current);
			path.add(new PointPair(p, current));
			
			p.gvalue = tentative_g_score;
			p.hvalue = Math.abs(destX - p.x) + Math.abs(destY - p.y);
			p.fvalue = p.gvalue + p.hvalue;
			
			
		}
		
	}
	
	private static class PointPair{
		public Point from, to;
		public PointPair(Point to, Point from){
			this.from = from;
			this.to = to;
		}
	}
	
	private static LinkedList<Point> retracePath(LinkedList<PointPair> pairList){
		LinkedList<Point> path = new LinkedList<Point>();
		if (!pairList.getLast().to.equals(new Point(destX, destY))){
			path.addFirst(findFrom(pairList, new Point(destX, destY)).to);
		}else{
			path.addFirst(pairList.getLast().to);
		}
		while (! path.getFirst().equals(new Point(startX, startY))){
			PointPair pp = findFrom(pairList, path.getFirst());
			path.addFirst(pp.from);
		}
		return path;
	}
		
	private static PointPair findFrom(LinkedList<PointPair> pairList, Point p){
		for (PointPair pp : pairList){
			if (pp.to.equals(p)) return pp;
		}
		return null;
	}
	
	private static PointAndValue findLowestF(LinkedList<PointAndValue> list){
		PointAndValue lowest = list.get(0);
		for (PointAndValue next : list){
			if (next.fvalue < lowest.fvalue) lowest = next;
		}
		return lowest;
	}
	
	@SuppressWarnings("serial")
	private static class PointAndValue extends Point{
		public int fvalue, gvalue, hvalue;
		public PointAndValue(int x, int y, int fvalue, int gvalue, int hvalue){
			super(x,y);
			this.fvalue = fvalue;
			this.gvalue = gvalue;
			this.hvalue = hvalue;
		}
	}
	
	static int startX, startY;
	static int destX, destY;
	
	private static int[][] loadTerrian(File file) throws IOException{
		BufferedImage img = (BufferedImage) ImageIO.read(file);
		Raster raster = img.getRaster();
		int[][] terrian = new int[raster.getHeight()][raster.getWidth()];
		int[] pixel = new int[3];
		int[] black = new int[]{0,0,0};
//		int[] white = new int[]{255,255,255};
		int[] blue = new int[]{0,0,255};
		int[] yellow = new int[]{255,255,0};
		for (int y = 0; y < raster.getHeight(); ++y){
			for (int x = 0; x < raster.getWidth(); ++x){
				raster.getPixel(x, y, pixel);
				if (Arrays.equals(pixel, black)){
					terrian[y][x] = -1;
				}else{
					terrian[y][x] = 1;
					if (Arrays.equals(pixel, blue)){
						startX = x;
						startY = y;
					}else if (Arrays.equals(pixel, yellow)){
						destX = x;
						destY = y;
					}
				}
			}
		}
		return terrian;
	}
	
}
