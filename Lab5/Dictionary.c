#include <stdlib.h>
#include <stdio.h>
#include "Dictionary.h"
#include "list.h"
#include <string.h>

//type definition for Dictionary
typedef struct DictionaryObj {
    //includes integers storing the size of the internal table and the number of key/value pairs
    //and an array of linked lists that stores Entries
    int  tableSize;
    int size;
    List** table;
} DictionaryObj;

//type  definition of Entry
typedef struct EntryObj {
    //includes Strings storing the key and value
    char* key;
    char* value;
} EntryObj;

// allows EntryObj* to be called Entry in this file
typedef struct EntryObj* Entry;

/*
 *
 * YOUR FUNCTION IMPLEMENTATIONS GO BELOW HERE
 *
*/

//constructor for Entry, allocates memory and initializes it's variables
Entry newEntry(char* key, char* value) {
    //allocates memory for the Entry and sets the key and value pointers to the arguments
    Entry entry = malloc(sizeof(Entry));
    entry -> key = key;
    entry -> value = value;

    return entry;
}

//frees the variables of Entry and sets it to NULL
void freeEntry(Entry* pE) {
    //sets entry to the passed Entry object pointer
    Entry entry = (*pE);

    if (entry -> key != NULL) {
        //if the key of the entry is not NULL, frees the key of the entry and sets the key to NULL
        free(entry -> key);
        entry -> key = NULL;
    }

    if (entry -> value != NULL) {
        //if the value of the entry is not NULL, frees the value of the entry and sets the value to NULL
        free(entry -> value);
        entry -> value = NULL;
    }

    //frees the entry and sets it to NULL
    free(entry);
    entry = NULL;
}

//constructor for Dictionary, allocates memory and initializes it's variables
Dictionary newDictionary(int tableSize) {
    //allocates memory for a Dictionary, sets the tableSize and size pointers of the Dictionary to the arguments
    Dictionary dictionary = malloc(sizeof(DictionaryObj*));
    dictionary -> tableSize = tableSize;
    dictionary -> size = 0;
    //allocates memory for an array of type List of size tableSize and sets the table pointer of Dictionary to the array
    dictionary -> table = calloc(tableSize, sizeof(List*));

    return dictionary;
}

//frees the Dictionary, first frees the internal table, then resets all variables
// and sets the Dictionary to NULL
void freeDictionary(Dictionary* pD) {
    //sets the passed Dictionary pointer to a Dictionary called dictionary
    Dictionary dictionary = (*pD);

    if (dictionary != NULL) {
        //if the dictionary is not empty
        if (dictionary -> table != NULL) {
            //if the array of linked lists of dictionary is not empty
            for (int i = 0; i < dictionary -> tableSize; i++) {
                //loops through the array of linked lists
                if (dictionary -> table[i] != NULL) {
                    //if the linked list in the array at the current index is not empty
                    for (int j = 0; j < dictionary -> table[i]->size; j++) {
                        //loops through the linked list at the current index in the array
                        //gets the item at the current index in the linked list and casts it to an Entry
                        Entry entry = (Entry)get(dictionary -> table[i], j);

                        if (entry != NULL) {
                            //if the entry is not empty
                            //frees the entry passing a pointer to entry as an argument, and sets entry to NULL
                            freeEntry(&entry);
                            entry = NULL;
                        }
                        //removes the node that previously stored the entry that was removed
                        remove_node(dictionary -> table[i], j);
                    }
                    //frees the linked list at the current index and sets the index in array to NULL
                    free(dictionary -> table[i]);
                    dictionary -> table[i] = NULL;
                }
            }
            //frees the array of the dictionary and sets it to NULL
            free(dictionary -> table);
            dictionary -> table = NULL;
        }
        //frees the dictionary and sets it as well as the pointer from the arguments to NULL
        free(dictionary);
        dictionary = NULL;
        pD = NULL;
    }
}

//returns 0 if the table is empty, 1 if it is not empty
int isEmpty(Dictionary D) {
    if (D -> size == 0) {
        //returns 1/true if the Dictionary is empty
        return 1;
    } else if (D -> size >= 1) {
        //return 0/false if the Dictionary is not empty
        return 0;
    }
}

//returns the number of keys/values stored  in the Dictionary
int size(Dictionary D) {
    //initializes a counter to 0
    int size = 0;

    for (int i = 0; i < D -> tableSize; i++) {
        //loops through the table array
        if (D -> table[i] != NULL) {
            //if the linked list at the current index in the array is not empty
            for (int j = 0; j < D->table[i]->size; j++) {
                //loops through the linked list at the current index and increments size
                size++;
            }
        }
    }

    return size;
}

//this needs to be here for things to compile
int hash(Dictionary D, char* key);

//adds a new key/value pair  into the Dictionary
void insert(Dictionary D, char* key, char* value) {
    //computes an index in the array of the dictionary by hashing the key parameter
    int index = hash(D, key);

    if (D -> table[index] == NULL) {
        //if the linked list in the table array of the dictionary at the computed index is empty
        //creates a new list, creates a new Entry with the key and value parameters
        List* list = make_list();
        Entry entry = newEntry(key, value);
        //adds the entry to the front of the list
        add(list, 0, entry);
        //sets the list at the computed index in the table array to the list storing the entry
        //increments the size of the number of keys/values stored
        D -> table[index] = list;
        D -> size++;
        //exits the method
        return;
    } else {
        //if the linked list in the table array of the dictionary at the computed index is not empty
        for (int i = 0; i < D -> table[index] -> size; i++) {
            //loops through the list at the computed index of the table array
            //gets the item at the current index of the list and casts it to an Entry
            Entry currentEntry = (Entry)get(D -> table[index], i);
            if (strcmp(currentEntry -> key, key) == 0) {
                //if the key of the current entry in the list is the same as the key parameter
                //sets the value of the current entry to the value parameter
                currentEntry -> value = value;
                //exits the method
                return;
            }
        }
    }

    //creates a new entry with the key and value parameters, adds the entry to the front of the list at the
    //computed index in the table array and increments the number of keys/values stored in the dictionary
    Entry entry = newEntry(key, value);
    add(D -> table[index], 0, entry);
    D -> size++;
}

