/* keeptime.c */
/* Lab 2 
DC Pankratz */
#include <stdio.h>
 
int main() {
  int i;
  FILE *fp;
  if ((fp = fopen("./times.dat", "w")) == NULL) {
     fprintf(stderr, "cannot open times.dat\n");
     return 1;
  }
 
  /* the file should grow every 2 seconds */
  for (i=0;i<500;i++) {
    sleep(2); 
    fprintf(fp, "Time elapsed = %4d sec\n", i*2);
    fflush(fp);
  }
  fclose(fp);
  return 0;
}
 
 
 
