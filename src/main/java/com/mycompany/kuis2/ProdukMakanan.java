package com.mycompany.kuis2;

public class ProdukMakanan extends Produk {
    private static final long serialVersionUID = 1L;

    private String masaKadaluarsa;

    public ProdukMakanan(String kode, String nama, int stok, double hargaSatuan, String masaKadaluarsa) {
        super(kode, nama, stok, hargaSatuan);
        this.masaKadaluarsa = masaKadaluarsa;
    }

    @Override
    public String getKategori() {
        return "Makanan / Camilan";
    }

    @Override
    public void tampilkanDetail() {
        System.out.println("  -> Kategori        : " + getKategori());
        System.out.println("  -> Masa Kadaluarsa : " + this.masaKadaluarsa);
        System.out.printf("  -> Harga Satuan    : Rp%,.0f%n", getHargaSatuan());
        System.out.println("  -> Sisa Stok       : " + getStok() + " bks");
    }

    @Override
    public double hitungTotalHarga(int qty) {
        double hargaPerUnit = getHargaSatuan();
        if (qty >= 20 && hargaPerUnit > 1000) {
            hargaPerUnit -= 1000;
            System.out.println("  [PROMO GROSIR] Potongan harga Rp1.000/bks untuk pembelian grosir snack!");
        }
        return hargaPerUnit * qty;
    }

    public String getMasaKadaluarsa() {
        return this.masaKadaluarsa;
    }

    public void setMasaKadaluarsa(String masaKadaluarsa) {
        this.masaKadaluarsa = masaKadaluarsa;
    }
}