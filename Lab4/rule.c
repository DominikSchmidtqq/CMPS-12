
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "list.h"
#include "rule.h"
#include "helpers.h"

Rule* make_rule(char* key) {
  /*
    TODO 3
  */
    //constructor for rule, allocates memory for a new rule,
    //sets the key pointer to the argument and
    //sets the expansion pointer to a new linked list
    Rule* rule = malloc(sizeof(Rule));
    rule -> key = key;
    rule -> expansions = make_list();

    return rule;
  /*
    TODO 3
  */
}

void free_rule(Rule* rule) {
  /*
    TODO 3
  */
    if (rule -> key != NULL) {
        //if the key pointer of the rule is not NULL
        //frees the key pointer and
        //sets the key pointer to NULL
        free(rule -> key);
        rule -> key = NULL;
    }

    if (rule -> expansions != NULL) {
        //if the expansion pointer of the rule is not NULL
        //frees the expansion by freeing the linked list and
        //sets the expansion pointer to NULL
        free_list(rule -> expansions);
        rule -> expansions = NULL;
    }
  /*
    TODO 3
  */
	
}


List* read_grammar(char* filename) {
   
  /*
   * TODO 4A
   */ 
    List* grammar = make_list();
  /* 
   * TODO 4A
   */
  FILE* input_file = fopen(filename,"r");
  char buffer[1000];
  
  int number_of_expansions = 0;
  int buffer_index = 0;

  int number_of_rules = 0;
  for (char current = fgetc(input_file); current != EOF; current = fgetc(input_file)){
      if (current == ':'){
      
	  
      char* key = calloc(buffer_index+1,sizeof(char));
      memcpy(key,buffer,buffer_index);
      /*
       * TODO 4B
       */ 
	   Rule* rule = make_rule(key);
       add(grammar, grammar -> size, rule);
      //Construct a new Rule* and add it to grammar 
      /*
       * TODO 4B
       */ 
      buffer_index = 0;
    }
    else if (current == ',' || current == '\n') {
      char* expansion = calloc(buffer_index+1,sizeof(char));
      memcpy(expansion,buffer,buffer_index);
		
      /*
       * TODO 4C
       */
      Rule* rule = get(grammar, grammar -> size - 1);
      add(rule -> expansions, rule -> expansions -> size, expansion);
      //Get the last Rule* inserted into grammar and add expansion to it 
      /*
       *
       * TODO 4C
       */ 
      buffer_index = 0;
		 
    }
    else {
      buffer[buffer_index] = current;
      buffer_index++;
    }
  }
  fclose(input_file);

  
  /*
   * TODO 4D
   */ 
  return grammar; // replace this to return the grammar we just filled up
  /*
   * TODO 4D
   */ 
}



char* expand(char* text, List* grammar){

  /*
   * BONUS TODO
   */
	List* splitText = split(text, "#");
    List* possibleExpansion = make_list();
     for (int i = 0; i < splitText -> size; i++) {
         char* textAtIndex = copy_string(get(splitText, i));
         if (i % 2 == 0) {

             add(possibleExpansion, 0, textAtIndex);
         } else {
             for (int j = 0; j < grammar -> size; j++) {
                 List* currentList = get(grammar, j);
                 char* keyAtIndex = currentList -> key;

                 if (strcmp(textAtIndex, keyAtIndex) == 0) {
                     char* randomExpansion = get_random(currentList);
                     add(possibleExpansion, expand(randomExpansion, grammar));
                 }
             }
         }
     }
    char* result = join(possibleExpansion);
    free_list(possibleExpansion);
    free_list(splitText);
    return result;
  /*
   * BONUS TODO
   */
}

//Iterates through a grammar list and prints out all of the rules
void print_grammar(List* grammar){
  
  for (int ii = 0; ii < grammar->size; ii++){
    Rule* rule = get(grammar,ii);
    for (int jj = 0; jj < rule->expansions->size; jj++){
      printf("A potential expansion of rule '%s' is '%s'\n",rule->key, (char*) get(rule->expansions,jj));
    }
  }
  
}
