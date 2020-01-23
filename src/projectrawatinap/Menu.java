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
import java.util.Scanner;

/**
 *
 * @author msi
 */
public class Menu {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    Scanner scanner = new Scanner(System.in);
    Koneksi k = new Koneksi();
    public int pilihan=0;


    public int getPilihan(){
        return pilihan;
    }

    public void cetakMenu() throws IOException {
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│              Menu               │");
        System.out.println("├─────────────────────────────────┤");
        System.out.println("│  1. Data Dokter");
        System.out.println("│  2. Data Perawat");
        System.out.println("│  3. Data Pasien");
        System.out.println("│  4. Data Kamar");
        System.out.println("│                                 │");
        System.out.println("└─────────────────────────────────┘");
        System.out.print("Pilihan Anda (1-6)? : ");
        pilihan = scanner.nextInt();
    }

    public void cetakMenuDokter() throws IOException {
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│           Menu Dokter           │");
        System.out.println("├─────────────────────────────────┤");
        System.out.println("│  1. Tambah Data");
        System.out.println("│  2. Hapus Data");
        System.out.println("│  3. Ubah Data");
        System.out.println("│  4. cari Data");
        System.out.println("└─────────────────────────────────┘");
        System.out.println("Pilihan Anda (1-3)?: ");
        pilihan = scanner.nextInt();
    }

    public void cetakMenuPerawat() throws IOException {
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│          Menu Perawat           │");
        System.out.println("├─────────────────────────────────┤");
        System.out.println("│  1. Tambah Data");
        System.out.println("│  2. Hapus Data");
        System.out.println("│  3. Ubah Data");
        System.out.println("│  4. cari Data");
        System.out.println("└─────────────────────────────────┘");
        System.out.println("Pilihan Anda (1-3)?: ");
        pilihan = scanner.nextInt();
    }

    public void cetakMenuPasien() throws IOException {
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│          Menu Pasien            │");
        System.out.println("├─────────────────────────────────┤");
        System.out.println("│  1. Tambah Data");
        System.out.println("│  2. Hapus Data");
        System.out.println("│  3. Ubah Data");
        System.out.println("│  4. cari Data");
        System.out.println("└─────────────────────────────────┘");
        System.out.println("Pilihan Anda (1-3)?: ");
        pilihan = scanner.nextInt();
    }

    public void cetakMenuKamar() throws IOException {
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│          Menu kamar             │");
        System.out.println("├─────────────────────────────────┤");
        System.out.println("│  1. Tambah Data");
        System.out.println("│  2. Hapus Data");
        System.out.println("│  3. Ubah Data");
        System.out.println("│  4. cari Data");
        System.out.println("└─────────────────────────────────┘");
        System.out.println("Pilihan Anda (1-3)?: ");
        pilihan = scanner.nextInt();
    }

    public void tambahDokter() throws IOException {
        k.setDB("localhost","3306","rawat_inap","root","");
        String id,nama,alamat,spesialisasi;
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│      Tambah Data Dokter         │");
        System.out.println("├─────────────────────────────────┤");
        System.out.print("│  1. Masukan ID Dokter : ");id = reader.readLine();
        System.out.print("│  2. Masukan Nama Dokter : ");nama = reader.readLine();
        System.out.print("│  3. Masukan alamat Dokter : ");alamat = reader.readLine();
        System.out.print("│  4. Masukan spesialisasi Dokter : ");spesialisasi = reader.readLine();
        k.query = "insert into t_dokter values('"+id+"','"+nama+"','"+alamat+"','"+spesialisasi+"');";
        k.crud();
        if(k.count>0){
            System.out.println("Data Berhasil Dimasukan ");
        }else{
            System.out.println("Gagal Memasukan Data");
        }
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void ubahDokter() throws IOException, SQLException {
        k.setDB("localhost","3306","rawat_inap","root","");
        String id,nama,alamat,spesialisasi;
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│        Ubah Data Dokter         │");
        System.out.println("├─────────────────────────────────┤");
        System.out.print("│  1. Masukan ID Dokter yang akan diubah : ");id = reader.readLine();
        k.query="select *from t_dokter where id_dokter='"+id+"'";
        k.ambil();
        if(k.rs.getString("id_dokter").equals(id)){
            System.out.println("ID Dokter : "+id);
            System.out.println("Kosongkan jika tidak ingin diubah");
            System.out.print("Nama Dokter : "+k.rs.getString("nama_dokter")+"   Ubah? : ");nama = reader.readLine();
            System.out.print("alamat Dokter : "+k.rs.getString("alamat")+"   Ubah? : ");alamat = reader.readLine();
            System.out.print("Spesialisasi Dokter : "+k.rs.getString("spesialisasi")+"   Ubah? : ");spesialisasi = reader.readLine();
            if(!nama.equals("")){
                k.query="update t_dokter set nama_dokter='"+nama+"' where id_dokter='"+id+"'";
                k.crud();
            }
            if(!alamat.equals("")){
                k.query="update t_dokter set alamat='"+alamat+"' where id_dokter='"+id+"'";
                k.crud();
            }
            if(!spesialisasi.equals("")){
                k.query="update t_dokter set spesialisasi='"+spesialisasi+"' where id_dokter='"+id+"'";
                k.crud();
            }
        }else{
            System.out.println("Data Tidak Ditemukan");
        }
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void hapusDokter() throws IOException, SQLException {
        k.setDB("localhost","3306","rawat_inap","root","");
        String id,yt;
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│        Hapus Data Dokter        │");
        System.out.println("├─────────────────────────────────┤");
        System.out.print("│  1. Masukan ID Dokter yang akan dihapus : ");id = reader.readLine();
        k.query="select *from t_dokter where id_dokter='"+id+"'";
        k.ambil();
        if(k.rs.getString("id_dokter").equals(id)){
            System.out.println("ID Dokter : "+id + " Nama Dokter : "+k.rs.getString("nama_dokter"));
            System.out.print("Yakin mau dihapus (y/t)? : ");yt=reader.readLine().toUpperCase();
            if(yt.equals("Y")){
                k.query="delete from t_dokter where id_dokter='"+id+"'";
                k.crud();
                if(k.count>0){
                    System.out.println("Data Berhasil Dihapus ");
                }else{
                    System.out.println("Gagal Menghapus Data");
                }
            }

        }else{
            System.out.println("Data Tidak Ditemukan");
        }
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }
}
