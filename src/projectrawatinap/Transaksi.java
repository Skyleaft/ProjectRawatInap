package projectrawatinap;

import projectrawatinap.koneksi.Koneksi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

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


}
