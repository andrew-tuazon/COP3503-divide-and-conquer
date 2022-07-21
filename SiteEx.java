import java.util.*;
public class SiteEx
{
	// Comparable point class for sites
	public static class Point implements Comparable<Point>
	{
		// Time as x, distance as y
		int x, y;
		// Point constructor
		public Point(int d, int t)
		{
			x = d;
			y = t;
		}
		// Order in increasing times
		@Override
		public int compareTo(Point o)
		{
			return Integer.compare(x, o.x);
		}
	}
	// BIT class
	public static class Fenwick
	{
		// Store max value, as well as forward and reverse array
		int max;
		long[] arr;
		long[] revArr;
		// BIT constructor
		Fenwick(int n)
		{
			// 1 for inclusive bound 1 for 1-index
			max = n + 1 + 1;
			arr = new long[max];
			revArr = new long[max];
		}
		// Method to return sum of lower quadrant
		long sum(int index)
		{
			// 1-indexed
			index++;
			long ret = 0;
			while (index != 0)
			{
				// Add in the current bucket
				ret += arr[index];
				// Reduce by the lowest 1 bit
				index -= (index & -index);
			}
			return ret;
		}
		// Method to return sum of upper quadrant
		long revSum(int index)
		{
			// 1-indexed
			index++;
			long ret = 0;
			while (index != 0)
			{
				// Add in the current bucket
				ret += revArr[index];
				// Reduce by the lowest 1 bit
				index -= (index & -index);
			}
			return ret;
		}
		// Increment in the reverse array
		void revInc(int index, int value)
		{
			// 1-indexed
			index++;
			while (index < max)
			{
				// Update value at the index
				revArr[index] += value;
				// Increase by the lowest 1 bit
				index += (index & -index);
			}
		}
		// Increment in the forward array 
		void inc(int index, int value)
		{
			// 1-indexed
			index++;
			while (index < max)
			{
				// Update value at the index
				arr[index] += value;
				// Increase by the lowest 1 bit
				index += (index & -index);
			}
		}
	}
	public static void main(String[] args)
	{
		int n;
		int height = 0;
		long sweep[];
		long revSweep[];
		Scanner sc = new Scanner(System.in);
		ArrayList<Point> points = new ArrayList<Point>();
		// Read in number of sites
		n = sc.nextInt();
		// Read input
		for(int i = 0; i < n; i++)
		{
			int d = sc.nextInt();
			int t = sc.nextInt();
			//Update max distance
			if(d > height)
			{
				height = d;
			}
			points.add(new Point(t, d));
		}
		sc.close();
		// Sort by increasing times
		Collections.sort(points);
		// Create BIT
		Fenwick bit = new Fenwick(height);
		// Forward sweep array
		sweep = new long[n];
		int i = 0;
		// For all points store sum of lower quadrant and increment
		for(Point e : points)
		{
			sweep[i] = bit.sum(e.y);
			bit.inc(e.y, 1);
			i++;
		}
		// Reverse sweep array
		revSweep = new long[n];
		// Start from the end of the array
		i = n - 1;
		// Keep track of how many numbers we've seen
		int j = 0;
		// For all points store sum of upper quadrant and increment
		for(int k = points.size() - 1; k >= 0; k--)
		{
			revSweep[i] = j - bit.revSum(points.get(k).y);
			bit.revInc(points.get(k).y, 1);
			i--;
			j++;
		}
		// Initialize answer
		long answer = 0;
		// Loop through and multiply array sums
		for(i = 0; i < n; i++)
		{
			answer += sweep[i] * revSweep[i];
		}
		// Print the answer
		System.out.println(answer);
	}
}
