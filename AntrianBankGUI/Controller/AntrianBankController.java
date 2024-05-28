package AntrianBankGUI.Controller;

import AntrianBankGUI.Model.Nasabah;
import AntrianBankGUI.View.NotificationDialog;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class AntrianBankController {
    private Queue<Nasabah> antrianNasabah;
    private int nomorAntrianTeller;
    private int nomorAntrianCustomerService;
    private String fileName;
    private Map<String, Integer> layananCounters;
    private static final int COUNTER_LIMIT = 5;

    public AntrianBankController(String fileName) {
        antrianNasabah = new LinkedList<>();
        nomorAntrianTeller = 1;
        nomorAntrianCustomerService = 1;
        this.fileName = fileName;
        layananCounters = new HashMap<>();
        layananCounters.put("Teller", 1);
        layananCounters.put("Customer Service", 1);
    }

    public void tambahAntrian(JTextField namaField, JComboBox<String> layananComboBox, JTextField nomorRekeningField, JComboBox<String> jenisTransaksiComboBox, JTextField kontakInformasiField) {
        try {
            String nama = namaField.getText();
            String layanan = (String) layananComboBox.getSelectedItem();
            String nomorRekening = nomorRekeningField.getText();
            String jenisTransaksi = (String) jenisTransaksiComboBox.getSelectedItem();
            String kontakInformasi = kontakInformasiField.getText();

            if (nama.isEmpty() || layanan.isEmpty() || nomorRekening.isEmpty() || jenisTransaksi.isEmpty() || kontakInformasi.isEmpty()) {
                NotificationDialog.showNotification("Semua data harus diisi!");
                return;
            }

            String nomorAntrian = generateNomorAntrian(layanan);
            LocalDate tanggal = LocalDate.now();
            Nasabah nasabah = new Nasabah(nama, nomorAntrian, tanggal, layanan, nomorRekening, jenisTransaksi, kontakInformasi);
            antrianNasabah.add(nasabah);
            simpanDataAntrian(nasabah);

            namaField.setText("");
            layananComboBox.setSelectedIndex(0);
            nomorRekeningField.setText("");
            jenisTransaksiComboBox.setSelectedIndex(0);
            kontakInformasiField.setText("");

            NotificationDialog.showNotification("Nasabah berhasil ditambahkan ke antrian!");
        } catch (Exception e) {
            NotificationDialog.showNotification("Error: Terjadi kesalahan saat menambahkan nasabah. " + e.getMessage());
        }
    }

    private String generateNomorAntrian(String layanan) {
        int nomor = layanan.equals("Teller") ? nomorAntrianTeller++ : nomorAntrianCustomerService++;
        if (nomor > 99) {
            nomor = 1;
            if (layanan.equals("Teller")) {
                nomorAntrianTeller = 1;
            } else {
                nomorAntrianCustomerService = 1;
            }
        }
        return (nomor < 10 ? "0" : "") + nomor + (layanan.equals("Teller") ? "A" : "B");
    }

    public void panggilAntrian(JTextArea outputArea) {
        try {
            if (!antrianNasabah.isEmpty()) {
                Nasabah nasabah = antrianNasabah.poll();
                String layanan = nasabah.getLayanan();
                int counter = layananCounters.get(layanan);
                layananCounters.put(layanan, (counter % COUNTER_LIMIT) + 1);
                outputArea.setText("<Nomor antrian " + nasabah.getNomorAntrian() + " silahkan menuju " + nasabah.getLayanan() + " counter " + counter + ">");
            } else {
                NotificationDialog.showNotification("Antrian kosong, tidak ada nasabah yang dapat dipanggil!");
            }
        } catch (Exception e) {
            NotificationDialog.showNotification("Error: Terjadi kesalahan saat memanggil nasabah. " + e.getMessage());
        }
    }

    public void tampilkanAntrian(JTextArea outputArea) {
        try {
            if (antrianNasabah.isEmpty()) {
                NotificationDialog.showNotification("Antrian Kosong!");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("DAFTAR NASABAH DALAM ANTRIAN\n");
                sb.append("===========================\n");
                for (Nasabah nasabah : antrianNasabah) {
                    sb.append("Nama: ").append(nasabah.getNama())
                            .append("\nNomor Antrian: ").append(nasabah.getNomorAntrian())
                            .append("\nTanggal: ").append(nasabah.getTanggal())
                            .append("\nLayanan: ").append(nasabah.getLayanan())
                            .append("\nNomor Rekening/Kartu Identitas: ").append(nasabah.getNomorRekening())
                            .append("\nJenis Transaksi: ").append(nasabah.getJenisTransaksi())
                            .append("\nKontak Informasi: ").append(nasabah.getKontakInformasi()).append("\n\n");
                }
                outputArea.setText(sb.toString());
            }
        } catch (Exception e) {
            NotificationDialog.showNotification("Error: Terjadi kesalahan saat menampilkan antrian. " + e.getMessage());
        }
    }

    private void simpanDataAntrian(Nasabah nasabah) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("Nama: " + nasabah.getNama() + ", Nomor Antrian: " + nasabah.getNomorAntrian() + ", Tanggal: " + nasabah.getTanggal() + ", Layanan: " + nasabah.getLayanan() + ", Nomor Rekening/Kartu Identitas: " + nasabah.getNomorRekening() + ", Jenis Transaksi: " + nasabah.getJenisTransaksi() + ", Kontak Informasi: " + nasabah.getKontakInformasi());
            writer.newLine();
        } catch (IOException e) {
            NotificationDialog.showNotification("Error: Terjadi kesalahan saat menyimpan data antrian. " + e.getMessage());
        }
    }
}

