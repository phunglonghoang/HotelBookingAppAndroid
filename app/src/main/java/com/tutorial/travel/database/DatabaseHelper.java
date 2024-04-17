package com.tutorial.travel.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.tutorial.travel.Activity.PasswordUtils;
import com.tutorial.travel.model.HotelModel;
import com.tutorial.travel.model.RoomModel;
import com.tutorial.travel.model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME ="Hotel.db" ;
//    private static final String DATABASE_NAME = "Hotel.db";
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
    public static final String COLUMN_DOB = "dob"; // Change from "DOB" to "dob"
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ROLE_ID_FK = "roleId";

    // hotel
    public static final String TABLE_HOTEL = "hotel";
    public static final String COLUMN_HOTEL_ID = "id";
    public static final String COLUMN_HOTEL_NAME = "hotelName";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_STAR_RATING = "starRating";
    public static final String COLUMN_IMAGE = "image";

    // roomtype
    public static final String TABLE_ROOM_TYPE = "roomType";
    public static final String COLUMN_ROOM_TYPE_ID = "id";
    public static final String COLUMN_ROOM_TYPE_NAME = "roomTypeName";
    public static final String COLUMN_ROOMTYPE_DESCRIPTIONS = "descriptions";

    // room
    public static final String TABLE_ROOM = "room";
    public static final String COLUMN_ROOM_ID = "id";
    public static final String COLUMN_ROOM_NAME = "roomName";
    public static final String COLUMN_PRICE = "price";

    public static final String COLUMN_ROOM_IMAGE = "image";
    public static final String COLUMN_ROOM_STATUS = "status";

    public static final String COLUMN_HOTEL_ID_FK = "hotel_id";
    public static final String COLUMN_ROOM_TYPE_ID_FK = "roomTypeId";

    // booking
    private static final String TABLE_BOOKING = "booking";
    private static final String COLUMN_BOOKING_ID = "id";
    private static final String COLUMN_ROOM_ID_FK = "roomId";
    private static final String COLUMN_USER_ID_FK = "userId";
    private static final String COLUMN_CHECK_IN_DATE = "checkIn";
    private static final String COLUMN_CHECK_OUT_DATE = "checkOut";
    private static final String COLUMN_IS_CONFIRMED = "isConfirmed";

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

        // Tạo bảng hotel
        String CREATE_HOTEL_TABLE = "CREATE TABLE " + TABLE_HOTEL + "("
                + COLUMN_HOTEL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_HOTEL_NAME + " TEXT, "
                + COLUMN_LOCATION + " TEXT ,"
                + COLUMN_STAR_RATING + " INTEGER,"
                + COLUMN_IMAGE + " TEXT"
                + ")";
        db.execSQL(CREATE_HOTEL_TABLE);


        // Tạo bảng roomType
        String CREATE_ROOM_TYPE_TABLE = "CREATE TABLE " + TABLE_ROOM_TYPE + "("
                + COLUMN_ROOM_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ROOM_TYPE_NAME + " TEXT,"
                + COLUMN_ROOMTYPE_DESCRIPTIONS + " TEXT"
                + ")";
        db.execSQL(CREATE_ROOM_TYPE_TABLE);


        // Tạo bảng room
        // Tạo bảng room
        String CREATE_ROOM_TABLE = "CREATE TABLE " + TABLE_ROOM + "("
                + COLUMN_ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ROOM_NAME + " TEXT,"
                + COLUMN_PRICE + " INTEGER,"
                + COLUMN_ROOM_IMAGE + " TEXT,"
                + COLUMN_ROOM_STATUS + " TEXT,"
                + COLUMN_HOTEL_ID_FK + " INTEGER,"
                + COLUMN_ROOM_TYPE_ID_FK + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_HOTEL_ID_FK + ") REFERENCES " + TABLE_HOTEL + "(" + COLUMN_HOTEL_ID + "),"
                + "FOREIGN KEY(" + COLUMN_ROOM_TYPE_ID_FK + ") REFERENCES " + TABLE_ROOM_TYPE + "(" + COLUMN_ROOM_TYPE_ID + ")"
                + ")";
        db.execSQL(CREATE_ROOM_TABLE);



        // Taoh bảng booking
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
        values.put(COLUMN_DOB, user.getDOB()); // Change from "getDOB()" to "getDob()"
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

    private void insertRole(SQLiteDatabase db, String roleName) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROLE_NAME, roleName);
        db.insert(TABLE_ROLE, null, values);
    }

    public void insertUser(SQLiteDatabase db, String username, String email, String phone, String password, int roleId) {
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
        values.put(COLUMN_ROOM_TYPE_NAME, roomType);
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
    public List<HotelModel> getAllHotelNames1(){
        List<HotelModel> hotels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HOTEL, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int hotelId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HOTEL_ID));
                String hotelName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HOTEL_NAME));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION));
                // Khởi tạo đối tượng Hotel từ dữ liệu của cơ sở dữ liệu và thêm vào danh sách
                HotelModel hotelModel = new HotelModel(hotelId, hotelName, location);
                hotels.add(hotelModel);
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
    public List<RoomModel> getRoomsByHotelId(int hotelId) {
        List<RoomModel> roomList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM room WHERE hotel_id = ?", new String[]{String.valueOf(hotelId)});
        if (cursor.moveToFirst()) {
            do {
                RoomModel room = new RoomModel();
                room.setId(cursor.getInt(0));
                room.setRoomName(cursor.getString(1));
                room.setPrice(cursor.getDouble(2));
                roomList.add(room);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return roomList;
    }
}