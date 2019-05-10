package th.ac.chula.kirbio.mobileprogproject.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.List;
import java.util.UUID;

@Dao
@TypeConverters(UUIDConverter.class)
public interface MemoItemDAO {

    @Query("SELECT * FROM memoitem")
    List<MemoItem> loadAllMemos();

    @Query("SELECT * FROM memoitem WHERE uid = :uid")
    List<MemoItem> loadMemoByUID(String uid);

    @Query("SELECT * FROM memoitem WHERE memoId = :id")
    MemoItem loadMemoById(UUID id);

    @Insert()
    void insertMemo(MemoItem memo);

    @Query("UPDATE memoitem SET name=:name,detail=:detail WHERE memoId = :id")
    void updateMemo(UUID id,String name,String detail);

    @Query("SELECT COUNT(*) FROM memoitem")
    Integer countTotalMemoNumber();

    @Delete
    void deleteMemo(MemoItem memo);

    @Query("DELETE FROM memoitem WHERE memoId = :id")
    void deleteMemoById(UUID id);
}
