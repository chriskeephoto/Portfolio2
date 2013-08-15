/*
graph.cpp
Developed by Chris Kee
This file provides the functionality of the "graph" class defined
in graph.h. The dijkstra method finds the shortest path from one
vertex to all other connected vertices in a graph.  The way I
implemented the dijkstra method was by building a list (defined by map<> Path) 
of neighboring nodes and then sorting the list to find the closest neighbor. 
I update this weight in "dArray" and repeat the search from the closest neighbor.
Each time a new vertex is searched from, the dFS variable (distance
from start vertex) is updated with the new total distance.
NOTE: This program is to be compiled with graph.h and main.cpp
*/

#include "graph.h"

graph::graph() // fill Array with -1 weights
{
	int i, j;

	for(i = 0; i < 10; i++)
	{
		for(j = 0; j < 10; j++)
		{
			a[i][j] = -1;
		}

		a[i][i] = 0; // set zeros in diagnal
	}
}

void graph::add(int row, int col, int weight)  // add weight between two vertices (row, col)
{
	if(row == col)
	{
		cout << "Error! No weight can be added to the same vertex" << endl;
	}
	else if(weight == 0)
	{
		cout << "Error! Weight 0 is not allowed." << endl;
	}
	else if(a[row][col] != -1)
	{
		cout << "Error! Weight for this edge already exists." << endl;
	}
	else
	{
		a[row][col] = weight;
		a[col][row] = weight;
	}

}
void graph::remove(int row, int col)	// delete weight between two vertices (row, col)
{
	if(a[row][col] == 0)
	{
		cout << "Error! No edge to be deleted." << endl;
	}
	else if(a[row][col] == -1)
	{
		cout << "Error! This edge does not exist!" << endl;
	}
	else
	{
		a[row][col] = -1;
		a[col][row] = -1;
	}
}



void graph::dijkstra(int vStart)	// find shortest path from one vertex to all connected vertices
{
	multimap<int, dPath> Path; // list for shortest path
	multimap<int, dPath>::const_iterator itPath;  // iterator for path

	int vStart2 = vStart; //	used for keeping start node 0
	int dFS = 0;  // distance from start node

	int dArray[10]; // build 1 dim array to hold weights between vertices

	int i,j,c,d; // variables used for iteration

	int newA[10][10];
	for(c = 0; c < 10; c++)
	{
		for(d = 0; d < 10; d++)
		{
			newA[c][d] = a[c][d]; // Make coppy of a[][] to edit
		}
	}

	for(c = 0; c < 10; c++) // set all array elements = -1;
		dArray[c] = -1;

	dArray[vStart] = 0; // set starting vertex weight as 0!

	dPath myPath;

///////////  Find shortest path to every node //////////////////////////////////


	for(i = 0; i < 10; i++) // visit every possible node
	{
		
		for(j = 0; j < 10; j++) // determine negiboring nodes
		{
			if(newA[vStart][j] != -1 && newA[vStart][j] != 0)	// Check if weight can exist between vertices
			{
				// add data to myPath
				myPath.vertex = j;
				myPath.weight = dFS + newA[vStart][j];

				if(dArray[j] == -1) // First time for a vertex to be visited
				{
					Path.insert(pair<int, dPath>(newA[vStart][j],  myPath));	 // add to myPath to Path list
					dArray[j] = dFS + newA[vStart][j]; // add shortest path to dArray
					dArray[vStart2] = 0; // keep starting vertex at zero
				}
				else if(myPath.weight < dArray[j])  // Update to the new weight if the it's smaller
				{
					Path.insert(pair<int, dPath>(newA[vStart][j],  myPath));  // add to myPath to Path list
					dArray[j] = dFS + newA[vStart][j]; // add shortest path to dArray
					dArray[vStart2] = 0; // keep starting vertex at zero
				}

			}
		}
			
		if(!Path.empty())
		{
			itPath = Path.begin();  // find smallest vertex (beginning of Path list)
			c = itPath->second.vertex;
			dFS = itPath->second.weight;  // update dFS (distance from start vertex)
			newA[vStart][c] = -1;	// remove weight from newA	so it can not be revisited
			newA[c][vStart] = -1;
			Path.erase(itPath);	// remove first list item (smallest weight)
			vStart = c;	// new point to search from
		}
		
	
	}	// End of finding pahts

////////////////////////////////////////////////////////////////////////////////

	for(c = 0; c < 10; c++)  // Print Dijkstra shortest path to every node
	{
		cout << "(" << c << "," << dArray[c] << ")";
		if(c < 9)
			cout << "-";
	}
	cout << endl;
	return;
}
