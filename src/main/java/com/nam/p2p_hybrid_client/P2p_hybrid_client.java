/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.nam.p2p_hybrid_client;

import com.nam.p2p_hybrid_client.peer.ServerSocketController;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NAM
 */
public class P2p_hybrid_client {

    public static Client client = new Client();

    public static void main(String[] args) throws Exception {
        ServerSocketController testChat = null;
        Thread thread = new Thread(client);
        thread.start();

        while (true) {
            try {

                System.out.println("Enter your choice");
                Scanner sc = new Scanner(System.in);
                String choice = sc.nextLine();
                switch (choice) {
                    case "chat_with":
                        System.out.println("Enter your chat");

                        String chat = sc.nextLine();
                        chatAll(chat, client);
                        break;
                    case "show_all_peer_port":
                        showAllPeerPort();
                        break;

                }
            } catch (Exception e) {
            }
        }

    }

    public static void chatAll(String message, Client client) throws Exception {
        List<ServerSocketController> allPeersConnected = client.ownServer.getServerSocketController();
        try {
            for (ServerSocketController peer : allPeersConnected) {
                peer.sendMessage(message);
            }
        } catch (Exception e) {
        }
    }

    private static void showAllPeerPort() {
        List<ServerSocketController> allPeersConnected = client.ownServer.getServerSocketController();
        for (ServerSocketController peer : allPeersConnected) {
            System.out.println("Peer's port: " + peer.getConnectedSocket().getLocalPort());
        }
    }

}
