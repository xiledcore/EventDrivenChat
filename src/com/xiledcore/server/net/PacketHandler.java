package com.xiledcore.server.net;

import com.xiledcore.server.api.event.EventDispatcher;
import com.xiledcore.server.api.event.impl.EchoResponseEvent;
import com.xiledcore.server.api.event.impl.data.EchoResponseEventData;
import com.xiledcore.server.net.impl.WriteMessagePacket;

/*
 * This class handles the decoded packets. It uses the {@link EventDispatcher} instance to submit
 * events to the queue, according to the packet.
 * 
 * @author Xiledcore
 */
public final class PacketHandler {

	/*
	 * The {@link EventDispatcher} instance used for submitting events.
	 */
	private final EventDispatcher dispatcher;

	/*
	 * The constructor of this class.
	 * 
	 * @param dispatcher The dispatcher instance to be used for submitting
	 * events.
	 */
	public PacketHandler(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	/*
	 * Handles the packet by checking if it's an instance of certain packets.
	 * This is not a good method to do it, but I was too lazy to actually
	 * implement something worthwhile. This method acts more as a placeholder,
	 * and a better solution will probably come in the near future.
	 * 
	 * @param packet The packet to handle.
	 */
	public void handle(Packet packet) {
		if (packet instanceof WriteMessagePacket) {
			WriteMessagePacket messagePacket = (WriteMessagePacket) packet;

			dispatcher.dispatch(new EchoResponseEvent(),
					new EchoResponseEventData(messagePacket));
		} else {
			// TODO: make this system better...
		}
	}
}
