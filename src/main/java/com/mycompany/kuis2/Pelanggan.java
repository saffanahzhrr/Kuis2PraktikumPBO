package com.mycompany.kuis2;

import java.io.Serializable;

public class Pelanggan implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nama;

    public Pelanggan(String id, String nama) {
        setId(id);
        setNama(nama);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        if (id != null && !id.trim().isEmpty()) {
            this.id = id.trim();
        } else {
            System.out.println("[PERINGATAN] ID Pelanggan tidak boleh kosong!");
            this.id = "UNKNOWN-ID";
        }
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        if (nama != null && !nama.trim().isEmpty()) {
            this.nama = nama.trim();
        } else {
            System.out.println("[PERINGATAN] Nama Pelanggan tidak boleh kosong!");
            this.nama = "Anonim";
        }
    }

    public String getIdPelanggan() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Pelanggan [ID=" + this.id + ", Nama=" + this.nama + "]";
    }
}