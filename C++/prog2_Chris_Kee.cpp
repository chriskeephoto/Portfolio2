// prog2_Chris_Kee.cpp
// Linked List Program
// Developed by: Chris Kee

// This program manages a singley linked list.
// The user can insert, remove, search, view list size, print list

#include <iostream>
#include <stdlib.h>
#include <string>
using namespace std;

//=========================================================
// class declaration

template<class SL>
class SingleList
{
private:
	struct Node  // a structure used to be a linked list item
	{
		SL data;
		Node *next;  // stores pointer to next node or NULL(end of list)
	};

public:
	SingleList()  // constructor used to initialize a linked list
	{
		init();
	}
	int size();
	void insert(SL num);  // function to insert an item
	void printList();  // function to print the list
	void remove(SL num);  // function to remove a list item
	void find(SL num);  // function to search for a list item

private:
	int listSize;  // keeps track of the list size
	Node *head;  // head node is used to keep track of the list
	void init()  // initialize list with a head node that points to NULL(end of list)
	{
		listSize = 0;
		head = new Node;
		head->next = NULL;
	}
};

//=========================================================
// class function definitions

template<class SL>
int SingleList<SL>::size()
{
	return listSize;
}

template<class SL>
void SingleList<SL>::insert(SL num)
{
	Node *newPtr;
	Node *currentPtr;

	// create new node to insert
	newPtr = new Node;
	newPtr->data = num;
	newPtr->next = NULL;

	if(head->next == NULL) // insert at begining (i.e. right after head)
	{
		head->next = newPtr;
	}
	else // node will be inserted at end
	{
		currentPtr = head;
		while(currentPtr->next != NULL)
		{
			currentPtr = currentPtr->next;  // walk to end of list
		}

		currentPtr->next = newPtr;  // insert new node at end
	}

	listSize = listSize + 1; // increment listSize
}

template<class SL>
void SingleList<SL>::printList()
{
	Node *currentPtr;

	if(head->next == NULL) // check for empyt list
	{
		cout << endl << "List is empty!" << endl << endl;
	}
	else
	{
		cout << endl << "The List: ";
		currentPtr = head->next;  // statring position
		do
		{
			if(currentPtr->next != NULL) // if not last item print "," after item
				cout << currentPtr->data << ", ";
			else
				cout << currentPtr->data;
			currentPtr = currentPtr->next;
		}
		while(currentPtr != NULL);
		cout << endl << endl;
	}
}

template<class SL>
void SingleList<SL>::remove(SL num)
{
	bool DONE = false;  // used to end while loop
	Node *previousPtr;
	Node *currentPtr;
	Node *tempPtr;  // used to store nodes that will be freed
	bool removed = false;  // used to decide if item was removed
	
	currentPtr = head->next;  // starting point to search
	while(!DONE)
	{
		if(currentPtr->data == num)
		{
			if(head->next == currentPtr) // match on first node 
			{
				tempPtr = currentPtr;
				head->next = currentPtr->next; // head->next points to the "next" node
				free(tempPtr);  // release current node from memory
				listSize = listSize - 1;  // decrement listSize
				if(head->next == NULL)  // empty list
					DONE = true;  // no more to search
				else
					currentPtr = head->next;  // start with new "first" list item
			}
			else  // match on node that is not the first
			{
				tempPtr = currentPtr;
				previousPtr->next = currentPtr->next;
				free(tempPtr);  // release current node from memory
				listSize = listSize - 1;  // decrement listSize
				if(previousPtr->next == NULL)  // reached the end of list
					DONE = true;  // no more to search
				else
					currentPtr = previousPtr->next;  // start searching from updated "current" node
			}

			removed = true;  // indicate that item was removed
		}
		else
		{
			if(currentPtr->next != NULL) // full list has not been searched
			{
				previousPtr = currentPtr;  // keep track of previous node
				currentPtr = currentPtr->next;  // walk to next node
			}
			else
				DONE = true;  // reached end of list
		}
	}

	if(removed)
		cout << endl << num << " removed from list." << endl << endl;
	else
		cout << endl << "Error! " << num << " not in list." << endl << endl;
}

