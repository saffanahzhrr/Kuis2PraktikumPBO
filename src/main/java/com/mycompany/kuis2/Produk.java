package com.mycompany.kuis2;

import java.io.Serializable;

public abstract class Produk implements Serializable {
    private static final long serialVersionUID = 1L;

    private String kode;
    private String nama;
    private int stok;
    private double hargaSatuan;

    public static int totalProdukTerdaftar = 0;


    public Produk(String kode, String nama, int stok, double hargaSatuan) {
        setKode(kode);
        setNama(nama);
        setStok(stok);
        setHargaSatuan(hargaSatuan);

        totalProdukTerdaftar++;
    }

    public abstract void tampilkanDetail();
    public abstract String getKategori();
    public abstract double hitungTotalHarga(int qty);

    public boolean kurangiStok(int jumlah) {
        if (jumlah <= 0) {
            System.out.println("[PERINGATAN] Jumlah pembelian harus lebih besar dari 0!");
            return false;
        }
        if (this.stok >= jumlah) {
            this.stok -= jumlah;
            return true;
        }
        return false;
    }


    public String getKode() {
        return this.kode;
    }

    public void setKode(String kode) {
        if (kode != null && !kode.trim().isEmpty()) {
            this.kode = kode.trim();
        } else {
            System.out.println("[PERINGATAN] Kode produk tidak boleh kosong!");
            this.kode = "UNKNOWN-OBJ";
        }
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        if (nama != null && !nama.trim().isEmpty()) {
            this.nama = nama.trim();
        } else {
            System.out.println("[PERINGATAN] Nama produk tidak boleh kosong!");
            this.nama = "Tanpa Nama";
        }
    }

    public int getStok() {
        return this.stok;
    }

    public void setStok(int stok) {
        if (stok >= 0) {
            this.stok = stok;
        } else {
            System.out.println("[PERINGATAN] Stok tidak boleh negatif! Nilai 0 digunakan.");
            this.stok = 0;
        }
    }

    public double getHargaSatuan() {
        return this.hargaSatuan;
    }

    public void setHargaSatuan(double hargaSatuan) {
        if (hargaSatuan >= 0) {
            this.hargaSatuan = hargaSatuan;
        } else {
            System.out.println("[PERINGATAN] Harga tidak boleh negatif! Nilai 0 digunakan.");
            this.hargaSatuan = 0;
        }
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (Stok: %d, Harga: Rp%,.0f)", this.kode, this.nama, this.stok, this.hargaSatuan);
    }
}