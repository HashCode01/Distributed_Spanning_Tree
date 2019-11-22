package spanningTree;

import java.io.IOException;
import java.util.*;

import spanningTree.bridge;
import spanningTree.config_msg;
import spanningTree.lan;
import spanningTree.netw_info;

/*
 * This is an Java based implementation of The Distributed Spanning Tree Algorithm
 * originally developed by Radia Perlman
 * Author: Rohan Shukla
 * CSE Scetion A
 * References : https://github.com/ani8897/Spanning-Tree-Protocol/blob/master/network.cpp
 */
public class ST 
{
	@SuppressWarnings("resource")
	public static void main(String args[]) throws IOException
	{
		int tr = 1;
		int n;
		n = Integer.parseInt(ConsoleInput.readToWhiteSpace(false));
		ArrayList<bridge> bridge_network = new ArrayList<bridge>();
		TreeSet<Character> lan_set = new TreeSet<Character>();

		// Initializing the bridges
		for (int i = 0; i <= n; i++)
		{
			bridge b = new bridge();
			String line;
			line = new Scanner(System.in).nextLine(); // getting line by line i/p
			if (i != 0)
			{
				for (int j = 0; j < line.length(); j++)
				{
					if (j == 1)
					{
						if (line.charAt(j + 1) != ':') // check for colon
						{
							b.id = 10 * ((int) line.charAt(j) - 48) + ((int) line.charAt(j + 1) - 48);
							b.root = b.id;
							j++;
						}
						else //set id
						{
							b.id = (int) line.charAt(j) - 48;
							b.root = b.id;
						}
					}
					if (j != 0 & j != 1 & j != 2)
					{
						if (line.charAt(j) != ' ' && line.charAt(j) != ':')
						{
							b.adj_lans.add(line.charAt(j)); // push for adjacent LANs
							lan_set.add(line.charAt(j)); // push to make a LANs set
						}
					}
				}
				Collections.sort(b.adj_lans);
				bridge_network.add(b); // make a bridge network
			}
		}
		
		System.out.print("-----------------------------Bridge Representation-------------------------------");
		System.out.print("\n");
			print_bridge_network(bridge_network); //printing bridge network

			System.out.print("-----------------------------LAN Representation-------------------------------");
			System.out.print("\n");

			ArrayList<lan> lan_network = new ArrayList<lan>();

			while (!lan_set.isEmpty()) //creating a LAN network
			{
				char c = lan_set.first();
				lan l = new lan();
				l.id = c;
				for (int i = 0; i < bridge_network.size();i++)
				{
					for (int j = 0; j < bridge_network.get(i).adj_lans.size(); j++)
					{
						if (bridge_network.get(i).adj_lans.get(j) == c)
						{
							l.adj_bridges.add(bridge_network.get(i).id);
						}
					}
				}
				lan_network.add(l); //pushing in LAN network
				lan_set.remove(lan_set.first()); //removing from LAN set
			}

			print_lan_network(lan_network); //print LAN network

			// Implementing spanning tree protocol

			LinkedList<config_msg> sender_queue = new LinkedList<config_msg>(); // making a sender_queue and recieved
			LinkedList<config_msg> reciever_queue = new LinkedList<config_msg>();
			ArrayList<netw_info> netw_info_queue = new ArrayList<netw_info>();
			int timestamp = 0;
			int initial = 1;

			for (int i = 0; i < bridge_network.size();i++) 
			{
				config_msg m = new config_msg();
				m.root = bridge_network.get(i).id;
				m.dist = 0;
				m.source = bridge_network.get(i);
				sender_queue.offer(m);
			}

			while (!sender_queue.isEmpty())
			{
				System.out.print("\n");
				System.out.print("time\t");
				System.out.print("Bridge\t");
				System.out.print("status\t");
				System.out.print("sent/recieved msg");
				System.out.print("\n");
				if (initial != 1)
				{
					while (!sender_queue.isEmpty())
					{
						sender_queue.poll();
					}
				}
				while (!reciever_queue.isEmpty())
				{
					config_msg m = reciever_queue.peek();
					config_msg to_be_published = UpdateConfig(m, bridge_network);
					if (to_be_published.root != -1)
					{
						sender_queue.offer(to_be_published);
						System.out.print("Compare and updated");
						System.out.print("\n");
					}
					netw_info t = new netw_info();
					t.time = timestamp;
					t.bridge = m.destination;
					t.status = 'r';
					t.m = m;
					if (tr == 1)
					{
						System.out.print(t.time);
						System.out.print("\tB");
						System.out.print(t.bridge);
						System.out.print("\t");
						System.out.print(t.status);
						System.out.print("\t(B");
						System.out.print(t.m.root);
						System.out.print(",");
						System.out.print(t.m.dist);
						System.out.print(",B");
						System.out.print(t.m.source.id);
						System.out.print(")\n");
					}
					netw_info_queue.add(t);
					reciever_queue.poll();
				}

				LinkedList<config_msg> temp = new LinkedList<config_msg>();
				while (!sender_queue.isEmpty())
				{
					config_msg m = sender_queue.peek();
					SortedSet<config_msg> reciever_queue_by_set = SendMessage(m, bridge_network, lan_network);
					sender_queue.poll();
					while (!reciever_queue_by_set.isEmpty())
					{
						reciever_queue.offer(reciever_queue_by_set.first());
						reciever_queue_by_set.remove(reciever_queue_by_set.first());
					}
					netw_info t = new netw_info();
					t.time = timestamp;
					t.bridge = m.source.id;
					t.status = 's';
					t.m = m;
					netw_info_queue.add(t);
					if (tr == 1)
					{
						System.out.print(t.time);
						System.out.print("\tB");
						System.out.print(t.bridge);
						System.out.print("\t");
						System.out.print(t.status);
						System.out.print("\t(B");
						System.out.print(t.m.root);
						System.out.print(",");
						System.out.print(t.m.dist);
						System.out.print(",B");
						System.out.print(t.m.source.id);
						System.out.print(")\n");
					}
					temp.offer(m);
				}
				while (!temp.isEmpty())
				{
							sender_queue.push(temp.getFirst());
							temp.pop();
				}

						timestamp++;
						initial = 0;

		}

					for (int i = 0; i < lan_network.size(); i++)
					{
						lan_network.get(i).DP = Collections.min((lan_network.get(i).adj_bridges));
					}

					System.out.print("\n");
					System.out.print("-----------------------------DP for LAN-------------------------------");
					System.out.print("\n");
					print_lan_network(lan_network);
					System.out.print("\n");
					for (int i = 0; i < bridge_network.size(); i++)
					{
						System.out.print("B");
						System.out.print(bridge_network.get(i).id);
						System.out.print("--->");
						for (int j = 0; j < bridge_network.get(i).adj_lans.size(); j++)
						{
							int flag = 0;
							char c = bridge_network.get(i).adj_lans.get(j);
							System.out.print(" ");
							System.out.print(c);
							if (c == bridge_network.get(i).RP.first)
							{
								System.out.print("(RP)");
								flag = 1;
							}
							for (int k = 0;k < lan_network.size();k++)
							{
								if (c == lan_network.get(k).id && bridge_network.get(i).id == lan_network.get(k).DP && flag == 0)
								{
										System.out.print("(DP)");
										flag = 1;
										break;
								}
							}
							if (flag == 0)
							{
								System.out.print("(NP)");
								if(bridge_network.get(i).adj_lans.contains(c))
								{
									bridge_network.get(i).adj_lans.remove(c);
									j--;
								}
							}
						}
						System.out.print("\n");
					}

					System.out.print("-----------------------------Updating LANs-------------------------------");
					System.out.print("\n");
					System.out.print("\n");

					for (int k = 0; k < lan_network.size();k++)
					{
						char c = lan_network.get(k).id;
						ArrayList<Integer> l = new ArrayList<Integer>();
						for (int i = 0; i < bridge_network.size();i++)
						{
							for (int j = 0; j < bridge_network.get(i).adj_lans.size(); j++)
							{
								if (bridge_network.get(i).adj_lans.get(j) == c)
								{
									l.add(bridge_network.get(i).id);
								}
							}
						}
						lan_network.get(k).adj_bridges = l;
					}
					System.out.print("-----------------------------Final Bridge Representation-------------------------------");
					System.out.print("\n");
					print_bridge_network(bridge_network);
				}

