package hs.karlsruhe.wgfinder.Entity;

import android.text.Editable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.annotation.Nonnull;

@Entity(tableName = "benutzer")
public class Benutzer {

    @PrimaryKey(autoGenerate = true)
    @Nonnull
    @ColumnInfo(name = "nameId")
    private int id;

    public Benutzer() {}

    private String vorname;

    private String nachname;

    private Integer alter;

    private String email;

    private Integer rolle;

    //Ab hier Entities für die Profilverwaltung

    private Integer preis;

    private Integer wohnflaeche;

    private Integer mitbewohner;

    private String hobbys;

    public Integer getPreis() {
        return preis;
    }

    public Integer getWohnflaeche() {
        return wohnflaeche;
    }

    public Integer getMitbewohner() {
        return mitbewohner;
    }

    public String getHobbys() {
        return hobbys;
    }

    public void setPreis(Integer preis) {
        this.preis = preis;
    }

    public void setWohnflaeche(Integer wohnflaeche) {
        this.wohnflaeche = wohnflaeche;
    }

    public void setMitbewohner(Integer mitbewohner) {
        this.mitbewohner = mitbewohner;
    }

    public void setHobbys(String hobbys) {
        this.hobbys = hobbys;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }


    public Integer getAlter() {
        return alter;
    }

    public void setAlter(Integer alter) {
        this.alter = alter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRolle() {
        return rolle;
    }

    public void setRolle(Integer rolle) {
        this.rolle = rolle;
    }


}
