# LAPORAN & PANDUAN LENGKAP KUIS 2 PBO
## SISTEM E-COMMERCE (TOKO DIGITAL)
### KOMBINASI MATRIKS: 1 5 7

---

## 1. DESKRIPSI KOMBINASI TUGAS
Sesuai aturan Kuis 2 Praktikum Pemrograman Berorientasi Objek (PBO), kombinasi 3 digit angka yang dipilih adalah **1 5 7**:

| Digit | Elemen | Penjelasan Aturan | Implementasi pada Program |
| :---: | :--- | :--- | :--- |
| **1** | **Tema Domain & Main Class** | **Sistem E-Commerce (Toko Digital)**<br>Praktikan dilarang keras mengubah kode `MainToko`. Membangun arsitektur OOP (Class, Encapsulation, Inheritance, Polymorphism, Association) dan Custom Exception. | Dibuat class `SistemToko`, `Pelanggan`, `Produk`, `ProdukElektronik`, `ProdukPakaian`, `ProdukMakanan`, `Transaksi`, serta custom exception pendukung. |
| **5** | **Format File Input .txt** | **Varian 5 (Strip & Baris Baru)**<br>File masal `input_transaksi.txt` menggunakan pembatas garis miring `/` dan awalan tanda strip `-`. | Parsing string `ID-ACT-xx/KD-OBJ-xx/JML-x/` per baris menggunakan blok `try-catch` terisolasi agar baris gagal tidak memutus transaksi lainnya. |
| **7** | **Serialization & Output Teks** | **File Serialization**: `sistem_state.ser`<br>**File Laporan Teks**: `laporan.txt`<br>**Tantangan Iterasi**: Looping `.values()` pada `HashMap` Aktor Pelanggan untuk mencetak ID dan nama. | `simpanLaporanAkhir()` membekukan memori ke `sistem_state.ser` (`ObjectOutputStream`) dan menulis `laporan.txt` menggunakan `.values()`. |

---

## 2. STRUKTUR PROYEK
Proyek dibangun dengan arsitektur standar Maven (kompatibel langsung dengan Apache NetBeans IDE):

```
D:\NetBeansProjects\Kuis2_PBO_157
│   pom.xml
│   input_transaksi.txt
│   laporan.txt                  (Dihasilkan otomatis saat program selesai)
│   sistem_state.ser             (Dihasilkan otomatis saat program selesai)
│   .gitignore
│   README.md
│
└───src
    ├───main
    │   ├───java
    │   │   └───com
    │   │       └───mycompany
    │   │           └───kuis2
    │   │                   MainToko.java                        [Class Utama - Tidak Diubah]
    │   │                   SistemToko.java                      [Sistem Controller / Facade]
    │   │                   Pelanggan.java                       [Entitas Aktor - Encapsulation]
    │   │                   Produk.java                          [Abstract Superclass]
    │   │                   ProdukElektronik.java                [Subclass Inheritance & Garansi]
    │   │                   ProdukPakaian.java                   [Subclass Inheritance & Diskon]
    │   │                   ProdukMakanan.java                   [Subclass Inheritance]
    │   │                   DapatGaransi.java                    [Interface Abstraction]
    │   │                   DapatDiskon.java                     [Interface Abstraction]
    │   │                   Transaksi.java                       [Class Asosiasi Pelanggan - Produk]
    │   │                   PelangganTidakDitemukanException.java [Custom Exception 1]
    │   │                   StokTidakCukupException.java          [Custom Exception 2]
    │   │                   ProdukTidakDitemukanException.java    [Custom Exception 3]
    │   │
    │   └───resources
    │           input_transaksi.txt                              (Cadangan path resources)
    │
    └───test
```

---

## 3. PENJELASAN KONSEP DASAR OOP & IMPLEMENTASINYA

