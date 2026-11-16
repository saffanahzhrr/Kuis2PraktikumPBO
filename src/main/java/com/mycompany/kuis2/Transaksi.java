package com.mycompany.kuis2;

import java.io.Serializable;

public class Transaksi implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int counterTransaksi = 1;

    private String idTransaksi;
    private Pelanggan pelanggan;
    private Produk produk;
    private int jumlah;
    private double totalBiaya;

    public Transaksi(Pelanggan pelanggan, Produk produk, int jumlah) {
        this.idTransaksi = String.format("TRX-%03d", counterTransaksi++);
        this.pelanggan = pelanggan;
        this.produk = produk;
        this.jumlah = jumlah;
        this.totalBiaya = produk.hitungTotalHarga(jumlah);
    }

    public void cetakStruk() {
        System.out.println("==================================================");
        System.out.println("               STRUK TRANSAKSI TOKO               ");
        System.out.println("==================================================");
        System.out.println("No. Transaksi : " + this.idTransaksi);
        System.out.println("Pelanggan     : " + this.pelanggan.getNama() + " (" + this.pelanggan.getId() + ")");
        System.out.println("Produk Dibeli : " + this.produk.getNama() + " [" + this.produk.getKode() + "]");
        System.out.println("Kategori      : " + this.produk.getKategori());
        System.out.println("Jumlah Beli   : " + this.jumlah + " unit");
        
        if (this.produk instanceof DapatGaransi) {
            ((DapatGaransi) this.produk).klaimGaransi();
        }

        System.out.printf("Total Bayar   : Rp%,.0f%n", this.totalBiaya);
        System.out.println("Status Stok   : Sisa stok saat ini = " + this.produk.getStok() + " unit");
        System.out.println("==================================================\n");
    }

    public String getIdTransaksi() {
        return this.idTransaksi;
    }

    public Pelanggan getPelanggan() {
        return this.pelanggan;
    }

    public Produk getProduk() {
        return this.produk;
    }

    public int getJumlah() {
        return this.jumlah;
    }

    public double getTotalBiaya() {
        return this.totalBiaya;
    }

    @Override
    public String toString() {
        return String.format("[%s] Pelanggan: %s | Produk: %s | Qty: %d | Total: Rp%,.0f",
                this.idTransaksi, this.pelanggan.getNama(), this.produk.getNama(), this.jumlah, this.totalBiaya);
    }
}