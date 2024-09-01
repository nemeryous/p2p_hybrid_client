/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.nam.p2p_hybrid_client;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NAM
 */
public class P2p_hybrid_client {

    public static void main(String[] args) {
        Client client = new Client();
        Thread thread = new Thread(client);
        thread.start();
        
        while (true) {
            try {
                Scanner sc = new Scanner(System.in);
                String messageToServer = sc.nextLine();
                if(messageToServer.equals("quit")) break;
                client.sendMessage(messageToServer);
                
               
            } catch (IOException ex) {
                Logger.getLogger(P2p_hybrid_client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
