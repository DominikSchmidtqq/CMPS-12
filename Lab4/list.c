#include <stdlib.h>
#include "list.h"
#include <stdio.h>
#include <assert.h>

Node* make_node(void* data, Node* next){
	/* 
	* TODO 2
	*/

    //allocates memory for a new node
    //sets the data pointer to the argument that was passed
    //sets the next pointer to the argument that was passed
    Node* node = malloc(sizeof(Node));
    node -> data = data;
    node -> next = next;

    return node;
	/* 
	* TODO 2
	*/ 
}

List* make_list(){
	/* 
	* TODO 2
	*/

    //allocates memory for a new list
    //sets the head pointer to NULL
    //sets the size pointer to 0
	List* list = malloc(sizeof(List));
    list -> head = NULL;
    list -> size = 0;

	return list;
	/* 
	* TODO 2
	*/ 
}

void free_node(Node* node){
	
	/* 
	* TODO 2
	*/
    //checks if data in points to null
	if (node -> data != NULL) {
        //free the data pointer and set it to null
        free(node -> data);
        node -> data = NULL;
    }

    //checks if node is null
    if (node != NULL) {
        //free node and set it to null
        free(node);
        node = NULL;
    }
	/* 
	* TODO 2
	*/ 
}

void free_list(List* list) {

	/* 
	* TODO 2
	*/

    //gets the head of the list and creates a new Node struct
    Node* current = list -> head;
    Node* previous = NULL;

	for (int i = 0; i < list -> size; i++) {
        //loops through the list
        if (current != NULL) {
            //if the current Node is not NULL
            //sets the placeholder node to the current node
            //sets the current node to the next pointer of the current node
            //frees the previous node and sets it equal to NULL
            previous = current;
            current = current -> next;
            free_node(previous);
            previous = NULL;
        }
    }
	/* 
	* TODO 2
	*/ 
}

void add(List* list, int index, void* data) {
	
	/* 
	* TODO 2
	*/

    //if index is either less than 0 or bigger than the size of the list
    //asserts "Index is out of bounds"
    assert( !(index > list->size || index < 0) && "Index is out of bounds");

    if (list -> size == 0) {
        //if the list is empty
        //sets the head pointer to a new Node, passing data and NULL as arguments
        //increments the size of the list
        list -> head = make_node(data, NULL);
        list -> size++;
    } else {
        //gets the head of the list
        Node *current = list->head;
        for (int i = 0; i < index - 1; i++) {
            //loops through the list
            //sets the current node equal the the next pointer of current
            current = current->next;
        }

        //creates a new node with arguments data and the next node relative to current
        //sets the next pointer of the current node to the new node
        //increments the size
        Node* node = make_node(data, current -> next);
        current -> next = node;
        list -> size++;
    }
	/* 
	* TODO 2
	*/ 
}

void* get(List* list, int index){
	/* 
	* TODO 2
	*/

    //if index is either less than 0 or bigger than the size of the list
    //asserts "Index is out of bounds"
    assert( !(index >= list->size || index < 0) && "Index is out of bounds");

    //gets the head of the list
    Node* current = list -> head;

    for (int i = 0; i < index; i++) {
        //loops through list up to index
        //sets current node to the next pointer of the current node
        current = current -> next;
    }

    return current -> data;
	/* 
	* TODO 2
	*/ 
}

void set(List* list, int index, void* data) {
	
	/* 
	* TODO 2
	*/

    //gets the head of the list
	Node* current = list -> head;

    for (int i = 0; i < index; i++) {
        //loops through the list up to index
        //sets the current node to the next pointer of current
        current = current -> next;
    }

    //sets the data pointer of current to the argument
    current -> data = data;

	/* 
	* TODO 2
	*/ 
}
