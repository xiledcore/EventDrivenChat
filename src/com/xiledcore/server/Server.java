package com.xiledcore.server;

/*
 * This class is the main class of the program. I didn't want to clutter the main class, so I just created
 * an initializer class which starts everything up; it's a bootstrap.
 * 
 * @author Xiledcore
 */
public final class Server {

	/*
	 * The constructor of the Server class. It creates an instance of the {@link
	 * ServerInitializer} class which acts as a bootstrap for the application.
	 */
	public Server() {
		ServerInitializer initializer = new ServerInitializer();

		initializer.initialize();
	}

	/*
	 * The entry-point of the application.
	 */
	public static void main(String[] args) {
		new Server();
	}
}