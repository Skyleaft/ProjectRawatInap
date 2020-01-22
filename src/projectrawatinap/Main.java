/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectrawatinap;

import projectrawatinap.koneksi.Koneksi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

/**
 *
 * @author msi
 */
public class Main {
    public static void clrscr(){
        //Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }
    
    
    public static void main(String[] args) throws IOException, SQLException {
        String hak_akses = null;
        String yt=null;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        LoginScreen ls = new LoginScreen();

        Koneksi k = new Koneksi();
        k.setDB("localhost","3306","rawat_inap","root","");
        k.konek();
        do {
            clrscr();
            System.out.println();
            ls.cetakLogin();

            k.query="Select * from t_user where username = '"+ls.getUname()+"' and password = '"+ls.getPass()+"';";
            k.ambil();
            if(k.rs.next()){
                clrscr();

                if(k.rs.getString("lvl_user").equals("admin")){
                    hak_akses="Admin";
                }else{
                    hak_akses="Petugas";
                }
                System.out.println("\n┌──────────────────────────────┐");
                System.out.println("│        Login Berhasil        │");
                System.out.println("│  Anda Login Sebagai "+hak_akses);
                System.out.println("└──────────────────────────────┘");
            }
            else{
                System.out.println("┌────────────────────────────────┐");
                System.out.println("│  Username atau Password Salah  │");
                System.out.println("└────────────────────────────────┘");
                System.out.println("Tekan Enter Untuk Login Lagi");
                reader.readLine();
//                System.out.print("Login Lagi(Y/T) ? : ");
//                yt=reader.readLine().toUpperCase();


            }
        }while (hak_akses==null);

        Menu menu = new Menu();
        if(hak_akses.equals("Admin")){
            menu.cetakMenu();
            switch (menu.getPilihan()){
                case 1 :
                    menu.cetakMenuDokter();
                    switch (menu.getPilihan()){
                        case 1:
                            menu.tambahDokter();
                            break;
                        case 2:
                            menu.ubahDokter();
                            break;
                    }
                    break;
                case 2 :
                    menu.cetakMenuPerawat();
                    break;
                case 3 :
                    menu.cetakMenuPasien();
                    break;
                case 4 :
                    break;
            }
            
        }
    }
}
