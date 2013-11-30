AutoIntegration: Twoards automatic integration solution design using model-driven collaboration and interface property specifications
===============

This project aims to automatically design integration solutions given sufficient information about the general collaboration scenarios between systems, the interface properties (structure, message semantics, and message exchange behaviors), and correspondences between data concepts from different domain ontologies defined as ontology mappings.

Given these information, the software automatically finds behavior mismatch patterns, generate routers, and composite integration solution.

**Input**: the integration scenario modeled using BPMN collaboration diagram, the interface model describing information of actual interfaces including service-oriented operation and messages. The messages are semantically annotated using concepts from the domain ontology.

**Output**: the integration solution that addresses solution to message semantic and behaivor mismatches. Semantic translator is generated to execute ontology mappings from source ontology to target ontology. Enterprise Integration Pattern routers are generated to route messages to mediate the behavior mismatches.

The overall method to generate integration solution from a well-defined BPMN collaboration model is as following:

![Solution](/cases/solution.png)

Data Semantics Mediation
-------------------------

Understanding the semantics of exchanged messages is a challenge to the computer. I use ontologies to define the information models within certain domains. The association between ontologies and message elements is achieved by semantic annotation.

Firstly, each message object in the interface model has a `semanticRef` attribute to refer to a clearly defined message ontology. The relations of elements and the overall message is explicated by the message ontology.

Secondly, each message object refers to a series of _SemanticAnnotation_ objects using the `annotations` property. A SemanticAnnotation object associates an element in the message schema to a concept in an ontology. This annotation explicates the meaning of a certain data element, while the relations between elements are defined within the ontology. 

for example: A semanticAnnotation looks like

    dataElementRef = "/orderInfro/patientName"
    conceptRef = "www.nhc.org/ontologies/HL7Ontology.owl#PatientName"

This annotation associates information conainted in the `patientName` element to the domain concept `PatientName`.

During generation of integration solution, the computer examines the two message objects of the two operations connected by a message flow in BPMN collaboration model.

1. for each concept referenced by the annotations of the target message, find if there is corresponding concept in the annotations of the source message. If these exists, this element has semantically equivalent counterparts in the source message, no matter what it is named.
2. If there does not exact concept in the source message, the computer loads ontology mappings defined in the knowledge base, and find equivalent (or sub, super, intersection, etc.) concepts for the specific concept defined in these mappings. This step derives a set of corresponding concepts.
3. Use the set to check if any of these concepts are referenced in the annotations of the source message. If there is, then the required cocnept in the target message does have counterparts in the source message, in the guarantee of existance of ontology mapping.
4. The computer generates a semantic translator for this message flow. The `mappingDefs` property of this translator references a list of mappings that are suitable to translate concepts (those we found before) in the source ontology (containing concepts referenced by the source message) to concepts in the target ontology (which contains concepts referenced by the target message).
5. The semantic translator is connected to the inbound-endpoint-connector of the message flow, and before all routers.
6. If the found corresponding concepts are not at all referenced by the source message annotation, probably user intervention is necessary to define new ontology mapping or new annotation.

In this project, the ontology definitions and mappings are stored in the Knowledge Base using Jena TDB.

Behavior Mismatch Mediation Test Cases
---------------

When the collaborating systems differ in their message exchange behaviors, mediation is necessary.

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



