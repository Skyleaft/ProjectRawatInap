package projectrawatinap;

import projectrawatinap.koneksi.Koneksi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class RegistrasiRawatInap {
    Koneksi k = new Koneksi();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    Scanner scanner = new Scanner(System.in);
    String ip,port,user,pass;


    public void setDB(String _ip,String _port,String _user,String _pass){
        k.setDB(_ip,_port,"rawat_inap",_user,_pass);
        ip=_ip;
        port=_port;
        user=_user;
        pass=_pass;
    }

    public void registerPasien() throws SQLException, IOException {
        String no_pasien,nama,jk,jenis_kelamin = null,tgl_lahir,no_telp,alamat,pekerjaan;

        k.query ="SELECT * FROM pasien";
        k.ambil();
        k.rs.last();
        int baris = k.rs.getRow();
        String baru;
        if(baris==0){
            baru = "P001";
        }else{
            int tambah = Integer.valueOf(k.rs.getString(1).substring(3,(k.rs.getString(1).length())))+ 1;
            if(tambah < 10){
                baru = "P00"+ tambah;
            }else if(tambah < 100){
                baru = "P0"+ tambah;
            }else{
                baru = "P"+ tambah;
            }
        }
        no_pasien=baru;

        System.out.println("┌────────────────────────────────────┐");
        System.out.println("│         Registrasi pasien          │");
        System.out.println("├────────────────────────────────────┤");
        System.out.println("│  1. ID Pasien (Otomatis): "+no_pasien);
        System.out.print("│  2. Masukan Nama Pasien : ");nama = reader.readLine();
        System.out.print("│  3. Masukan Jenis Kelamin Pasien (L/P)?: ");jk = reader.readLine().toUpperCase();
        System.out.print("│  7. Masukan Tanggal Lahir Pasien(yyyy-mm-dd) : ");tgl_lahir = reader.readLine();
        System.out.print("│  5. Masukan Nomor Telepon Pasien : ");no_telp = reader.readLine();
        System.out.print("│  3. Masukan alamat Pasien : ");alamat = reader.readLine();
        System.out.print("│  4. Masukan Pekerjaan Pasien : ");pekerjaan = reader.readLine();
        if(jk.equals("L")){
            jenis_kelamin="Laki-Laki";
        }
        else if(jk.equals("P")){
            jenis_kelamin="Perempuan";
        }
        else{
            System.out.println("Salah Memasukan data Jenis Kelamin");
        }
        k.query = "insert into pasien values('"+no_pasien+"','"+nama+"','"+jenis_kelamin+"','"+tgl_lahir+"','"+no_telp+"','"+alamat+"','"+pekerjaan+"');";
        k.crud();
        if(k.count>0){
            System.out.println("Data Berhasil Disimpan ");
        }else{
            System.out.println("Gagal Menyimpan Data");
        }
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void registerRawat() throws SQLException, IOException {
        String no_rawat,no_pasien,kd_kamar,id_perawat,id_dokter,tgl;
        int lama;

        MasterDataPasien mpas = new MasterDataPasien();
        MasterDataKamar mk = new MasterDataKamar();
        mk.setDB(ip, port, user, pass);
        mpas.setDB(ip, port, user, pass);

        k.query ="SELECT * FROM rawat";
        k.ambil();
        k.rs.last();
        int baris = k.rs.getRow();
        String baru;
        if(baris==0){
            baru = "R001";
        }else{
            int tambah = Integer.valueOf(k.rs.getString(1).substring(3,(k.rs.getString(1).length())))+ 1;
            if(tambah < 10){
                baru = "R00"+ tambah;
            }else if(tambah < 100){
                baru = "R0"+ tambah;
            }else{
                baru = "R"+ tambah;
            }
        }
        no_rawat=baru;

        System.out.println("┌────────────────────────────────────────┐");
        System.out.println("│         Registrasi Rawat Inap          │");
        System.out.println("├────────────────────────────────────────┤");
        System.out.println("│  1. ID Rawat (Otomatis): "+no_rawat);
        tgl = java.time.LocalDate.now().toString();
        System.out.print("│  2. Tanggal Rawat : "+tgl+" Ubah? : ");tgl = reader.readLine();
        if(tgl.equals("")){
            tgl = java.time.LocalDate.now().toString();
        }
        mpas.tampilPasienRawat();
        System.out.print("│  3. Masukan ID Pasien : ");no_pasien = reader.readLine();
        System.out.println("Kamar Tersedia :");
        mk.tampilKamarTersedia();
        System.out.print("│  4. Masukan KD Kamar : ");kd_kamar = reader.readLine();
        System.out.print("│  5. Masukan ID Perawat yang mengurus : ");id_perawat = reader.readLine();
        System.out.print("│  6. Masukan ID Dokter : ");id_dokter = reader.readLine();

        k.query = "insert into rawat values('"+no_rawat+"','"+tgl+"','"+no_pasien+"','"+kd_kamar+"','"+id_perawat+"','"+id_dokter+"',0,0,0);";
        k.crud();
        if(k.count>0){
            System.out.println("Data Berhasil Disimpan ");
            k.query = "update pasien set status = 1 where no_pasien='"+no_pasien+"'";
            k.crud();
            k.query = "update kamar set status = 1 where kd_kamar ='"+kd_kamar+"'";
            k.crud();
        }else{
            System.out.println("Gagal Menyimpan Data");
        }

        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();

    }



}
