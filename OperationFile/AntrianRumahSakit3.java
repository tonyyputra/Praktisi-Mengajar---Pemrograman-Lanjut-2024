import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Pasien {
    private String nama;
    private int usia;
    private int nomorAntrian;
    private LocalDate tanggal;

    public Pasien(String nama, int usia, int nomorAntrian, LocalDate tanggal) {
        this.nama = nama;
        this.usia = usia;
        this.nomorAntrian = nomorAntrian;
        this.tanggal = tanggal;
    }

    public String getNama() {
        return nama;
    }

    public int getUsia() {
        return usia;
    }

    public int getNomorAntrian() {
        return nomorAntrian;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }
}

public class AntrianRumahSakit3 {
    private Queue<Pasien> antrianPasien;
    private Scanner scanner;
    private int nomorAntrian;
    private String fileName;

    public AntrianRumahSakit3(String fileName) {
        antrianPasien = new LinkedList<>();
        scanner = new Scanner(System.in);
        nomorAntrian = 1;
        this.fileName = fileName;
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
                    if (usia <= 0) {
                        System.out.println("<Error: Usia harus berupa angka positif.>");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("<Error: Usia harus berupa angka.>");
                }
            }
            LocalDate tanggal = LocalDate.now();
            Pasien pasien = new Pasien(nama, usia, nomorAntrian, tanggal);
            antrianPasien.offer(pasien);
            nomorAntrian++;
            simpanDataAntrian(pasien);
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
                System.out.println("\n<Pasien atas nama " + pasien.getNama() + " silahkan menuju Apotek untuk mengambil obat!>");
            } else {
                System.out.println("\n<Antrian kosong, tidak ada pasien yang dapat dipanggil>");
            }
        } catch (Exception e) {
            System.out.println("\n<Error: Terjadi kesalahan saat memanggil pasien> " + e.getMessage());
            e.fillInStackTrace();
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
                    System.out.println("\nPasien ke-" + (i + 1));
                    System.out.println("Nama: " + pasien.getNama() + "\nUsia: " + pasien.getUsia());
                    System.out.println("Nomor Antrian: " + pasien.getNomorAntrian() + "\nTanggal: " + pasien.getTanggal());
                }
            }
        } catch (Exception e) {
            System.out.println("\n<Error: Terjadi kesalahan saat menampilkan antrian> " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void simpanDataAntrian(Pasien pasien) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("Nama: " + pasien.getNama() + ", Usia: " + pasien.getUsia() +
                         ", Nomor Antrian: " + pasien.getNomorAntrian() + ", Tanggal: " + pasien.getTanggal());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("<Error: Terjadi kesalahan saat menyimpan data antrian> " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AntrianRumahSakit3 antrianRS = new AntrianRumahSakit3("dataAntrian.txt");
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
                        break;
                    default:
                        System.out.println("\n<Pilihan tidak valid>");
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
