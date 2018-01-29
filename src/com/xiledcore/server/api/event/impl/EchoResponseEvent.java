package com.xiledcore.server.api.event.impl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.xiledcore.server.api.event.Event;
import com.xiledcore.server.api.event.EventData;
import com.xiledcore.server.api.event.impl.data.EchoResponseEventData;

/*
 * This class represents the event that is fired when a packet has been interpreted as a {@link WriteMessagePacket}.
 * All it does is echo the message it received from the client back to the client.
 * 
 * @author Xiledcore
 */
public class EchoResponseEvent extends Event {

	/*
	 * This method is called by the event handler when an event has been taken
	 * out of the event queue to be processed by the event loop.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xiledcore.server.api.event.Event#onNotify(com.xiledcore.server.api
	 * .event.EventData)
	 */
	@Override
	public void onNotify(EventData data) {
		EchoResponseEventData echoData = (EchoResponseEventData) data;
		Socket connection = echoData.getPacket().getConnection();

		try {
			DataOutputStream dos = new DataOutputStream(
					connection.getOutputStream());
			dos.writeUTF(echoData.getPacket().getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
