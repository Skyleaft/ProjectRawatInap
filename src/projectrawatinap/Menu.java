/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectrawatinap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 *
 * @author msi
 */
public class Menu {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    Scanner scanner = new Scanner(System.in);
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
        System.out.println("└─────────────────────────────────┘");
        System.out.println("Pilihan Anda (1-3)?: ");
        pilihan = scanner.nextInt();
    }

    public void tambahDokter() throws IOException {
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│      Tambah Data Dokter         │");
        System.out.println("├─────────────────────────────────┤");
        reader.readLine();
    }
}
