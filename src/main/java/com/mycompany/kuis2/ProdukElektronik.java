package com.mycompany.kuis2;

public class ProdukElektronik extends Produk implements DapatGaransi {
    private static final long serialVersionUID = 1L;

    private int masaGaransiBulan;

    public ProdukElektronik(String kode, String nama, int stok, double hargaSatuan, int masaGaransiBulan) {
        super(kode, nama, stok, hargaSatuan);
        this.masaGaransiBulan = masaGaransiBulan;
    }

    @Override
    public String getKategori() {
        return "Elektronik";
    }

    @Override
    public void tampilkanDetail() {
        System.out.println("  -> Kategori      : " + getKategori());
        System.out.println("  -> Garansi Resmi : " + this.masaGaransiBulan + " Bulan");
        System.out.printf("  -> Harga Satuan  : Rp%,.0f%n", getHargaSatuan());
        System.out.println("  -> Sisa Stok     : " + getStok() + " unit");
    }

    @Override
    public double hitungTotalHarga(int qty) {
        return getHargaSatuan() * qty;
    }

    @Override
    public int getMasaGaransiBulan() {
        return this.masaGaransiBulan;
    }

    @Override
    public void klaimGaransi() {
        System.out.println("  [GARANSI AKTIF] Garansi berlaku selama " + this.masaGaransiBulan + " bulan sejak pembelian.");
    }

    public void setMasaGaransiBulan(int masaGaransiBulan) {
        this.masaGaransiBulan = masaGaransiBulan;
    }
}