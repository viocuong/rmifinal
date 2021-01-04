/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.rmi.RemoteException;
import server.controller.Controller;

/**
 *
 * @author cuongnv
 */
public class Run {
    public static void main(String[] args) throws RemoteException {
        Controller con = new Controller();
    }
    
}
