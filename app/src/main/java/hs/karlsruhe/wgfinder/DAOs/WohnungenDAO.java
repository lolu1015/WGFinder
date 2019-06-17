package hs.karlsruhe.wgfinder.DAOs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import hs.karlsruhe.wgfinder.Entity.Login;
import hs.karlsruhe.wgfinder.Entity.Wohnungen;

import java.util.List;

@Dao
public interface WohnungenDAO {

    @Insert
    void insertWohnung(Wohnungen wohnungen);

    @Query("SELECT * FROM wohnung")
    List<Wohnungen> findWohnung();

    @Query("SELECT * FROM wohnung where WohnungsId = 1")
    Wohnungen findWohnungtest();

    @Query("DELETE FROM wohnung")
    void deleteWohnungen();
}
