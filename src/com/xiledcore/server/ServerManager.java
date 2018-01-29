package com.xiledcore.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.xiledcore.server.api.event.EventDispatcher;
import com.xiledcore.server.net.Packet;
import com.xiledcore.server.net.PacketHandler;
import com.xiledcore.server.net.codec.PacketDecoder;

/*
 * The server manager handles everything that has to do with the connection and the packets. It submits the
 * incoming data from connections, first to a packet decoder which turns the raw data into a packet. That packet
 * is then sent to the {@link PacketHandler} instance which handles the packet and triggers the appropriate event(s).
 * 
 * @author Xiledcore
 */
public final class ServerManager {

	/*
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger(ServerManager.class
			.getName());

	/*
	 * The {@link ServerSocket} instance which we will use to receive incoming
	 * connections and data.
	 */
	private ServerSocket socket;

	/*
	 * The {@link ArrayList} instance which keeps track of the connections.
	 */
	private List<Socket> connections = new ArrayList<>();

	/*
	 * The {@link PacketHandler} instance which handles the decoded packets.
	 */
	private final PacketHandler packetHandler;

	/*
	 * The {@link PacketDecoder} instance which decodes incoming raw data and
	 * creates a packet with that data, to be submitted to the packetHandler.
	 */
	private final PacketDecoder packetDecoder;

	/*
	 * This is the {@link ExecutorService} instance which will be used to submit
	 * packets to the packetHandler. It has two threads in its thread pool, an
	 * arbitrary number.
	 */
	private final ExecutorService packetService = Executors
			.newFixedThreadPool(2);

	/*
	 * The constructor of the {@link ServerManager} class. It creates the
	 * handler and decoder instance, and then initializes the server socket
	 * through the init() method.
	 */
	public ServerManager(EventDispatcher dispatcher) {
		packetHandler = new PacketHandler(dispatcher);
		packetDecoder = new PacketDecoder();

		init();
	}

	/*
	 * This method simply initializes the {@link ServerSocket} instance.
	 */
	private void init() {
		try {
			socket = new ServerSocket(ServerConstants.PORT);
		} catch (IOException e) {
			logger.log(
					Level.SEVERE,
					"Something went wrong: could not instantiate ServerSocket instance!",
					e);
		}
	}

	/*
	 * This method is called manually from the {@link ServerInitializer} class
	 * when everything is set up.
	 * 
	 * It's an infinite loop (TODO: make it end sometime) that adds incoming TCP
	 * connections to the list of connections, and also decodes and handles the
	 * packets.
	 */
	public void start() {
		while (true) {
			try {
				Socket connection = socket.accept();
				connections.add(connection);

				Packet packet = packetDecoder.decode(connection);
				packetService.execute(() -> {
					packetHandler.handle(packet);
				});
			} catch (IOException e) {
				logger.log(
						Level.SEVERE,
						"Something went wrong: could not instantiate Socket instance!",
						e);
			}
		}
	}

	/*
	 * This method is called manually by the ServerInitializer when the event
	 * loop service is done. It closes the {@link ServerSocket} and loops
	 * through every instance of our connections list, and closes their
	 * respective sockets as well.
	 */
	public void shutdown() {
		try {
			socket.close();
			for (Socket connection : connections) {
				connection.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
