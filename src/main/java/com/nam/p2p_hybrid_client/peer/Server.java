/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nam.p2p_hybrid_client.peer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NAM
 */
public class Server implements Runnable{
    ServerSocket serverSocket;
    ArrayList<ServerSocketController> controllers;
    public Server(int port) {
        controllers = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(port);
                    } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {            
            try {
                Socket socket = serverSocket.accept();
                ServerSocketController controller = new ServerSocketController(socket);
                controllers.add(controller);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
