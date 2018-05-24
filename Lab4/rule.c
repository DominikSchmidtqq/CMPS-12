
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "list.h"
#include "rule.h"
#include "helpers.h"

Rule* make_rule(char* key){
  /*
    TODO 3
  */
  return NULL; // replace this
  /*
    TODO 3
  */
}

void free_rule(Rule* rule){
  /*
    TODO 3
  */
	
  /*
    TODO 3
  */
	
}


List* read_grammar(char* filename){
   
  /*
   * TODO 4A
   */ 
  //Construct a new List* called grammar that we will fill up in the following code
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
	   
      //Construct a new Rule* and add it to grammar 
      /*
       * TODO 4B
       */ 
      buffer_index = 0;
    }
    else if (current == ',' || current == '\n'){
      
      char* expansion = calloc(buffer_index+1,sizeof(char));      
      memcpy(expansion,buffer,buffer_index);
		
      /*
       * TODO 4C
       */ 
      //Get the last Rule* inserted into grammar and add expansion to it 
      /*
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
  return NULL; // replace this to return the grammar we just filled up
  /*
   * TODO 4D
   */ 
}



char* expand(char* text, List* grammar){

  /*
   * BONUS TODO
   */
	
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
