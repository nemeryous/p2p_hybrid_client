/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nam.p2p_hybrid_client.peer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NAM
 */
public class Peer implements Runnable {

    Socket peerSocket;
    DataInputStream dis;
    DataOutputStream dos;

    public Peer(Socket peerSocket) {
        try {
            this.peerSocket = peerSocket;
            this.dis = new DataInputStream(peerSocket.getInputStream());
            this.dos = new DataOutputStream(peerSocket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {

            dos.writeUTF("Hello World");
            dos.flush();
//            System.out.println("Test send message to another peers");
        } catch (Exception ex) {
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {
            try {
                String message = dis.readUTF();
                System.out.println(message);
            } catch (IOException ex) {
                Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