### A. Class dan Object
- **Class** adalah cetak biru (blueprint) atau template yang mendefinisikan karakteristik (atribut/field) dan perilaku (method) dari suatu entitas.
- **Object** adalah instansiasi konkret dari suatu class yang menempati memori saat program berjalan.
- **Implementasi**:
  - `Pelanggan` merepresentasikan aktor pembeli (`new Pelanggan("ACT-01", "Andi")`).
  - `Produk` merepresentasikan barang jualan.
  - `SistemToko` mengelola seluruh alur sistem.

### B. Encapsulation (Enkapsulasi & Data Hiding)
- Enkapsulasi membungkus data agar terlindung dari akses atau modifikasi ilegal dari luar class.
- Seluruh atribut (`id`, `nama`, `stok`, `hargaSatuan`) dibuat bertipe **`private`**.
- Akses dan perubahan nilai hanya boleh melalui method **Getter** dan **Setter** yang dilengkapi dengan **validasi logika bisnis**:
  - Validasi bahwa ID dan Nama tidak boleh kosong/null.
  - Validasi bahwa stok dan harga tidak boleh bernilai negatif.
  - Method `kurangiStok(int jumlah)` menjamin stok hanya berkurang jika kuantitas mencukupi.

### C. Inheritance (Pewarisan Sifat)
- Konsep di mana subclass dapat mewarisi seluruh atribut dan method dari superclass, sehingga mencegah redundansi kode (DRY - *Don't Repeat Yourself*).
- **Implementasi**:
  - Superclass abstrak: `Produk`
  - Subclass konkret:
    1. `ProdukElektronik extends Produk` (menambahkan atribut `masaGaransiBulan`)
    2. `ProdukPakaian extends Produk` (menambahkan atribut `ukuran`)
    3. `ProdukMakanan extends Produk` (menambahkan atribut `masaKadaluarsa`)

### D. Polymorphism (Polimorfisme & Abstraction)
- Kemampuan objek untuk memiliki banyak bentuk perilaku yang berbeda melalui satu antarmuka umum (Dynamic Method Dispatch / Late Binding).
- **Implementasi**:
  1. **Abstract Method**: Method `tampilkanDetail()`, `getKategori()`, dan `hitungTotalHarga(int qty)` pada class `Produk` dioverride secara spesifik oleh masing-masing subclass.
  2. **Interface**:
     - `DapatGaransi`: diimplementasikan oleh `ProdukElektronik` untuk fitur klaim garansi.
     - `DapatDiskon`: diimplementasikan oleh `ProdukPakaian` untuk perhitungan potongan harga.
  3. **Penggunaan `instanceof`**: Pada method cetak struk transaksi, sistem mendeteksi secara dinamis apakah produk merupakan barang yang memiliki garansi.

### E. Association (Asosiasi)
- Hubungan struktural antara dua class yang saling berinteraksi tanpa saling memiliki kepemilikan mutlak (relasi *use-a* / *has-a*).
- **Implementasi**:
  - Class `Transaksi` mengasosiasikan 1 objek `Pelanggan` dan 1 objek `Produk` dengan kuantitas tertentu (`Pelanggan pelanggan; Produk produk;`).

### F. Custom Exception (Exception Handling)
- Menangani kondisi kesalahan yang spesifik untuk domain bisnis aplikasi e-commerce:
  1. **`PelangganTidakDitemukanException`**: Terpicu saat baris input memuat `ID-ACT-99` yang tidak ada dalam database toko.
  2. **`StokTidakCukupException`**: Terpicu saat baris input meminta 5 unit kemeja (`OBJ-B`), padahal stok hanya tersedia 2 unit.
  3. **`ProdukTidakDitemukanException`**: Menangani kode barang yang tidak dikenal.
- Seluruh exception ditangkap di dalam loop pembacaan baris file, sehingga transaksi yang gagal dibatalkan dengan pesan yang jelas tanpa menghentikan pemrosesan baris-baris transaksi valid berikutnya.

### G. Collection Framework
- **`HashMap<String, Pelanggan> dataPelanggan`**: Menyimpan data pelanggan dengan kunci ID (misal: "ACT-01"). Memberikan kecepatan akses $O(1)$ saat validasi transaksi.
- **`HashMap<String, Produk> dataProduk`**: Menyimpan katalog produk berdasarkan kode produk (misal: "OBJ-A").
- **`ArrayList<Transaksi> riwayatTransaksi`**: Menyimpan log urutan transaksi yang sukses dieksekusi secara terstruktur dan dinamis.

### H. File I/O Input (Varian 5: Strip & Baris Baru)
- Format masal pada `input_transaksi.txt`:
  ```
  ID-ACT-01/KD-OBJ-A/JML-3/
  ID-ACT-02/KD-OBJ-C/JML-2/
  ID-ACT-01/KD-OBJ-B/JML-5/
  ID-ACT-99/KD-OBJ-A/JML-1/
  ID-ACT-02/KD-OBJ-A/JML-1/
  ```
- Algoritma pemecahan (parsing):
  Setiap baris di-*split* dengan delimiter `/`, kemudian tiap token diperiksa awalan `ID-`, `KD-`, dan `JML-` untuk diekstraksi nilainya secara presisi.

### I. Object Serialization (Digit 3: sistem_state.ser)
- Seluruh class entitas mengimplementasikan `java.io.Serializable` dengan `serialVersionUID`.
- Menggunakan `ObjectOutputStream` untuk membekukan objek `SistemToko` (termasuk seluruh isi HashMap dan ArrayList di dalamnya) ke file biner `sistem_state.ser`.

### J. File I/O Output Teks & Tantangan Iterasi .values() (Digit 3: laporan.txt)
- Menggunakan `FileWriter` dan `PrintWriter` untuk menulis laporan ke file teks `laporan.txt`.
- Memenuhi tantangan digit 7:
  ```java
  for (Pelanggan p : this.dataPelanggan.values()) {
      pw.printf("%d. ID Pelanggan : %-10s | Nama Pelanggan: %s%n", no++, p.getId(), p.getNama());
  }
  ```

---

## 4. HASIL OUTPUT EKSEKUSI PROGRAM

### Output di Layar Konsol:
```text
>>> Inisialisasi Toko Sukses Makmur Berhasil Dijalankan! <<<
[INFO MASTER] Pelanggan Berhasil Didaftarkan -> ID: ACT-01, Nama: Andi
[INFO MASTER] Pelanggan Berhasil Didaftarkan -> ID: ACT-02, Nama: Rina
[INFO MASTER] Produk Berhasil Ditambahkan   -> Kode: OBJ-A | Laptop Premium | Stok: 10
[INFO MASTER] Produk Berhasil Ditambahkan   -> Kode: OBJ-B | Kemeja Reguler | Stok: 2
[INFO MASTER] Produk Berhasil Ditambahkan   -> Kode: OBJ-C | Snack Ekonomis | Stok: 50
--- MEMULAI PEMBACAAN FILE TRANSAKSI ---
[FILE I/O] Membuka file transaksi: D:\NetBeansProjects\Kuis2_PBO_157\input_transaksi.txt
--------------------------------------------------
>>> Memproses Baris 1: ID-ACT-01/KD-OBJ-A/JML-3/
==================================================
               STRUK TRANSAKSI TOKO               
==================================================
No. Transaksi : TRX-001
Pelanggan     : Andi (ACT-01)
Produk Dibeli : Laptop Premium [OBJ-A]
Kategori      : Elektronik
Jumlah Beli   : 3 unit
  [GARANSI AKTIF] Garansi berlaku selama 24 bulan sejak pembelian.
Total Bayar   : Rp37,500,000
Status Stok   : Sisa stok saat ini = 7 unit
==================================================

>>> Memproses Baris 2: ID-ACT-02/KD-OBJ-C/JML-2/
==================================================
               STRUK TRANSAKSI TOKO               
==================================================
No. Transaksi : TRX-002
Pelanggan     : Rina (ACT-02)
Produk Dibeli : Snack Ekonomis [OBJ-C]
Kategori      : Makanan / Camilan
Jumlah Beli   : 2 unit
Total Bayar   : Rp30,000
Status Stok   : Sisa stok saat ini = 48 unit
==================================================

>>> Memproses Baris 3: ID-ACT-01/KD-OBJ-B/JML-5/
  [ERROR CUSTOM EXCEPTION] Stok 'Kemeja Reguler' (OBJ-B) TIDAK MENCUKUPI! Diminta: 5, Tersedia: 2
  -> Transaksi baris ini DIBATALKAN, melanjutkan ke baris berikutnya.

>>> Memproses Baris 4: ID-ACT-99/KD-OBJ-A/JML-1/
  [ERROR CUSTOM EXCEPTION] Pelanggan dengan ID 'ACT-99' TIDAK TERDAFTAR di dalam sistem!
  -> Transaksi baris ini DIBATALKAN, melanjutkan ke baris berikutnya.

>>> Memproses Baris 5: ID-ACT-02/KD-OBJ-A/JML-1/
==================================================
               STRUK TRANSAKSI TOKO               
==================================================
No. Transaksi : TRX-003
Pelanggan     : Rina (ACT-02)
Produk Dibeli : Laptop Premium [OBJ-A]
Kategori      : Elektronik
Jumlah Beli   : 1 unit
  [GARANSI AKTIF] Garansi berlaku selama 24 bulan sejak pembelian.
Total Bayar   : Rp12,500,000
Status Stok   : Sisa stok saat ini = 6 unit
==================================================

--- SELESAI MEMPROSES SELURUH DATA TRANSAKSI DARI FILE ---
Total Transaksi Berhasil Dicatat: 3 transaksi.

==================================================
       MENJALANKAN SIMPAN LAPORAN AKHIR           
==================================================
-> [SERIALIZATION SUKSES] Status memori sistem berhasil dibekukan ke: sistem_state.ser
-> [FILE I/O TEKS SUKSES] Laporan aktor aktif berhasil ditulis ke: laporan.txt
==================================================
```

### Isi File `laporan.txt`:
```text
==================================================
           LAPORAN DAFTAR AKTOR AKTIF             
           TOKO DIGITAL: TOKO SUKSES MAKMUR
==================================================
Waktu Cetak Laporan : Sun Nov 15 15:26:51 WIB 2026
Total Aktor Aktif   : 2 Pelanggan
--------------------------------------------------
DAFTAR PELANGGAN TERDAFTAR (ITERASI HASHMAP .values()):
--------------------------------------------------
1. ID Pelanggan : ACT-01     | Nama Pelanggan: Andi
2. ID Pelanggan : ACT-02     | Nama Pelanggan: Rina
--------------------------------------------------
CATATAN TAMBAHAN: STATUS STOK TERAKHIR SETELAH TRANSAKSI
--------------------------------------------------
- [OBJ-A] Laptop Premium       -> Sisa Stok: 6 unit
- [OBJ-B] Kemeja Reguler       -> Sisa Stok: 2 unit
- [OBJ-C] Snack Ekonomis       -> Sisa Stok: 48 unit
==================================================
               AKHIR DARI LAPORAN                 
==================================================
```

---

## 5. CARA MENJALANKAN DI APACHE NETBEANS
1. Buka aplikasi **Apache NetBeans**.
2. Klik menu **File** -> **Open Project...**.
3. Navigasikan ke folder `D:\NetBeansProjects\Kuis2_PBO_157` (ikon cangkir kopi/folder proyek Maven akan otomatis terdeteksi).
4. Klik **Open Project**.
5. Di panel Projects sebelah kiri, klik kanan proyek `Kuis2_PBO_157` -> **Clean and Build**.
6. Klik kanan file `MainToko.java` -> pilih **Run File** (atau tekan `Shift + F6`), atau tekan tombol **Run Project (F6)**.
7. Amati output di jendela Output NetBeans: seluruh transaksi akan tereksekusi, file `sistem_state.ser` dan `laporan.txt` akan langsung tercipta di direktori proyek.
