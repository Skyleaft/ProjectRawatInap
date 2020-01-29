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
import java.text.ParseException;

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
    
    
    public static void main(String[] args) throws IOException, SQLException, ParseException {
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

                if(k.rs.getString("hak_akses").equals("Admin")){
                    hak_akses="Admin";
                }else if(k.rs.getString("hak_akses").equals("Petugas")){
                    hak_akses="Petugas";
                }else if(k.rs.getString("hak_akses").equals("Dokter")){
                    hak_akses="Dokter";
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
            }
        }while (hak_akses==null);

        //initial
        Menu menu = new Menu();
        MasterDataDokter md = new MasterDataDokter();
        MasterDataPerawat mp = new MasterDataPerawat();
        MasterDataTindakan mt = new MasterDataTindakan();
        MasterDataKamar mk = new MasterDataKamar();
        MasterDataPasien pas = new MasterDataPasien();
        RegistrasiRawatInap reg = new RegistrasiRawatInap();
        Transaksi tr = new Transaksi();

        md.setDB(ip,port,user,pass);
        mp.setDB(ip,port,user,pass);
        mt.setDB(ip,port,user,pass);
        mk.setDB(ip, port, user, pass);
        pas.setDB(ip, port, user, pass);
        reg.setDB(ip,port,user,pass);
        tr.setDB(ip,port,user,pass);

        if(hak_akses.equals("Admin")){
            do{
                menu.cetakMenu();
                switch (menu.getPilihan()) {
                    case 1: //menu dokter
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
                                    default:
                                        System.out.println("Pilihan anda salah");
                                        System.out.println("Tekan Enter Untuk Melanjutkan");
                                        reader.readLine();
                                        break;
                                }
                                break;
                            case 5:
                                md.tampilDokter();
                                break;
                            default:
                                System.out.println("Pilihan anda salah");
                                System.out.println("Tekan Enter Untuk Melanjutkan");
                                reader.readLine();
                                break;
                        }
                        break;
                    case 2: //menu perawat
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
                                    default:
                                        System.out.println("Pilihan anda salah");
                                        System.out.println("Tekan Enter Untuk Melanjutkan");
                                        reader.readLine();
                                        break;
                                }
                                break;
                            case 5:
                                mp.tampilPerawat();
                                break;
                            default:
                                System.out.println("Pilihan anda salah");
                                System.out.println("Tekan Enter Untuk Melanjutkan");
                                reader.readLine();
                                break;
                        }
                        break;
                    case 3: //menu pasien
                        menu.cetakMenuPasien();
                        switch (menu.getPilihan()){
                            case 1:
                                pas.hapusPasien();
                                break;
                            case 2:
                                pas.ubahPasien();
                                break;
                            case 3:
                                menu.cariPasienBerdasarkan();
                                switch (menu.getPilihan()){
                                    case 1:
                                        pas.cariPasienID();
                                        break;
                                    case 2:
                                        pas.cariPasienNama();
                                        break;
                                    default:
                                        System.out.println("Pilihan anda salah");
                                        System.out.println("Tekan Enter Untuk Melanjutkan");
                                        reader.readLine();
                                        break;
                                }
                                break;
                            case 4:
                                pas.tampilPasien();
                                break;
                            default:
                                System.out.println("Pilihan anda salah");
                                System.out.println("Tekan Enter Untuk Melanjutkan");
                                reader.readLine();
                                break;
                        }
                        break;
                    case 4: //menu kamar
                        menu.cetakMenuKamar();
                        switch(menu.getPilihan()){
                            case 1:
                                mk.tambahKamar();
                                break;
                            case 2:
                                mk.hapusKamar();
                                break;
                            case 3:
                                mk.ubahKamar();
                                break;
                            case 4:
                                menu.cariKamarBerdasarkan();
                                switch(menu.getPilihan()){
                                    case 1:
                                        mk.cariKamarKd();
                                        break;
                                    case 2:
                                        mk.cariKamarTipe();
                                        break;
                                    default:
                                        System.out.println("Pilihan anda salah");
                                        System.out.println("Tekan Enter Untuk Melanjutkan");
                                        reader.readLine();
                                        break;
                                }
                                break;
                            case 5:
                                mk.tampilKamar();
                                break;
                            default:
                                System.out.println("Pilihan anda salah");
                                System.out.println("Tekan Enter Untuk Melanjutkan");
                                reader.readLine();
                                break;
                        }
                        break;
                    case 5: //menu tindakan
                        menu.cetakMenuTindakan();
                        switch (menu.getPilihan()){
                            case 1:
                                mt.tambahTindakan();
                                break;
                            case 2:
                                mt.hapusTindakan();
                                break;
                            case 3:
                                mt.ubahTindakan();
                                break;
                            case 4:
                                menu.cariTindakanBerdasarkan();
                                switch (menu.getPilihan()){
                                    case 1:
                                        mt.cariTindakanKd();
                                        break;
                                    case 2:
                                        mt.cariTindakanNama();
                                        break;
                                    default:
                                        System.out.println("Pilihan anda salah");
                                        System.out.println("Tekan Enter Untuk Melanjutkan");
                                        reader.readLine();
                                        break;
                                }
                                break;
                            case 5:
                                mt.tampilTindakan();
                                break;
                            default:
                                System.out.println("Pilihan anda salah");
                                System.out.println("Tekan Enter Untuk Melanjutkan");
                                reader.readLine();
                                break;
                        }
                        break;
                    case 6: //menu registrasi
                        menu.cetakMenuRegistrasi();
                        switch (menu.getPilihan()){
                            case 1:
                                reg.registerPasien();
                                break;
                            case 2:
                                reg.registerRawat();
                                break;
                            default:
                                System.out.println("Pilihan anda salah");
                                System.out.println("Tekan Enter Untuk Melanjutkan");
                                reader.readLine();
                                break;
                        }
                        break;
                    case 7:
                        mt.Tindak();
                        break;
                    case 8:
                        menu.cetakMenuTransaksi();
                        switch (menu.getPilihan()){
                            case 1:
                                tr.pembayaran();
                                break;
                            case 2:
                                mt.lihatDetailTindakan();
                                break;
                            default:
                                System.out.println("Pilihan anda salah");
                                System.out.println("Tekan Enter Untuk Melanjutkan");
                                reader.readLine();
                                break;
                        }
                        break;
                    default:
                        System.out.println("Pilihan anda salah");
                        System.out.println("Tekan Enter Untuk Melanjutkan");
                        reader.readLine();
                        break;
                }
                clrscr();
            }while (menu.getPilihan()!=9);

        }else if(hak_akses.equals("Petugas")){  //kalo hak akses petugas
            do{
                menu.cetakMenuPetugas();
                switch (menu.getPilihan()){
                    case 1:
                        menu.cetakMenuRegistrasi();
                        switch (menu.getPilihan()){
                            case 1:
                                reg.registerPasien();
                                break;
                            case 2:
                                reg.registerRawat();
                                break;
                            default:
                                System.out.println("Pilihan anda salah");
                                System.out.println("Tekan Enter Untuk Melanjutkan");
                                reader.readLine();
                                break;
                        }
                        break;
                    case 2:
                        pas.tampilPasien();
                        break;
                    case 3:
                        mk.tampilKamar();
                        break;
                    case 4:
                        md.tampilDokter();
                        break;
                    case 5:
                        mp.tampilPerawat();
                        break;
                    case 6:
                        mt.lihatDetailTindakan();
                        break;
                    case 7:
                        tr.pembayaran();
                        break;
                    default:
                        System.out.println("Pilihan anda salah");
                        System.out.println("Tekan Enter Untuk Melanjutkan");
                        reader.readLine();
                        break;
                }
                clrscr();
            }while (menu.getPilihan()!=8);

        }else if (hak_akses.equals("Dokter")){
            do{
                menu.cetakMenuAksesDokter();
                switch (menu.getPilihan()){
                    case 1:
                        mt.Tindak();
                        break;
                    case 2:
                        pas.tampilPasien();
                        break;
                    case 3:
                        mk.tampilKamar();
                        break;
                    case 4:
                        md.tampilDokter();
                        break;
                    case 5:
                        mp.tampilPerawat();
                        break;
                    default:
                        System.out.println("Pilihan anda salah");
                        System.out.println("Tekan Enter Untuk Melanjutkan");
                        reader.readLine();
                        break;
                }
                clrscr();
            }while (menu.getPilihan()!=6);
        }



    }
}
