import java.util.InputMismatchException;
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

public class AntrianRumahSakit2 {
    private Queue<Pasien> antrianPasien;
    private Scanner scanner;

    public AntrianRumahSakit2() {
        antrianPasien = new LinkedList<>();
        scanner = new Scanner(System.in);
    }

    public void tambahAntrian() {
        try {
            System.out.print("\nMasukkan nama pasien: ");
            String nama = scanner.nextLine();
            int usia = -1;
            while (usia < 0) {
                try {
                    System.out.print("Masukkan usia pasien: ");
                    usia = Integer.parseInt(scanner.nextLine());
                    if (usia < 0) {
                        System.out.println("\n<Usia harus berupa angka positif!>");
                        System.out.println();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\n<Usia harus berupa angka!>");
                    System.out.println();
                }
            }
            Pasien pasien = new Pasien(nama, usia);
            antrianPasien.offer(pasien);
            System.out.println("\n<Pasien atas nama " + pasien.getNama() + " telah ditambahkan ke dalam antrian>");
        } catch (Exception e) {
            System.out.println("\n<Error: Terjadi kesalahan saat menambahkan pasien> " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void panggilAntrian() {
        try {
            Pasien pasien = antrianPasien.poll();
            if (pasien != null) {
                System.out.println("\nMEMANGGIL PASIEN");
                System.out.println("====================================================================");
                System.out.println("<Pasien atas nama " + pasien.getNama() + " silahkan menuju Apotek untuk mengambil obat!>");
            } else {
                System.out.println("\n<Antrian kosong, tidak ada pasien yang dapat dipanggil>");
            }
        } catch (Exception e) {
            System.out.println("\n<Error: Terjadi kesalahan saat memanggil pasien> " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void tampilkanAntrian() {
        try {
            if (antrianPasien.isEmpty()) {
                System.out.println("<Antrian Kosong>");
            } else {
                System.out.println("\nDAFTAR PASIEN DALAM ANTRIAN");
                System.out.println("===========================");
                Pasien[] pasienArray = antrianPasien.toArray(new Pasien[antrianPasien.size()]);
                for (int i = 0; i < pasienArray.length; i++) {
                    Pasien pasien = pasienArray[i];
                    System.out.println("Pasien ke-" + (i + 1));
                    System.out.println("Nama: " + pasien.getNama() + "\nUsia: " + pasien.getUsia());
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println("\n<Error: Terjadi kesalahan saat menampilkan antrian> " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AntrianRumahSakit2 antrianRS = new AntrianRumahSakit2();
        Scanner scanner = new Scanner(System.in);

        int pilihan = 0;
        do {
            System.out.println("\nSISTEM ANTRIAN RUMAH SAKIT");
            System.out.println("==========================");
            System.out.println("1. Menambah Pasien Baru ke Antrian");
            System.out.println("2. Memanggil Pasien");
            System.out.println("3. Menampilkan Urutan Antrian");
            System.out.println("4. Keluar");
            System.out.print("Pilih opsi (1/2/3/4): ");
            try {
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
                        System.out.println("\n<Pilihan tidak valid, harap input ulang pilihan Anda!>");
                }
            } catch (InputMismatchException e) {
                System.out.println("\n<Pilihan harus berupa angka!>");
                scanner.next(); 
            } catch (Exception e) {
                System.out.println("\n<Error: Terjadi kesalahan pada sistem> " + e.getMessage());
                e.printStackTrace();
                scanner.nextLine();
            }
        } while (pilihan != 4);

        scanner.close();
    }
}
