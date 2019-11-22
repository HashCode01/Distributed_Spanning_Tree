package spanningTree;

import java.util.ArrayList;

/*
 * Bridge class to implement each bridge
 * Author: Rohan Shukla
 */

public class bridge 
{
	public int id;
	public int root;
	public int root_distance;
	public ArrayList<Character> adj_lans = new ArrayList<Character>();
	public ArrayList<FWD_table> forwarding_table = new ArrayList<FWD_table>();
	public Pair<Character,Integer> RP = new Pair<Character,Integer>();
	
	public bridge()
	{
		id = -1;
		root = id;
		RP = new Pair<Character, Integer>('\0',-1);
		root_distance = -1;
	}
}
