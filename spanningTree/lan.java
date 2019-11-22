package spanningTree;

import java.util.ArrayList;

/*
 * LAn class for implementing a lan network
 * Author: Rohan Shukla
 */
public class lan 
{
	public char id;
	public int DP;
	public ArrayList<Integer> adj_bridges = new ArrayList<Integer>();
	public ArrayList<Integer> hosts = new ArrayList<Integer>();
	public lan()
	{
		id = '\0';
		DP = -1;
	}
}
