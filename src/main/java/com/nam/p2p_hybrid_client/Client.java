/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nam.p2p_hybrid_client;

import com.nam.p2p_hybrid_client.peer.Peer;
import com.nam.p2p_hybrid_client.peer.Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NAM
 */
public class Client implements Runnable {

    Socket clientSocket;
    DataInputStream dis;
    DataOutputStream dos;
    Server ownServer;
    String name;
    List<Integer> allServerPorts;
    List<Peer> peers;

    public Client() {
        peers = new ArrayList<>();
        try {
            this.clientSocket = new Socket("localhost", 9999);
            dis = new DataInputStream(clientSocket.getInputStream());
            dos = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            sendMessage("Hello Server");
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {

            try {
                String messageFromServer;
                messageFromServer = dis.readUTF();
                switch (messageFromServer) {
                    case "own_server_port":
                        
                        createOwnServer();
                        break;
                        
                    case "all_server_ports":
                        
                        getAllServerPorts();
                        break;
                    case "new_peer":
                        
                        int newPeerPort = Integer.parseInt(dis.readUTF());
                        createNewPeer(newPeerPort);
                        
                        break;
                    default:
                        System.out.println(messageFromServer);
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }

        }

    }

    public void sendMessage(String message) throws IOException {
        dos.writeUTF(message);
        dos.flush();
    }

    public void createOwnServer() throws IOException {
        int ownServerPort = Integer.parseInt(dis.readUTF());
        ownServer = new Server(ownServerPort);
        Thread thread = new Thread(ownServer);
        thread.start();
        sendMessage("create_server_successful");
    }

    public void createNewPeer(Integer newPeerPort) {
        try {
            Socket newSocket = new Socket("localhost", newPeerPort);
            System.out.println("Connecting to peer with port: " + newPeerPort);
            Peer newPeer = new Peer(newSocket);
            Thread thread = new Thread(newPeer);
            thread.start();
            peers.add(newPeer);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getAllServerPorts() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            this.allServerPorts = (ArrayList<Integer>) objectInputStream.readObject();

        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("Error in getAllServerPorts method");
        }
        for (Integer i : allServerPorts) {
            try {
                createNewPeer(i);
//                Socket newSocket = new Socket("localhost", i);
//                Peer newPeer = new Peer(newSocket);
//                System.out.println("Connecting to peer with port: " + i);
//                peers.add(newPeer);
//                Thread thread = new Thread(newPeer);
//                thread.start();
            } catch (Exception ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error in getAllServerPorts method: " + ex);
            }
        }
    }
}
