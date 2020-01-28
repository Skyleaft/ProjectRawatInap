/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectrawatinap;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import projectrawatinap.koneksi.Koneksi;

/**
 *
 * @author msi
 */
public class LoginScreen {
    private String uname,pass;

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    Console console = System.console();
    Koneksi k = new Koneksi();

    public  String getUname(){
        return uname;
    }
    public String getPass(){
        return pass;
    }

    public void cetakLogin() throws IOException, SQLException {
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│             Login               │");
        System.out.println("├─────────────────────────────────┤");
        System.out.print("│  Username : ");uname=reader.readLine();
        System.out.print("│  Password : ");pass=reader.readLine();
        System.out.println("│                                 │");
        System.out.println("└─────────────────────────────────┘");

    }
}
