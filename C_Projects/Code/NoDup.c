#include<stdio.h>
// include other libraries if needed


int main()
{
int testArray[] = {29, 17, 8, 17, 9, 17, 29, 9};
//expected output
// 29,17,8,9
int size = sizeof(testArray) / sizeof(testArray[0]);
for (int i = 0; i < size; i++)
{
	printf("%d\t", testArray[i]);
}
printf("\n");

//implement your code here
for(int i = 0; i < size;i++)
{
	for (int j = i+1; j < size; j++)
	{
		if(testArray[i] == testArray[j])
		{
			for(int k=j; k < size; k++)
			{
				testArray[k] = testArray[k+1];
			}
			size--;
			j--;
		}
	}
}

for (int i = 0; i < size; i++)
{
	printf("%d\t", testArray[i]);
}
_getch();
 return 0;   
}