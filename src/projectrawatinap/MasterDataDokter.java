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

public class MasterDataDokter {
    Koneksi k = new Koneksi();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    Scanner scanner = new Scanner(System.in);
    public void setDB(String ip,String port,String user,String pass){
        k.setDB(ip,port,"rawat_inap",user,pass);
    }

    public void tambahDokter() throws IOException {
        String id,nama,alamat,spesialisasi,no_telp,jk,jenis_kelamin = null,tgl_lahir;
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│      Tambah Data Dokter         │");
        System.out.println("├─────────────────────────────────┤");
        System.out.print("│  1. Masukan ID Dokter : ");id = reader.readLine();
        System.out.print("│  2. Masukan Nama Dokter : ");nama = reader.readLine();
        System.out.print("│  3. Masukan alamat Dokter : ");alamat = reader.readLine();
        System.out.print("│  4. Masukan spesialisasi Dokter : ");spesialisasi = reader.readLine();
        System.out.print("│  5. Masukan Nomor Telepon Dokter : ");no_telp = reader.readLine();
        System.out.print("│  6. Masukan Jenis Kelamin Dokter (L/P)?: ");jk = reader.readLine().toUpperCase();
        System.out.print("│  7. Masukan Tanggal Lahir Dokter(yyyy-mm-dd) : ");tgl_lahir = reader.readLine();
        if(jk.equals("L")){
            jenis_kelamin="Laki-Laki";
        }
        else if(jk.equals("P")){
            jenis_kelamin="Perempuan";
        }
        else{
            System.out.println("Salah Memasukan data Jenis Kelamin");
        }

        k.query = "insert into dokter values('"+id+"','"+nama+"','"+alamat+"','"+spesialisasi+"','"+no_telp+"','"+jenis_kelamin+"','"+tgl_lahir+"');";
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
        String id,nama,alamat,spesialisasi,no_telp,jenis_kelamin,tgl_lahir;
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│        Ubah Data Dokter         │");
        System.out.println("├─────────────────────────────────┤");
        System.out.print("│  1. Masukan ID Dokter yang akan diubah : ");id = reader.readLine();
        k.query="select * from dokter where id_dokter='"+id+"'";
        k.ambil();
        if (k.rs.next()) {
            if(k.rs.getString("id_dokter").equals(id)){
                System.out.println("ID Dokter : "+id);
                System.out.println("Kosongkan jika tidak ingin diubah");
                System.out.print("Nama Dokter : "+k.rs.getString("nama_dokter")+"   Ubah? : ");nama = reader.readLine();
                System.out.print("alamat Dokter : "+k.rs.getString("alamat")+"   Ubah? : ");alamat = reader.readLine();
                System.out.print("Spesialisasi Dokter : "+k.rs.getString("spesialisasi")+"   Ubah? : ");spesialisasi = reader.readLine();
                System.out.print("Nomor Telepon Dokter : "+k.rs.getString("no_telp")+"   Ubah? : ");no_telp = reader.readLine();
                System.out.print("jenis_kelamin Dokter : "+k.rs.getString("jenis_kelamin")+"   Ubah? : ");jenis_kelamin = reader.readLine();
                System.out.print("tgl_lahir Dokter : "+k.rs.getString("tgl_lahir")+"   Ubah? : ");tgl_lahir = reader.readLine();
                if(!nama.equals("")){
                    k.query="update dokter set nama_dokter='"+nama+"' where id_dokter='"+id+"'";
                    k.crud();
                }
                if(!alamat.equals("")){
                    k.query="update dokter set alamat='"+alamat+"' where id_dokter='"+id+"'";
                    k.crud();
                }
                if(!spesialisasi.equals("")){
                    k.query="update dokter set spesialisasi='"+spesialisasi+"' where id_dokter='"+id+"'";
                    k.crud();
                }
                if(!no_telp.equals("")){
                    k.query="update dokter set no_telp='"+no_telp+"' where id_dokter='"+id+"'";
                    k.crud();
                }
                if(!jenis_kelamin.equals("")){
                    k.query="update dokter set jenis_kelamin='"+jenis_kelamin+"' where id_dokter='"+id+"'";
                    k.crud();
                }
                if(!tgl_lahir.equals("")){
                    k.query="update dokter set tgl_lahir='"+tgl_lahir+"' where id_dokter='"+id+"'";
                    k.crud();
                }
            }
        }else{
            System.out.println("Data Tidak Ditemukan");
        }

        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void hapusDokter() throws IOException, SQLException {
        String id,yt;
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│        Hapus Data Dokter        │");
        System.out.println("├─────────────────────────────────┤");
        System.out.print("│  1. Masukan ID Dokter yang akan dihapus : ");id = reader.readLine();
        k.query="select * from dokter where id_dokter='"+id+"'";
        k.ambil();
        if (k.rs.next()){
            if(k.rs.getString("id_dokter").equals(id)){
                System.out.println("ID Dokter : "+id + " Nama Dokter : "+k.rs.getString("nama_dokter"));
                System.out.print("Yakin mau dihapus (y/t)? : ");yt=reader.readLine().toUpperCase();
                if(yt.equals("Y")){
                    k.query="delete from dokter where id_dokter='"+id+"'";
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

    public void cariDokter() throws IOException, SQLException {
        String nama,yt;
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│        Hapus Data Dokter        │");
        System.out.println("├─────────────────────────────────┤");
        System.out.print("│  1. Masukan Nama Dokter yang ingin dicari : ");nama = reader.readLine();
        if(k.rs.getString("nama_dokter").equals(nama)){
            k.query="select * from dokter where nama_dokter='"+nama+"'";
            k.ambil();
        }else{
            System.out.println("Data Tidak Ditemukan");
        }
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }

    public void tampilDokter() throws SQLException, IOException {
        String id,nama,alamat,spesialisasi,no_telp,jk,jenis_kelamin = null,tgl_lahir;
        System.out.println("┌───────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                                        Data Dokter                                        │");
        System.out.println("├───────────────────────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│  ID Dokter  │  Nama Dokter  │ Alamat  │ Spesialisasi  │  No.Telp  │ Jenis Kelamin │ Tanggal Lahir │");
        k.query="select *from dokter";
        k.ambil();
        while (k.rs.next()){
            System.out.println("│ "+k.rs.getString(1)+" │ "+k.rs.getString(2)+" │ "+k.rs.getString(3)+" │ "+k.rs.getString(4)+" │ "+k.rs.getString(5)+" │ "+k.rs.getString(6)+" │ "+k.rs.getString(7));
        }
        System.out.println("Tekan Enter untuk melanjutkan");
        reader.readLine();
    }
}
