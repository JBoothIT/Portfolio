TITLE Assignment Three - bit count(boothj3.asm)
; Name: Jeremy Booth
; BlazerID: MedivalJ 
; Class: CS330
; Creation Date: 4/1/2013
; Last Modified: 4/1/2013
;--------------------------------------------------------------------------
;-Description: This program takes a 32-bit integer as input. Then, it     -
;-finds the lowest, highest and total number of bits.                     -
;--------------------------------------------------------------------------
INCLUDE Irvine32.inc
.data
prompt BYTE "Please enter a value: ", 0dh, 0ah, 0
min BYTE "Min: ", 0dh, 0ah, 0          ;min bit value
max BYTE "Max: ", 0dh, 0ah, 0          ;max bit value
total BYTE "Total: ", 0dh, 0ah, 0      ;total number of bits
nl BYTE " ", 0dh, 0ah, 0               ;newline
value DWORD ?                          ;stores the 32-bit int value
count DWORD 32                         ;stopping point for shift operations
index DWORD 0                          ;index for shift operation loop
;-------------------------------------------------------------------------
;-This section performs the operations required to find the min and max  -
;-and print their values.                                                -
;-------------------------------------------------------------------------
.code
main PROC
         mov       edx, OFFSET prompt  ;prompt for value
         call      WriteString         
         call      ReadInt             ;read in 32-bit integer
         mov       ebx, eax            ;ebx = eax
         bsf       eax, ebx            ;eax = right most bit in ebx
         mov       edx, OFFSET min     
         call      WriteString
         call      WriteInt            ;print min bit value
         mov       edx, OFFSET nl      ;newline
         call      WriteString
         mov       edx, OFFSET max
         call      WriteString                       
         bsr       eax, ebx            ;eax = left most bit in ebx
         call      WriteInt            ;print max bit value
         mov       ebx, 0              ;ebx = 0
;-------------------------------------------------------------------------
;-This section performs a loop which shifts through the entire 32-bit    -
;-integer and counts total number of 1 bits                              -
;-------------------------------------------------------------------------
L1:
         cmp       ebx, count          ;if ebx == count
         je        L2                  ;jump to L2
         shr       eax, 1              ;shift bit in eax right 1
         adc       ecx, 0              ;add carry flag to ecx. 
         inc       ebx                 ;ebx++ or ebx = ebx + 1
         jmp       L1                  ;jump to L1
L2:
         mov       eax, ecx            ;eax = ecx
         mov       edx, OFFSET nl      
         call      WriteString         ;newline
         mov       edx, OFFSET total
         call      WriteString
         call      WriteInt            ;Write total number of 1 bits 
         mov       edx, OFFSET nl
         call      WriteString         ;newline
         exit                          ;Exit
main ENDP
END main