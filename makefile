# Variable Declarations

CC = gcc
CFLAGS = -c -Wall
OBJECT = main.o fibon.o hello.o
PROGRAM = uab_sh
#DEP = functions.h

# Rules
all: $(PROGRAM)

main.o: main.c 
	$(CC) $(CFLAGS) main.c

fibon.o: fibon.c
	$(CC) $(CFLAGS) fibon.c

hello.o: hello.c 
	$(CC) $(CFLAGS) hello.c

$(PROGRAM): $(OBJECT) 
	$(CC) $(OBJECT) -o $(PROGRAM)
exec:
	./uab_sh

clean:
	rm -rf uab_sh
	rm -rf *.o

#End of file