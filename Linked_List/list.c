#include "list.h"
#include <stdio.h>
#include <stdlib.h>
/*
Written by: Luukas Suomi
ST#: 301443184
For: CMPT 300, SFU
Description:
Code that implements the list ADT with the constrain that no calls to the system for extra memory is to be made.
*/
//for implementation purposes we will need some global variables
static bool first_call_create_list = false;

static int Now = 0, Next = 1, Highest = 0, Nodes_inUse = 0;
static int Now_h = 0, Next_h = 1, Highest_h = 0, Heads_inUse = 0;
int heads_in_use(){
    return Heads_inUse;
}
int nodes_in_use(){
    return Nodes_inUse;
}

// General Error Handling:
// Client code is assumed never to call these functions with a NULL List pointer, or 
// bad List pointer. If it does, any behaviour is permitted (such as crashing).
// HINT: Use assert(pList != NULL); just to add a nice check, but not required.

// Makes a new, empty list, and returns its reference on success. 
// Returns a NULL pointer on failure.

 
//assumes the node in position zero has already been used
//takes the now empty node and swaps it with the last available node in the array
//thus, there should always be a open node in position zero in our array
void take_node(){
    if(Nodes_inUse != 100){
        Nodes_inUse++;
    }
    if(Now != -1){
        nodePool.node_array[Now].inUse = true;
    }
    if(Next == 99){
        int temp = Next;
        Next = -1;
        Now = temp; 
        Highest = Now;
    }else if(Next == -1){
        Now = -1;

    }else{
        if(nodePool.node_array[Next +1].inUse == true){
            int temp = Next;
            Next = Highest;
            Now = temp;
        }else{
            int temp = Next;
            Next++;
            Now = temp; 
            Highest = Now;
        }
    }
}
//assumes the head in position zero has already been used
//takes the now empty head and swaps it with the last available head in the array
//thus, there should always be a open head in position zero in our array
void take_head(){
    if(Heads_inUse != 10){
        Heads_inUse++;
    }
    if(Now_h != -1){
        nodePool.head_array[Now_h].inUse = true;
    }
    if(Next_h == 9){
        int temp = Next_h;
        Next_h = -1;
        Now_h = temp; 
        Highest_h = Now_h;
    }else if(Next == -1){
        Now = -1;

    }else{
        if(nodePool.node_array[Next_h +1].inUse == true){
            int temp = Next_h;
            Next_h = Highest_h;
            Now_h = temp;
        }else{
            int temp = Next_h;
            Next_h++;
            Now_h = temp; 
            Highest_h = Now_h;
        }
    }
}

void return_node(int index){
    if(Nodes_inUse != 0){
        Nodes_inUse--;
        nodePool.node_array[index].inUse = false;
        nodePool.node_array[index].next = NULL;
        nodePool.node_array[index].item = NULL;
        nodePool.node_array[index].prev = NULL;
    }
    //case in which the Now index must not be updated
    if(index > Now && Now != -1){
        //if the index is smaller than next than it should be updated
        if(index < Next){
            Next = index;
        }
        //if it is not smaller than next we do nothing

        //if the Now index is at the very end
    }else if(Next== -1){
        //if the list is completely empty
        if(Now == -1){
            Now = index;
            Highest = index;
        //if there is only one item in the list
        }else{
            int temp = Now;
            Now = index;
            Next = temp;
        }
    //if we are in the generic case
    }else{
        int temp = Now;
        Now = index;
        Next = temp;
    }
    if(Nodes_inUse == 0){
        Highest = Now;
    }
}

void return_head(int index){
    if(Heads_inUse != 0){
        Heads_inUse--;
        nodePool.head_array[index].inUse = false;
        nodePool.head_array[index].next = NULL;
        nodePool.head_array[index].item = NULL;
        nodePool.head_array[index].prev = NULL;
    }
    if(index > Now_h && Now_h != -1){
        if(index < Next_h){
            Next_h = index;
        }
    }else if(Next_h== -1){
        //if the list is completely empty
        if(Now_h == -1){
            Now_h = index;
            Highest_h = index;
        //if there is only one item in the list
        }else{
            int temp = Now_h;
            Now_h = index;
            Next_h = temp;
        }
    }else{
        int temp = Now_h;
        Now_h = index;
        Next_h = temp;
    }
    if(Nodes_inUse == 0){
        Highest_h = Now_h;
    }
}

