package com.xiledcore.server.api.event;

/*
 * This abstract class represents an Event.
 * 
 * @author Xiledcore
 */
public abstract class Event {

	/*
	 * This is the method called by every event's event handler when the time
	 * comes.
	 */
	public abstract void onNotify(EventData data);
}
