                                                          /* hello.c
/* lab2 */
 
#include <stdio.h>
 
int main()  {
  int i=0;
  while (i++ < 1000) {
     sleep(13);
     printf(" %ihello ",i);
     fflush(stdout);
  }
  return 0;
}
 
 
