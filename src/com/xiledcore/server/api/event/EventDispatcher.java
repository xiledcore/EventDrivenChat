package com.xiledcore.server.api.event;

/*
 * This class provides certain other classes with an interface for communicating with the {@link EventLoop} instance,
 * more specifically: adding events to its event queue. This class exists because I would rather not
 * provide classes like the {@link PacketHandler} complete access to the {@link EventLoop} instance. It only
 * needs access to the enqueueEvent method, and it can do so indirectly through this class.
 * 
 * @author Xiledcore
 */
public final class EventDispatcher {

	/*
	 * The {@link EventLoop} instance which will be used to submit events.
	 */
	private final EventLoop loop;

	/*
	 * The constructor for the {@link EventDispatcher} class.
	 */
	public EventDispatcher(EventLoop loop) {
		this.loop = loop;
	}

	/*
	 * The actual method which submits an {@link Event} object to the event
	 * queue in the {@link EventLoop} class.
	 */
	public void dispatch(Event event, EventData data) {
		loop.enqueueEvent(event, data);
	}
}
