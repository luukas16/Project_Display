
//#include "list.h"
#include "list.c"
#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

// !IMPORTANT! Please note: What files that need to be included vary depending on the operating system
// If Ubuntu is being used than DO NOT include list.c as this will make the code not compile
// However if one is testing the code on a windows machine than please do include the list.c file
void free_func(void* item){
    item  = NULL;
}
bool search_fn(void* pItem, void* pComparisonArg){
    if(pItem == pComparisonArg){
        return true;
    }
    return false;
}
COMPARATOR_FN search = &search_fn;
void List_print(List *pList){
     //This sectin is for TESTING only
    struct Node_s *curr = pList->head->next;
    printf("new list:\n");
    while(curr != NULL){
        printf("Data: %d \n", curr->item);
        curr = curr->next;
    }   
}
void print_nodebank(int index){
    for(int i = 0;i<index;i++){
        printf("Node in position: %d, has value %d, and index %d:  ", i, nodePool.node_array[i].item, nodePool.node_array[i].index);
        printf("\n");
    }
}
FREE_FN free_1 = &free_func;
int main(){
//The testing of this code will be done in the order the function appear within the .h file
//thus we begin with the testing of List_create():
printf("Begin Testing: \n");
printf("Attempting to make more than 10 lists... \n");
    List *lists[10];
    List list = *List_create();
    List list2 = *List_create();
    List list3 = *List_create();
    List list4 = *List_create();
    List list5 = *List_create();
    List list6 = *List_create();
    List list7 = *List_create();
    List list8 = *List_create();
    List list9 = *List_create();
    List list10 = *List_create();
    lists[0] = &list;
    lists[1] = &list2;
    lists[2] = &list3;
    lists[3] = &list4;
    lists[4] = &list5;
    lists[5] = &list6;
    lists[6] = &list7;
    lists[7] = &list8;
    lists[8] = &list9;
    lists[9] = &list10;


    if(List_create() == NULL){
        printf("Correctly stopped the creation of the 11th list\n");
    }else{
        printf("Code failed, failed to handle the case when all heads are in use\n");
    }
    printf("making sure that all of the lists have a head node and tail along with their length being zero:\n");
    for(int i = 0; i < 10; i++){
        if(lists[i] != NULL){
            if(lists[i]->list_length != 0){
                printf("!ERROR! List number: %d, has failed the list length check. != 0\n", i+1);
                printf("!ERROR! list length = %d. but should be zero.\n", lists[i]->list_length);
            }
            if(lists[i]->current->isHead != true){
                printf("!ERROR! List number: %d, has failed the head check. The current node is not a head\n", i+1);
            }
            if(lists[i]->tail->isHead != true){
                printf("!ERROR! List number: %d, has failed the tail check. The tail should be the head at this time\n", i+1);
            }
        }
    }
    printf("If no error messages are displayed than the code has passed the head, length and tail checks. Proceeding...\n");
    printf("#\n");
    printf("#\n");
    printf("#\n");
    printf("Begin testing the insert_after function:\n");
    printf("Attempting to add more than 100 nodes to list 1\n");
    for(int i = 0; i<100;i++){
        List_insert_after(&list, i);
    }
    if(List_insert_after(&list, 27) != -1){
        printf("!ERROR! the code allowed the user to use more than 100 nodes\n");
    }
    printf("If no error messages are displayed then the code successfully blocked more than 100 nodes being used\n");
    printf("Testing the free list functionality....\n");
    printf("nodes in use before freeing: %d\n", nodes_in_use());
    List_free(&list, free_1);
    printf("nodes in use after freeing: %d\n", nodes_in_use());
    printf("Continuing to test functionality...\n");
    List_prev(&list2);
    if(list2.state_of_current == LIST_OOB_START){
        printf("List correctly identified being before the start of the list\n");
    }
    List_insert_after(&list2, 3);
    printf("State of current: %d should be 3\n", list2.state_of_current);
    List_print(&list2);
    printf("List curr: %d should be 3\n", List_curr(&list2));
    List_next(&list2);
    printf("State of current: %d should be 1\n", list2.state_of_current);
    if(list2.state_of_current == LIST_OOB_END){
        printf("List correctly identified being after the end of the list\n");
    }
    List_insert_after(&list2, 4);
    List_print(&list2);
    printf("Testing final possibility for List_insert_after, moving one space back and inserting betweeen two nodes\n");
    printf("List prev: %d", List_prev(&list2));
    List_insert_after(&list2, 27);
    List_print(&list2);
    printf("Done tesing the inser_after function...\n");
    printf("#\n");
    printf("#\n");
    printf("#\n");
    printf("Begin testing the Insert_before function\n");
    printf("Moving the curr pointer to before the list\n");
    List_prev(&list3);
    List_insert_after(&list3, 27);
    List_print(&list3);
    printf("List curr: %d should be 27\n", List_curr(&list3));
    printf("Code should have printed a list where one item is present after the head\n");
    printf("Now I will move the current pointer beyond the end of the list and attempt an insert.. \n");
    List_next(&list3);
    List_insert_after(&list3, 16);
    List_print(&list3);
    printf("The list should be 27 followed by 16\n...");
    printf("I will now move the current pointer to the head and see if the code does not insert before the head\n");
    List_prev(&list3);
    List_prev(&list3);
    List_insert_after(&list3, 46);
    List_print(&list3);
    printf("The list should be 46 followed 27 followed by 16\n...");
    printf("Done testing insert before...\n");
    printf("#\n");
    printf("#\n");
    printf("#\n");
    printf("Testing list append\n");
    printf("I will add to the list used in the previous section\n");
    List_append(&list3, 33);
    List_print(&list3);
    printf("List should be 46-27-16-33\n");
    printf("I will also test List_prepend here as it is quick\n");
    List_prepend(&list3, 100);
    List_print(&list3);
    printf("List should be 100-46-27-16-33\n");
    printf("Done testing prepend and append\n");
    printf("#\n");
    printf("#\n");
    printf("#\n");
    printf("Begin testing list remove\n");
    printf("First I will back up the current pointer and remove all the items in list 3\n");
    List_prev(&list3);
    List_prev(&list3);
    List_prev(&list3);
    List_prev(&list3);
    printf("List curr: %d \n", List_curr(&list3));
    printf("Removing %d from the list\n", List_remove(&list3));
    List_print(&list3);
    printf("Removing %d from the list\n", List_remove(&list3));
    List_print(&list3);
    printf("Removing %d from the list\n", List_remove(&list3));
    List_print(&list3);
    printf("Removing %d from the list\n", List_remove(&list3));
    List_print(&list3);
    printf("Removing %d from the list\n", List_remove(&list3));
    List_print(&list3);
    List_prev(&list3);
    printf("Attempting to remove the head of the list..\n");
    if(List_remove(&list3) != NULL){
        printf("!ERROR! code did not handle the attempt of removing the head correctly\n");
    }
    printf("Success... Potential error handled if no error message is displayed\n");
    printf("Attempting to remove before the list..\n");
    List_prev(&list3);    
    if(List_remove(&list3) != NULL){
        printf("!ERROR! code did not handle the attempt of removing the head correctly\n");
    }
        printf("Success... Potential error handled if no error message is displayed\n");
    printf("Attempting to remove after the list..\n");
    List_next(&list3); 
    List_next(&list3); 
    if(List_remove(&list3) != NULL){
        printf("!ERROR! code did not handle the attempt of removing the head correctly\n");
    }
    printf("If no error messages have appeared then the code was successful in handling the edge cases.\n");
    printf("Done testing List_remove and consequently List_prev and Next\n");
    printf("#\n");
    printf("#\n");
    printf("#\n");
    printf("Begin testing List_trim....\n");
    printf("First we need to populate a list..\n");
    for(int i =0; i < 5; i++){
        List_insert_after(&list4, i);
    }
    List_print(&list4);
    printf("Removing all items from the list, checking to see if the tail is updated correctly\n");
    for(int i =0; i < 5; i++){
        List_trim(&list4);
        void * temp = List_last(&list4);
        printf("List last ->: %d\n", temp);
    }
    List_print(&list4);
    printf("Observe how the tail pointer is updated correctly\n");
    printf("Done testing list trim\n");
    printf("#\n");
    printf("#\n");
    printf("#\n");
    printf("Begin testing List concat\n");
    for(int i =0; i < 5; i++){
        List_insert_after(&list5, i+6);
    } 
    List_print(&list5);
    printf("List 5 has length : %d\n", List_count(&list5)); 
    for(int i =0; i < 7; i++){
        List_insert_after(&list6, i+23);
    } 
    List_print(&list6);
    printf("List 6 has length : %d\n", List_count(&list6)); 
    List_concat(&list5, &list6);
    List_print(&list5);
    printf("List 5 has new length : %d\n", List_count(&list5));
    printf("The length of the newly created list after concatination should be 12\n");
    printf("Done testing List_concat\n");
    printf("#\n");
    printf("#\n");
    printf("#\n");
    printf("Final test on List search\n");
    List_first(&list5);
    printf("Searching for entry that should be present in list 5: 7\n");
    if(List_search(&list5, search, 7) == 7){
        printf("The item was found in the list\n");
    }else{
        printf("The item was not found in the list\n");
    }
    printf("Searching for entry that should NOT be present in list 5: 107\n");
    List_first(&list5);
    if(List_search(&list5, search, 107) == 107){
        printf("!ERROR! List_search should not have returned true\n");
    }else{
        printf("Correct, the item was not in the list and the code identified this\n");
    }
    printf("Testing of all functions concluded. Freeing lists....\n");
    printf("Nodes in use before free: %d\n", nodes_in_use());
    printf("Heads in use before free: %d\n", heads_in_use());
    List_free(&list2, free_1);
    List_free(&list3, free_1);
    List_free(&list4, free_1);
    List_free(&list5, free_1);
    List_free(&list7, free_1);
    List_free(&list8, free_1);
    List_free(&list9, free_1);
    List_free(&list10, free_1);
    printf("Nodes in use after free: %d\n", nodes_in_use());
    printf("Heads in use after free: %d\n", heads_in_use());

    
}