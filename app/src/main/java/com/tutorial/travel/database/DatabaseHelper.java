package com.tutorial.travel.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.tutorial.travel.Activity.PasswordUtils;
import com.tutorial.travel.model.Hotel;
import com.tutorial.travel.model.User;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Hotel.db";
    private static final int DATABASE_VERSION = 1;

    // role
    private static final String TABLE_ROLE = "role";
    private static final String COLUMN_ROLE_ID = "roleId";
    private static final String COLUMN_ROLE_NAME = "roleName";

    // users
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_DOB = "DOB";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ROLE_ID_FK = "roleId";

    // hotel
    public static final String TABLE_HOTEL = "hotel";
    public static final String COLUMN_HOTEL_ID = "id";
    public static final String COLUMN_HOTEL_NAME = "hotelName";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_STAR_RATING = "star_rating";
    public static final String COLUMN_IMAGE = "image";



    //room
    private static final String TABLE_ROOM = "room";
    private static final String COLUMN_ROOM_ID = "id";
    private static final String COLUMN_ROOM_NAME = "roomName";
    private static final String COLUMN_ROOM_TYPE = "roomType";
    private static final String COLUMN_ROOM_RATE = "rate";
    private static final String COLUMN_ROOM_IMAGE = "image";
    private static final String COLUMN_HOTEL_ID_FK = "hotel_id";
    //booking
    private static final String TABLE_BOOKING = "booking";
    private static final String COLUMN_BOOKING_ID = "id";
    private static final String COLUMN_ROOM_ID_FK = "roomID";
    private static final String COLUMN_USER_ID_FK = "userID";
    private static final String COLUMN_CHECK_IN_DATE = "checkIn";
    private static final String COLUMN_CHECK_OUT_DATE = "checkOut";
    private static final String COLUMN_IS_CONFIRMED = "status";



    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng role
        String CREATE_ROLE_TABLE = "CREATE TABLE " + TABLE_ROLE + "("
                + COLUMN_ROLE_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_ROLE_NAME + " TEXT"
                + ")";
        db.execSQL(CREATE_ROLE_TABLE);


        // Tạo bảng users
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PHONE + " TEXT,"
                + COLUMN_DOB + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_ROLE_ID_FK + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_ROLE_ID_FK + ") REFERENCES " + TABLE_ROLE + "(" + COLUMN_ROLE_ID + ")"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // tạo bảng hotel
        String CREATE_HOTEL_TABLE = "CREATE TABLE " + TABLE_HOTEL + "("
                + COLUMN_HOTEL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_HOTEL_NAME + " TEXT, "
                + COLUMN_LOCATION + " TEXT ,"
                + COLUMN_STAR_RATING + " INTEGER,"
                + COLUMN_IMAGE + " TEXT"
                + ")";
        db.execSQL(CREATE_HOTEL_TABLE);

        // tạo bảng room
        String CREATE_ROOM_TABLE = "CREATE TABLE " + TABLE_ROOM + "("
                + COLUMN_ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ROOM_NAME + " TEXT,"
                + COLUMN_ROOM_TYPE + " TEXT,"
                + COLUMN_ROOM_RATE + " REAL,"
                + COLUMN_ROOM_IMAGE + " TEXT"
                + ")";
        db.execSQL(CREATE_ROOM_TABLE);

        // taoh bảng booking
        String CREATE_BOOKING_TABLE = "CREATE TABLE " + TABLE_BOOKING + "("
                + COLUMN_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ROOM_ID_FK + " INTEGER,"
                + COLUMN_USER_ID_FK + " INTEGER,"
                + COLUMN_CHECK_IN_DATE + " TEXT,"
                + COLUMN_CHECK_OUT_DATE + " TEXT,"
                + COLUMN_IS_CONFIRMED + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_ROOM_ID_FK + ") REFERENCES " + TABLE_ROOM + "(" + COLUMN_ROOM_ID + "),"
                + "FOREIGN KEY(" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + ")"
                + ")";
        db.execSQL(CREATE_BOOKING_TABLE);

        insertData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOTEL);
        onCreate(db);
    }

    private void insertData(SQLiteDatabase db) {
        insertRole(db, "Admin");
        insertRole(db, "Customer");

        String hashPass = PasswordUtils.hashPassword("123");
        insertUser(db, "Admin", "Admin@gmail.com", "086868686", hashPass, 1);
        insertUser(db, "nd", "nd@gmail.com", "321321214", hashPass, 2);

    }
    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PHONE, user.getPhone());
        values.put(COLUMN_PASSWORD, PasswordUtils.hashPassword(user.getPassword()));
        values.put(COLUMN_ROLE_ID_FK, 2);
        values.put(COLUMN_DOB, user.getDOB());
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    public int getUserRoleId(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ROLE_ID_FK, COLUMN_PASSWORD};
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int roleId = -1;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int passwordColumnIndex = cursor.getColumnIndex(COLUMN_PASSWORD);
                if (passwordColumnIndex >= 0) {
                    String hashedPassword = cursor.getString(passwordColumnIndex);
                    if (PasswordUtils.checkPassword(password, hashedPassword)) {
                        int roleIdColumnIndex = cursor.getColumnIndex(COLUMN_ROLE_ID_FK);
                        if (roleIdColumnIndex >= 0) {
                            roleId = cursor.getInt(roleIdColumnIndex);
                            break;
                        }
                    }
                }
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return roleId;
    }


    private void insertRole(SQLiteDatabase db,  String roleName ) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROLE_NAME, roleName);
        db.insert(TABLE_ROLE, null, values);
    }

    public void insertUser(SQLiteDatabase db,String username, String email, String phone, String password, int roleId) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_ROLE_ID_FK, roleId);
        db.insert(TABLE_USERS, null, values);
    }


    public Cursor getUserInfoById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_USERNAME, COLUMN_EMAIL, COLUMN_PHONE, COLUMN_DOB};
        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(userId)};
        return db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
    }

    public static String getColumnUsername() {
        return COLUMN_USERNAME;
    }

    public static String getColumnEmail() {
        return COLUMN_EMAIL;
    }

    public static String getColumnPhone() {
        return COLUMN_PHONE;
    }

    public static String getColumnDob() {
        return COLUMN_DOB;
    }




