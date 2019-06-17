package hs.karlsruhe.wgfinder;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import hs.karlsruhe.wgfinder.DAOs.BenutzerDAO;
import hs.karlsruhe.wgfinder.DAOs.LoginDAO;
import hs.karlsruhe.wgfinder.DAOs.TempDAO;
import hs.karlsruhe.wgfinder.Entity.Benutzer;
import hs.karlsruhe.wgfinder.Entity.Login;
import hs.karlsruhe.wgfinder.Entity.Temp;

@Database(entities = {Benutzer.class, Login.class, Temp.class}, version = 1, exportSchema = false)
 abstract class WGFinderRoomDatabase extends RoomDatabase {

    public abstract BenutzerDAO benutzerDAO();
    public abstract LoginDAO loginDAO();
    public abstract TempDAO tempDAO();
    private static WGFinderRoomDatabase INSTANCE;

    static WGFinderRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WGFinderRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    WGFinderRoomDatabase.class,
                                    "wgfinder13_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
