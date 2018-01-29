package com.xiledcore.server;

/*
 * This class contains constants pertaining to the actual connection details.
 * 
 * @author Xiledcore
 */
public final class ServerConstants {

	/*
	 * The port that the ServerSocket will listen to.
	 */
	public static final int PORT = 5671;

	/*
	 * The constructor of the {@link ServerConstants} class. It's made private
	 * because we don't want anyone instantiating this class, since its sole
	 * purpose is to store constants.
	 */
	private ServerConstants() {
	}
}
