AutoIntegration
===============

Automatic design integration solutions




Test Cases
---------------

###simpleLoop

client exchange messages with server. One loop-sender pattern, one loop-receiver pattern.


###Simple Exclusive

one send task is connected to exclusive receive tasks. Should use CBR.

###Single Sequence

one send task is connected to two sequenced tasks. Should use RoutingSlip.

### complex1

process1 has two send tasks. They are tangled with two receive tasks in process 2, while one is also connected to process 3.

order-mismatch, multiple-receivers, and multiple-senders

### complex 2

process1 has 3 send tasks. They are tangled with two receive tasks in process 2, while one is also connected to process 3.

order-mismatch, multiple-receivers, and multiple-senders

### case 1

CIS and RIS. CIS use two seperate tasks to receive response (accept vs. reject). RIS uses two sequenced tasks to receive order info, and two sequenced tasks to send report parts.

exclusive-receivers, multiple-senders, and sequenced-receivers.

### case 2

CIS and RIS. variation is CIS now has the receive task for study completion. The order of completion and report is different in CIS and RIS. This needs resequencer.





