package com.xiledcore.server.net.impl;

import java.net.Socket;

import com.xiledcore.server.net.Packet;

/*
 * This class is a model class which represents the message from the client.
 * 
 * @author Xiledcore
 */
public class WriteMessagePacket extends Packet {

	/*
	 * This instance represents the message from the client that it wants echoed
	 * back.
	 */
	private String message;

	/*
	 * The constructor of this class.
	 * 
	 * @param connection The client connection.
	 * 
	 * @param message The message from the client.
	 */
	public WriteMessagePacket(Socket connection, String message) {
		super(connection);
		this.message = message;
	}

	/*
	 * A getter-method that returns the message that the client wants echoed
	 * back to it.
	 * 
	 * @returns message
	 */
	public String getMessage() {
		return message;
	}
}
