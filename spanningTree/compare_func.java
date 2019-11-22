package spanningTree;

import spanningTree.config_msg;

/*
 * Comparator function for comparing configuration messages 
 * Author: Rohan Shukla
 */
public class compare_func 
{
	public boolean functorMethod(final config_msg j1, final config_msg j2)
	{
		if (j1.destination < j2.destination)
		{
			return true;
		}
		else
		{
			return false;
		}
		//compare j1 and j2 and return true or false
	}
}
