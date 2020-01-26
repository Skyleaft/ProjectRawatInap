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
        String ytserver=null;
        String ip="localhost",port="3306",user="root",pass=null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        LoginScreen ls = new LoginScreen();
        Koneksi k = new Koneksi();
        System.out.println("default ip:localhost   port:3306   user:root  pass:-");
        System.out.print("Setting server mysql?(y/t) : ");ytserver = reader.readLine().toUpperCase();
        if(ytserver.equals("Y")){
            System.out.print("Masukan IP Server : ");ip=reader.readLine();
            System.out.print("Masukan port Server : ");port=reader.readLine();
            System.out.print("Masukan username Server : ");user=reader.readLine();
            System.out.print("Masukan password Server : ");pass=reader.readLine();
            k.setDB(ip,port,"rawat_inap",user,pass);
            k.konek();
        }
        else if(ytserver.equals("T")){
            k.setDB(ip,port,"rawat_inap",user,pass);
            k.konek();
        }


        do {
            clrscr();
            System.out.println();
            ls.cetakLogin();

            k.query="Select * from user where username = '"+ls.getUname()+"' and password = '"+ls.getPass()+"';";
            k.ambil();
            if(k.rs.next()){
                clrscr();

                if(k.rs.getString("hak_akses").equals("admin")){
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

        //initial
        Menu menu = new Menu();
        MasterDataDokter md = new MasterDataDokter();
        MasterDataPerawat mp = new MasterDataPerawat();
        md.setDB(ip,port,user,pass);
        mp.setDB(ip,port,user,pass);

        do{
            if(hak_akses.equals("Admin")){
                menu.cetakMenu();
                switch (menu.getPilihan()) {
                    case 1:
                        menu.cetakMenuDokter();
                        switch (menu.getPilihan()) {
                            case 1:
                                md.tambahDokter();
                                break;
                            case 2:
                                md.hapusDokter();
                                break;
                            case 3:
                                md.ubahDokter();
                                break;
                            case 4:
                                menu.cariDokterBerdasarkan();
                                switch (menu.getPilihan()){
                                    case 1:
                                        md.cariDokterID();
                                        break;
                                    case 2:
                                        md.cariDokterNama();
                                        break;
                                    case 3:
                                        md.cariDokterSpesialisasi();
                                        break;
                                }
                                break;
                            case 5:
                                md.tampilDokter();
                                break;
                        }
                        break;
                    case 2:
                        menu.cetakMenuPerawat();
                        switch (menu.getPilihan()) {
                            case 1:
                                mp.tambahPerawat();
                                break;
                            case 2:
                                mp.hapusPerawat();
                                break;
                            case 3:
                                mp.ubahPerawat();
                                break;
                            case 4:
                                menu.cariPerawatBerdasarkan();
                                switch (menu.getPilihan()){
                                    case 1:
                                        mp.cariPerawatID();
                                        break;
                                    case 2:
                                        mp.cariPerawatNama();
                                        break;
                                    case 3:
                                        mp.cariPerawatAlamat();
                                        break;
                                }
                                break;
                            case 5:
                                mp.tampilPerawat();
                                break;
                        }
                        break;
                    case 3:
                        menu.cetakMenuPasien();
                        break;
                    case 4:
                        break;
                }
            }
            clrscr();
        }while (menu.getPilihan()!=8);

    }
}
