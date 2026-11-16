package com.mycompany.kuis2;

public class ProdukPakaian extends Produk implements DapatDiskon {
    private static final long serialVersionUID = 1L;

    private String ukuran;

    public ProdukPakaian(String kode, String nama, int stok, double hargaSatuan, String ukuran) {
        super(kode, nama, stok, hargaSatuan);
        this.ukuran = ukuran;
    }

    @Override
    public String getKategori() {
        return "Pakaian / Fashion";
    }

    @Override
    public void tampilkanDetail() {
        System.out.println("  -> Kategori     : " + getKategori());
        System.out.println("  -> Ukuran (Size): " + this.ukuran);
        System.out.printf("  -> Harga Satuan : Rp%,.0f%n", getHargaSatuan());
        System.out.println("  -> Sisa Stok    : " + getStok() + " pcs");
    }

    @Override
    public double hitungTotalHarga(int qty) {
        double total = getHargaSatuan() * qty;
        if (qty >= 3) {
            total = hitungHargaSetelahDiskon(5.0) * qty;
            System.out.println("  [PROMO FASHION] Diskon 5% berhasil diterapkan untuk pembelian >= 3 pcs!");
        }
        return total;
    }

    @Override
    public double hitungHargaSetelahDiskon(double persenDiskon) {
        return getHargaSatuan() * (1.0 - (persenDiskon / 100.0));
    }

    public String getUkuran() {
        return this.ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }
}