	private static SortedSet<config_msg> SendMessage(config_msg m, ArrayList<bridge> bridge_network, ArrayList<lan> lan_network) 
	{
		SortedSet<config_msg> messages = new TreeSet<config_msg>(new Comparator<config_msg>()
		{
			public int compare(final config_msg j1, final config_msg j2)
			{
				if (j1.destination < j2.destination)
				{
					return 1;
				}
				else
				{
					return 0;
				}
			}
		});
		int root = m.root;
		int d = m.dist;
		bridge source = m.source;
		for (int i = 0; i < bridge_network.size();i++) //in bridge n/w
		{
			if (source.id == bridge_network.get(i).id) //if
			{
				for (int j = 0; j < bridge_network.get(i).adj_lans.size(); j++)
				{
					for (int k = 0; k < lan_network.size(); k++)
					{
						if (bridge_network.get(i).adj_lans.get(j) == lan_network.get(k).id)
						{
							for (int p = 0; p < lan_network.get(k).adj_bridges.size();p++)
							{
								if (lan_network.get(k).adj_bridges.get(p) != source.id)
								{
									config_msg ms = new config_msg();
									ms.root = root;
									ms.dist = d;
									ms.source = source;
									ms.destination = lan_network.get(k).adj_bridges.get(p);
									ms.lan = lan_network.get(k).id;
									messages.add(ms);
								}
							}

						}
					}
				}
			}

		}
		return (messages);
	}

