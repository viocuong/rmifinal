/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import model.RmiInterface;
import model.Student;
import java.sql.*;
import java.rmi.registry.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author cuongnv
 */
public class Controller extends  UnicastRemoteObject implements RmiInterface{
    private String host = "localhost";
    private int port = 8888;
    private Connection con;
    private Registry registry;
    
    public Controller() throws RemoteException{
        try {
            open();
            registry = LocateRegistry.createRegistry(port);
            registry.rebind("rmiserver", this);
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void open(){
        try {
            String sql= "jdbc:mysql://localhost/LTM";
            String classUrl ="com.mysql.jdbc.Driver";
            Class.forName(classUrl);
            con = DriverManager.getConnection(sql, "root","root");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    @Override
    public ArrayList<Student> getStudents(String key) throws RemoteException {
        ArrayList<Student> ans = new ArrayList<>();
        try {
            String sql ="select * from tbl_student where ten like '%"+key+"%'";
            PreparedStatement ps =con .prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ans.add(new Student(rs.getInt("ma"),rs.getString("ten"),rs.getString("khoa"),rs.getString("ngaysinh"),rs.getString("quequan")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
        
    }

    @Override
    public boolean edit(Student s) throws RemoteException {
        try {
            String sql = "update tbl_student set ten=?,khoa=?,ngaysinh=?,quequan=? where ma="+s.getMa();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, s.getTen());
            ps.setString(2, s.getKhoa());
            ps.setString(3, s.getNgaysinh());
            ps.setString(4, s.getQuequan());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            
            ex.printStackTrace();
            return false;
        }
        
    }

    @Override
    public boolean add(Student s) throws RemoteException {
        try {
            String sql ="insert into tbl_student(ten,khoa,ngaysinh,quequan) values(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, s.getTen());
            ps.setString(2, s.getKhoa());
            ps.setString(3, s.getNgaysinh());
            ps.setString(4, s.getQuequan());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete(int ma) throws RemoteException {
        try {
            String sql = "delete from tbl_student where ma=" +ma;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
}