//    public List<Hotel> getAllHotels() {
//        List<Hotel> hotelList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] columns = {COLUMN_HOTEL_NAME, COLUMN_LOCATION, COLUMN_STAR_RATING, COLUMN_IMAGE};
//        Cursor cursor = db.query(TABLE_HOTEL, columns, null, null, null, null, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                String hotelName = cursor.getString(cursor.getColumnIndex(COLUMN_HOTEL_NAME));
//                String location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION));
//                int starRating = cursor.getInt(cursor.getColumnIndex(COLUMN_STAR_RATING));
//                String image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));
//                Hotel hotel = new Hotel(hotelName, location, starRating, image);
//                hotelList.add(hotel);
//            } while (cursor.moveToNext());
//            cursor.close();
//        }
//        db.close();
//        return hotelList;
//    }

    public ArrayList<User> getAllUsers(String abc)
    {
        ArrayList<User>arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String qry =  "select * from users where username = '" + abc + "' ";
        Cursor cursor = db.rawQuery(qry,null);
        while (cursor.moveToNext())
        {
            String userid = cursor.getString(0);
            String username = cursor.getString(1);

            String email = cursor.getString(3);
            String phone = cursor.getString(4);



            User user = new User(userid,username,email,phone);

            arrayList.add(user);
        }
            return arrayList;
    }

    public ArrayList<User> getAllUsersNO() {
        ArrayList<User> userList = new ArrayList<>();
        // Select tất cả các cột từ bảng Users
        String selectQuery = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Duyệt qua tất cả các hàng và thêm vào danh sách người dùng
        if (cursor.moveToFirst()) {
            do {
                String userId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));

                User user = new User(userId, username, email, phone);
                userList.add(user);
            } while (cursor.moveToNext());
        }

        // Đóng con trỏ và database khi không cần thiết nữa
        cursor.close();
        db.close();

        return userList;
    }

    public User adminViewUser(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry =  "select * from users where username = '" + user + "' ";
        Cursor cursor = db.rawQuery(qry, null);

        if(cursor!= null)
        {
            cursor.moveToFirst();
        }
        User userprofile =new User(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));

        userprofile.setUsername(cursor.getString(1));
        userprofile.setPassword(cursor.getString(2));
        userprofile.setEmail(cursor.getString(3));
        userprofile.setPhone(cursor.getString(4));
        userprofile.setDOB(cursor.getString(5));
//        userprofile.setRoleId(cursor.getString(6));
//        userprofile.setId(cursor.getString(0));

        db.close();
        cursor.close();
        return userprofile;
    }

    public boolean adminUpdateProfile(User userprofile, String user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME,userprofile.getUsername());
        cv.put(COLUMN_PASSWORD,userprofile.getPassword());
        cv.put(COLUMN_EMAIL,userprofile.getEmail());
        cv.put(COLUMN_PHONE,userprofile.getPhone());
        cv.put(COLUMN_DOB,userprofile.getDOB());
        cv.put(COLUMN_ROLE_ID, userprofile.getRoleId());

        db.update(TABLE_USERS,cv,"username = ?", new String[] {user});
        return true;
    }

    public boolean deteleUser(String user){
        SQLiteDatabase db =getWritableDatabase();
        int i = db.delete(TABLE_USERS,"username = ?", new String[]{user});
        if(i >0){
            return true;
        }else{
            return false;
        }
    }

//test đổi role
public boolean updateRoleIdForUser(String username, int roleId) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("role_id", roleId);

    // Cập nhật roleId trong bảng người dùng dựa trên tên người dùng
    int rowsAffected = db.update("users", values, "username = ?", new String[]{username});
    db.close();

    return rowsAffected > 0; // Trả về true nếu có ít nhất một hàng được cập nhật thành công
}

