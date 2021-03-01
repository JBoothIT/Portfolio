#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "functions.h"

//Clears the screen and prompts the user
void msg()
{
	static int init = 1;
	if(init)
	{
		printf("\e[1;1H\e[2J");
		init = 0;
	}
	printf("%s", "uab_sh > ");
	
}

//Reads in line
char *readIn(void)
{
	char param[101];
	fgets(param, 101, stdin);
	return param;
}

//parse string - still working out the kinks :)
char **parseString(char *cmd)
{
	char delim[] = " ";
	char* temp = strtok(cmd, delim);
	if (temp != NULL)
	{
		int i = 0;
		char** tokens = malloc(3 * sizeof(char*));
		while (temp != NULL)
		{
			tokens[i++] = temp;
			temp = strtok(NULL, " ");
		}
		for (i = 0; i < 3; i++)
		{
			printf("%s\n", tokens[i]);
		}
		return tokens;
	}
	else
		return NULL;
}

//Command
int command(char ** cmd)
{
	int pid;
	if (cmd[0] != NULL)
	{
		pid = fork();
		if (pid == 0)
		{
			/*if (cmd[0] == "fibonnaci" && cmd[1] != NULL)
			{
				fibon(cmd[1]);
			}
			else if (strcmp(cmd[0],"fibonnaci") != 0 && cmd[1] == NULL)
			{
				printf("Please enter a value: ");
				char *line;
				char **cmd;
				line = readLine();
				cmd = parseString(line);
				fibon(cmd[0]);
				free(line);
				free(cmd);
			}
			else if (cmd[0] == "hello")
			{
				hello();
			}*/
			exit(0);
			return 0;
		}
		else if (pid < 0)
		{
			perror("Something went wrong...");
			return 0;
		}
	}
	else
	{
		return 0;
	}
}


int main()
{
	char *line;
	char **cmd;
	int stat = 0;
	while (1)
	{
		msg();
		line = readIn();
		cmd = parseString(line);
		stat = command(cmd);
		if (stat == 1)
		{
			break;
		}
	}
	free(line);
	free(cmd);

	return 0;
}