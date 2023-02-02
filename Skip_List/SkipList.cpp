#include "SkipList.h"
#include <iostream>
#include <vector>
/*
name: Luukas Suomi
st#: 301443184
date: Dec 6, 2021
class: CMPT 225 D200 Tom Schermer
for: Assignment 4
Description: contains implementations for
 void insert(Key k, Value v);
 void remove(Key k);
 Entry* ceilingEntry(Key k);
 Entry* floorEntry(Key k);
 Entry* greaterEntry(Key k);
 Entry* lesserEntry(Key k);
 which are the methods of a Skip List.
plus an added destructor
*/

using namespace std;

SkipList::SkipList() : listHeads() {
    makeNewLevelList();
    makeNewLevelList();
}

// makes a new list on the top level of existing list.
// call only when top list is NULL or just the two sentinels. 
void SkipList::makeNewLevelList() {
    SkipList::Entry* minusInfinity = new Entry("!!", "");	// "!!" < any other string.
    SkipList::Entry* plusInfinity = new Entry("}}", "");	// "}}" > any other key.

    Quad* first = new Quad(minusInfinity);
    Quad* last = new Quad(plusInfinity);

    int numLists = listHeads.size();
    Quad* oldFirst = numLists == 0 ? NULL : listHeads[numLists - 1];
    Quad* oldLast  = numLists == 0 ? NULL : oldFirst->next;

    first->prev = NULL;
    first->next = last;
    first->above = NULL;
    first->below = oldFirst;

    last->prev = first;
    last->next = NULL;
    last->above = NULL;
    last->below = oldLast;

    if(oldFirst != NULL) {
        oldFirst->above = first;
        oldLast->above = last;
    }
    listHeads.push_back(first);
}

void SkipList::printOneList(int listNum) {
	Quad* bottomCurrent = listHeads[0];
	Quad* current = listHeads[listNum];

	while(bottomCurrent->next != NULL) {
		std::string toPrint;
		if(current->entry->getKey() == bottomCurrent->entry->getKey()) {
			toPrint = current->entry->getKey();
			current = current->next;
		}
		else {
			toPrint = "--";
		}
		cout << "--" << toPrint;
		bottomCurrent = bottomCurrent->next;
	}
	cout << "--" << bottomCurrent->entry->getKey() << "--" << endl;
}

void SkipList::print() {
	int numLists = listHeads.size();
	for(int i = numLists - 1; i >= 0; i--) {
		printOneList(i);
	}
} 

SkipList::Entry* SkipList::find(Key k) {
    int numLists = listHeads.size();
    Quad* current = listHeads[numLists - 1];

    while (current->below != NULL) {
        current = current->below;			// drop down
        while(k >= current->next->entry->getKey()) {	// scan forward
            current = current->next;
        }
    } 

    if(current->entry->key == k) {
        return current->entry;
    }
    else {
        return NULL;
    }
}

// the "trail" is a vector of the last node visited on each list
// the last element of trail is on the lowest list; the first is on the highest.
std::vector<SkipList::Quad*>* SkipList::findWithTrail(Key k) {
    std::vector<SkipList::Quad*>* trail = new std::vector<Quad*>();

    int numLists = listHeads.size();
    Quad* current = listHeads[numLists - 1];

    while (current != NULL) {
        while(k >= current->next->entry->getKey()) {	// scan forward
            current = current->next;
        }
	trail->push_back(current);
        current = current->below;			// drop down
    } 
    return trail;
}


// fill in the assigned functions here.

