package com.xiledcore.server.api.event;

/*
 * This abstract class represents an event handler. It takes a type parameter where <E extends Event>.
 * This means that in order for an event handler to be created, we have to specify what kind of event we want it to deal with
 * in the type parameters.
 * 
 * 
 * @author Xiledcore
 */
public abstract class EventHandler<E extends Event> {

	/*
	 * This method notifies the event, and passes the relevant information (in
	 * form of a {@link EventData} instance) to the Event instance.
	 */
	public abstract <E extends Event> void notifyEvent(E event, EventData data);
}