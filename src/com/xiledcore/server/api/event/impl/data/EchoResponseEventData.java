package com.xiledcore.server.api.event.impl.data;

import com.xiledcore.server.api.event.EventData;
import com.xiledcore.server.net.impl.WriteMessagePacket;

/*
 * This class is a model class that represents the data that will be passed to the onNotify method of the
 * {@link EchoResponseEvent} class.
 * 
 * @author Xiledcore
 */
public class EchoResponseEventData extends EventData {

	/*
	 * The {@link WriteMessagePacket} instance which contains relevant data like
	 * the connection from which this packet was sent from, along with the
	 * actual message from the client.
	 */
	private WriteMessagePacket packet;

	/*
	 * The constructor of this class.
	 */
	public EchoResponseEventData(WriteMessagePacket packet) {
		this.packet = packet;
	}

	/*
	 * A getter method that retrieves the packet.
	 */
	public WriteMessagePacket getPacket() {
		return packet;
	}
}