List* List_create(){
    //list_s is a storage container for all of the ndoes we are to create
    //it is not any one particular list but rather the pool of nodes we have to use

    //create the actual list
    List the_list;
    //create a pointer to the adress of the actual list so that we may pass it to various functions for modification
    struct List_s *list = &the_list;
    list->state_of_current = 3;
    list->list_length = 0;


    if(first_call_create_list == false)
    {
        first_call_create_list = true;


        for(int i =0; i < LIST_MAX_NUM_NODES;i++){
            nodePool.node_array[i].next = NULL;
            nodePool.node_array[i].prev= NULL;
            nodePool.node_array[i].isHead = false;
            nodePool.node_array[i].index = i;
            nodePool.node_array[i].inUse = false;

            //printf("Node has index %d, and value %d\n", nodePool.node_array[i].index, nodePool.node_array[i].item);
        }
        //creating the heads
        for(int g = 0; g < LIST_MAX_NUM_HEADS;g++){
            nodePool.head_array[g].next = NULL;
            nodePool.head_array[g].prev= NULL;
            nodePool.head_array[g].isHead = true;
            nodePool.head_array[g].index = g;
            nodePool.head_array[g].inUse = false;

        }
        //for now the head and the tail are the same
        list->head = &nodePool.head_array[Now_h];
        list->tail = &nodePool.head_array[Now_h];
        take_head();
        list->current = list->head;
        return list;

    }else{
        if(Heads_inUse < 10){
            //logic to check for open head
            list->head = &nodePool.head_array[Now_h];
            list->tail = &nodePool.head_array[Now_h];
            take_head();
            list->current = list->head;
            return list;
        }
    }
    return NULL;
}


// Returns the number of items in pList.
int List_count(List* pList){
    return pList->list_length;
}

// Returns a pointer to the first item in pList and makes the first item the current item.
// Returns NULL and sets current item to NULL if list is empty.
void* List_first(List* pList){
    //in my interpretation the heads do not store items so it would not ake sense to return NULL
    if( pList->head->next != NULL){
        pList->current = pList->head->next;
        return pList->current->item;
    }else{
        pList->current = pList->head;
        return NULL;
    }
    return NULL;
}

// Returns a pointer to the last item in pList and makes the last item the current item.
// Returns NULL and sets current item to NULL if list is empty.
void* List_last(List* pList){
    //once again in my interpretation an empty list still contains a head node. Just with no items after
    if(pList->tail != NULL && pList->tail->isHead != true){
        pList->current = pList->tail;
        return pList->current->item;
    }else{
        pList->current = pList->head;
        return NULL;
    }
    return NULL;
}

// Advances pList's current item by one, and returns a pointer to the new current item.
// If this operation advances the current item beyond the end of the pList, a NULL pointer 
// is returned and the current item is set to be beyond end of pList.
void* List_next(List* pList){
    //if the current is before the list move to the head
    if(pList->state_of_current == LIST_OOB_START){
        pList->current = pList->head;
        pList->state_of_current = 3;
        return pList->current->item;
    //if the node we are looking at has a next return its item
    }else if(pList->current->next != NULL){
        pList->current = pList->current->next;
        return pList->current->item;
    //if the current node does not have a next progress beyond the list and return NULL
    }else{
        pList->state_of_current = LIST_OOB_END;
        pList->current = NULL;
        return NULL;
    }
    return NULL;

}

// Backs up pList's current item by one, and returns a pointer to the new current item. 
// If this operation backs up the current item beyond the start of the pList, a NULL pointer 
// is returned and the current item is set to be before the start of pList.
void* List_prev(List* pList){

    if(pList->state_of_current == LIST_OOB_END){
        pList->current = pList->tail;
        pList->state_of_current = 3;
        return pList->current->item;
    }else if(pList->current->isHead == true ){
        pList->state_of_current = LIST_OOB_START;
        pList->current =NULL;
        return NULL;
    }else if(pList->state_of_current == LIST_OOB_START){
        return NULL;
    }else{
        pList->current = pList->current->prev;
        return pList->current->item;
    }
    return NULL;
}

