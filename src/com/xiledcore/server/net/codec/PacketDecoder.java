package com.xiledcore.server.net.codec;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.xiledcore.server.net.Packet;
import com.xiledcore.server.net.impl.WriteMessagePacket;

/*
 * This class handles the decoding of the raw data received from the client.
 * Every packet starts with an int that represents the opcode of the packet, and with that,
 * we can determine what kind of packet it is, and handle the rest of the data reading accordingly.
 * 
 * 
 * @author Xiledcore
 */
public final class PacketDecoder {

	/*
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger(PacketDecoder.class
			.getName());

	/*
	 * The decode method of this class. It simply reads an int from the stream
	 * of bytes sent from the client, and determines the opcode from that. It
	 * then knows which packet type was sent, and it can create a new packet for
	 * the {@link PacketHandler} instance to handle accordingly.
	 * 
	 * @param connection The connection from which we will receive the raw data.
	 */
	public Packet decode(Socket connection) {
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(connection.getInputStream());
		} catch (IOException e1) {
			logger.log(Level.WARNING, "Something went wrong!", e1);
		}

		int opcode = 0;

		try {
			opcode = dis.readInt();

		} catch (IOException e) {

		}

		switch (opcode) {
		case 1:
			try {
				return new WriteMessagePacket(connection, dis.readUTF());
			} catch (IOException e) {
				logger.log(Level.WARNING, "Could not read!", e);
			}
			break;

		default:
			logger.log(Level.WARNING, "Invalid opcode!");
			break;
		}

		return null;
	}

	/*
	 * The constructor of this class.
	 */
	public PacketDecoder() {
	}
}
