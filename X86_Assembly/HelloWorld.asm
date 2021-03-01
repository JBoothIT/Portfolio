TITLE   Hello World
INCLUDE Irvine32.inc
.data
prompt BYTE "Hello, World!",0dh,0ah,0
.code
main PROC
call Clrscr
mov edx, OFFSET prompt
call WriteString
exit
main ENDP
end main