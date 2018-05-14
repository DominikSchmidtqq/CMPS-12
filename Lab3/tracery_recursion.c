#include <stdio.h>
#include <stdlib.h>

//create a new String from the buffer array up to count
char* make_string_from(char* from, int count) {
	/* TODO 2 */
    char* array = calloc(1,count + 1);
    for (int i = 0; i < count; i++) {
        array[i] = from[i];
    }

    return  array;
	/* TODO 2 */
}

//read in each character of grammar-story.txt and splits lines
//into rules and expansions
int main(int argc, char** argv) {
	/* TODO 1 */

    //prints each argument
    for (int i = 0; i < argc; i++) {
        printf("%s\n", argv[i]);
    }
 	/* TODO 1 */

    //create a new array to store characters in, an index for tracking character count
    //a rule string, and an expansion string
    char char_buffer[1000];
    int buffer_index = 0;
    char* rule = NULL;
    char* expansion = NULL;

    //get the first character in the input stream
    char character = (char)getchar();

    //loop through all characters until the end of input stream is reached
    while(character != EOF) {
        //current character is a ":" meaning that all the characters up to here
        //are a rule
        if (character == ':') {
            //clear rule if the rule pointer is not null
            if(rule != NULL) {
                free(rule);
                rule = NULL;
            }

            //create a rule string from the stored characters
            rule = make_string_from(char_buffer, buffer_index);
            //reset the buffer index to 0
            buffer_index = 0;
            //go to the next character
            character = (char)getchar();

        //current character is a "," or a new line, meaning that all the
        //characters up to here are an expansion
        } else if ((character == ',') || (character == '\n')) {
            //clear expansion if the expansion pointer is not null
            if (expansion != NULL) {
                free(expansion);
                expansion = NULL;
            }

            //make a new string from the stored characters up to index
            expansion = make_string_from(char_buffer, buffer_index);
            //reset buffer index
            buffer_index = 0;
            //print out the rule and expansion
            printf("An expansion of rule '%s' is '%s'\n", rule, expansion);
            //go to the next character
            character = (char)getchar();

        } else {
            //add the character to the array at index
            char_buffer[buffer_index] = character;
            //increment index
            buffer_index++;
            //go to the next character
            character = (char)getchar();
        }
    }

    //free rule and expansion, set both to null
    free(rule);
    free(expansion);
    rule = NULL;
    expansion = NULL;

 	/* TODO 3 */
 	/* TODO 3 */
}


