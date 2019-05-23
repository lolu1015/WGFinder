package hs.karlsruhe.wgfinder.DAOs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import hs.karlsruhe.wgfinder.Entity.Benutzer;

import java.util.List;


@Dao
public interface BenutzerDAO {



    @Insert
    void insertBenutzer(Benutzer benutzer);

    @Query("SELECT * FROM benutzer WHERE email = :email")
    List<Benutzer> findProduct(String email);

    @Query("SELECT * FROM benutzer WHERE email = :email")
    Benutzer findBenutzer(String email);

    @Query("DELETE FROM benutzer WHERE email = :email")
    void deleteName(String email);

    @Query("SELECT * FROM benutzer ORDER BY NameId DESC LIMIT 1")
    Benutzer getLastName();

    @Query("UPDATE benutzer set rolle = :rolle")
    void updateRole(Integer rolle);

    @Update
    void updateBenutzer(Benutzer benutzer);


}