void SkipList::insert(Key k, Value v){
    //we dont run the code if the key is already present
    if(find(k) != NULL){
        return;
    }
    int insertHeight = 0;
    //1 = heads(success), 0 = tails(fail)
    while((std::rand() % 2) != 0){
        insertHeight++;
    }
    //if our insert height is too large we must increase the height of our Skip List
    int Iterator = insertHeight;
    int level = listHeads.size()-1;
    while(Iterator >= level){
        Iterator--;
        makeNewLevelList();
    }
    //starting at the level specified by insertHeight we begin to perform a 
    //modified Dlinked list insert 
    //the minusInfinity node at the given height for our new node 
    Quad* curr = listHeads[insertHeight];
    Quad* lastInserted;
    for(int j = insertHeight;j>=0; j--){
        //create our new quad for this level
        SkipList::Entry* NewEntry = new Entry(k, v);
        Quad* NewQuad = new Quad(NewEntry);
        while(curr->next->entry->getKey() < k){
            curr = curr->next;
        }
        //add it to the correct spot in the Dlinked list
        NewQuad->next = curr->next;
        curr->next->prev = NewQuad;
        curr->next = NewQuad;
        NewQuad->prev = curr;
        
        //we dont want to run into a seg fault by going below a level 0 node
        if(curr->below != NULL){
            curr = curr->below;
        }
        //this if is for preventing the topmost node in the new tower
        //from trying to access a lastinserted value that does not exist
        if(j <insertHeight){
            lastInserted->below = NewQuad;
        }
        lastInserted = NewQuad;
    }


}
void SkipList::remove(Key k){
    //we initialise a vector that holds the returned vector 
    //from findwithtrail
    std::vector<Quad*>* trail = findWithTrail(k);
    //numlists will be used as a loop bound later
    int numLists = listHeads.size()-1;
    //size will be used as a loop bound
    int size = trail->size()-1;
    //we need these two temps to accurately remove and delete entries
    Quad* current = NULL;
    Quad* Del;
    //loop through the returned vector
    //note that it stops above and to the left of our wanted element
    //we will handle that below
    for(int i=0; i<size ;i++){
        current = trail->at(i);
        //if we have found the given key
        if(current->entry->getKey() == k){
            //skip over it in the doubly linked list that is one level
                Quad* previ = current->prev;
                Quad* after = current->next;
                current->next->prev = previ;
                current->prev->next = after;
                //here we delete the respective entry and quad of the element
                Del = current;
                delete Del->entry;
                delete Del; 
        }

    }
    //the above misses the bottom most element so we remove it here
    if(current->below != NULL){
        current = current->below;
        //it may also be too far left so we iterate to it
        //not that we continue from current meaning this wont be O(n)
        while(current->entry->getKey()!= k){
            current = current->next;
        }
        //same update and delete as above but for the bottom most elements
                Quad* previ = current->prev;
                Quad* after = current->next;
                current->next->prev = previ;
                current->prev->next = after;
                Del = current;
                delete Del->entry;
                delete Del;
    }
    //now we need to clean up the empty lists possibly created
    //we first delete all of the top lists
    for(int i = numLists; i>=0;i--){
       current = listHeads[i];
       if(current->next->entry->getKey() == "}}"){
           //we also delete their respective entries and quads
            Del = current;
            delete Del->next->entry;
            delete Del->next;
            delete Del->entry;
            delete Del;
            //make sure to get rid of that entry from the listHeadsvector seeing ad it no longer exists
            listHeads.pop_back();
       } 
    }
    //we delete the vector used
    delete trail;
    //then we add a new top list because we had deleted it
    makeNewLevelList();
}
SkipList::Entry* SkipList::ceilingEntry(Key k){
    //special case handling if the given entry is the largest in the list
    //we simply return said value
    if(k == "}}"){
        Entry* infinity = new Entry("}}", "");
        return infinity;
    }
    //used as a loop bound
    int numLists = listHeads.size();
    Quad* current = listHeads[numLists - 1];
    //find our value
    while (current->below != NULL) {
        current = current->below;			// drop down
        while(k >= current->next->entry->getKey()) {	// scan forward
            current = current->next;
        }
    } 
        //we move one forward which will return the value one higher than our element
        current = current->next;
        return current->entry; 
}
SkipList::Entry* SkipList::floorEntry(Key k){
    //same as ceiling except me move one back from curr
    if(k == "!!"){
        Entry* mininfinity = new Entry("!!", "");
        return mininfinity;
    }
    int numLists = listHeads.size();
    Quad* current = listHeads[numLists - 1];

    while (current->below != NULL) {
        current = current->below;			// drop down
        while(k >= current->next->entry->getKey()) {	// scan forward
            current = current->next;
        }
    } 
        current = current->prev;
        return current->entry; 
}
SkipList::Entry* SkipList::greaterEntry(Key k){
    if(k == "}}"){
        Entry* infinity = new Entry("}}", "");
        return infinity;
    }
    int numLists = listHeads.size();
    Quad* current = listHeads[numLists - 1];

    while (current->below != NULL) {
        current = current->below;			// drop down
        while(k >= current->next->entry->getKey()) {	// scan forward
            current = current->next;
        }
    } 
    current= current->next;
    //if we have a duplicate value me skip over it
    if(current->entry->getKey() == k){
        if(current->next != NULL){
            current = current->next;
            return current->entry;
        }
        else{
            Entry* infinity = new Entry("}}", "");
            return infinity;
        }
    }
    //otherwise we return the entry
    return current->entry;
}
SkipList::Entry* SkipList::lesserEntry(Key k){
        if(k == "!!"){
        Entry* mininfinity = new Entry("!!", "");
        return mininfinity;
    }
    int numLists = listHeads.size();
    Quad* current = listHeads[numLists - 1];

    while (current->below != NULL) {
        current = current->below;			// drop down
        while(k >= current->next->entry->getKey()) {	// scan forward
            current = current->next;
        }
    } 
    current = current->prev;
    //if we have a duplicate value skip it
    if(current->entry->getKey() == k){
        if(current->prev != NULL){
            current = current->prev;
            return current->entry;
        }
        else{
            Entry* mininfinity = new Entry("!!", "");
            return mininfinity;
        }
    }
    //otherwise return the entry
    return current->entry;
}
//added destructor
//NO MEMORY LEAKS ALLOWED HERE :)
SkipList::~SkipList(){
    Quad* current;
    int size = listHeads.size()-1;
    //for every sub-list in the Skip list
    for(int i=size; i>-1; i--){
        current=listHeads[i];
        while(current->next!=nullptr){
            //we delete every entry
            current=current->next;
            delete current->prev->entry;
            delete current->prev;
        }
        //delete the last entry "}}"
        delete current->entry;
        delete current;
        //making sure to remove every element fro this vector
        listHeads.pop_back();
    }
}

