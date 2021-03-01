#
# Tello Python3 Control Demo
#
# http://www.ryzerobotics.com/
#
# 1/1/2018
#
# Modified by Seth Lewis for Rama Ramaraju
# University of Alabama at Birmingham CS420 Software Engineering
#
# program has been adapted to accept strings piped from java for SDK control
# of Tello drone
#
# 7/2/2019

import threading
import socket
import sys
#  import time

host = ''
port = 9000
port_state = 8890
local_video_port = 11111
locaddr = (host, port)
#  videoaddr = (host, local_video_port)

# Create UDP sockets
sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
#  socket_video = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

tello_address = ('192.168.10.1', 8889)

sock.bind(locaddr)
#  socket_video.bind(videoaddr)


def recv():
    #  count = 0
    while True:
        try:
            data, server = sock.recvfrom(1518)
            sys.stdout.write(data.decode(encoding="utf-8") + '\n')
            sys.stdout.flush()
        except Exception:
            sys.stdout.write('\nExit . . .\n' + '\n')
            sys.stdout.flush()
            break


# recvThread create
recvThread = threading.Thread(target=recv)
recvThread.start()

while True:

    try:
        msg = sys.stdin.readline().strip()

        if not msg or 'end' in msg:
            sys.stdout.write('Terminating Operations...' + '\n')
            sys.stdout.flush()
            sock.close()
            break

        # Send data
        msg = msg.encode(encoding="utf-8")
        sent = sock.sendto(msg, tello_address)
    except KeyboardInterrupt:
        sys.stdout.write('\n . . .\n' + '\n')
        sys.stdout.flush()
        sock.close()
        break
