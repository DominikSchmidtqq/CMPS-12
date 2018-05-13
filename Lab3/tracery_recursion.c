#include <stdio.h>
#include <stdlib.h>

char* make_string_from(char* from, int count) {
	/* TODO 2 */
    char* array = malloc(count + 1);
    for (int i = 0; i < count; i++) {
        array[i] = from[i];
    }




    return  array;
	return NULL;
	/* TODO 2 */
}

int main(int argc, char** argv) {
	/* TODO 1 */
    for (int i = 0; i < argc; i++) {
        printf("%s\n", argv[i]);
    }
 	/* TODO 1 */

    char char_buffer[1000];
    int buffer_index;
    char* rule;
    char* expansion;

    while(getchar() != EOF) {
        if (getchar() == ":") {
            rule = make_string_from(char_buffer, buffer_index);
            buffer_index = 0;

            if(rule != NULL) {
                free(rule);
                rule = NULL;
            }
        } else if ((getchar() == ",") || (getchar() == "/n")) {
            expansion = make_string_from(char_buffer, buffer_index);
            buffer_index = 0;
            printf("A potential expansion of rule '%s' is '%s'\n", rule, expansion);

            if (expansion != NULL) {
                free(expansion);
                expansion = NULL;
            }
        } else {
            char_buffer[buffer_index] = getchar();
            buffer_index++;
        }

    }

  
 	/* TODO 3 */
 	/* TODO 3 */
}


