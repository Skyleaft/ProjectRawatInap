package projectrawatinap;

import projectrawatinap.koneksi.Koneksi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Scanner;

/**
 *
 * @author msi
 */

public class MasterDataKamar {
    Koneksi k = new Koneksi();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    Scanner scanner = new Scanner(System.in);


    public void setDB(String ip,String port,String user,String pass){
        k.setDB(ip,port,"rawat_inap",user,pass);
    }

    public void tambahKamar() throws IOException, SQLException {
        String kd = null,tipe_kamar = null,no_kamar,biaya_kamar = null,deskripsi;
        k.query ="SELECT * FROM kamar";
        k.ambil();
        k.rs.last();
        int baris = k.rs.getRow();
        String baru;
        if(baris==0){
            baru = "KMR001";
        }else{
            int tambah = Integer.valueOf(k.rs.getString(1).substring(3,(k.rs.getString(1).length())))+ 1;
            if(tambah < 10){
                baru = "KMR00"+ tambah;
            }else if(tambah < 100){
                baru = "KMR0"+ tambah;
            }else{
                baru = "KMR"+ tambah;
            }
        }
        kd=baru;
        System.out.println("┌────────────────────────────────────┐");
        System.out.println("│          Tambah Data Kamar         │");
        System.out.println("├────────────────────────────────────┤");
        System.out.println("│  1. Kode Kamar (Otomatis): "+kd);
        System.out.print("│  2. Masukan Tipe Kamar : ");tipe_kamar = reader.readLine();
        System.out.print("│  3. Masukan Nomor Kamar : ");no_kamar = reader.readLine();
        System.out.print("│  4. Masukan Biaya Kamar : ");biaya_kamar = reader.readLine();
        System.out.print("│  5. Masukan Deskripsi Kamar : ");deskripsi = reader.readLine();
        k.query = "insert into kamar values('"+kd+"','"+tipe_kamar+"','"+no_kamar+"','"+biaya_kamar+"','"+deskripsi+"','0');";
        k.crud();
        if(k.count>0){
            System.out.println("Data Berhasil Disimpan ");
        }else{
            System.out.println("Gagal Menyimpan Data");
        }
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void ubahKamar() throws IOException, SQLException {
        String kd = null,tipe_kamar = null,no_kamar,biaya_kamar = null,deskripsi;
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│         Ubah Data Kamar         │");
        System.out.println("├─────────────────────────────────┤");
        System.out.print("│  1. Masukan Kode Kamar yang akan diubah : ");kd = reader.readLine();
        k.query="select * from kamar where kd_kamar='"+kd+"'";
        k.ambil();
        if (k.rs.next()) {
            System.out.println("Kode Kamar : "+kd);
            System.out.println("Kosongkan jika tidak ingin diubah");
            System.out.print("Tipe Kamar : "+k.rs.getString("tipe_kamar")+"   Ubah? : ");tipe_kamar = reader.readLine();
            System.out.print("Nomor Kamar : "+k.rs.getString("no_kamar")+"   Ubah? : ");no_kamar = reader.readLine();
            System.out.print("Biaya Kamar : "+k.rs.getString("biaya_kamar")+"   Ubah? : ");biaya_kamar = reader.readLine();
            System.out.print("Deskripsi kamar : "+k.rs.getString("Deskripsi")+"   Ubah? : ");deskripsi = reader.readLine();
            if(!tipe_kamar.equals("")){
                k.query="update kamar set tipe_kamar='"+tipe_kamar+"' where kd_kamar='"+kd+"'";
                k.crud();
            }
            if(!no_kamar.equals("")){
                k.query="update kamar set no_kamar='"+no_kamar+"' where kd_kamar='"+kd+"'";
                k.crud();
            }
            if(!biaya_kamar.equals("")){
                k.query="update kamar set biaya_kamar='"+biaya_kamar+"' where kd_kamar='"+kd+"'";
                k.crud();
            }
            if(!deskripsi.equals("")){
                k.query="update kamar set Deskripsi='"+deskripsi+"' where kd_kamar='"+kd+"'";
                k.crud();
            }
        }else{
            System.out.println("Data Tidak Ditemukan");
        }

        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void hapusKamar() throws IOException, SQLException {
        String kd,yt;
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│         Hapus Data kamar        │");
        System.out.println("├─────────────────────────────────┤");
        System.out.print("│  1. Masukan Kode Kamar yang akan dihapus : ");kd = reader.readLine();
        k.query="select * from kamar where kd_kamar='"+kd+"'";
        k.ambil();
        if (k.rs.next()){
            if(k.rs.getString("kd_kamar").equals(kd)){
                System.out.println("Kode Kamar : "+kd + " Tipe Kamar : "+k.rs.getString("tipe_kamar") + " Nomor Kamar : "+k.rs.getString("no_kamar"));
                System.out.print("Yakin mau dihapus (y/t)? : ");yt=reader.readLine().toUpperCase();
                if(yt.equals("Y")){
                    k.query="delete from kamar where kd_kamar='"+kd+"'";
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

    public void cariKamarKd() throws IOException, SQLException {
        String kd,yt;
        System.out.println("┌───────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                   Cari Data Kamar Berdasarkan Kode                    │");
        System.out.println("├───────────────────────────────────────────────────────────────────────┤");
        System.out.print("│  1. Masukan Kode Kamar yang ingin dicari : ");kd = reader.readLine();
        System.out.println();
        CommandLineTable table = new CommandLineTable();
        table.setShowVerticalLines(true);
        table.setHeaders("Kode Kamar","Tipe Kamar","Nomor Kamar","Biaya Kamar","Deskripsi");
        k.query="select * from kamar where kd_kamar='"+kd+"'";
        k.ambil();
        if(k.rs.next()){
            table.addRow(k.rs.getString(1),k.rs.getString(2),k.rs.getString(3),
                    k.rs.getString(4),k.rs.getString(5));
            table.print();
        }else{
            System.out.println("Data Tidak Ditemukan");
        }
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void cariKamarTipe() throws IOException, SQLException {
        String tipe_kamar,yt;
        System.out.println("┌───────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                    Cari Data Kamar Berdasarkan Tipe                   │");
        System.out.println("├───────────────────────────────────────────────────────────────────────┤");
        System.out.print("│  1. Masukan Tipe Kamar yang ingin dicari : ");tipe_kamar = reader.readLine();
        System.out.println();
        CommandLineTable table = new CommandLineTable();
        table.setShowVerticalLines(true);
        table.setHeaders("Kode Kamar","Tipe Kamar","Nomor Kamar","Biaya Kamar","Deskripsi");
        k.query="SELECT *FROM kamar WHERE tipe_kamar LIKE '%"+tipe_kamar+"%'";
        k.ambil();
        while (k.rs.next()){
            table.addRow(k.rs.getString(1),k.rs.getString(2),k.rs.getString(3),
                    k.rs.getString(4),k.rs.getString(5));
        }
        table.print();
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }
    public void tampilKamar() throws SQLException, IOException {
        CommandLineTable table = new CommandLineTable();
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        table.setShowVerticalLines(true);
        table.setHeaders("Kode Kamar","Tipe Kamar","Nomor Kamar","Biaya Kamar","Deskripsi");
        k.query="select * from kamar";
        k.ambil();
        while (k.rs.next()){
            table.addRow(k.rs.getString(1),k.rs.getString(2),k.rs.getString(3),
                    kursIndonesia.format(k.rs.getInt(4)),k.rs.getString(5));

        }
        table.print();
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void tampilKamarTersedia() throws SQLException, IOException {
        CommandLineTable table = new CommandLineTable();
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        table.setShowVerticalLines(true);
        table.setHeaders("Kode Kamar","Tipe Kamar","Nomor Kamar","Biaya Kamar");

        k.query="select * from kamar where status =0";
        k.ambil();
        while (k.rs.next()){
            table.addRow(k.rs.getString(1),k.rs.getString(2),k.rs.getString(3),
                    kursIndonesia.format(k.rs.getInt(4)));
        }
        table.print();

    }
}
