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

  
 	/* TODO 3 */
 	/* TODO 3 */
}