// test add hotel
    public long addHotel(String hotelName, String location, int StarRating, String imageUrl){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOTEL_NAME,hotelName);
        values.put(COLUMN_LOCATION,location);
        values.put(COLUMN_STAR_RATING,StarRating);
        values.put(COLUMN_IMAGE, imageUrl);

        long id = db.insert(TABLE_HOTEL,null,values);
        db.close();
        return id;
    }

    // test add room
    public long addRoomType(String roomType, int numBeds, int numPeople) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_TYPE, roomType);
//        values.put(COLUMN_HOTEL_ID_FK, hotelid);
//        values.put(COLUMN_NUM_BEDS, numBeds);
//        values.put(COLUMN_HOTEL_ID_FK, numPeople);
                    // values.put(COLUMN_ROOM_NAME, numPeople);
                    //        values.put(COLUMN_ROOM_RATE, numBeds);
        long id = db.insert(TABLE_ROOM, null, values);
        db.close();
        return id;
    }

    //test tiep
    public List<String> getAllHotelNames() {
        List<String> hotelNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_HOTEL_NAME + " FROM " + TABLE_HOTEL, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                hotelNames.add(cursor.getString(0));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return hotelNames;
    }

    // GetAllHotel nhưng hiện thêm location
    public List<Hotel> getAllHotelNames1(){
        List<Hotel> hotels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HOTEL, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int hotelId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HOTEL_ID));
                String hotelName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HOTEL_NAME));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION));
                // Khởi tạo đối tượng Hotel từ dữ liệu của cơ sở dữ liệu và thêm vào danh sách
                Hotel hotel = new Hotel(hotelId, hotelName, location);
                hotels.add(hotel);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return hotels;
    }
    private long getHotelIdByName(String hotelName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_HOTEL_ID};
        String selection = COLUMN_HOTEL_NAME + "=?";
        String[] selectionArgs = {hotelName};
        Cursor cursor = db.query(TABLE_HOTEL, columns, selection, selectionArgs, null, null, null);
        long hotelId = -1;
        if (cursor != null && cursor.moveToFirst()) {
            hotelId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_HOTEL_ID));
            cursor.close();
        }
        db.close();
        return hotelId;
    }

    public List<Hotel> searchHotelsByName(String hotelName){
        List<Hotel> hotels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM" + TABLE_HOTEL + "WHERE" + COLUMN_HOTEL_NAME + " LIKE '%" + hotelName + "%'", null);

        if(cursor != null && cursor.moveToFirst()){
            do{
                Hotel hotel = new Hotel();
                hotel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HOTEL_ID)));
                hotel.setHotelName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HOTEL_NAME)));
                hotel.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION)));

                hotels.add(hotel);
            }while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return hotels;
    }

    public ArrayList<String> searchHotels(String searchString) {
        ArrayList<String> searchResults = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM hotel WHERE hotelName LIKE ?", new String[]{"%" + searchString + "%"});
        if (cursor.moveToFirst()) {
            do {
                searchResults.add(cursor.getString(cursor.getColumnIndexOrThrow("hotelName")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return searchResults;
    }

    public Hotel adminViewHotel(String hotelname){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry =  "select * from hotel where username = '" + hotelname + "' ";
        Cursor cursor = db.rawQuery(qry, null);

        if(cursor!= null)
        {
            cursor.moveToFirst();
        }
        Hotel hotelprofile =new Hotel(cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4));

        hotelprofile.setHotelName(cursor.getString(1));
        hotelprofile.setLocation(cursor.getString(2));
        hotelprofile.setStarRating(cursor.getInt(3));
        hotelprofile.setImage(cursor.getString(4));

//        userprofile.setRoleId(cursor.getString(6));
//        userprofile.setId(cursor.getString(0));

        db.close();
        cursor.close();
        return hotelprofile;
    }

    public boolean adminUpdateHotelpf(Hotel hotelprofile, String hotels){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_HOTEL_NAME,hotelprofile.getHotelName());
        cv.put(COLUMN_LOCATION,hotelprofile.getLocation());
        cv.put(COLUMN_STAR_RATING,hotelprofile.getStarRating());
        cv.put(COLUMN_IMAGE,hotelprofile.getImage());


        db.update(TABLE_HOTEL,cv,"hotelName = ?", new String[] {hotels});
        return true;
    }

    public boolean deteleHotel(String hotels){
        SQLiteDatabase db =getWritableDatabase();
        int i = db.delete(TABLE_HOTEL,"hotelName = ?", new String[]{hotels});
        if(i >0){
            return true;
        }else{
            return false;
        }
    }

    public Hotel getHotelByName(String hotelName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_HOTEL, new String[]{COLUMN_HOTEL_ID, COLUMN_HOTEL_NAME, COLUMN_LOCATION, COLUMN_STAR_RATING, COLUMN_IMAGE},
                COLUMN_HOTEL_NAME + "=?", new String[]{hotelName}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Hotel hotel = new Hotel();
        hotel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HOTEL_ID)));
        hotel.setHotelName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HOTEL_NAME)));
        hotel.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION)));
        hotel.setStarRating(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STAR_RATING)));
        hotel.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)));

        cursor.close();
        return hotel;
    }



}