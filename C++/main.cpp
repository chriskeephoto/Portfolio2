/*
main.cpp
Developed by Chris Kee
This program allows the user to add and delete weights between graph vertices.
When given the "dijkstra x" command, the program will find the shotest path
between x and all other vertices.
NOTE: This program is to be compiled with graph.h and graph.cpp
*/

#include "graph.h"

void display();

int main()
{
	string uInput;
	int v1, v2, weight; // variables used to store user input

	graph myGraph = graph();

	display();
	cin >> uInput;

	while(uInput != "quit")
	{
		if(uInput == "add")	// Add weigh between two vertices
		{
			cin >> v1;
			cin >> v2;
			cin >> weight;

			if((v1 < 10) && (v2 < 10))		// Check to make sure vertices exist
			{
				myGraph.add(v1, v2, weight);
			}
			else
				cout << "Error! Invalid Vertices" << endl;

			display();
			cin >> uInput;
		}

		else if(uInput == "delete")	// Delete weight between two vertices
		{
			cin >> v1;
			cin >> v2;
			if((v1 < 10) && (v2 < 10))	// Check to make sure vertices exist
			{
				myGraph.remove(v1,v2);
			}
			else
				cout << "Error! Invalid Vertices" << endl;

			display();
			cin >> uInput;
		}

		else if(uInput == "dijkstra")  // Show shortest path from one vertex to the rest
		{
			cin >> v1;
			myGraph.dijkstra(v1);
			display();
			cin >> uInput;
		}

		else
		{
			display();
			cin >> uInput;
		}
	}

	return 0;
}

void display()
{
	cout << "graph> ";
}