package projectrawatinap;

import projectrawatinap.koneksi.Koneksi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.*;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Transaksi {
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

    public void pembayaran() throws SQLException, IOException, ParseException {
        String no_faktur,no_rawat,tgl,no_pasien = null,kd_kamar = null,tgl_reg;
        Date dreg,dbayar;
        long diff;
        int nominal,hari = 0,biaya_kamar = 0,total_biaya_kamar,total_biaya_tindakan = 0,total;
        int biaya_tindakan,kembalian;
        CommandLineTable table = new CommandLineTable();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        k.query ="SELECT * FROM pembayaran";
        k.ambil();
        k.rs.last();
        int baris = k.rs.getRow();
        String baru;
        if(baris==0){
            baru = "TR001";
        }else{
            int tambah = Integer.valueOf(k.rs.getString(1).substring(3,(k.rs.getString(1).length())))+ 1;
            if(tambah < 10){
                baru = "TR00"+ tambah;
            }else if(tambah < 100){
                baru = "TR0"+ tambah;
            }else{
                baru = "TR"+ tambah;
            }
        }
        no_faktur=baru;

        System.out.println("┌────────────────────────────────────┐");
        System.out.println("│              Pembayaran            │");
        System.out.println("├────────────────────────────────────┤");
        System.out.println("│  1. No Faktur (Otomatis): "+no_faktur);
        tgl = java.time.LocalDate.now().toString();
        System.out.println("│  2. Tanggal Bayar : "+tgl);
        System.out.print("│  3. Masukan No Rawat : ");no_rawat = reader.readLine();
        k.query="SELECT *FROM rawat JOIN pasien ON rawat.`no_pasien`=pasien.`no_pasien` JOIN kamar ON rawat.`kd_kamar`=kamar.`kd_kamar` WHERE rawat.no_rawat='"+no_rawat+"'";
        k.ambil();
        if(k.rs.next()){
            if (k.rs.getInt("rawat.status")==1){
                no_pasien = k.rs.getString("no_pasien");
                kd_kamar = k.rs.getString("kd_kamar");
                tgl_reg = k.rs.getString("tgl_register");
                biaya_kamar = k.rs.getInt("biaya_kamar");
                dreg = dateFormat.parse(tgl_reg);
                dbayar = dateFormat.parse(tgl);
                diff = dbayar.getTime() - dreg.getTime();
                hari = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                k.query="SELECT *FROM detail_rawat JOIN tindakan ON tindakan.`kd_tindakan`=detail_rawat.`kd_tindakan` JOIN `rawat` ON rawat.`no_rawat`=detail_rawat.`no_rawat` WHERE detail_rawat.`no_rawat`='"+no_rawat+"'";
                k.ambil();
                while (k.rs.next()){
                    biaya_tindakan=k.rs.getInt("biaya_tindakan");
                    total_biaya_tindakan += biaya_tindakan;
                }
                System.out.println("│  4. Lama Menginap : "+hari+" Hari");
                total_biaya_kamar = hari * biaya_kamar;

                System.out.println("│  5. Total Biaya Kamar : "+kursIndonesia.format(total_biaya_kamar));
                System.out.println("│  6. Total Biaya Tindakan : "+kursIndonesia.format(total_biaya_tindakan));
                if(total_biaya_tindakan!=0){
                    total= total_biaya_tindakan+total_biaya_kamar;
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
                }else{
                    total=total_biaya_kamar;
                }
                System.out.println("│  7. Total Keseluruhan : "+kursIndonesia.format(total));
                System.out.print("│  8. Masukan Nominal bayar : ");nominal = scanner.nextInt();
                if(nominal>=total){
                    kembalian = nominal - total;
                    System.out.println("│  9. Kembalian : "+kursIndonesia.format(kembalian));
                    k.query = "insert into pembayaran values('"+no_faktur+"','"+no_rawat+"','"+tgl+"','"+total+"','"+nominal+"','"+kembalian+"');";
                    k.crud();
                    if(k.count>0){
                        System.out.println("Data Berhasil Disimpan ");
                        k.query = "update pasien set status = 0 where no_pasien='"+no_pasien+"'";
                        k.crud();
                        k.query = "update kamar set status = 0 where kd_kamar ='"+kd_kamar+"'";
                        k.crud();
                        k.query = "update rawat set status = 0 where no_rawat ='"+no_rawat+"'";
                        k.crud();
                    }else{
                        System.out.println("Gagal Menyimpan Data");
                    }
                    System.out.println("Pembayaran Selesai");
                }else{
                    System.out.println("Uang Anda Kurang");
                }

            }else{
                System.out.println("Transaksi sudah dilakukan");
            }
        }else {
            System.out.println("Data Tidak Ditemukan");
        }

        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }
}
