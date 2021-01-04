/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.rmi.Remote;
import java.rmi.*;
import java.util.ArrayList;
/**
 *
 * @author cuongnv
 */
public interface RmiInterface extends  Remote {
    public ArrayList<Student> getStudents(String key) throws RemoteException;
    public boolean edit(Student s) throws RemoteException;
}
