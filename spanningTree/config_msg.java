package spanningTree;

import spanningTree.bridge;
/*
 * Configuration message declaration classes
 * Author: Rohan Shukla
 */
public class config_msg 
{
	public int root;
	public int dist;
	public bridge source = new bridge();
	public int destination;
	public char lan;
	public config_msg()
	{
		root = -1;
		dist = -1;
		destination = -1;
		lan = '\0';
	}
}