// Returns a pointer to the current item in pList.
void* List_curr(List* pList){
    if(pList->state_of_current == LIST_OOB_END || pList->state_of_current == LIST_OOB_START){
        return NULL;
    }else if(pList->current->isHead == true){
        return NULL;
    }else{
       return pList->current->item; 
    }
    return NULL;
}

// Adds the new item to pList directly after the current item, and makes item the current item. 
// If the current pointer is before the start of the pList, the item is added at the start. If 
// the current pointer is beyond the end of the pList, the item is added at the end. 
// Returns 0 on success, -1 on failure.
int List_insert_after(List* pList, void* pItem){
    if(Nodes_inUse == 100){
        return -1;
    }
    //first we check whether or not the current pointer is at the end of the list
    //if not we must do some more work with updating the previous and next pointers of the existing nodes
    if(pList->state_of_current == LIST_OOB_END){
        //we set the current pointer to the head of the list so that the logic below will work as planned
        //the new node will be inserted at the start of the list directly after the head
        pList->current = pList->tail;
       pList->state_of_current = 3;
    }
    else if(pList->state_of_current == LIST_OOB_START){
        pList->current = pList->head;
        pList->state_of_current = 3;
    }
    if(pList->current->next != NULL){
        //first set the nodes value
        nodePool.node_array[Now].item = pItem;
        //update currents next previous pointer to the new node
        pList->current->next->prev = &nodePool.node_array[Now];
        //before we lost acces to current next we must set the next pointer
        nodePool.node_array[Now].next = pList->current->next;
        //set the current's next pointer to the new node
        pList->current->next = &nodePool.node_array[Now];
        //set the new nodes prev pointer point back to current
        nodePool.node_array[Now].prev = pList->current;

        //now that we have used current to update all of our pointers we are free to swap it to the new node
        pList->current = &nodePool.node_array[Now];

        //now that we have used the new node we must swap it with a node that hasent been used
        take_node();

        //update the list length
        pList->list_length++;

    //we drop into the else block if the current pointer is at the end of the list
    }else{
        //first step is to give the node the value
        nodePool.node_array[Now].item = pItem;
        //next step is to add this new node to the spot specified in the function description
        pList->current->next = &nodePool.node_array[Now];
        //update the previous pointer for our new node
        nodePool.node_array[Now].prev = pList->current;
        //We must upfate the new node pointer so that we are keeping track that a new node is in use
        //update current to this new node
        pList->current = &nodePool.node_array[Now];
        take_node();
        //update the list length
        pList->list_length++;
        //update the tail in the case that this new node is being added at the end of the list
        pList->tail = pList->current;
    }
    return 0;
}

// Adds item to pList directly before the current item, and makes the new item the current one. 
// If the current pointer is before the start of the pList, the item is added at the start. 
// If the current pointer is beyond the end of the pList, the item is added at the end. 
// Returns 0 on success, -1 on failure.
int List_insert_before(List* pList, void* pItem){
    if(Nodes_inUse == 100){
        return -1;
    }
    //there are a couple of cases that we must consider here
    //firstly if the current pointer is pointing at the head of the list it would not be sensible to carry out this insert as if the head were a true node
    if(pList->state_of_current == LIST_OOB_END){
        pList->current = pList->tail;
        pList->state_of_current = 3;
        //first step is to give the node the value
        nodePool.node_array[Now].item = pItem;
        //next step is to add this new node to the spot specified in the function description
        pList->current->next = &nodePool.node_array[Now];
        //update the previous pointer for our new node
        nodePool.node_array[Now].prev = pList->current;
        //We must upfate the new node pointer so that we are keeping track that a new node is in use
        take_node();
        //update current to this new node
        pList->current = pList->current->next;
        //update the list length
        pList->list_length++;
        //update the tail in the case that this new node is being added at the end of the list
        pList->tail = pList->current;
        return 0;
    }else if(pList->state_of_current == LIST_OOB_START){
        pList->current = pList->head;
       pList->state_of_current = 3;
    }
    if(pList->current->isHead == true){
        //if the current pointer is the head we must insert after the head
        //in order to maintain list structure
        //we must also consider if the head has a next or not
        if(pList->current->next != NULL){
            //first give the item to the node
            nodePool.node_array[Now].item = pItem;
            //next update the heads nexts previous
            pList->current->next->prev = &nodePool.node_array[Now];
            //and update our nodes next
            nodePool.node_array[Now].next  = pList->current->next;
            //now we can update the heads pointer
            pList->current->next = &nodePool.node_array[Now];
            //and our nodes prev
            nodePool.node_array[Now].prev = pList->current;
            //now we are free to swap nodes
            take_node();
            //and update the list length
            pList->list_length++;

        //the head is the only item in the list
        }else{
            //update the new nodes item
            nodePool.node_array[Now].item = pItem;
            //update the heads next pointer
            pList->current->next = &nodePool.node_array[Now];
            //link the new node to the head via the prev pointer
            nodePool.node_array[Now].prev = pList->current;
            //now that we have used current we may update it
            pList->current = &nodePool.node_array[Now];
            //update the tail pointer in this case
            pList->tail = pList->current;
            //now we are free to swap the nodes in the nodepool
            take_node();
            //update the length of the list 
            pList->list_length++;

        }
    //the current node is not the head
    }else{
            //first give the item to the node
            nodePool.node_array[Now].item = pItem;
            //next we need to set the currents prev next pointer
            pList->current->prev->next = &nodePool.node_array[Now];
            //now we update our nodes prev
            nodePool.node_array[Now].prev = pList->current->prev;
            //now we worry about currents pointers
            pList->current->prev = &nodePool.node_array[Now];
            //and finally update the nodes pointer
            nodePool.node_array[Now].next = pList->current;
            //now we are free to swap nodes
           take_node();
            //and update the list length
            pList->list_length++;
    }
    return 0;
}

