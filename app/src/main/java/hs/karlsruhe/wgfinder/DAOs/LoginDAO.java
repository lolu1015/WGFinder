package hs.karlsruhe.wgfinder.DAOs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import hs.karlsruhe.wgfinder.Entity.Login;

import java.util.List;

@Dao
public interface LoginDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLogin(Login login);

    @Query("SELECT * FROM login WHERE email = :email AND passwort = :passwort")
    List<Login> findProduct(String email, String passwort);

    @Query("SELECT * FROM login WHERE email = :email")
    Login findLogin(String email);

    @Query("DELETE FROM login WHERE email = :email AND passwort = :passwort")
    void deleteEmail(String email, String passwort);

}
