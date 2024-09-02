/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nam.p2p_hybrid_client.peer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NAM
 */
public class Server implements Runnable {

    ServerSocket serverSocket;
    Set<ServerSocketController> allPeerConnected;
    Integer port;

    public Server(int port) {
        allPeerConnected = new HashSet<>();
        this.port = port;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Open server at port: " + serverSocket.getLocalPort());
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New peer's connected: " + socket.getInetAddress().getHostAddress());
                ServerSocketController newPeerConnected = new ServerSocketController(socket);
                allPeerConnected.add(newPeerConnected);
                Thread thread = new Thread(newPeerConnected);
                thread.start();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public List<ServerSocketController> getServerSocketController() {
        if (!allPeerConnected.isEmpty()) {
            return  new ArrayList<>(allPeerConnected);
        }
        return null;
    }

}
