/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.nam.p2p_hybrid_client;

import com.nam.p2p_hybrid_client.peer.ServerSocketController;
import java.io.File;
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
    public static String SHARED_DIRECTORY = "fileSharing";
    public static String FOLDER_DOWNLOAD = "D:/TestDownLoad/";

    public static void main(String[] args) throws Exception {
        createShareFileFolder();
        ServerSocketController testChat = null;
        Thread thread = new Thread(client);
        thread.start();
        client.createClientName();
        while (true) {
            try {

                System.out.println("Enter your choice");
                Scanner sc = new Scanner(System.in);
                String choice = sc.nextLine();
                switch (choice) {
                    case "chat_with":
                        System.out.println("Enter your chat");

                        String chat = sc.nextLine();
                        chatAll(client.name + ": " + chat);
                        break;
                    case "show_all_peer_port":
                        showAllPeerPort();
                        break;
                    case "request_download_file":
                        chatAll("request_download_file");
                        break;
                    case "download_file":
                        String fileName = sc.nextLine();
                        handleDownloadFile(fileName);
                        break;
                }
            } catch (Exception e) {
            }
        }

    }

    public static void chatAll(String message) throws Exception {
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
    
    public static void createShareFileFolder() {
        File directory = new File(SHARED_DIRECTORY);
        
        if (!directory.exists()) {
            if (directory.mkdir()) {
                System.out.println("Thu muc da tao thanh cong: " + SHARED_DIRECTORY);
            } else {
                System.out.println("Khong the tao thu muc: " + SHARED_DIRECTORY);
            }
        } else {
            System.out.println("Thu muc da ton tại: " + SHARED_DIRECTORY);
        }
    }

    private static void handleDownloadFile(String fileName) {
        try {
            chatAll("download_file");
            chatAll(fileName);
        } catch (Exception ex) {
            Logger.getLogger(P2p_hybrid_client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
