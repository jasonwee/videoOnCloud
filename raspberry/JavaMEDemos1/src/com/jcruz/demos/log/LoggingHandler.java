/* 
 * 2014 Jose Cruz <joseacruzp@gmail.com>.
 */
package com.jcruz.demos.log;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;
import java.io.IOException;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.ServerSocketConnection;
import javax.microedition.io.SocketConnection;

/**
 * Redirects log messages to remote console. Logging handler accepts TCP
 * connections on port 23 (Telnet) and redirects log messages to these
 * connections. Only one connection is accepted at a time -- when new client
 * connects, previous one gets disconnected. Before first client connects,
 * messages are directed to
 * <code>System.out</code>
 *
 * @see Logger
 */
public class LoggingHandler extends StreamHandler {

    private static final String PREFIX = "Logging: ";
    
    private static final int LOGGING_PORT = 23; // well-known telnet port
    
    private ServerSocketConnection servSocket;
    private SocketConnection socket;
    private Thread thread;
    
    private volatile boolean shouldRun;
    
    private static volatile LoggingHandler instance;
    
    private LoggingHandler() {}
    
    /**
     * Define our Log Handler
     * @return
     */
    public static synchronized LoggingHandler getInstance() {
        if (instance == null) {
            instance = new LoggingHandler();
            instance.setLevel(Level.ALL);
        }
        return instance;
    }

    /**
     * Start handler. Output is directed to
     * <code>System.out</code> and handler is attached to Global Logger; After
     * that, listening thread is started.
     *
     * @see #run
     */
    public void start() {
        setOutputStream(System.out);
        Logger.getGlobal().addHandler(this);
    }

}
