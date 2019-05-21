package hs.karlsruhe.wgfinder.Entity;

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

    @ColumnInfo(name = "vorname")
    private String vorname;

    @ColumnInfo(name = "nachname")
    private String nachname;

    @ColumnInfo(name = "alter")
    private Integer alter;

    @ColumnInfo(name = "email")
    private String email;



    public Benutzer(){}

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
}
