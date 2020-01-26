package projectrawatinap;

import projectrawatinap.koneksi.Koneksi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class MasterDataTindakan {
    Koneksi k = new Koneksi();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    Scanner scanner = new Scanner(System.in);

    public void setDB(String ip,String port,String user,String pass){
        k.setDB(ip,port,"rawat_inap",user,pass);
    }

    public void tambahTindakan() throws IOException, SQLException {
        String kd = null,nama,biaya = null;
        k.query ="SELECT * FROM tindakan";
        k.ambil();
        k.rs.last();
        int baris = k.rs.getRow();
        String baru;
        if(baris==0){
            baru = "TIN001";
        }else{
            int tambah = Integer.valueOf(k.rs.getString(1).substring(3,(k.rs.getString(1).length())))+ 1;
            if(tambah < 10){
                baru = "TIN00"+ tambah;
            }else if(tambah < 100){
                baru = "TIN0"+ tambah;
            }else{
                baru = "TIN"+ tambah;
            }
        }
        kd=baru;
        System.out.println("┌───────────────────────────────────────┐");
        System.out.println("│          Tambah Data Tindakan         │");
        System.out.println("├───────────────────────────────────────┤");
        System.out.println("│  1. Masukan Kode Tindakan (Otomatis) : "+kd);
        System.out.print("│  2. Masukan Nama Tindakan : ");nama = reader.readLine();
        System.out.print("│  3. Masukan Biaya Tindakan : ");biaya = reader.readLine();
        k.query = "insert into tindakan values('"+kd+"','"+nama+"','"+biaya+"');";
        k.crud();
        if(k.count>0){
            System.out.println("Data Berhasil Disimpan ");
        }else{
            System.out.println("Gagal Menyimpan Data");
        }
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void ubahTindakan() throws IOException, SQLException {
        String kd,nama,biaya;
        System.out.println("┌────────────────────────────────────────┐");
        System.out.println("│            Ubah Data Tindakan          │");
        System.out.println("├────────────────────────────────────────┤");
        System.out.print("│  1. Masukan Kode Tindakan yang akan diubah : ");kd = reader.readLine();
        k.query="select * from tindakan where kd_tindakan='"+kd+"'";
        k.ambil();
        if (k.rs.next()) {
            System.out.println("ID Perawat : "+kd);
            System.out.println("Kosongkan jika tidak ingin diubah");
            System.out.print("Nama Tindakan : "+k.rs.getString("nama_tindakan")+"     Ubah? : ");nama = reader.readLine();
            System.out.print("Biaya Tindakan : "+k.rs.getString("biaya_tindakan")+"     Ubah? : ");biaya = reader.readLine();
            if(!nama.equals("")){
                k.query="update tindakan set nama_tindakan='"+nama+"' where kd_tindakan='"+kd+"'";
                k.crud();
            }
            if(!biaya.equals("")){
                k.query="update tindakan set biaya_tindakan='"+biaya+"' where kd_tindakan='"+kd+"'";
                k.crud();
            }
        }else{
            System.out.println("Data Tidak Ditemukan");
        }

        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void hapusTindakan() throws IOException, SQLException {
        String kd,yt;
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│        Hapus Data Tindakan       │");
        System.out.println("├─────────────────────────────────┤");
        System.out.print("│  1. Masukan Kode Tindakan yang akan dihapus : ");kd = reader.readLine();
        k.query="select * from tindakan where kd_tindakan='"+kd+"'";
        k.ambil();
        if (k.rs.next()){
            if(k.rs.getString("kd_tindakan").equals(kd)){
                System.out.println("Kode Tindakan : "+kd + " Nama Tindakan : "+k.rs.getString("nama_tindakan"));
                System.out.print("Yakin mau dihapus (y/t)? : ");yt=reader.readLine().toUpperCase();
                if(yt.equals("Y")){
                    k.query="delete from tindakan where kd_tindakan='"+kd+"'";
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

    public void cariTindakanKd() throws IOException, SQLException {
        String kd,yt;
        System.out.println("┌───────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                   Cari Data Tindakan Berdasarkan Kode                    │");
        System.out.println("├───────────────────────────────────────────────────────────────────────┤");
        System.out.print("│  1. Masukan Kode Tindakan yang ingin dicari : ");kd = reader.readLine();
        System.out.println();
        CommandLineTable table = new CommandLineTable();
        table.setShowVerticalLines(true);
        table.setHeaders("Kode Tindakan","Nama Tindakan","Biaya Tindakan");
        k.query="select * from tindakan where kd_tindakan='"+kd+"'";
        k.ambil();
        if(k.rs.next()){
            table.addRow(k.rs.getString(1),k.rs.getString(2),k.rs.getString(3));
            table.print();
        }else{
            System.out.println("Data Tidak Ditemukan");
        }
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void cariTindakanNama() throws IOException, SQLException {
        String nama,yt;
        System.out.println("┌───────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                   Cari Data Tindakan Berdasarkan Nama                   │");
        System.out.println("├───────────────────────────────────────────────────────────────────────┤");
        System.out.print("│  1. Masukan Nama Tindakan yang ingin dicari : ");nama = reader.readLine();
        System.out.println();
        CommandLineTable table = new CommandLineTable();
        table.setShowVerticalLines(true);
        table.setHeaders("Kode Tindakan","Nama Tindakan","Biaya Tindakan");
        k.query="SELECT *FROM tindakan WHERE nama_tindakan LIKE '%"+nama+"%'";
        k.ambil();
        while (k.rs.next()){
            table.addRow(k.rs.getString(1),k.rs.getString(2),k.rs.getString(3));
        }
        table.print();
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void tampilTindakan() throws SQLException, IOException {
        String kd,nama,biaya = null;
        CommandLineTable table = new CommandLineTable();
        table.setShowVerticalLines(true);
        table.setHeaders("Kode Tindakan","Nama Tindakan","Biaya Tindakan");
        k.query="select *from tindakan";
        k.ambil();
        while (k.rs.next()){
            table.addRow(k.rs.getString(1),k.rs.getString(2),k.rs.getString(3));
        }
        table.print();
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }
    
}
