#include <stdio.h>
#include <stdlib.h>

char* make_string_from(char* from, int count) {
	/* TODO 2 */
    char* array = calloc(1,count + 1);
    for (int i = 0; i < count; i++) {
        array[i] = from[i];
    }

    return  array;
	/* TODO 2 */
}

int main(int argc, char** argv) {
	/* TODO 1 */
    for (int i = 0; i < argc; i++) {
        printf("%s\n", argv[i]);
    }
 	/* TODO 1 */

    char char_buffer[1000];
    int buffer_index = 0;
    char* rule = NULL;
    char* expansion = NULL;

    while(getchar() != EOF) {
        char character = (char)getchar();
        if (character == ':') {
            if(rule != NULL) {
                free(rule);
                rule = NULL;
            }

            rule = make_string_from(char_buffer, buffer_index);
            buffer_index = 0;

        } else if ((character == ',') || (character == '\n')) {
            if (expansion != NULL) {
                free(expansion);
                expansion = NULL;
            }

            expansion = make_string_from(char_buffer, buffer_index);
            buffer_index = 0;
            printf("An expansion of rule '%s' is '%s'\n", rule, expansion);

        } else {
            char_buffer[buffer_index] = character;
            buffer_index++;
        }
    }

    free(rule);
    free(expansion);
    rule = NULL;
    expansion = NULL;

 	/* TODO 3 */
 	/* TODO 3 */
}


