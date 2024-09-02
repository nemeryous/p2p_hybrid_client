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

    public static void main(String[] args) throws Exception {
        ServerSocketController testChat = null;
        Client client = new Client();
        Thread thread = new Thread(client);
        thread.start();
        System.out.println("After thread start");

        while (true) {
            System.out.println("Enter your choice");
            Scanner sc = new Scanner(System.in);
            String choice = sc.nextLine();
            switch (choice) {
                case "chat_with":
                    System.out.println("Enter your chat");
//                    if (client.ownServer.getServerSocketController() != null) {
//                       String chat = sc.nextLine();
//                       
//                    }
                    String chat = sc.nextLine();
                    chatAll(chat, client);
                    break;
            }
        }

//        if (testChat != null) {
//            System.out.println("Now test chat isn't null");
//
//            while (true) {
//                try {
//                    Scanner sc = new Scanner(System.in);
//                    System.out.println("Enter your chat");
//                    String messageToServer = sc.nextLine();
//
//                    if (messageToServer.equals("quit")) {
//                        break;
//                    }
//                    testChat.sendMessage(messageToServer);
////                if(messageToServer.equals("quit")) break;
////                client.sendMessage(messageToServer);
//
//                } catch (Exception ex) {
//                    Logger.getLogger(P2p_hybrid_client.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
    }

    public static void chatAll(String message, Client client) throws Exception{
        try {
            List<ServerSocketController> allPeersConnected = client.ownServer.getServerSocketController();

            for (ServerSocketController peer : allPeersConnected) {
                peer.sendMessage(message);
            } 
        } catch (Exception e) {
        }
    }

}
