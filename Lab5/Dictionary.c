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
typedef struct DictionaryObj* Dictionary;

/*
 *
 * YOUR FUNCTION IMPLEMENTATIONS GO BELOW HERE
 *
*/
Dictionary newDictionary(int tableSize) {
    Dictionary dictionary = malloc(sizeof(Dictionary));
    dictionary -> tableSize = tableSize;
    dictionary -> size = 0;
    dictionary -> table = make_list();
    return dictionary;
}


Entry newEntry(char* key, char* value) {
    Entry entry = malloc(sizeof(Entry));
    entry -> key = key;
    entry -> value = value;
    return entry;
}

void freeEntry(Entry* pE) {
    if (pE -> key != NULL) {
        free(pE -> key);
        pE -> key = NULL;
    }

    if (pE -> value != NULL) {
        free(pE -> value);
        pE -> value = NULL;
    }

    pE = NULL;
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