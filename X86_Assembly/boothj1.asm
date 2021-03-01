TITLE Simple Computation               (SimpleComp.asm)
; Name: Jeremy Booth
; BlazerID: MedivalJ
; Class: CS330
; Creation Date: 3/3/2013
; Last Modified: 3/4/2014
;--------------------------------------------------------------------------
;- Description: This program takes two values from input, uses them in    -
;- the equation A * B - (A+B)/(A-B) and prints the resulting value in the -
;- console.                                                               -
;--------------------------------------------------------------------------
INCLUDE Irvine32.inc
.data
prompt   BYTE "Enter a signed value: ",0dh,0ah,0
problem  BYTE "Error! Cannot divide by zero!", 0dh, 0ah, 0
result   BYTE "Result: ",0dh,0ah,0
A        DWORD ?
B        DWORD ?
.code
main     PROC
;--------------------------------------------------------------------------
;- This section contains the write and read funtions which prompt the     -
;- user to input a value for the variables A and B.                       -
;--------------------------------------------------------------------------
         call      Clrscr
         mov       edx,OFFSET prompt
         call      WriteString                   
         call      ReadInt
         mov       A, eax              ;A = eax                      
         mov       edx, OFFSET prompt  
         call      WriteString         
         call      ReadInt                       
         mov       B, eax              ;B = eax
;--------------------------------------------------------------------------
;- This section contains all of the operations to calculate each part of  -
;- the expression and return the overall result.                          -
;--------------------------------------------------------------------------
         mov       ebx, A              ;ebx contains A              
         sub       ebx, B              ;ebx = A - B
         cmp       ebx, 0              ;if ebx == 0, print error
         je        L1                  ;jump to L1 if equal to 0
         jne       L2                  ;jump to L2 if not equal to 0
L1: 
         mov       edx, OFFSET problem  
         call      WriteString         ;print error message
         exit                          ;exit the program if jump to L1
L2:
         mov       ecx, A              
         add       ecx, B              ;ecx = A + B 
         mov       eax, ecx
         cdq
         idiv      ebx                 ;eax = eax/ebx
         mov       ebx, eax
         mov       ecx, A
         mov       eax, B
         imul      ecx                 ;eax = eax * ecx
         sub       eax, ebx            ;eax = eax - ebx
         mov       edx, OFFSET result
         call      WriteInt            ;Result is printed to the console
         exit
main ENDP
END main