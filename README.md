# Distributed_Spanning_Tree

<h4>Java based implementation of Distributed Spanning Tree Algorithm Designed by Radia Perlman</h4><hr>

There are <b>9</b> classes defined in this code:<br><hr>

<b>bridge:</b><br>
Stores its own id, the root bridge's id, distance from the root, the forwarding table (as a vector of ftable objects), adjacency list of lans (storing the ids), and the root port (stored in the form of pair of lan and the bridge connected to the lan with minimum root distance).

<b>lan:</b><br>
Stores its own id, adjacency list of lans (storing the ids), adjacency list of hosts(again ids) and the Designated port.

<b>config_msg:</b><br>
Stores the root id, the current distance from the root, the bridge object of the source bridge, the id of the destination bridge and the lan via which the message is sent. There is also a structure for comparing messages by destination ids

<b>netw_info:</b><br>
Used to store the trace objects(Timestamp, bridge activity,...)

<b>fwd_table:</b><br>
Used to store create an entry of the Forwarding table. Stores host id and Forwarding LAN id

<b>data_packet:</b><br>
Stores the host id of the source and destination, the lan via which the packet is sent and the destination bridge id.

<b>consoleInput:</b><br>
Used to override the default console input output functions and use custom i/o methods

<b>compare_func:</b><br>
Used to define a comparator function which is used to compare the config messages received by LAN & Bridges

<b>ST:</b><br>
Main Class to implement the distributed spanning Tree Algorithm

<hr><b>Trace File explanation:</b><hr>
For setting up the Spanning tree protocol, the trace file is of the form as mentioned in the Problem Statement. For data transfer, the trace file is of the form

<b>T Bi s/r X->Y L</b>

where,

T = Timestamp Bi = the activity at the ith bridge s/r = whether the packet is received or sent X = Source host Y = Destination host L = the Lan via which the packet is received or will be sent on. '-' means the packet is broadcast.

trace = 0 => No trace outputted trace = 1 => Trace will be outputted in the order of the happening of the events trace = 2 => Trace will be outputted at the end of simulation results

<hr><b>Algorithm for Spanning tree:</b><hr>
Each bridge initially claims that it itself is the root and sends the packet accordingly transmits the packet on all its adjcent lans. When it realises it is not the root, it only keeps updating the parameters(root, distance of root and RP) and transmits the update packets on the lan. Whenever there is nothing to be sent, it implies that the Root bridge and its distance from every other bridge along with the RP is found out and the program terminates.

<hr><b>Algorithm for learning bridges:</b><hr>
Same as discussed in any standard textbook. For implementing, two queues of data packets, sent and received, were maintained for the purpose of trace and whenever there is nothing to be sent on any bridge, then it implies that the data packet has supposedly reached the required host.
<hr>