// Adds item to the end of pList, and makes the new item the current one. 
// Returns 0 on success, -1 on failure.
int List_append(List* pList, void* pItem){
//check for available nodes here
    if(Nodes_inUse == 100){
        return -1;
    }
//use the lists tail pointer to update the new node
    //update the new nodes item
    nodePool.node_array[Now].item = pItem;
    //now update the pointers
    nodePool.node_array[Now].prev = pList->tail;
    //update tails next pointer
    pList->tail->next = &nodePool.node_array[Now];
    //now that we have used the tail pointer we must update it
    pList->tail = pList->tail->next;
    //and the new current
    pList->current = pList->tail;
    //make sure to swao the nodes
    take_node();
    //update list length
    pList->list_length++;
    return 0;
}

// Adds item to the front of pList, and makes the new item the current one. 
// Returns 0 on success, -1 on failure.
int List_prepend(List* pList, void* pItem){
    if(Nodes_inUse == 100){
        return -1;
    }
    if(pList->head->next != NULL){
        //first give the item to the node
        nodePool.node_array[Now].item = pItem;
        //next update the heads nexts previous
        pList->head->next->prev = &nodePool.node_array[Now];
        //and update our nodes next
        nodePool.node_array[Now].next  = pList->head->next;
        //now we can update the heads pointer
        pList->head->next = &nodePool.node_array[Now];
        //and our nodes prev
        nodePool.node_array[Now].prev = pList->head;
        //now we are free to swap nodes
        take_node();
        //and update the list length
        pList->list_length++;

    }else{
        //update the new nodes item
        nodePool.node_array[Now].item = pItem;
        //now update the pointers
        nodePool.node_array[Now].prev = pList->head;
        //update tails next pointer
        pList->head->next = &nodePool.node_array[Now];
        //and the new current
        pList->current = &nodePool.node_array[Now];
        //update the tail in this case
        pList->tail = pList->current;
        //make sure to swao the nodes
        take_node();
        //update list length
        pList->list_length++;
    }
    return 0;
}

