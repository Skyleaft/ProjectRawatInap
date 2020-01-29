package projectrawatinap;

import projectrawatinap.koneksi.Koneksi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class MasterDataPerawat {
    Koneksi k = new Koneksi();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    Scanner scanner = new Scanner(System.in);

    public void setDB(String ip,String port,String user,String pass){
        k.setDB(ip,port,"rawat_inap",user,pass);
    }


    public void tambahPerawat() throws IOException, SQLException {
        String id = null,nama,alamat,no_telp,jk,jenis_kelamin = null,tgl_lahir;
        k.query ="SELECT * FROM perawat";
        k.ambil();
        k.rs.last();
        int baris = k.rs.getRow();
        String baru;
        if(baris==0){
            baru = "PER001";
        }else{
            int tambah = Integer.valueOf(k.rs.getString(1).substring(3,(k.rs.getString(1).length())))+ 1;
            if(tambah < 10){
                baru = "PER00"+ tambah;
            }else if(tambah < 100){
                baru = "PER0"+ tambah;
            }else{
                baru = "PER"+ tambah;
            }
        }
        id=baru;
        System.out.println("┌───────────────────────────────────────┐");
        System.out.println("│          Tambah Data Perawat          │");
        System.out.println("├───────────────────────────────────────┤");
        System.out.println("│  1. ID Perawat (Otomatis): "+id);
        System.out.print("│  2. Masukan Nama Perawat : ");nama = reader.readLine();
        System.out.print("│  3. Masukan alamat Perawat : ");alamat = reader.readLine();
        System.out.print("│  4. Masukan Nomor Telepon Perawat : ");no_telp = reader.readLine();
        System.out.print("│  5. Masukan Jenis Kelamin Perawat (L/P)?: ");jk = reader.readLine().toUpperCase();
        System.out.print("│  6. Masukan Tanggal Lahir Perawat(yyyy-mm-dd) : ");tgl_lahir = reader.readLine();
        if(jk.equals("L")){
            jenis_kelamin="Laki-Laki";
        }
        else if(jk.equals("P")){
            jenis_kelamin="Perempuan";
        }
        else{
            System.out.println("Salah Memasukan data Jenis Kelamin");
        }

        k.query = "insert into perawat values('"+id+"','"+nama+"','"+alamat+"','"+no_telp+"','"+jenis_kelamin+"','"+tgl_lahir+"');";
        k.crud();
        if(k.count>0){
            System.out.println("Data Berhasil Disimpan ");
        }else{
            System.out.println("Gagal Menyimpan Data");
        }
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void ubahPerawat() throws IOException, SQLException {
        String id,nama,alamat,no_telp,jenis_kelamin=null,tgl_lahir,jk=null;
        System.out.println("┌────────────────────────────────────────┐");
        System.out.println("│            Ubah Data Perawat           │");
        System.out.println("├────────────────────────────────────────┤");
        System.out.print("│  1. Masukan ID Perawat yang akan diubah : ");id = reader.readLine();
        k.query="select * from perawat where id_perawat='"+id+"'";
        k.ambil();
        if (k.rs.next()) {
            System.out.println("ID Perawat : "+id);
            System.out.println("Kosongkan jika tidak ingin diubah");
            System.out.print("Nama Perawat : "+k.rs.getString("nama_perawat")+"     Ubah? : ");nama = reader.readLine();
            System.out.print("Alamat Perawat : "+k.rs.getString("alamat")+"     Ubah? : ");alamat = reader.readLine();
            System.out.print("Nomor Telepon Perawat : "+k.rs.getString("no_telp")+"     Ubah? : ");no_telp = reader.readLine();
            System.out.print("jenis_kelamin Perawat : "+k.rs.getString("jenis_kelamin")+"     Ubah(L/P)? : ");jk = reader.readLine();
            System.out.print("Tanggal Lahir Perawat : "+k.rs.getString("tanggal_lahir")+"     Ubah(yyyy-mm-dd)? : ");tgl_lahir = reader.readLine();
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
                k.query="update perawat set nama_perawat='"+nama+"' where id_perawat='"+id+"'";
                k.crud();
            }
            if(!alamat.equals("")){
                k.query="update perawat set alamat='"+alamat+"' where id_perawat='"+id+"'";
                k.crud();
            }
            if(!no_telp.equals("")){
                k.query="update perawat set no_telp='"+no_telp+"' where id_perawat='"+id+"'";
                k.crud();
            }
            if(!jk.equals("")){
                k.query="update perawat set jenis_kelamin='"+jenis_kelamin+"' where id_perawat='"+id+"'";
                k.crud();
            }
            if(!tgl_lahir.equals("")){
                k.query="update perawat set tanggal_lahir='"+tgl_lahir+"' where id_perawat='"+id+"'";
                k.crud();
            }
        }else{
            System.out.println("Data Tidak Ditemukan");
        }

        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void hapusPerawat() throws IOException, SQLException {
        String id,yt;
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│        Hapus Data Perawat       │");
        System.out.println("├─────────────────────────────────┤");
        System.out.print("│  1. Masukan ID Perawat yang akan dihapus : ");id = reader.readLine();
        k.query="select * from perawat where id_perawat='"+id+"'";
        k.ambil();
        if (k.rs.next()){
            if(k.rs.getString("id_perawat").equals(id)){
                System.out.println("ID Perawat : "+id + " Nama Perawat : "+k.rs.getString("nama_perawat"));
                System.out.print("Yakin mau dihapus (y/t)? : ");yt=reader.readLine().toUpperCase();
                if(yt.equals("Y")){
                    k.query="delete from perawat where id_perawat='"+id+"'";
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

    public void cariPerawatID() throws IOException, SQLException {
        String id,yt;
        System.out.println("┌───────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                   Cari Data Perawat Berdasarkan ID                    │");
        System.out.println("├───────────────────────────────────────────────────────────────────────┤");
        System.out.print("│  1. Masukan ID Perawat yang ingin dicari : ");id = reader.readLine();
        System.out.println();
        CommandLineTable table = new CommandLineTable();
        table.setShowVerticalLines(true);
        table.setHeaders("ID Perawat","Nama Perawat","Alamat","No. Telp","Jenis Kelamin","Tanggal Lahir");
        k.query="select * from perawat where id_perawat='"+id+"'";
        k.ambil();
        if(k.rs.next()){
            table.addRow(k.rs.getString(1),k.rs.getString(2),k.rs.getString(3),
                    k.rs.getString(4),k.rs.getString(5),k.rs.getString(6));
            table.print();
        }else{
            System.out.println("Data Tidak Ditemukan");
        }
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void cariPerawatNama() throws IOException, SQLException {
        String nama,yt;
        System.out.println("┌───────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                   Cari Data Perawat Berdasarkan Nama                  │");
        System.out.println("├───────────────────────────────────────────────────────────────────────┤");
        System.out.print("│  1. Masukan Nama Perawat yang ingin dicari : ");nama = reader.readLine();
        System.out.println();
        CommandLineTable table = new CommandLineTable();
        table.setShowVerticalLines(true);
        table.setHeaders("ID Perawat","Nama Perawat","Alamat","No. Telp","Jenis Kelamin","Tanggal Lahir");
        k.query="SELECT *FROM perawat WHERE nama_perawat LIKE '%"+nama+"%'";
        k.ambil();
        while (k.rs.next()){
            table.addRow(k.rs.getString(1),k.rs.getString(2),k.rs.getString(3),
                    k.rs.getString(4),k.rs.getString(5),k.rs.getString(6));
        }
        table.print();
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void cariPerawatAlamat() throws IOException, SQLException {
        String alamat,yt;
        System.out.println("┌───────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                 Cari Data Perawat Berdasarkan Alamat                  │");
        System.out.println("├───────────────────────────────────────────────────────────────────────┤");
        System.out.print("│  1. Masukan Alamat Perawat yang ingin dicari : ");alamat = reader.readLine();
        System.out.println();
        CommandLineTable table = new CommandLineTable();
        table.setShowVerticalLines(true);
        table.setHeaders("ID Perawat","Nama Perawat","Alamat","No. Telp","Jenis Kelamin","Tanggal Lahir");
        k.query="SELECT *FROM perawat WHERE alamat = '"+alamat+"'";
        k.ambil();
        while (k.rs.next()){
            table.addRow(k.rs.getString(1),k.rs.getString(2),k.rs.getString(3),
                    k.rs.getString(4),k.rs.getString(5),k.rs.getString(6));
        }
        table.print();
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void tampilPerawat() throws SQLException, IOException {
        String id,nama,alamat,spesialisasi,no_telp,jk,jenis_kelamin = null,tgl_lahir;
        CommandLineTable table = new CommandLineTable();
        table.setShowVerticalLines(true);
        table.setHeaders("ID Perawat","Nama Perawat","Alamat","No. Telp","Jenis Kelamin","Tanggal Lahir");
        k.query="select *from perawat";
        k.ambil();
        while (k.rs.next()){
            table.addRow(k.rs.getString(1),k.rs.getString(2),k.rs.getString(3),
                    k.rs.getString(4),k.rs.getString(5),k.rs.getString(6));
        }
        table.print();
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }
    
}
