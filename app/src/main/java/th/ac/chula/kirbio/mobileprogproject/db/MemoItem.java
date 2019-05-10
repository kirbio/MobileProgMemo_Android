package th.ac.chula.kirbio.mobileprogproject.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity

@TypeConverters(UUIDConverter.class)
public class MemoItem {

    @PrimaryKey
    @NonNull
    public UUID memoId;

    @NonNull
    public String name;

    public String detail;

    @ColumnInfo(name="uid")
    @NonNull
    public String userId;
}
