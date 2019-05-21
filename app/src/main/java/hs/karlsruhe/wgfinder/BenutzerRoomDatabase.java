package hs.karlsruhe.wgfinder;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import hs.karlsruhe.wgfinder.DAOs.BenutzerDAO;
import hs.karlsruhe.wgfinder.Entity.Benutzer;

@Database(entities = {Benutzer.class}, version = 1, exportSchema = false)
 abstract class BenutzerRoomDatabase extends RoomDatabase {

    public abstract BenutzerDAO benutzerDAO();
    private static BenutzerRoomDatabase INSTANCE;

    static BenutzerRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BenutzerRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    BenutzerRoomDatabase.class,
                                    "benutzer_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
