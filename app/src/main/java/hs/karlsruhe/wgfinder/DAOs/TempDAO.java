package hs.karlsruhe.wgfinder.DAOs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import hs.karlsruhe.wgfinder.Entity.Temp;

import java.util.List;

@Dao
public interface TempDAO {
    @Insert
    void insertTemp(Temp temp);

    @Query("UPDATE temps SET email = :email WHERE TempId = 1")
    void updateTemp(String email);

    @Query("SELECT email FROM temps WHERE TempId = 1")
    String findTemp();

    @Query("DELETE FROM temps")
    void deleteTemps();

    @Query("SELECT count(*) FROM temps")
    Integer anzahlSpalten();
}
