AutoIntegration
===============

Automatic design integration solutions

finds behavior mismatch patterns, generate routers, and composite integration solution.


Test Cases
---------------

###simpleLoop

client exchange messages with server. One loop-sender pattern, one loop-receiver pattern.

![SimpleLoop](http://photo.yupoo.com/jjfd/Dlv9KJZX/medium.jpg)


###Simple Exclusive

one send task is connected to exclusive receive tasks. Should use CBR.

![SimpleExclusive](http://photo.yupoo.com/jjfd/Dlv9Kvwu/medish.jpg)

###Single Sequence

one send task is connected to two sequenced tasks. Should use RoutingSlip.



### complex1

process1 has two send tasks. They are tangled with two receive tasks in process 2, while one is also connected to process 3.

order-mismatch, multiple-receivers, and multiple-senders

![Complex 1](http://photo.yupoo.com/jjfd/Dlv9LoBE/medish.jpg)


### complex 2

process1 has 3 send tasks. They are tangled with two receive tasks in process 2, while one is also connected to process 3.

order-mismatch, multiple-receivers, and multiple-senders

![Complex 2](http://photo.yupoo.com/jjfd/Dlv9L5tX/medish.jpg)

### case 1

CIS and RIS. CIS use two seperate tasks to receive response (accept vs. reject). RIS uses two sequenced tasks to receive order info, and two sequenced tasks to send report parts.

exclusive-receivers, multiple-senders, and sequenced-receivers.

![Case 1](http://photo.yupoo.com/jjfd/Dlv9KvKu/medish.jpg)

### case 2

CIS and RIS. variation is CIS now has the receive task for study completion. The order of completion and report is different in CIS and RIS. This needs resequencer.

![Case 2](http://photo.yupoo.com/jjfd/DlvcqqOS/medish.jpg)



