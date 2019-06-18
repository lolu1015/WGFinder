package hs.karlsruhe.wgfinder.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.annotation.Nonnull;

@Entity(tableName = "temps")
public class Temp {
    @PrimaryKey
    @Nonnull
    @ColumnInfo(name = "TempId")
    private int id;

    public Temp() {}

    private String email;

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
}
