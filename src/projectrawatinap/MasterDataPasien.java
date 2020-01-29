package projectrawatinap;

import projectrawatinap.koneksi.Koneksi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class MasterDataPasien {
    Koneksi k = new Koneksi();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    Scanner scanner = new Scanner(System.in);


    public void setDB(String ip,String port,String user,String pass){
        k.setDB(ip,port,"rawat_inap",user,pass);
    }

    public void ubahPasien() throws IOException, SQLException {
        String kd = null,nama = null,no_telp,tgl_lahir = null,deskripsi,jk = null,jenis_kelamin = null,alamat,pekerjaan;
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│        Ubah Data Pasien         │");
        System.out.println("├─────────────────────────────────┤");
        System.out.print("│  1. Masukan ID Pasien yang akan diubah : ");kd = reader.readLine().toUpperCase();
        k.query="select * from pasien where no_pasien='"+kd+"'";
        k.ambil();
        if (k.rs.next()) {
            System.out.println("Kode Kamar : "+kd);
            System.out.println("Kosongkan jika tidak ingin diubah");
            System.out.print("Nama Pasien : "+k.rs.getString("nama_pasien")+"   Ubah? : ");nama = reader.readLine();
            System.out.print("Jenis Kelamin : "+k.rs.getString("jenis_kelamin")+"   Ubah? : ");jk = reader.readLine().toUpperCase();
            System.out.print("Tanggal Lahir : "+k.rs.getString("tgl_lahir")+"   Ubah? : ");tgl_lahir = reader.readLine();
            System.out.print("No. Telp : "+k.rs.getString("no_telp")+"   Ubah? : ");no_telp = reader.readLine();
            System.out.print("Alamat : "+k.rs.getString("alamat")+"   Ubah? : ");alamat = reader.readLine();
            System.out.print("Pekerjaan : "+k.rs.getString("pekerjaan")+"   Ubah? : ");pekerjaan = reader.readLine();
            if(jk.equals("L")){
                jenis_kelamin="Laki-Laki";
            }
            else if(jk.equals("P")){
                jenis_kelamin="Perempuan";
            }
            else{
                System.out.println("Salah Memasukan data Jenis Kelamin");
            }
            if(!nama.equals("")){
                k.query="update pasien set tipe_pasien='"+nama+"' where no_pasien='"+kd+"'";
                k.crud();
            }
            if(!jk.equals("")){
                k.query="update pasien set jenis_kelamin='"+jenis_kelamin+"' where no_pasien='"+kd+"'";
                k.crud();
            }
            if(!tgl_lahir.equals("")){
                k.query="update pasien set tgl_lahir='"+tgl_lahir+"' where no_pasien='"+kd+"'";
                k.crud();
            }
            if(!no_telp.equals("")){
                k.query="update pasien set no_telp='"+no_telp+"' where no_pasien='"+kd+"'";
                k.crud();
            }
            if(!alamat.equals("")){
                k.query="update pasien set alamat='"+alamat+"' where no_pasien='"+kd+"'";
                k.crud();
            }
            if(!pekerjaan.equals("")){
                k.query="update pasien set pekerjaan='"+pekerjaan+"' where no_telp='"+kd+"'";
                k.crud();
            }
        }else{
            System.out.println("Data Tidak Ditemukan");
        }

        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void hapusPasien() throws IOException, SQLException {
        String id,yt;
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│        Hapus Data Pasien        │");
        System.out.println("├─────────────────────────────────┤");
        System.out.print("│  1. Masukan ID Pasien yang akan dihapus : ");id = reader.readLine().toUpperCase();
        k.query="select * from pasien where no_pasien='"+id+"'";
        k.ambil();
        if (k.rs.next()){
            if(k.rs.getString("no_pasien").equals(id)){
                System.out.println("ID Pasien : "+id + " Nama Pasien : "+k.rs.getString("nama_pasien"));
                System.out.print("Yakin mau dihapus (y/t)? : ");yt=reader.readLine().toUpperCase();
                if(yt.equals("Y")){
                    k.query="delete from pasien where no_pasien='"+id+"'";
                    k.crud();
                    if(k.count>0){
                        System.out.println("Data Berhasil Dihapus ");
                    }else{
                        System.out.println("Gagal Menghapus Data");
                    }
                }
            }
        }else{
            System.out.println("Data Tidak Ditemukan");
        }


        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void cariPasienID() throws IOException, SQLException {
        String id;
        System.out.println("┌───────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                   Cari Data Pasien Berdasarkan ID                     │");
        System.out.println("├───────────────────────────────────────────────────────────────────────┤");
        System.out.print("│  1. Masukan ID Pasien yang ingin dicari : ");id = reader.readLine().toUpperCase();
        System.out.println();
        CommandLineTable table = new CommandLineTable();
        table.setShowVerticalLines(true);
        table.setHeaders("ID Pasien","Nama Pasien","Jenis Kelamin","Tanggal Lahir","No. Telp","Alamat","Pekerjaan");
        k.query="select * from pasien where no_pasien='"+id+"'";
        k.ambil();
        if(k.rs.next()){
            table.addRow(k.rs.getString(1),k.rs.getString(2),k.rs.getString(3),
                    k.rs.getString(4),k.rs.getString(5),k.rs.getString(6),k.rs.getString(7));
            table.print();
        }else{
            System.out.println("Data Tidak Ditemukan");
        }
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void cariPasienNama() throws IOException, SQLException {
        String nama;
        System.out.println("┌───────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                   Cari Data Pasien Berdasarkan Nama                   │");
        System.out.println("├───────────────────────────────────────────────────────────────────────┤");
        System.out.print("│  1. Masukan Nama Pasien yang ingin dicari : ");nama = reader.readLine();
        System.out.println();
        CommandLineTable table = new CommandLineTable();
        table.setShowVerticalLines(true);
        table.setHeaders("ID Pasien","Nama Pasien","Jenis Kelamin","Tanggal Lahir","No. Telp","Alamat","Pekerjaan");
        k.query="SELECT *FROM pasien WHERE nama_pasien LIKE '%"+nama+"%'";
        k.ambil();
        while (k.rs.next()){
            table.addRow(k.rs.getString(1),k.rs.getString(2),k.rs.getString(3),
                    k.rs.getString(4),k.rs.getString(5),k.rs.getString(6),k.rs.getString(7));
        }
        table.print();
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void tampilPasien() throws SQLException, IOException {
        CommandLineTable table = new CommandLineTable();
        table.setShowVerticalLines(true);
        table.setHeaders("ID Pasien","Nama Pasien","Jenis Kelamin","Tanggal Lahir","No. Telp","Alamat","Pekerjaan");
        k.query="select *from pasien";
        k.ambil();
        while (k.rs.next()){
            table.addRow(k.rs.getString(1),k.rs.getString(2),k.rs.getString(3),
                    k.rs.getString(4),k.rs.getString(5),k.rs.getString(6),k.rs.getString(7));
        }
        table.print();
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void tampilPasienRawat() throws SQLException, IOException {
        CommandLineTable table = new CommandLineTable();
        table.setShowVerticalLines(true);
        table.setHeaders("ID Pasien","Nama Pasien","Jenis Kelamin","Tanggal Lahir");
        k.query="select *from pasien where status=0";
        k.ambil();
        while (k.rs.next()){
            table.addRow(k.rs.getString(1),k.rs.getString(2),k.rs.getString(3),k.rs.getString(6));
        }
        table.print();
    }



}

