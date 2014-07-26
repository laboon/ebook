#include "stdlib.h"

void leakMem() {
  char* foo;
  foo = (char *) malloc (128);
}

int main() {
  leakMem();
}
