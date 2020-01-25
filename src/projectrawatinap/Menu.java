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
        System.out.println("│  5. Data Tindakan");
        System.out.println("│  6. Registrasi Rawat Inap");
        System.out.println("│  7. Pembayaran");
        System.out.println("│  8. Keluar");
        System.out.println("│                                 │");
        System.out.println("└─────────────────────────────────┘");
        System.out.print("Pilihan Anda (1-8)? : ");
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
        System.out.println("│  5. Tampilkan Data");
        System.out.println("└─────────────────────────────────┘");
        System.out.print("Pilihan Anda (1-5)?: ");
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
        System.out.println("Pilihan Anda (1-4)?: ");
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
        System.out.println("Pilihan Anda (1-4)?: ");
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
        System.out.println("Pilihan Anda (1-4)?: ");
        pilihan = scanner.nextInt();
    }

}
