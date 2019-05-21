package hs.karlsruhe.wgfinder.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import javax.annotation.Nonnull;

@Entity(tableName = "login", indices = {@Index(value = {"email"},
        unique = true)})
public class Login {
    @PrimaryKey(autoGenerate = true)
    @Nonnull
    @ColumnInfo(name = "loginId")
    private int id;

    private String email;

    private String passwort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
}
