TITLE Assignment Two - sum (boothj2.asm)
; Name: Jeremy Booth
; BlazerID: MedivalJ 
; Class: CS330
; Creation Date: 3/14/2013
; Last Modified: 3/15/2013
;--------------------------------------------------------------------------
;-Description: This program takes in an N number of values, adds them     -
;-together, and divdes the resulting value by the count of the values     -
;-entered.                                                                -
;--------------------------------------------------------------------------
INCLUDE Irvine32.inc
.data
prompt BYTE "Please enter a value(0 to exit): ", 0dh, 0ah, 0
result BYTE "The sum of all integers is: ", 0dh, 0ah, 0
cSum BYTE "Number of values entered are: ", 0dh, 0ah, 0 
nl BYTE " ", 0dh, 0ah, 0               ;newline
count DWORD 0                          ;number of values counter.
value DWORD 0                          ;stores total added input value
.code
main PROC
         call      Clrscr
;--------------------------------------------------------------------------
;-This section contains the operations which prompt the user for input,   -
;-adds the input together and keeps track of the count of the values      -
;-entered.                                                                -
;--------------------------------------------------------------------------
L1: 
         mov       edx, OFFSET prompt  ;Offset prompt and mov to edx
         call      WriteString         ;Prompt the user to enter a value
         call      readInt             ;Value is read into eax
         jz        L2                  ;Jump to L2 if eax = 0 
         mov       ebx, eax            ;ebx = eax 
         add       value, ebx          ;value = value + ebx
         inc       count               ;count++ or count = count + 1
         jmp       L1                  ;jump back to top of L1
;--------------------------------------------------------------------------
;-This section contains the operations which divide the total sum of the  -
;-values entered by count and prints the resulting value to the console.  -
;-Also, the value of count is printed to the console.                     -
;--------------------------------------------------------------------------
L2:
		 cmp       count, 0
		 je        L3
         mov       eax, value          ;eax = value
         cdq
         idiv      count               ;eax = value/count
         mov       edx, OFFSET result  ;Offset result and mov to edx
         call      WriteString         ;Write message message in result 
         call      WriteInt            ;Write eax to console
         mov       edx, OFFSET nl      
         call      WriteString         ;newline
         mov       eax, count          ;eax = count
         mov       edx, OFFSET cSum    
         call      WriteString         ;print cSum message to console
         call      WriteInt            ;Write eax to console
         mov       edx, OFFSET nl
         call      WriteString         ;newline
         exit                          ;exit program
L3:      
         exit
main ENDP
END main