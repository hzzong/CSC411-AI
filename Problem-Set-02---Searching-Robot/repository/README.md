# Problem-Set-02---Searching-Robot

For this target searching robot getAction() function inplementation, I used A Star Algorithm to implement it. 
Each time getAction() get called, it would run a A Star searching process, and get the shortest path from current node to the target point. 
I created a Node class as a subclass in the Robot, which saves the coordinate of the point as well its parent, children, f, g and h.
I created a global node arraylist "history" to store the previous pathes. And these pathes will be added into the closed list before each A Star searching. 
I created open list as a priority queue which pops the node has the least f(n).
During A Star seaching, I add the first node into the open list, and I created a while loop which ends when the open list is empty. 
In the while loop, first pop the open list and go through its children, if the child is the target, then add it to the closed list and break, stop searching, 
if it is not in the open list, add it, or it's in the open list but this one has better f, then update it. Otherwise, ignore it. 
Then, since closed list has too many nodes and it is not a path,  go all the way back from the target to the current node,
When the parent of the node is the current node, this node is the next step to the target.

I get the idea from Mr. G, which searches the target point every time getAction() get called,
in this case, the robot can even handle the situation that the target is moving. 
