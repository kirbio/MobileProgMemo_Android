package th.ac.chula.kirbio.mobileprogproject.db;

import android.arch.persistence.room.TypeConverter;

import java.util.UUID;

public class UUIDConverter {
    @TypeConverter
    public static UUID toUUID(String suuid) {
        UUID result = UUID.fromString(suuid);
        return suuid == null ? null : result;
    }

    @TypeConverter
    public static String toString(UUID uuid) {
        return uuid == null ? null : uuid.toString();
    }
}
