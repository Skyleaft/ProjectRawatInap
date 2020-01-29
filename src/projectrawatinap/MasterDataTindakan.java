package projectrawatinap;

import projectrawatinap.koneksi.Koneksi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
        System.out.print("│  1. Masukan Kode Tindakan yang akan diubah : ");kd = reader.readLine().toUpperCase();
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
        System.out.println("│        Hapus Data Tindakan      │");
        System.out.println("├─────────────────────────────────┤");
        System.out.print("│  1. Masukan Kode Tindakan yang akan dihapus : ");kd = reader.readLine().toUpperCase();
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
        System.out.println("│                   Cari Data Tindakan Berdasarkan Kode                 │");
        System.out.println("├───────────────────────────────────────────────────────────────────────┤");
        System.out.print("│  1. Masukan Kode Tindakan yang ingin dicari : ");kd = reader.readLine().toUpperCase();
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
        System.out.println("│                   Cari Data Tindakan Berdasarkan Nama                 │");
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
        CommandLineTable table = new CommandLineTable();
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        table.setShowVerticalLines(true);
        table.setHeaders("Kode Tindakan","Nama Tindakan","Biaya Tindakan");
        k.query="select *from tindakan";
        k.ambil();
        while (k.rs.next()){
            table.addRow(k.rs.getString(1),k.rs.getString(2),kursIndonesia.format(k.rs.getInt(3)));
        }
        table.print();
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void tampilTindakan2() throws SQLException, IOException {
        CommandLineTable table = new CommandLineTable();
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        table.setShowVerticalLines(true);
        table.setHeaders("Kode Tindakan","Nama Tindakan","Biaya Tindakan");
        k.query="select *from tindakan";
        k.ambil();
        while (k.rs.next()){
            table.addRow(k.rs.getString(1),k.rs.getString(2),kursIndonesia.format(k.rs.getInt(3)));
        }
        table.print();
    }


    public void lihatDetailTindakan() throws SQLException, IOException {
        CommandLineTable table = new CommandLineTable();
        String kd,yt = null,no_rawat;
        int total_biaya_tindakan = 0;
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│             Detail Rawat            │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.print("│  1. Masukan No Rawat  : ");no_rawat = reader.readLine();


        k.query="SELECT *FROM detail_rawat JOIN tindakan ON tindakan.`kd_tindakan`=detail_rawat.`kd_tindakan` " +
                "JOIN `rawat` ON rawat.`no_rawat`=detail_rawat.`no_rawat` " +
                "JOIN pasien ON rawat.`no_pasien`=pasien.`no_pasien` WHERE detail_rawat.`no_rawat`='"+no_rawat+"'";
        k.ambil();

        table.setShowVerticalLines(true);
        table.setHeaders("No Rawat","Nama Pasien","Nama Tindakan","Biaya Tindakan");
        while (k.rs.next()){
            table.addRow(k.rs.getString("no_rawat"),k.rs.getString("nama_pasien"),
                    k.rs.getString("nama_tindakan"),kursIndonesia.format(k.rs.getInt("biaya_tindakan")));
            total_biaya_tindakan+=k.rs.getInt("biaya_tindakan");
        }
        table.print();
        System.out.println("Total Biaya Tindakan : "+kursIndonesia.format(total_biaya_tindakan));
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();

    }

    public void Tindak() throws IOException, SQLException {
        String kd,yt = null,no_rawat,nama_pasien;
        int total = 0;
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│            Tindakan             │");
        System.out.println("├─────────────────────────────────┤");
        System.out.print("│  1. Masukan No Rawat yang akan di tindak : ");no_rawat = reader.readLine();
        k.query="SELECT *FROM rawat JOIN pasien ON rawat.`no_pasien`=pasien.`no_pasien`  WHERE rawat.no_rawat='"+no_rawat+"'";
        k.ambil();
        if (k.rs.next()){
            nama_pasien=k.rs.getString("nama_pasien");
            do{
                System.out.println("No Rawat : "+no_rawat+"    Nama Pasien : "+nama_pasien);
                tampilTindakan2();
                System.out.print("│  2. Masukan Kode Tindakan : ");kd = reader.readLine();
                k.query="insert into detail_rawat values('"+no_rawat+"','"+kd+"')";
                k.crud();
                if(k.count>0){
                    System.out.println("Berhasil ditambahkan");
                }else{
                    System.out.println("Gagal Menambahkan tindakan");
                }
                System.out.print("Tindak Lagi? (y/t) : ");yt=reader.readLine().toUpperCase();
            }while (yt.equals("Y"));
        }else{
            System.out.println("Data Tidak Ditemukan");
        }
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }
    
}
