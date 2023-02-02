output: main.o list.o
	gcc list.o main.o -o output

main.o: main.c
	gcc -c main.c
list.o: list.c list.h
	gcc -c list.c 
clean: 
	rm *.o output