// Return current item and take it out of pList. Make the next item the current one.
// If the current pointer is before the start of the pList, or beyond the end of the pList,
// then do not change the pList and return NULL.
void* List_remove(List* pList){
    if(pList->state_of_current == LIST_OOB_END || pList->state_of_current == LIST_OOB_START){
        return NULL;
    }
    //if the head is the current item in the list
    //then we do nothing and return NULL
    if(pList->current->isHead == true ){
        return NULL;
    //the node is not the head
    }else{
        //if the node is the not the last item in the list
        if(pList->current->next != NULL){
            void * returned = pList->current->item;
            struct Node_s *temp = pList->current;
            //we have established that the node has a next
            //we do not need to check the state of the previous pointer as if the node is not the head
            //it is guaranteed to have at least one previous even if that previous is the head
            //we begin by skipping over the list with its previous node
            pList->current->prev->next = pList->current->next;
            // the next node still points to our node via the previous pointer
            //we fix that by skipping over
            pList->current->next->prev = pList->current->prev;
            //now our node is free to be wiped and placed back into the nodePool
            //we do this with a handy function call
            pList->current = pList->current->next;
            return_node(temp->index);
            //update the lists length
            pList->list_length--;
            return returned;


        //we are removing the tail of the list
        }else{
            void * returned = pList->current->item;
            //pass over the node
            pList->current->prev->next = NULL;
            //update the tail
            pList->tail = pList->current->prev;
            //now we can give the node back to the nodePool
            return_node(pList->current->index);
            pList->state_of_current = LIST_OOB_END;
            pList->current = NULL;
            //update the list length
            pList->list_length--;
            return returned;

        }
    }
    return NULL;
}

// Return last item and take it out of pList. Make the new last item the current one.
// Return NULL if pList is initially empty.
void* List_trim(List* pList){
     struct Node_s *temp = pList->tail;
     if(temp->isHead == true){
        return NULL;
    //means that there is at least one item in the list
     }else{
        pList->current = pList->tail->prev;
        pList->tail->prev->next = NULL;
        pList->tail = pList->tail->prev;
        return_node(temp->index);
        pList->list_length--;
        return temp->item;
     }

}

// Adds pList2 to the end of pList1. The current pointer is set to the current pointer of pList1. 
// pList2 no longer exists after the operation; its head is available
// for future operations.
void List_concat(List* pList1, List* pList2){
    //conect the lists via the pointers provided
    pList1->tail->next = pList2->head->next;
    pList2->head->next->prev = pList1->tail;

    pList1->tail = pList2->tail;
    pList1->list_length = pList1->list_length + pList2->list_length;
    //now we must give the second list's head back
    return_head(pList2->head->index);
}

// Delete pList. pItemFreeFn is a pointer to a routine that frees an item. 
// It should be invoked (within List_free) as: (*pItemFreeFn)(itemToBeFreedFromNode);
// pList and all its nodes no longer exists after the operation; its head and nodes are 
// available for future operations.
typedef void (*FREE_FN)(void* pItem);
void List_free(List* pList, FREE_FN pItemFreeFn){
    pList->current = pList->tail;
    while(pList->current->isHead == false){
        (*pItemFreeFn)(pList->current->item );
        pList->current->next = NULL;
        pList->current->inUse = false;
        pList->current = pList->current->prev;
        //give our node back to the list
        return_node(pList->current->next->index);
    }//the while loop stops when we reach the head so we need to delete that as well
    return_head(pList->current->index);
    pList->current = NULL;
    pList->state_of_current = 3;
    pList->tail = NULL;
    pList->head = NULL;
}

// Search pList, starting at the current item, until the end is reached or a match is found. 
// In this context, a match is determined by the comparator parameter. This parameter is a
// pointer to a routine that takes as its first argument an item pointer, and as its second 
// argument pComparisonArg. Comparator returns 0 if the item and comparisonArg don't match, 
// or 1 if they do. Exactly what constitutes a match is up to the implementor of comparator. 
// 
// If a match is found, the current pointer is left at the matched item and the pointer to 
// that item is returned. If no match is found, the current pointer is left beyond the end of 
// the list and a NULL pointer is returned.
// 
// If the current pointer is before the start of the pList, then start searching from
// the first node in the list (if any).
typedef bool (*COMPARATOR_FN)(void* pItem, void* pComparisonArg);
void* List_search(List* pList, COMPARATOR_FN pComparator, void* pComparisonArg){
    if(pList->state_of_current == LIST_OOB_START){
        pList->current = pList->head;
       pList->state_of_current = 3;
    }
    struct Node_s *temp = pList->current;
    while(temp != NULL){
        if( ((*pComparator)(temp->item,pComparisonArg)) == 1 ){
            return temp->item;
        }else{
            temp = temp->next;
            pList->current = temp;
        }
    }
    pList->state_of_current == LIST_OOB_END;
    return NULL;
}
