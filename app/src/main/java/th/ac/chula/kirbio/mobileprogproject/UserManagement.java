package th.ac.chula.kirbio.mobileprogproject;

public class UserManagement {
    private static String userID;

    public static void SetCurrentUserID(String newID){
        userID = newID;
    }

    public static String GetCurrentUserID(){
        return userID;
    }
}
