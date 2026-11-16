package com.mycompany.kuis2;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SistemToko implements Serializable {
    private static final long serialVersionUID = 1L;

    private String namaToko;
    private HashMap<String, Pelanggan> dataPelanggan;
    private HashMap<String, Produk> dataProduk;
    private ArrayList<Transaksi> riwayatTransaksi;

    public SistemToko(String namaToko) {
        this.namaToko = namaToko;
        this.dataPelanggan = new HashMap<>();
        this.dataProduk = new HashMap<>();
        this.riwayatTransaksi = new ArrayList<>();
        System.out.println(">>> Inisialisasi " + this.namaToko + " Berhasil Dijalankan! <<<");
    }

    public void tambahPelanggan(String id, String nama) {
        Pelanggan p = new Pelanggan(id, nama);
        this.dataPelanggan.put(id, p);
        System.out.println("[INFO MASTER] Pelanggan Berhasil Didaftarkan -> ID: " + id + ", Nama: " + nama);
    }

    public void tambahProduk(String kode, String nama, int stok) {
        Produk p;

        if (nama.toLowerCase().contains("laptop") || kode.equalsIgnoreCase("OBJ-A")) {
            p = new ProdukElektronik(kode, nama, stok, 12500000.0, 24);
        } else if (nama.toLowerCase().contains("kemeja") || kode.equalsIgnoreCase("OBJ-B")) {
            p = new ProdukPakaian(kode, nama, stok, 175000.0, "L");
        } else if (nama.toLowerCase().contains("snack") || kode.equalsIgnoreCase("OBJ-C")) {
            p = new ProdukMakanan(kode, nama, stok, 15000.0, "12 Bulan");
        } else {
            p = new ProdukElektronik(kode, nama, stok, 100000.0, 12);
        }

        this.dataProduk.put(kode, p);
        System.out.println("[INFO MASTER] Produk Berhasil Ditambahkan   -> Kode: " + kode + " | " 
                + nama + " | Stok: " + stok);
    }

    public void jalankanTransaksiDariFile(String namaFile) throws Exception {
        File fileInput = new File(namaFile);

        if (!fileInput.exists()) {
            fileInput = new File("src/main/resources/" + namaFile);
            if (!fileInput.exists()) {
                throw new FileNotFoundException("File '" + namaFile + "' tidak ditemukan di direktori kerja!");
            }
        }

        System.out.println("[FILE I/O] Membuka file transaksi: " + fileInput.getAbsolutePath());
        System.out.println("--------------------------------------------------");

        try (Scanner scanner = new Scanner(fileInput)) {
            int nomorBaris = 0;

            while (scanner.hasNextLine()) {
                nomorBaris++;
                String baris = scanner.nextLine().trim();

                if (baris.isEmpty()) {
                    continue;
                }

                System.out.println(">>> Memproses Baris " + nomorBaris + ": " + baris);

                try {
                    String[] tokens = baris.split("/");
                    String idPelanggan = null;
                    String kodeProduk = null;
                    int jumlahBeli = 0;

                    for (String token : tokens) {
                        token = token.trim();
                        if (token.startsWith("ID-")) {
                            idPelanggan = token.substring(3).trim();
                        } else if (token.startsWith("KD-")) {
                            kodeProduk = token.substring(3).trim();
                        } else if (token.startsWith("JML-")) {
                            jumlahBeli = Integer.parseInt(token.substring(4).trim());
                        }
                    }

                    if (idPelanggan == null || kodeProduk == null || jumlahBeli <= 0) {
                        throw new IllegalArgumentException("Format token tidak lengkap atau jumlah beli tidak valid pada baris: " + baris);
                    }

                    Pelanggan pelanggan = this.dataPelanggan.get(idPelanggan);
                    if (pelanggan == null) {
                        throw new PelangganTidakDitemukanException(
                            "Pelanggan dengan ID '" + idPelanggan + "' TIDAK TERDAFTAR di dalam sistem!"
                        );
                    }

                    Produk produk = this.dataProduk.get(kodeProduk);
                    if (produk == null) {
                        throw new ProdukTidakDitemukanException(
                            "Produk dengan Kode '" + kodeProduk + "' TIDAK DITEMUKAN di katalog toko!"
                        );
                    }

                    if (produk.getStok() < jumlahBeli) {
                        throw new StokTidakCukupException(
                            "Stok '" + produk.getNama() + "' (" + kodeProduk + ") TIDAK MENCUKUPI! Diminta: " 
                            + jumlahBeli + ", Tersedia: " + produk.getStok()
                        );
                    }

                    produk.kurangiStok(jumlahBeli);
                    Transaksi transaksiBaru = new Transaksi(pelanggan, produk, jumlahBeli);
                    this.riwayatTransaksi.add(transaksiBaru);

                    transaksiBaru.cetakStruk();

                } catch (PelangganTidakDitemukanException e) {
                    System.out.println("  [ERROR CUSTOM EXCEPTION] " + e.getMessage());
                    System.out.println("  -> Transaksi baris ini DIBATALKAN, melanjutkan ke baris berikutnya.\n");
                } catch (StokTidakCukupException e) {
                    System.out.println("  [ERROR CUSTOM EXCEPTION] " + e.getMessage());
                    System.out.println("  -> Transaksi baris ini DIBATALKAN, melanjutkan ke baris berikutnya.\n");
                } catch (ProdukTidakDitemukanException e) {
                    System.out.println("  [ERROR CUSTOM EXCEPTION] " + e.getMessage());
                    System.out.println("  -> Transaksi baris ini DIBATALKAN, melanjutkan ke baris berikutnya.\n");
                } catch (NumberFormatException e) {
                    System.out.println("  [ERROR PARSING ANGKA] Kuantitas JML harus berupa angka bulat! " + e.getMessage());
                    System.out.println("  -> Transaksi baris ini DIBATALKAN, melanjutkan ke baris berikutnya.\n");
                } catch (Exception e) {
                    System.out.println("  [ERROR TAK TERDUGA] Gagal memproses baris transaksi: " + e.getMessage());
                    System.out.println("  -> Melanjutkan ke baris berikutnya.\n");
                }
            }

            System.out.println("--- SELESAI MEMPROSES SELURUH DATA TRANSAKSI DARI FILE ---");
            System.out.println("Total Transaksi Berhasil Dicatat: " + this.riwayatTransaksi.size() + " transaksi.\n");
        }
    }

    public void simpanLaporanAkhir() {
        System.out.println("==================================================");
        System.out.println("       MENJALANKAN SIMPAN LAPORAN AKHIR           ");
        System.out.println("==================================================");

        String namaFileBiner = "sistem_state.ser";
        try (FileOutputStream fos = new FileOutputStream(namaFileBiner);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(this);

            System.out.println("-> [SERIALIZATION SUKSES] Status memori sistem berhasil dibekukan ke: " + namaFileBiner);
        } catch (IOException e) {
            System.out.println("-> [SERIALIZATION GAGAL] Terjadi kesalahan saat menyimpan file biner: " + e.getMessage());
        }

        String namaFileLaporan = "laporan.txt";
        try (FileWriter fw = new FileWriter(namaFileLaporan);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println("==================================================");
            pw.println("           LAPORAN DAFTAR AKTOR AKTIF             ");
            pw.println("           TOKO DIGITAL: " + this.namaToko.toUpperCase());
            pw.println("==================================================");
            pw.println("Waktu Cetak Laporan : " + new java.util.Date());
            pw.println("Total Aktor Aktif   : " + this.dataPelanggan.size() + " Pelanggan");
            pw.println("--------------------------------------------------");
            pw.println("DAFTAR PELANGGAN TERDAFTAR (ITERASI HASHMAP .values()):");
            pw.println("--------------------------------------------------");

            int no = 1;
            for (Pelanggan p : this.dataPelanggan.values()) {
                pw.printf("%d. ID Pelanggan : %-10s | Nama Pelanggan: %s%n", no++, p.getId(), p.getNama());
            }

            pw.println("--------------------------------------------------");
            pw.println("CATATAN TAMBAHAN: STATUS STOK TERAKHIR SETELAH TRANSAKSI");
            pw.println("--------------------------------------------------");
            for (Produk pr : this.dataProduk.values()) {
                pw.printf("- [%s] %-20s -> Sisa Stok: %d unit%n", pr.getKode(), pr.getNama(), pr.getStok());
            }

            pw.println("==================================================");
            pw.println("               AKHIR DARI LAPORAN                 ");
            pw.println("==================================================");

            System.out.println("-> [FILE I/O TEKS SUKSES] Laporan aktor aktif berhasil ditulis ke: " + namaFileLaporan);
        } catch (IOException e) {
            System.out.println("-> [FILE I/O GAGAL] Terjadi kesalahan saat menulis laporan teks: " + e.getMessage());
        }

        System.out.println("==================================================\n");
    }


    public String getNamaToko() {
        return this.namaToko;
    }

    public HashMap<String, Pelanggan> getDataPelanggan() {
        return this.dataPelanggan;
    }

    public HashMap<String, Produk> getDataProduk() {
        return this.dataProduk;
    }

    public ArrayList<Transaksi> getRiwayatTransaksi() {
        return this.riwayatTransaksi;
    }
}