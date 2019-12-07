### Experimental Event Sourcing and CQRS with AxonFramework, Apache Kafka and Spring Framework

#### Storage
The Event Store is responsible for storing events. Since events are not to be modified (an event is a fact that something happened and facts cannot be modified), an Event Store should be optimized for appends. Event ordering plays a really important role in event-sourced systems - as many times as we are reconstructing our materialized state, we want to arrive at the same result. Axon Server is the default choice in Axon and the best way to start. It is highly optimized for storing/retrieving events. If you really want, alternatively, you might consider an RDBMS or a NoSQL database. Axon has out-of-the-box implementations that support JPA, JDBC and Mongo.

#### Event Replaying
In order to have materialized state reconstructed we need to trigger an event replay. That means that all events will be handed over to event handlers which will process them and build the current state of an application* so we can apply our business logic. This gives us a powerful mechanism to diagnose (debug) any problems that happened in the past, just replay events up to the point in time when the incident occurred and assess the current state. If we encounter a problem with application logic, we could fix it and replay events.

We should be cautious about event replays. Think of the case in which event handler contacts some external system (sends a bill to be paid). Certainly, we don’t want to replay this event. Gateways to other systems can be disabled in case of event replays in order to avoid this kind of problems. Axon Framework provides more granular control to event handlers in terms which events get replayed and which don’t. Also, it is possible to monitor the progress of the replay procedure.

#### Event Sourcing and CQRS
Event Sourcing is a natural fit with CQRS. Typically, the command model in a CQRS based architecture is not stored, other than by its sequence of events. The query model is continuously updated to contain a certain representation of the current state, based on these same events.

Instead of reconstructing the entire command model state, which would be a lengthy process, we separate the model in aggregates; parts of the model that need to be strongly consistent. This separation in aggregates makes models easier to reason about, more adaptable to change, and more importantly, it makes applications more scalable.

Reloading an aggregate state reliably involves re-applying all past events to an aggregate. You can imagine that, after appending a large number of events, this can become a lengthy process. To reduce loading time you can take a snapshot after a certain amount of time, or a number of events, or some other criteria. A snapshot in this context represents a current state of an aggregate. Next time we want to do a replay, we’d start from a snapshot and replay only the events that came after taking the snapshot. Snapshotting is usually an asynchronous process, so it does not interfere with regular event processing. In Axon, building snapshots is a matter of configuring when they should be taken. Figure 3 shows how snapshots relate to the events in the Event Store.



#### Upcasting
It is fair to assume that event schema is going to change in the future. Event handlers usually can cope with these changes but we pollute business logic unnecessarily. For such purposes, we introduce upcasters. Upcaster is a function which takes an event described using an old schema and transforms (upcasts) it to the event described using new schema (there are certain cases where you want to combine several events into a single event, or to spawn several events from a single event). It is not uncommon to have several upcasters for a single event type, we chain them into upcaster chain which is used during event replaying. Event replay mechanism takes events, runs them through corresponding upcaster chains and hand them over to the event handlers.

When upgrading event schema, there are certain practices that make transition run smoothly.

Don’t remove fields, deprecate them
Provide default value when introducing a new field
Don’t change event type, introduce a new event
When applying an Event Driven Architecture on a large scale, we recommend having a deprecation policy in place, which describes how teams are expected to deal with the depreciation of elements and under what circumstances these elements may be removed.

#### Security Concerns
Events are immutable (not to be updated) and all information that is stored in these events must be available all the time. With some regulations (like GDPR for example) a user can demand from the software system to “forget” one’s data. This request contradicts the nature of Event Sourcing. Fortunately, there is a technique called cryptographic erasure which uses cryptographic algorithms to encrypt sensitive data. Keys used for encrypting data are stored alongside the event store. So, when we want to store sensitive data into an event, we encrypt it with given keys. Each time we do an event replay, data is decrypted and handed over to the event handlers. Once a user requests data deletion, keys are deleted and sensitive data cannot be accessed ever again. Axon Server provides an enterprise pack to deal with this kind of challenges.

#### Benefits
The benefits of Event Sourcing are a bit scattered around this article, so it wouldn’t hurt to sum them up in this chapter.

The full history of interactions with our application is stored in the Event Store. We could apply various machine learning algorithms to extract information from these interactions that matter to our business.

Agile approach to building software systems requires that we should be able to adapt to any change coming along the way. Ability to replay the event stream from the beginning of time with new business logic means that we don’t have to worry about decisions we make (apart from which events are important to be stored), we can always fix the behaviour later. Introducing new views to our event stream means adding a new component with event listeners to the solution.

In order to comply with certain regulations, it is required from a software system to provide a full audit log. Event-sourced systems give us exactly that, full audit log and we don’t have to provide any additional information to the reviewer. One additional report that is built up from the event stream and we’re ready to go.

We all know how difficult it is to investigate an incident that happened in production. It requires a lot of logs digging and reasoning about the state that the system was at that point in time. Event sourcing gives us a way to replay events to a certain point in time and debug the application in a state in which the incident occurred. We don’t have to worry about whether we put the correct log level or whether we logged all necessary paths to figure the incident out.

If we figure out that the problem is in our business logic, fixing of the problem may be difficult. The event history that is already there is not meant to be altered, so if we want to change that we might want to use upcasters to change the event structure in the way it fixes the incident.
