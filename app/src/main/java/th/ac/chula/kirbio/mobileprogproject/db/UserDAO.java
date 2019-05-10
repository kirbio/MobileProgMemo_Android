package th.ac.chula.kirbio.mobileprogproject.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user")
    List<User> loadAllUsers();

    @Query("SELECT * FROM user WHERE id = :id")
    User loadUserById(String id);

    @Query("SELECT name FROM user WHERE id = :id")
    String getUsernameById(String id);

    @Insert(onConflict = IGNORE)
    void insertUser(User user);
}
