package hs.karlsruhe.wgfinder.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.annotation.Nonnull;

@Entity(tableName = "wohnung")
public class Wohnungen {
    @PrimaryKey
    @Nonnull
    @ColumnInfo(name = "WohnungsId")
    private int id;

    public Wohnungen() {
    }

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    private Double preis;

    private Double wohnflaeche;

    private Integer mitbewohnerAnzahl;

    private String ort;

    private Boolean raucher;

    private Boolean tiere;

    private String hobbies;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Double getPreis() {
        return preis;
    }

    public void setPreis(Double preis) {
        this.preis = preis;
    }

    public Double getWohnflaeche() {
        return wohnflaeche;
    }

    public void setWohnflaeche(Double wohnflaeche) {
        this.wohnflaeche = wohnflaeche;
    }

    public Integer getMitbewohnerAnzahl() {
        return mitbewohnerAnzahl;
    }

    public void setMitbewohnerAnzahl(Integer mitbewohnerAnzahl) {
        this.mitbewohnerAnzahl = mitbewohnerAnzahl;
    }

    public Boolean getRaucher() {
        return raucher;
    }

    public void setRaucher(Boolean raucher) {
        this.raucher = raucher;
    }

    public Boolean getTiere() {
        return tiere;
    }

    public void setTiere(Boolean tiere) {
        this.tiere = tiere;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }
}