//returns the value in the Dictionary associated with key
char* lookup(Dictionary D, char* key) {
    //computes an index in the array of the dictionary by hashing the key parameter
    int index = hash(D, key);

    if (D -> table[index] == NULL) {
        //if the table array at the computed index is empty, returns NULL
        return NULL;
    } else {
        //if the table array at the computed index is not empty
        for (int i = 0; i < D -> table[index] -> size; i++) {
            //loops through the list at the computed index of table array
            //gets the item at the current index of the list and casts it to an Entry
            Entry currentEntry = (Entry)get(D -> table[index], i);
            if (strcmp(currentEntry -> key, key) == 0) {
                //if the key of the current entry is the same as the key parameter
                //returns the value of the current entry
                return currentEntry -> value;
            }
        }
    }
    //if the key is not found in the table array of Dictionary, returns NULL
    return NULL;
}

//deletes the Entry associated with the key
void delete(Dictionary D, char* key) {
    //computes an index in the array of the dictionary by hashing the key parameter
    int index = hash(D, key);

    if (D -> table[index] == NULL) {
        //if the table array at the computed index is empty
        //exits the method
        return;
    } else {
        //if the table array at the computed index is not empty
        for (int i = 0; i < D -> table[index] -> size; i++) {
            //loops through the list at the computed index of table array
            //gets the item at the current index of the list and casts it to an Entry
            Entry currentEntry = (Entry)get(D -> table[index], i);
            if (strcmp(currentEntry -> key, key) == 0) {
                //if the key of the current entry is the same as the key parameter
                //gets the node associated with the key, removes it, frees it and decrements the number of keys/values stored
                //in the dictionary
                void* node = get(D -> table[index], i);
                free(node);
                remove_node(D -> table[index], i);
                D -> table[index] -> size--;
            }
        }
    }
}

//removes all Entries from the Dictionary
void makeEmpty(Dictionary D) {
    if ((D != NULL) && (D -> table != NULL)) {
        //if the dictionary is not empty and the table array of the dictionary is not empty
        for (int i = 0; i < D -> tableSize; i++) {
            //loops through the table array
            if (D -> table[i] != NULL) {
                //if the list at the current index in the table array is not empty
                for (int j = 0; j < D -> table[i] -> size; j++) {
                    //loops through the list at the computed index of table array
                    //gets the item at the current index of the list and casts it to an Entry
                    Entry currentEntry = (Entry)get(D -> table[i], j);
                    if (currentEntry != NULL) {
                        //if the current entry is not empty, frees the current entry and sets it to NULL
                        free(currentEntry);
                        currentEntry = NULL;
                    }
                    //removes the node at the current index in the list
                    remove_node(D -> table[i], j);
                }
                //frees the list at the current index in the table array of the dictionary and sets the list to NULL
                free(D -> table[i]);
                D -> table[i] = NULL;
            }
        }
        //frees the table array of the dictionary and sets it to NULL
        free(D -> table);
        D -> table = NULL;
    }
    //sets the number of keys/values stored in the dictionary to 0
    D -> size = 0;
}

//prints the contents of the Dictionary into a file
void printDictionary(FILE* out, Dictionary D) {
    for (int i = 0; i < D -> tableSize; i++) {
        //loops through the table array
        if (D -> table[i] != NULL) {
            //if list at the current index in the table array is not empty
            for (int j = 0; j < D  -> table[i] -> size; j++) {
                //loops through the list at the computed index of table array
                //gets the item at the current index of the list and casts it to an Entry
                //and prints the key and value of the current entry
                Entry currentEntry = (Entry)get(D -> table[i], j);
                fprintf(out, "%s %s\n", currentEntry -> key, currentEntry -> value);
            }
        }
    }
}

/*
 *
 * YOUR FUNCTION IMPLEMENTATIONS GO ABOVE HERE
 *
*/

/*
 * YOUR CODE GOES ABOVE THIS COMMENT
 * DO NOT ALTER THESE FUNCTIONS
 * THESE ARE THE THREE FUNCTIONS THAT WILL ALLOW YOU TO CONVERT 
 * A STRING INTO A VALID ARRAY INDEX
 * YOU WILL ONLY NEED TO CALL hash(Dictionary D, char* key)
*/

// rotate_left()
// rotate the bits in an unsigned int
unsigned int rotate_left(unsigned int value, int shift) {
   int sizeInBits = 8*sizeof(unsigned int);
   shift = shift & (sizeInBits - 1);
   if ( shift == 0 ) {
      return value;
   }
   return (value << shift) | (value >> (sizeInBits - shift));
}

// pre_hash()
// turn a string into an unsigned int
unsigned int pre_hash(char* input) {
   unsigned int result = 0xBAE86554;
   while (*input) {
      result ^= *input++;
      result = rotate_left(result, 5);
   }
   return result;
}

// hash()
// turns a string into an int in the range 0 to tableSize-1
int hash(Dictionary D, char* key){
   return pre_hash(key) % D->tableSize;
}