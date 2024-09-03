/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nam.p2p_hybrid_client.peer;

import com.nam.p2p_hybrid_client.Client;
import com.nam.p2p_hybrid_client.P2p_hybrid_client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
        while (true) {
            try {
                String message = dis.readUTF();
                
                switch (message) {
                    case "request_download_file":
//                      Xu li su kien download file tu peer khac
                        handleRequstDownloadFile();
                        break;
                    case "download_file":
                        responseDownloadFile();
                        break;

                    default:
                        System.out.println(message);
                }
            } catch (IOException ex) {
                Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void handleRequstDownloadFile() {
        sendMessage("list_of_file");

//      Doc folder share chung        
        File shareFolder = new File(P2p_hybrid_client.SHARED_DIRECTORY);
        File[] listOfFiles = shareFolder.listFiles();
        Integer lengthOfFolder = listOfFiles.length;
        
        System.out.println(lengthOfFolder);
        sendMessage(lengthOfFolder.toString());
        sendMessage(P2p_hybrid_client.client.getName());
        if (listOfFiles.length != 0) {
            for (File file : listOfFiles) {
                System.out.println("File: " + file.getName());
                sendMessage(file.getName());
            }
        }
        
    }
    
    public void sendMessage(String message) {
        try {
            dos.writeUTF(message);
            dos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Socket getPeerSocket() {
        return peerSocket;
    }
    
    public void setPeerSocket(Socket peerSocket) {
        this.peerSocket = peerSocket;
    }
    
    public DataInputStream getDis() {
        return dis;
    }
    
    public void setDis(DataInputStream dis) {
        this.dis = dis;
    }
    
    public DataOutputStream getDos() {
        return dos;
    }
    
    public void setDos(DataOutputStream dos) {
        this.dos = dos;
    }
    
    public void responseDownloadFile() {
        
        try {
            
            String fileName = dis.readUTF();
            System.out.println("FileName: " + fileName);
            File file = new File(P2p_hybrid_client.SHARED_DIRECTORY + "/" + fileName);
            System.out.println(file);
            sendMessage("response_download_file");
            sendMessage(fileName);
            sendMessage(String.valueOf(file.length()));
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[8096];
            int count;
            while ((count = fis.read(bytes)) > 0) {
                System.out.println(count);
                dos.write(bytes, 0, count);
            }
            System.out.println("Gui file thanh cong");
            dos.flush();
            fis.close();
        } catch (Exception ex) {
            System.out.println("Loi o responseDownloadFile method: " + ex);
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
