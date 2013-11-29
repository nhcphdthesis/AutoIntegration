AutoIntegration
===============

Automatic design integration solutions

finds behavior mismatch patterns, generate routers, and composite integration solution.

**Input**: the integration scenario modeled using BPMN collaboration diagram, the interface model describing information of actual interfaces including service-oriented operation and messages. The messages are semantically annotated using concepts from the domain ontology.

**Output**: the integration solution that addresses solution to message semantic and behaivor mismatches. Semantic translator is generated to execute ontology mappings from source ontology to target ontology. Enterprise Integration Pattern routers are generated to route messages to mediate the behavior mismatches.


Test Cases
---------------

###simpleLoop

client exchange messages with server. One loop-sender pattern, one loop-receiver pattern.

![SimpleLoop](/cases/SimpleLoop.png)

_Output_

[Channel: channel [from=connector-DefaultOperation: client request, to=auto-agg-loopsender]
, Channel: channel [from=auto-agg-loopsender, to=connector-DefaultOperation: server receive]
, Channel: channel [from=connector-DefaultOperation: server response, to=auto-splitter]
, Channel: channel [from=auto-splitter, to=connector-DefaultOperation: client receive]
]

Which means:

__Flow 1__: Client-request-connector -> aggregator -> Server-resequest-connector.

__Flow 2__: Server-response-connector -> splitter -> Client-response-connector.


###Simple Exclusive

one send task is connected to exclusive receive tasks. Should use CBR.

![SimpleExclusive](http://photo.yupoo.com/jjfd/Dlv9Kvwu/medish.jpg)

_Output_

Solution [channels=
[Channel: channel [from=connector-DefaultOperation: send, to=auto-CBR]
, Channel: channel [from=auto-CBR, to=connector-DefaultOperation: receive1]
, Channel: channel [from=auto-CBR, to=connector-DefaultOperation: receive2]
]

Which means:

__Flow 1__: inbound-endpoint-connector -> CBR -> two outbound-endpoint-connector.

###Single Sequence

one send task is connected to two sequenced tasks. Should use RoutingSlip.

![SimpleSequence](/cases/SimpleSequence.png)

_Output_

[channels=
[Channel: channel [from=connector-DefaultOperation: send, to=auto-routingslip]
, Channel: channel [from=auto-routingslip, to=connector-DefaultOperation: receive1]
, Channel: channel [from=auto-routingslip, to=connector-DefaultOperation: receive2]
]

Which means

__Flow 1__: source-connecotr -> routing-slip(target1, target2) -> two target-connectors

### complex1

process1 has two send tasks. They are tangled with two receive tasks in process 2, while one is also connected to process 3.

order-mismatch, multiple-receivers, and multiple-senders

![Complex 1](http://photo.yupoo.com/jjfd/Dlv9LoBE/medish.jpg)

_Output_

[Channel: channel [from=connector-Send1, to=auto-recipientlist]
, Channel: channel [from=auto-recipientlist, to=auto-filter]
, Channel: channel [from=auto-filter, to=auto-agg-multisender]
, Channel: channel [from=auto-agg-multisender, to=connector-Receive21]
, Channel: channel [from=auto-recipientlist, to=connector-receiveE3]
, Channel: channel [from=connector-Send2, to=auto-routingslip]
, Channel: channel [from=auto-routingslip, to=auto-filter]
, Channel: channel [from=auto-routingslip, to=connector-Receive22]
]

Which means:

__Flow 1__ and __Flow 2__ are intermingled: 

    send1-connector -> recipient-list -> p3-connector
                                     |-> filter -> aggregator -> p2-receive-connector
    send2-connector -> routing-slip -|
                                     |-> receive2-connector  


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



