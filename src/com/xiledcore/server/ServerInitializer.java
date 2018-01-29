package com.xiledcore.server;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.xiledcore.server.api.event.EventDispatcher;
import com.xiledcore.server.api.event.EventLoop;
import com.xiledcore.server.api.event.impl.EchoResponseEvent;
import com.xiledcore.server.api.event.impl.handlers.EchoResponseEventHandler;

/*
 * This class acts as the bootstrap of the application. It starts up everything, and creates instances
 * of the classes required to set everything up.
 * 
 * It uses a {@link ExecutorService} to start the event loop which keeps track of the events, and handles them.
 * 
 * @author Xiledcore
 */
public final class ServerInitializer {

	/*
	 * The logger for this class.
	 */
	private static final Logger logger = Logger
			.getLogger(ServerInitializer.class.getName());

	/*
	 * This is the {@link ExecutorService} that only uses a single thread to
	 * process the events.
	 */
	private final ExecutorService eventLooper = Executors
			.newSingleThreadExecutor();

	/*
	 * This is the {@link ExecutorService} that is used for the {@link
	 * ServerManager} instance.
	 */
	private final ExecutorService serverLooper = Executors
			.newSingleThreadExecutor();

	/*
	 * The method which sets everything up. Since the {@link EventLoop} class is
	 * needed from the start, it is instantiated first.
	 */
	public void initialize() {
		EventLoop eventLoop = new EventLoop();

		addEventHandlers(eventLoop);

		Future<?> future = eventLooper.submit(eventLoop);

		EventDispatcher dispatcher = new EventDispatcher(eventLoop);
		ServerManager serverManager = new ServerManager(dispatcher);
		serverLooper.submit(() -> {
			serverManager.start();
		});

		logger.info("Server is up and running!");

		try {
			future.get();
			shutDown();
		} catch (InterruptedException e) {
			logger.log(Level.WARNING, "Something went wrong!", e);
		} catch (ExecutionException e) {
			logger.log(Level.WARNING, "Something went wrong!", e);
		}
	}

	/*
	 * This is the shutdown method for our ServerInitializer. When the event
	 * loop is done (TODO: make it end sometime), this method gets called.
	 */
	private void shutDown() {
		eventLooper.shutdownNow();
	}

	/*
	 * This method adds the event handlers for our events. Without adding the
	 * handlers, nothing will happen when events are submitted to the event
	 * queue.
	 * 
	 * @param eventLoop The event loop instance to use.
	 */
	private void addEventHandlers(EventLoop eventLoop) {
		eventLoop.addEventHandler(EchoResponseEvent.class,
				new EchoResponseEventHandler());
	}

	/*
	 * The constructor for the ServerInitializer class.
	 */
	public ServerInitializer() {
	}
}