template<class SL>
void SingleList<SL>::find(SL num)
{
	bool DONE = false;
	Node *currentPtr;
	bool inList = false;

	currentPtr = head->next;
	while(!DONE)
	{
		if(currentPtr->data == num) // check node for match
		{
			inList = true;
			DONE = true; // end loop
		}
		else // node is not a match
		{
			if(currentPtr->next == NULL) // end of list
				DONE = true; // end loop
			else
				currentPtr = currentPtr->next; // walk to next node
		}
	}

	if(inList)
		cout << endl << num << " is in the list." << endl << endl;
	else
		cout << endl << num << " is not in the list." << endl << endl;
}

//=========================================================
void instructions();
bool isGoodInput(string input);

int main()
{
	int input;
	int i;
	string testInput;

	SingleList<int> mySL;  // instantiate object mySL as integer list

	instructions();
	cin >> testInput;
	if(isGoodInput(testInput))  // error check
		input = atoi(testInput.c_str());
	else
		input = 0;   // activates switch default

	while(input != 6)   // run until user inputs 6 at instruction prompt
	{
		switch(input)
		{

			// View list size
		case 1:
			cout << endl << "List size: " << mySL.size() << endl << endl;
			instructions();
			cin >> testInput;
			if(isGoodInput(testInput))   // error check
				input = atoi(testInput.c_str());
			else
				input = 0;   // activates switch default
			break;

			// Print list
		case 2:
			mySL.printList();
			instructions();
			cin >> testInput;
			if(isGoodInput(testInput))   // error check
				input = atoi(testInput.c_str());
			else
				input = 0;   // activates switch default
			break;

			// Test if a value is in the list
		case 3:
			if(mySL.size() == 0)
			{
				cout << endl << "Error! List is empty." << endl;
			}
			else
			{
				cout << endl << "Enter an item to test: ";
				cin >> testInput;
				if(isGoodInput(testInput))   // error check
				{
					i = atoi(testInput.c_str());
					mySL.find(i);
				}
				else
					cout << endl << "Error! Bad input. Contains a bad data type." << endl;
			}
			cout << endl;
			instructions();
			cin >> testInput;
			if(isGoodInput(testInput))  // error check
				input = atoi(testInput.c_str());
			else
				input = 0;   // activates switch default
			break;

			// Add node to list
		case 4:
			cout << endl << "Enter an item to insert: ";
			cin >> testInput;
			if(isGoodInput(testInput))   // error check
			{
				i = atoi(testInput.c_str());
				mySL.insert(i);
			}
			else
				cout << endl << "Error! Bad input. Contains a bad data type." << endl;
			cout << endl;
			instructions();
			cin >> testInput;
			if(isGoodInput(testInput))   // error check
				input = atoi(testInput.c_str());
			else
				input = 0;   // activates switch default
			break;

			// Remove node from list
		case 5:
			if(mySL.size() == 0)
			{
				cout << endl << "Error! List is empty." << endl;
			}
			else
			{
				cout << endl << "Enter an item to remove: ";
				cin >> testInput;
				if(isGoodInput(testInput))   // error check
				{
					i = atoi(testInput.c_str());
					mySL.remove(i);
				}
				else
					cout << endl << "Error! Bad input. Contains a bad data type." << endl;
			}
			cout << endl;
			instructions();
			cin >> testInput;
			if(isGoodInput(testInput))   // error check
				input = atoi(testInput.c_str());
			else
				input = 0;   // activates switch default
			break;

			// Invalid input
		default:
			cout << endl << "Error! Invalid Input" << endl << endl;
			instructions();
			cin >> testInput;
			if(isGoodInput(testInput))   // error check
				input = atoi(testInput.c_str());
			else
				input = 0;   // activates switch default
			break;
		}
	}

	cout << "Goodbye!" << endl;
	return 0;
}

void instructions()
{
	cout << "	Select:" << endl;
	cout << "1 - View list size" << endl;
	cout << "2 - Print list" << endl;
	cout << "3 - Test if a value is in the list" << endl;
	cout << "4 - Add node to list" << endl;
	cout << "5 - Remove node from list" << endl;
	cout << "6 - Exit" << endl;
	cout << ">> ";
}

bool isGoodInput(string input)  // function for testing user input - i.e. error checking
{
	int counter;
	for(counter = 0; counter < input.length(); counter ++)
	{
		if(!isdigit(input[counter]))  // check if input is an integer
			return false;
	}
	return true;
}