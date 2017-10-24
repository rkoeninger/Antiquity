import java.io.*;
import java.util.*;

public class Floyd{
	public static void main(String[] args){
		int[][] costData = loadData("data.txt");
		int[][] pathData = initPathData(costData.length);
		System.out.println("Initial Cost Matrix:");
		print(costData);
		System.out.println();
		computeCosts(costData, pathData);
		System.out.println("Final Cost Matrix:");
		print(costData);
		System.out.println();
		System.out.println("Final Path Matrix:");
		print(pathData);
		System.out.println();
		System.out.println("Source\tDest\tCost\tPath");
		for (int r = 0; r < costData.length; ++r){
			for (int c = 0; c < costData.length; ++c){
				if (r != c){
					System.out.print(r);
					System.out.print(" ->\t");
					System.out.print(c);
					System.out.print("\t");
					System.out.print(costData[r][c]);
					System.out.print("\t");
					System.out.println(
					Arrays.toString(findPath(pathData, r, c)));
				}
			}
		}
		System.exit(0);
	}
	private static int[][] loadData(String filename){
		try{
			BufferedReader fileIn = new BufferedReader(
			new FileReader(filename));
			int nodeCount = Integer.parseInt(fileIn.readLine().trim());
			int[][] costData = new int[nodeCount][nodeCount];
			for (int row = 0; row < nodeCount; ++row){
				String line = fileIn.readLine();
				StringTokenizer lineTokens = new StringTokenizer(line);
				for (int col = 0; col < nodeCount; ++col){
					costData[row][col] = Integer.parseInt(
					lineTokens.nextToken());
				}
			}
			return costData;
		}catch (Exception exc){
			System.err.println("File not formatted properly - " +
			exc.getClass().getName());
		}
		return null;
	}
	private static void computeCosts(int[][] costData, int[][] pathData){
		for (int k = 0; k < costData.length; ++k){
			for (int row = 0; row < costData.length; ++row){
				if (row != k){
					for (int col = 0; col < costData.length; ++col){
						int newValue = costData[row][k] + costData[k][col];
						if ((col != k) & (newValue < costData[row][col])){
							costData[row][col] = newValue;
							pathData[row][col] = k;
						}
					}
				}
			}
		}
	}
	private static int[] findPath(int[][] pathData, int source, int dest){
		Vector<Integer> path = new Vector<Integer>();
		int currentNode = source;
		path.add(currentNode);
		while (currentNode != dest){
			currentNode = pathData[currentNode][dest];
			path.add(currentNode);
		}
		int[] pathArray = new int[path.size()];
		for (int x = 0; x < pathArray.length; ++x)
			pathArray[x] = path.get(x).intValue();
		return pathArray;
	}
	private static int[][] initPathData(int nodeCount){
		int[][] pathData = new int[nodeCount][nodeCount];
		for (int row = 0; row < nodeCount; ++row){
			for (int col = 0; col < nodeCount; ++col){
				pathData[row][col] = col;
			}
		}
		return pathData;
	}
	private static void print(int[][] array){
		for (int r = 0; r < array.length; ++r){
			for (int c = 0; c < array.length; ++c){
				System.out.print(array[r][c]);
				if (c < array.length - 1)
					System.out.print("\t");
			}
			System.out.println();
		}
	}
}
