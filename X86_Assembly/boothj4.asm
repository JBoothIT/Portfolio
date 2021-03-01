TITLE Assignment Four (booth4.asm)
; Name: Jeremy Booth
; BlazerID: MedivalJ
; Class: CS330
; Creation Date: 4/8/2013
; Last Modified: 4/16/2013
;-------------------------------------------------------------------------
;-Description: This program inserts 32-bit signed integer values into an -
;-array, sorts these values using bubble sort and prints the sorted      -
;-values to the console.                                                 -
;-------------------------------------------------------------------------
INCLUDE Irvine32.inc
.data
prompt BYTE "Please enter a value(0 to exit)", 0dh, 0ah, 0
print BYTE "Success!", 0dh, 0ah, 0                        
comma BYTE ", "                        ;comma
count DWORD 0                          ;counter
array DWORD 100 dup(?)                 ;array 
PDWORD TYPEDEF PTR DWORD               ;defines the pointer type
pArray PDWORD array                    ;pointer for the array
.code
main PROC
         mov       ecx, count          ;ecx = count
         call readIn                   ;call readIn
         call bubbleSort               ;call bubbleSort
         call display                  ;call display
exit
main ENDP
;-------------------------------------------------------------------------
;-This section read in values and inserts them into the array            -
;-------------------------------------------------------------------------
readIn PROC
         cmp       ecx, TYPE array     ;if ecx == array.length
         je        return              ;jump to return if equal
         mov       edx, OFFSET prompt
         call      WriteString         ;prompt user
         call      ReadInt             ;read input
         cmp       eax, 0              ;if eax == 0
         jz        return              ;jump to return
         mov       esi, OFFSET array
         mov       [esi + ecx] , eax   ;insert value eax in array[ecx]
         inc       ecx                 ;ecx++
         jmp       readIn              ;jump to readIn
return:
         ret                           ;return
readIn ENDP
;-------------------------------------------------------------------------
;-This section performs the bubbleSort algorithm, which sort the values. -
;-------------------------------------------------------------------------
bubbleSort PROC
         mov       ecx, TYPE array     ;ecx = array.length
         dec       ecx                 ;ecx--
L1: 
         push      ecx                 ;push ecx on the stack
         mov       esi, pArray         ;point to first value in array
L2: 
         mov       eax, [esi]          ;eax = array value
         cmp       [esi + 4], eax      ;if [esi + 4] >= eax
         jg L3                         ;jump to L3
         xchg      eax, [esi + 4]      ;exchange eax and [esi + 4]
         mov       [esi], eax          ;[esi] = eax
L3: 
         add       esi, 4              ;esi = esi + 4
         loop      L2                  ;loop to L2
         pop       ecx                 ;pop ecx off the stack
         loop      L1                  ;loop to L1
L4:
         ret                           ;return
bubbleSort ENDP
;-------------------------------------------------------------------------
;-This section displays the values within the array.                     -
;-------------------------------------------------------------------------
display PROC
         mov       edx, OFFSET print
         call      WriteString         ;Write success meessage to console
         mov       ecx, count          ;ecx = count
         mov       ebx, 0              ;ebx = 0
L5:
         cmp       ebx, ecx            ;if ebx == ecx
         je        L6                  ;jump to L6
         mov       eax,[esi, ebx]      ;eax = array[ebx]
         call      WriteInt            ;write the value of eax
         mov       edx, OFFSET comma
         call      WriteString         ;insert a comma after value
         inc       ebx                 ;ebx++
L6:
         ret                           ;return
display ENDP
END main