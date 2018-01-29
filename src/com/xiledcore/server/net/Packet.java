package com.xiledcore.server.net;

import java.net.Socket;

/*
 * This class is a model class which represents the decoded packet.
 * 
 * @author Xiledcore
 */
public abstract class Packet {

	/*
	 * A {@link Socket} instance which is the client's connection.
	 */
	private final Socket connection;

	/*
	 * The constructor of this class.
	 */
	public Packet(Socket connection) {
		this.connection = connection;
	}

	/*
	 * A getter-method which returns the {@link Socket} instance.
	 */
	public Socket getConnection() {
		return connection;
	}
}