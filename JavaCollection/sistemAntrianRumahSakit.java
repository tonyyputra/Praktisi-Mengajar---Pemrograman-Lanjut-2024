import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Pasien {
    private String nama;
    private int usia;

    public Pasien(String nama, int usia) {
        this.nama = nama;
        this.usia = usia;
    }

    public String getNama() {
        return nama;
    }

    public int getUsia() {
        return usia;
    }
}

public class AntrianRumahSakit {
    private Queue<Pasien> antrianPasien;
    private Scanner scanner;

    public AntrianRumahSakit() {
        antrianPasien = new LinkedList<>();
        scanner = new Scanner(System.in);
    }

    public void tambahAntrian() {
        System.out.print("\nMasukkan nama pasien: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan usia pasien: ");
        int usia = scanner.nextInt();
        scanner.nextLine();
        Pasien pasien = new Pasien(nama, usia);
        antrianPasien.offer(pasien);
        System.out.println();
        System.out.println("<Pasien atas nama " + pasien.getNama() + " telah ditambahkan ke dalam antrian>");
    }

    public void panggilAntrian() {
        Pasien pasien = antrianPasien.poll();
        if (pasien != null) {
            System.out.println ("\nMEMANGGIL PASIEN");
            System.out.println("====================================================================");
            System.out.println("<Pasien atas nama " + pasien.getNama() + " silahkan menuju Apotek untuk mengambil obat!>");
        } else {
            System.out.println("\n<Antrian kosong, tidak ada pasien yang dapat dipanggil>");
        }
    }

    public void tampilkanAntrian() {
        if (antrianPasien.isEmpty()) {
            System.out.println("<Antrian Kosong>");
        } else {
            System.out.println("\nDAFTAR PASIEN DALAM ANTRIAN");
            System.out.println("===========================");
            Pasien[] pasienArray = antrianPasien.toArray(new Pasien[antrianPasien.size()]);
            for (int i = 0; i < pasienArray.length; i++) {
                Pasien pasien = pasienArray[i];
                System.out.println("\nPasien ke-" + (i + 1));
                System.out.println("Nama: " + pasien.getNama() + "\nUsia: " + pasien.getUsia());
            }
        }
    }

    public static void main(String[] args) {
        AntrianRumahSakit antrianRS = new AntrianRumahSakit();
        Scanner scanner = new Scanner(System.in);

        int pilihan;
        do {
            System.out.println("\nSISTEM ANTRIAN RUMAH SAKIT");
            System.out.println("==========================");
            System.out.println("1. Menambah Pasien Baru ke Antrian");
            System.out.println("2. Memanggil Pasien");
            System.out.println("3. Menampilkan Urutan Antrian");
            System.out.println("4. Keluar");
            System.out.print("Pilih opsi (1/2/3/4): ");
            pilihan = scanner.nextInt();
            scanner.nextLine();
            switch (pilihan) {
                case 1:
                    antrianRS.tambahAntrian();
                    break;
                case 2:
                    antrianRS.panggilAntrian();
                    break;
                case 3:
                    antrianRS.tampilkanAntrian();
                    break;
                case 4:
                    System.out.println("\n<Shutting Down System>");
                    System.out.println();
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 4);
        
        scanner.close();
    }
}
