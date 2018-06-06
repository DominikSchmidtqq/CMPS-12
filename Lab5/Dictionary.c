#include <stdlib.h>
#include <stdio.h>
#include "Dictionary.h"
#include "list.h"

typedef struct DictionaryObj {
    int  tableSize;
    int size;
    List** table;
} DictionaryObj;

typedef struct EntryObj {
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

//constructor for Dictionary, allocates memory and initializes it's variables
Dictionary newDictionary(int tableSize) {
    Dictionary dictionary = malloc(sizeof(DictionaryObj*));
    dictionary -> tableSize = tableSize;
    dictionary -> size = 0;
    dictionary -> table = calloc(tableSize, sizeof(List*));

    return dictionary;
}

//frees the Dictionary, first frees the internal table, then resets all variables
// and sets the Dictionary to NULL
void freeDictionary(Dictionary* pD) {
    if(pD != NULL && pD -> table != NULL) {
        for (int i = 0; i < pD->tableSize; i++) {
            if (pD->table[i] != NULL) {
                for (int j = 0; j < pD->table[i]->size; j++) {
                    Entry entry = (Entry) get(pD->table[i], j);
                    if (entry != NULL) {
                        freeEntry(&entry);
                        entry = NULL;
                    }
                    remove_node(pD->table[i], j); // Free the node as well
                }
                free(pD->table[i]);
                pD->table[i] = NULL;
            }
        }
    }

    pD -> tableSize = 0;
    pD -> size = 0;
    pD = NULL;
}

//constructor for Entry, allocates memory and initializes it's variables
Entry newEntry(char* key, char* value) {
    Entry entry = malloc(sizeof(Entry));
    entry -> key = key;
    entry -> value = value;

    return entry;
}
 
//frees the variables of Entry and sets it to NULL
void freeEntry(Entry* pE) {
    if (pE -> key != NULL) {
        free(pE -> key);
        pE -> key = NULL;
    }

    if (pE -> value != NULL) {
        free(pE -> value);
        pE -> value = NULL;
    }

    free(pE);
    pE = NULL;
}

//returns 0 if the table is empty, 1 if it is not empty
int isEmpty(Dictionary D) {
    if (D -> size == 0) {
        return 1;
    } else if (D -> size >= 1) {
        return 0;
    }
}

//returns the number of keys/values in the Dictionary
int size(Dictionary D) {
    return D -> size;
}

//adds a new key/value pair  into the Dictionary
void insert(Dictionary D, char* key, char* value) {
    int index = hash(D, key);

    if (D -> table[index] == NULL) {
        List list = make_list();
        Entry entry = newEntry(key, value);
        add(list, 0, entry);
        D -> table[index] = list;
        D -> size++;
    } else {
        for (int i = 0; i < D -> table[index] -> size; i++) {
            Entry currentEntry = (Entry)get(D -> table[index], i);
            if (strcmp(currentEntry -> key, key) == 0) {
                currentEntry -> value = value;
                return;
            }
        }
    }

    Entry entry = newEntry(key, value);
    add(D -> table[index], 0, entry);
    D -> size++;
}

//returns the value in the Dictionary associated with key
char* lookup(Dictionary D, char* key) {
    int index = hash(D, key);
    if (D -> table[index] == NULL) {
        return NULL;
    } else {
        for (int i = 0; i < D -> table[index] -> size; i++) {
            Entry entry = (Entry)get(D -> table[index], i);
            if (strcmp(entry -> key, key) == 0) {
                return entry -> value;
            }
        }
    }
    return NULL;
}

//deletes the Entry associated with the key
void delete(Dictionary D, char* key) {
    int index = hash(D, key);
    if (D -> table[index] == NULL) {
        return;
    } else {
        for (int i = 0; i < D -> table[index] -> size; i++) {
            Entry entry = (Entry)get(D -> table[index], i);
            if (strcmp(entry -> key, key) == 0) {
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
        for (int i = 0; i < D -> tableSize; i++) {
            if (D -> table[i] != NULL) {
                for (int j = 0; j < D -> table[i] -> size; j++) {
                    Entry entry = (Entry)get(D -> table[i], j);
                    if (entry != NULL) {
                        free(&entry);
                        entry = NULL;
                    }
                    remove_node(D -> table[i], j);
                }
                free(D -> table[i]);
                D -> table[i] = NULL;
            }
        }
        free(D -> table[i]);
        D -> table = NULL;
    }
    D -> size = 0;
}

//prints the contents of the Dictionary into a file
void printDictionary(FILE* out, Dictionary D) {
    for (int i = 0; i < D -> tableSize; i++) {
        if (D -> table[i] != NULL) {
            for (int j = 0; j < D  -> table[i] -> size; j++) {
                fprintf(out, "Key=%s Value=%s\n", (Entry)get(D->table[i], j) -> key, (Entry)get(D->table[i], j) -> value);
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