	private static config_msg UpdateConfig(config_msg m, ArrayList<bridge> bridge_network) {
		int root = m.root;
		int d = m.dist;
		bridge source = m.source;
		int destination = m.destination;
		char lan = m.lan;

		config_msg return_message = new config_msg();

		for (int i = 0; i < bridge_network.size();i++)
		{
			if (destination == bridge_network.get(i).id)
			{
				bridge b = bridge_network.get(i);
				if (root < b.root)
				{
					return_message.root = root;
					return_message.dist = d + 1;
					return_message.source = bridge_network.get(i);
					bridge_network.get(i).root = root;
					bridge_network.get(i).RP = new Pair<Character, Integer>(lan,source.id);
					bridge_network.get(i).root_distance = d + 1;
				}
				else if (root == b.root && d + 1 < bridge_network.get(i).root_distance)
				{
					return_message.root = root;
					return_message.dist = d + 1;
					return_message.source = bridge_network.get(i);
					bridge_network.get(i).root = root;
					bridge_network.get(i).RP = new Pair<Character, Integer>(lan,source.id);
					bridge_network.get(i).root_distance = d + 1;
				}
				else if (root == b.root && d + 1 == bridge_network.get(i).root_distance && source.id < bridge_network.get(i).RP.second)
				{
					return_message.root = root;
					return_message.dist = d + 1;
					return_message.source = bridge_network.get(i);
					bridge_network.get(i).root = root;
					bridge_network.get(i).RP = new Pair<Character, Integer>(lan,source.id);
					bridge_network.get(i).root_distance = d + 1;
				}
				else
				{
					return_message.root = -1;
					return_message.dist = d + 1;
					return_message.source = bridge_network.get(i);
				}
			}
		}

		return (return_message);
}

	private static void print_bridge_network(ArrayList<bridge> network) 
	{
		System.out.print("Bridge NO.\t");
		System.out.print("Adjacent Lans");
		System.out.print("\n");
		for (int i = 0; i < network.size();i++)
		{
			System.out.print(network.get(i).id);
			System.out.print("\t\t");
			for (int j = 0; j < network.get(i).adj_lans.size();j++)
			{
				System.out.print(network.get(i).adj_lans.get(j));
				System.out.print(" ");
			}
			System.out.print("\n");
		}
	}

	private static void print_lan_network(ArrayList<lan> network) 
	{
		System.out.print("Lan No.\t\t");
		System.out.print("Adj Bridges\t");
		System.out.print("Designated Bridge");
		System.out.print("\n");
		for (int i = 0; i < network.size();i++)
		{
			System.out.print(network.get(i).id);
			System.out.print("\t\t");
			for (int j = 0; j < network.get(i).adj_bridges.size();j++)
			{
				System.out.print(network.get(i).adj_bridges.get(j));
				System.out.print(" ");
			}
			System.out.print("\t\t");
			System.out.print(network.get(i).DP);
			System.out.print("\n");
		}
		
	}
	/*
	 * Author: Rohan Shukla
	 */
}
