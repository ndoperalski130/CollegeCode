/* simpbiff.c */
/* lab 2  */
 
#include <stdio.h>
#include <fcntl.h>
 
int main() {
  int fd;
  
 /* infinite loop - use ^C to exit this prog */
  for(;;)  {
    fd = open("mymail", O_RDONLY);   /binary file
    if (fd == -1) {
       fprintf(stderr, "cannot open file\n");
    }
    else {
        fprintf(stderr, "you got mail!\n");
        close(fd);
    }
    sleep(10);
   } /* for */
 return 0;
}
