package com.xiledcore.server.api.event;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * The elusive {@link EventLoop} class. This class keeps track of the event queue, and also provides
 * accessors so that these instances can be manipulated.
 * 
 * @author Xiledcore
 */
public final class EventLoop implements Runnable {

	/*
	 * The logger for this class.
	 */
	private final static Logger logger = Logger.getLogger(EventLoop.class
			.getName());

	/*
	 * The event queue instance. It's a blocking queue because it might be
	 * accessed by several other threads in other parts of the program.
	 */
	private final BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>();

	/*
	 * This {@link ConcurrentHashMap} instance connects the data for the event,
	 * and the event in mind, together. This is a bad way of doing things,
	 * because if an entry is submitted to the map with the same key as
	 * something which is already in the map, it will be overridden. This will
	 * definitely cause complications.
	 * 
	 * This instance and its contribution to the program as a whole can be seen
	 * as a placeholder for some better solution. If you have one in mind, feel
	 * free to contribute to this project and I will merge your changes with
	 * this code if it's good.
	 */
	private final Map<Event, EventData> eventData = new ConcurrentHashMap<>();

	/*
	 * This {@link ConcurrentHashMap} instance connects the event handlers to
	 * their respective events. It takes a Event.class and an {@link
	 * EventHandler} as its type parameters.
	 * 
	 * If you want to add an event to this program, you will also have to create
	 * an event handler which handles the event accordingly, and also an {@link
	 * EventData} instance so that the right type of data can be stored in it.
	 */
	private final Map<Class<? extends Event>, EventHandler<? extends Event>> eventHandlers = new ConcurrentHashMap<>();

	/*
	 * This method is called when submitted to the {@link ExecutorService} in
	 * the {@link ServerInitializer} class. It's an infinite loop, and takes an
	 * event from the queue and hands it over to its respective event handler.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
			try {
				if (Thread.interrupted()) {
					break;
				}

				Event event = eventQueue.take();
				EventHandler<? extends Event> eventHandler = eventHandlers
						.get(event.getClass());
				EventData data = eventData.get(event);
				eventData.remove(event);

				if (eventHandler != null) {
					eventHandler.notifyEvent(event, data);
				}
			} catch (InterruptedException e) {
				logger.log(Level.WARNING, "Something went wrong!", e);
			}
		}
	}

	/*
	 * This method adds an entry to the eventHandlers instance.
	 */
	public void addEventHandler(Class<? extends Event> eventType,
			EventHandler<? extends Event> eventHandler) {
		eventHandlers.putIfAbsent(eventType, eventHandler);
	}

	/*
	 * This method is used to submit events to the event queue. It's important
	 * that an event handler has been set <b>before</b> you submit the event
	 * itself, or else nothing will happen.
	 */
	public void enqueueEvent(Event event, EventData data) {
		eventQueue.add(event);

		// TODO: Find a better solution since the eventData map cannot contain
		// duplicate keys...
		eventData.put(event, data);
	}
}
