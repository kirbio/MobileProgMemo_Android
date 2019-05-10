package th.ac.chula.kirbio.mobileprogproject.db.utils;

import java.util.UUID;

import th.ac.chula.kirbio.mobileprogproject.db.AppDatabase;
import th.ac.chula.kirbio.mobileprogproject.db.MemoItem;
import th.ac.chula.kirbio.mobileprogproject.db.User;

public class DatabaseInitializer {

    public static User addUser(final AppDatabase db, final String id, final String name) {
        User user = new User();
        user.id = id;
        db.userModel().insertUser(user);
        return user;
    }

    public static MemoItem addMemo(final AppDatabase db, final String uid, final String name, final String detail) {
        MemoItem memoItem = new MemoItem();
        memoItem.userId = uid;
        memoItem.name = name;
        memoItem.detail = detail;
        memoItem.memoId = UUID.randomUUID();//db.memoModel().countTotalMemoNumber()+1;
        db.memoModel().insertMemo(memoItem);
        return memoItem;
    }

    public static void testPolulateData(AppDatabase db){
        //Deprecated, do not use this
    }
}
