/*
graph.h
Developed by Chris Kee
This file provides the classes needed to store graph information 
and graph methods such as the "dijkstra" method.
NOTE: This file is to be compiled with main.cpp and graph.cpp
*/

#include <iostream>
#include <string>
#include <map>

using namespace std;

class graph
{
private:
	int a[10][10];
public:
	graph();
	void add(int row, int col, int weight);
	void remove(int row, int col);
	void dijkstra(int vStart);
};

class dPath  // Holds data for a vertex and weight to be add to Path (list).
{
public:
	dPath()
	{
		vertex = 0;
		weight = 0;
	}
	int vertex;
	int weight;
};