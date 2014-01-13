Behavior Mismatch Mediation Test Cases
---------------

When the collaborating systems differ in their message exchange behaviors, mediation is necessary.

This page collects some collaboration scenarios with behavior mismatches. These scenarios are used as test cases of the proposed method.


##Service Interaction Patterns (SIPs) Variations

[SIPs](http://math.ut.ee/~dumas/ServiceInteractionPatterns/)  represent a commonly recognised catalogue of typical behaviours of systems in process collaboration, and are regarded as well-established knowledge of choreography scenarios. By altering behaviours of participants in each pattern, a set of commonly encountered behaviour mismatch scenarios was derived. These scenarios with behaviour mismatches were modelled as BPMN coordinated choreographies. 

###Single-send/receive pattern

![Single-send](/cases/SIP/SIP-1-single-send.png)

Variation 1

![Single-send-var1](/cases/SIP/SIP-1-single-send-variation-1.png)

Variation 2

![Single-send-var2](/cases/SIP/SIP-1-single-send-variation-2.png)


###Send-receive pattern

![send-receive](/cases/SIP/SIP-2-send-receive.png)

Variation 1

client exchange messages with server. One loop-sender pattern, one loop-receiver pattern.

![send-receive-var1](/cases/SIP/SIP-2-send-receive-variation-1.png)


_Output_

    [Channel: channel [from=connector-DefaultOperation: client request, to=auto-agg-loopsender]
    , Channel: channel [from=auto-agg-loopsender, to=connector-DefaultOperation: server receive]
    , Channel: channel [from=connector-DefaultOperation: server response, to=auto-splitter]
    , Channel: channel [from=auto-splitter, to=connector-DefaultOperation: client receive]
    ]

Which means:

__Flow 1__: Client-request-connector -> aggregator -> Server-resequest-connector.

__Flow 2__: Server-response-connector -> splitter -> Client-response-connector.

Variation 2

![send-receive-var2](/cases/SIP/SIP-2-send-receive-variation-2.png)


###Racing-Incoming-Messages

![racing](/cases/SIP/SIP-4-racing-incoming-messages.png)


###One-to-many Send

![1-many](/cases/SIP/SIP-5-one-to-many-send.png)

Variation-1

![1-many-var1](/cases/SIP/SIP-5-one-to-many-send-variation-1.png)

###One-from-many Receive

![many-1](/cases/SIP/SIP-6-one-from-many-receive.png)

Variation-1

![many-1-var1](/cases/SIP/SIP-6-one-from-many-receive-variation-1.png)

Variation-2

![many-1-var2](/cases/SIP/SIP-6-one-from-many-receive-variation-2.png)


###One-to-many Send-Receive

![1-many-sr](/cases/SIP/SIP-7-one-to-many-send-receive.png)

Variation-1

![1-many-sr-var1](/cases/SIP/SIP-7-one-to-many-send-receive-variation-1.png)


###Multiple Responses

![multi-response](/cases/SIP/SIP-8-multi-responses.png)

Variation-1

![multi-response-var1](/cases/SIP/SIP-8-multi-responses-variation-1.png)

Variation-2

![multi-response-var2](/cases/SIP/SIP-8-multi-responses-variation-2.png)


###Contingent Requests

![contingent](/cases/SIP/SIP-9-contingent-requests.png)

Variation-1

![contingent-var1](/cases/SIP/SIP-9-contingent-requests-variation-1.png)


###Request with Referral

![req-ref](/cases/SIP/SIP-11-request-with-referral.png)

Variation-1

![req-ref-var1](/cases/SIP/SIP-9-contingent-requests-variation-1.png)


##Sell-constructed Complex Scenarios


###simpleLoop




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


##Real cases and variations

These scenarios are derived from a real case in hospital integration.

### case 1

CIS and RIS. CIS use two seperate tasks to receive response (accept vs. reject). RIS uses two sequenced tasks to receive order info, and two sequenced tasks to send report parts.

exclusive-receivers, multiple-senders, and sequenced-receivers.

![Case 1](http://photo.yupoo.com/jjfd/Dlv9KvKu/medish.jpg)

_Output_

    Solution [channels=``
    [Channel: channel [from=connector-Send Order Operation, to=auto-routingslip]
    , Channel: channel [from=auto-routingslip, to=connector-Receive Order]
    , Channel: channel [from=auto-routingslip, to=auto-splitter]
    , Channel: channel [from=auto-splitter, to=connector-Receive Order Item]
    , Channel: channel [from=connector-Send Response, to=auto-CBR]
    , Channel: channel [from=auto-CBR, to=connector-Receive Reject Operation]
    , Channel: channel [from=auto-CBR, to=connector-Receive Accept Operation]
    , Channel: channel [from=connector-Send Preliminary Report, to=auto-filter]
    , Channel: channel [from=auto-filter, to=auto-agg-multisender]
    , Channel: channel [from=auto-agg-multisender, to=connector-Receive Report Operation]
    , Channel: channel [from=connector-Send Supplement Report, to=auto-filter]
    ]

Which means

![mediation](/cases/Case 1-Mediation.png)

### case 2

CIS and RIS. variation is CIS now has the receive task for study completion. The order of completion and report is different in CIS and RIS. This needs resequencer.

![Case 2](http://photo.yupoo.com/jjfd/DlvcqqOS/medish.jpg)




