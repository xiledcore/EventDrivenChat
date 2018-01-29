package com.xiledcore.server.api.event.impl.handlers;

import com.xiledcore.server.api.event.Event;
import com.xiledcore.server.api.event.EventData;
import com.xiledcore.server.api.event.EventHandler;
import com.xiledcore.server.api.event.impl.EchoResponseEvent;

/*
 * The event handler for the {@link EchoResponseEvent}.
 * 
 * @author Xiledcore
 */
public class EchoResponseEventHandler extends EventHandler<EchoResponseEvent> {

	/*
	 * This method is called from inside the {@link EventLoop}'s loop when an
	 * event has been pulled from the queue, and the correct handler has been
	 * chosen.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xiledcore.server.api.event.EventHandler#notifyEvent(com.xiledcore
	 * .server.api.event.Event, com.xiledcore.server.api.event.EventData)
	 */
	@Override
	public <E extends Event> void notifyEvent(E event, EventData data) {
		event.onNotify(data);
	}

}
