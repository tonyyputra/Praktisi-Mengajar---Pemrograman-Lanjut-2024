import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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

public class AntrianRumahSakitGUI {
    private Queue<Pasien> antrianPasien;
    private int nomorAntrian;
    private String fileName;
    
    private JFrame frame;
    private JTextField namaField;
    private JTextField usiaField;
    private JTextArea outputArea;

    public AntrianRumahSakitGUI(String fileName) {
        antrianPasien = new LinkedList<>();
        nomorAntrian = 1;
        this.fileName = fileName;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Sistem Antrian Rumah Sakit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel namaLabel = new JLabel("Nama Pasien:");
        namaField = new JTextField();
        JLabel usiaLabel = new JLabel("Usia Pasien:");
        usiaField = new JTextField();
        JButton tambahButton = new JButton("Tambah Pasien");
        JButton panggilButton = new JButton("Panggil Pasien");
        JButton tampilkanButton = new JButton("Tampilkan Antrian");
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        tambahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahAntrian();
            }
        });

        panggilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panggilAntrian();
            }
        });

        tampilkanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tampilkanAntrian();
            }
        });

        panel.add(namaLabel);
        panel.add(namaField);
        panel.add(usiaLabel);
        panel.add(usiaField);
        panel.add(tambahButton);
        panel.add(panggilButton);
        panel.add(tampilkanButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void tambahAntrian() {
        try {
            String nama = namaField.getText();
            int usia = Integer.parseInt(usiaField.getText());
            if (usia <= 0) {
                outputArea.setText("<Error: Usia harus berupa angka positif.>");
                return;
            }
            LocalDate tanggal = LocalDate.now();
            Pasien pasien = new Pasien(nama, usia, nomorAntrian, tanggal);
            antrianPasien.offer(pasien);
            nomorAntrian++;
            simpanDataAntrian(pasien);
            outputArea.setText("<Pasien atas nama " + pasien.getNama() + " telah ditambahkan ke dalam antrian>");
            namaField.setText("");
            usiaField.setText("");
        } catch (NumberFormatException e) {
            outputArea.setText("<Error: Usia harus berupa angka.>");
        } catch (Exception e) {
            outputArea.setText("<Error: Terjadi kesalahan saat menambahkan pasien> " + e.getMessage());
        }
    }

    private void panggilAntrian() {
        try {
            Pasien pasien = antrianPasien.poll();
            if (pasien != null) {
                outputArea.setText("<Pasien atas nama " + pasien.getNama() + " silahkan menuju Apotek untuk mengambil obat!>");
            } else {
                outputArea.setText("<Antrian kosong, tidak ada pasien yang dapat dipanggil>");
            }
        } catch (Exception e) {
            outputArea.setText("<Error: Terjadi kesalahan saat memanggil pasien> " + e.getMessage());
        }
    }

    private void tampilkanAntrian() {
        try {
            if (antrianPasien.isEmpty()) {
                outputArea.setText("<Antrian Kosong>");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("DAFTAR PASIEN DALAM ANTRIAN\n");
                sb.append("===========================\n");
                for (Pasien pasien : antrianPasien) {
                    sb.append("Nama: ").append(pasien.getNama()).append("\nUsia: ").append(pasien.getUsia())
                            .append("\nNomor Antrian: ").append(pasien.getNomorAntrian())
                            .append("\nTanggal: ").append(pasien.getTanggal()).append("\n\n");
                }
                outputArea.setText(sb.toString());
            }
        } catch (Exception e) {
            outputArea.setText("<Error: Terjadi kesalahan saat menampilkan antrian> " + e.getMessage());
        }
    }

    private void simpanDataAntrian(Pasien pasien) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("Nama: " + pasien.getNama() + ", Usia: " + pasien.getUsia() +
                         ", Nomor Antrian: " + pasien.getNomorAntrian() + ", Tanggal: " + pasien.getTanggal());
            writer.newLine();
        } catch (IOException e) {
            outputArea.setText("<Error: Terjadi kesalahan saat menyimpan data antrian> " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new AntrianRumahSakitGUI("dataAntrian.txt");
    }
}
