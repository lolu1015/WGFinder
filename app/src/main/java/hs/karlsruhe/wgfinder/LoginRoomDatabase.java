package hs.karlsruhe.wgfinder;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import hs.karlsruhe.wgfinder.DAOs.LoginDAO;
import hs.karlsruhe.wgfinder.Entity.Login;


@Database(entities = {Login.class}, version = 1, exportSchema = false)
 abstract class LoginRoomDatabase extends RoomDatabase {

    public abstract LoginDAO loginDAO();
    private static LoginRoomDatabase INSTANCE;

    static LoginRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BenutzerRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    LoginRoomDatabase.class,
                                    "login_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
