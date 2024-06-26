package com.tutorial.travel.database;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
import com.tutorial.travel.Activity.PasswordUtils;


import com.tutorial.travel.model.BookingModel;


import com.tutorial.travel.model.Room;

import com.tutorial.travel.Adapter.ReviewAdapter;

import com.tutorial.travel.model.RoomModel;

import com.tutorial.travel.model.Booking;
import com.tutorial.travel.model.HotelModel;
import com.tutorial.travel.model.ReviewModel;
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
    public static final String COLUMN_ROOM_DESCRIPTION = "description";


    public static final String COLUMN_HOTEL_ID_FK = "hotel_id";
    public static final String COLUMN_ROOM_TYPE_ID_FK = "roomTypeId";

    // booking
    public static final String TABLE_BOOKING = "booking";
    public static final String COLUMN_BOOKING_ID = "id";
    public static final String COLUMN_ROOM_ID_FK = "roomId";
    public static final String COLUMN_HOTEL_NAME_BOOKING = "nameHotelBooking";
    public static final String COLUMN_HOTEL_LOCATION_BOOKING = "locationBooking";
    public static final String COLUMN_USER_ID_FK = "userId";
    public static final String COLUMN_CHECK_IN_DATE = "checkIn";
    public static final String COLUMN_CHECK_OUT_DATE = "checkOut";
    public static final String COLUMN_PAYMENT_METHOD = "payment";
    public static final String COLUMN_TOTAL_AMOUNT = "total";

    public static final String COLUMN_IS_CONFIRMED = "isConfirmed";


    //review
    public static final  String TABLE_REVIEW = "review";
    public static final String COLUMN_REVIEW_ID = "rvId";

    public static final String COLUMN_REVIEW_DETAIL = "rvDetail";
    public static final String COLUMN_RATING = "rating";



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

    //Tạo bảng REVIEW
        String CREATE_REVIEW_TABLE = "CREATE TABLE " + TABLE_REVIEW + "("
                + COLUMN_REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_REVIEW_DETAIL + " TEXT,"
                + COLUMN_RATING + " INTEGER,"
                + COLUMN_HOTEL_ID_FK + " INTEGER,"
                + COLUMN_USER_ID_FK + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_HOTEL_ID_FK + ") REFERENCES " + TABLE_HOTEL + "(" + COLUMN_HOTEL_ID + "),"
                + "FOREIGN KEY(" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + ")"
                + ")";
        db.execSQL(CREATE_REVIEW_TABLE);

        // Tạo bảng roomType
        String CREATE_ROOM_TYPE_TABLE = "CREATE TABLE " + TABLE_ROOM_TYPE + "("
                + COLUMN_ROOM_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ROOM_TYPE_NAME + " TEXT,"
                + COLUMN_ROOMTYPE_DESCRIPTIONS + " TEXT"
                + ")";
        db.execSQL(CREATE_ROOM_TYPE_TABLE);


        // Tạo bảng room
        String CREATE_ROOM_TABLE = "CREATE TABLE " + TABLE_ROOM + "("
                + COLUMN_ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ROOM_NAME + " TEXT,"
                + COLUMN_PRICE + " INTEGER,"
                + COLUMN_ROOM_IMAGE + " TEXT,"
                + COLUMN_ROOM_STATUS + " TEXT,"
                + COLUMN_ROOM_DESCRIPTION + " TEXT,"
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
                + COLUMN_HOTEL_NAME_BOOKING + " TEXT,"
                + COLUMN_HOTEL_LOCATION_BOOKING + " TEXT,"
                + COLUMN_CHECK_IN_DATE + " TEXT,"
                + COLUMN_CHECK_OUT_DATE + " TEXT,"
                + COLUMN_PAYMENT_METHOD + " TEXT,"
                + COLUMN_TOTAL_AMOUNT + " REAL,"
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

    ///thêm đánh giá
    public void addReview(ReviewModel review) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_REVIEW_DETAIL, review.getReviewDetail());
        values.put(COLUMN_RATING, review.getRating());
        values.put(COLUMN_HOTEL_ID_FK, review.getHotel_id());
        values.put(COLUMN_USER_ID_FK, review.getUser_id());

        db.insert(TABLE_REVIEW, null, values);
        db.close();



    }
    public static String getUsernameById(Context context, int userId) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String username = null;

        String selectQuery = "SELECT " + COLUMN_USERNAME + " FROM " + TABLE_USERS + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return username;
    }
    //đếm số lượng review từng khách sạn
    public static int countReviewHotel(Context context,int hotelId) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        int count = 0;

        String query = "SELECT COUNT(*) FROM " + TABLE_REVIEW +
                " WHERE " + COLUMN_HOTEL_ID_FK + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(hotelId)});
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }

        db.close();
        return count;
    }



    public  User getUserInfoById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_USERNAME, COLUMN_EMAIL, COLUMN_PHONE, COLUMN_DOB};
        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(userId)};
        return (User) db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
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
    public long addRoom(String roomName, String price, String image, String status, long hotelId, long roomTypeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_NAME, roomName);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_ROOM_IMAGE, image);
        values.put(COLUMN_ROOM_STATUS, status);
        values.put(COLUMN_HOTEL_ID_FK, hotelId);
        values.put(COLUMN_ROOM_TYPE_ID_FK, roomTypeId);

        // Thêm thông tin về phòng vào cơ sở dữ liệu
        long roomId = db.insert(TABLE_ROOM, null, values);
        db.close();
        return roomId;
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


    public static int getIdByUsername(Context context, String username) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String selectQuery = "SELECT " + COLUMN_ID + " FROM " + TABLE_USERS +
                " WHERE " + COLUMN_USERNAME + " = ?";

        int userId = -1; // Giá trị mặc định nếu không tìm thấy người dùng

        Cursor cursor = db.rawQuery(selectQuery, new String[]{username});

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
        }

        cursor.close();
        db.close();

        return userId;
    }
    public int getHotelIdByName(String hotelName) {
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
        return (int) hotelId;
    }

    public int getRoomIdByName(String roomName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ROOM_ID};
        String selection = COLUMN_ROOM_NAME + "=?";
        String[] selectionArgs = {roomName};
        Cursor cursor = db.query(TABLE_ROOM, columns, selection, selectionArgs, null, null, null);
        long roomId = -1;
        if (cursor != null && cursor.moveToFirst()) {
            roomId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ROOM_ID));
            cursor.close();
        }
        db.close();
        return (int) roomId;
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

    public ArrayList<String> searchRooms(String searchString) {
        ArrayList<String> searchResults = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM room WHERE roomName LIKE ?", new String[]{"%" + searchString + "%"});
        if (cursor.moveToFirst()) {
            do {
                searchResults.add(cursor.getString(cursor.getColumnIndexOrThrow("roomName")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return searchResults;


    }
//    public List<RoomModel> getRoomsByHotelId(int hotelId) {
//        List<RoomModel> roomList = new ArrayList<>();
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM room WHERE hotel_id = ?", new String[]{String.valueOf(hotelId)});
//        if (cursor.moveToFirst()) {
//            do {
//                RoomModel room = new RoomModel();
//                room.setId(cursor.getInt(0));
//                room.setRoomName(cursor.getString(1));
//                room.setPrice(cursor.getDouble(2));
//                roomList.add(room);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return roomList;
//    }
////    public ArrayList<ReviewModel> getReviewbyHotelId(String hotelId) {
//        ArrayList<ReviewModel> rvList = new ArrayList<>();
//        SQLiteDatabase db = this.getWritableDatabase();
////        "SELECT * FROM review WHERE hotel_id = ?", new String[]{String.valueOf(hotelId)}
//        Cursor cursor = db.rawQuery("SELECT * FROM " +
//                DatabaseHelper.TABLE_REVIEW +
//                " WHERE " + DatabaseHelper.COLUMN_HOTEL_ID +
//
//                "=?", new String[]{hotelId});
//        if (cursor.moveToFirst()) {
//            do {
//                ReviewModel rv = new ReviewModel();
//
//                rv.setId(cursor.getInt(0));
//                rv.setReviewDetail(cursor.getString(1));
//                rv.setRating(cursor.getDouble(2));
//
//                rvList.add(rv);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return rvList;
//    }
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


    public long getRoomTypeIdByName(String roomTypeName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ROOM_TYPE_ID};
        String selection = COLUMN_ROOM_TYPE_NAME + "=?";
        String[] selectionArgs = {roomTypeName};
        Cursor cursor = db.query(TABLE_ROOM_TYPE, columns, selection, selectionArgs, null, null, null);
        long roomTypeId = -1;
        if (cursor != null && cursor.moveToFirst()) {
            roomTypeId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ROOM_TYPE_ID));
            cursor.close();
        }
        db.close();
        return roomTypeId;
    }
    public List<String> getAllRoomTypes() {
        List<String> roomTypes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ROOM_TYPE_NAME + " FROM " + TABLE_ROOM_TYPE, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                roomTypes.add(cursor.getString(0));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return roomTypes;
    }

//    public HotelModel getHotelById(int hotelId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        HotelModel hotel = null;
//
//        // Câu truy vấn SQL để lấy thông tin của khách sạn
//        String query = "SELECT * FROM hotels WHERE _id = ?";
//        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(hotelId)});
//
//        // Kiểm tra xem có dữ liệu không và di chuyển con trỏ đến hàng đầu tiên
//        if (cursor.moveToFirst()) {
//            // Lấy thông tin từ cột tương ứng trong cơ sở dữ liệu
//            String hotelName = cursor.getString(cursor.getColumnIndexOrThrow("hotel_name"));
//            String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
//            int starRating = cursor.getInt(cursor.getInt(cursor.getColumnIndexOrThrow("star_rating")));
//            String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow("image_url"));
//
//            // Tạo một đối tượng HotelModel từ dữ liệu truy vấn
//            hotel = new HotelModel(hotelId, hotelName, location, starRating, imageUrl);
//        }
//
//        // Đóng con trỏ và đóng kết nối đến cơ sở dữ liệu
//        cursor.close();
//        db.close();
//
//        // Trả về đối tượng HotelModel hoặc null nếu không tìm thấy
//        return hotel;
//    }

 public boolean updateHotel(String hotelId, String hotelName, String location, int starRating, String image) {
     SQLiteDatabase db = this.getWritableDatabase();
     ContentValues contentValues = new ContentValues();
     contentValues.put(COLUMN_HOTEL_NAME, hotelName);
     contentValues.put(COLUMN_LOCATION, location);
     contentValues.put(COLUMN_STAR_RATING, starRating);
     contentValues.put(COLUMN_IMAGE, image);

     // Xác định điều kiện để cập nhật khách sạn
     String selection = COLUMN_HOTEL_ID + " = ?";
     String[] selectionArgs = { hotelId };

     // Thực hiện cập nhật và kiểm tra kết quả
     int rowsAffected = db.update(TABLE_HOTEL, contentValues, selection, selectionArgs);
     return rowsAffected > 0;
 }

    public boolean updateRoom(String roomId, String roomName, String typeRoomid, String price, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ROOM_NAME, roomName);
        contentValues.put(COLUMN_ROOM_TYPE_ID_FK, typeRoomid);
        contentValues.put(COLUMN_PRICE, price);
        contentValues.put(COLUMN_ROOM_IMAGE, image);

        // Xác định điều kiện để cập nhật khách sạn
        String selection = COLUMN_ROOM_ID + " = ?";
        String[] selectionArgs = { roomId };

        // Thực hiện cập nhật và kiểm tra kết quả
        int rowsAffected = db.update(TABLE_ROOM, contentValues, selection, selectionArgs);
        return rowsAffected > 0;
    }
// Kiểm tra xem username đã tồn tại trong cơ sở dữ liệu hay chưa
        public boolean checkUsernameExists (String username){
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{username});
            boolean exists = cursor.getCount() > 0;
            cursor.close();
            return exists;
        }

        // Kiểm tra xem email đã tồn tại trong cơ sở dữ liệu hay chưa
        public boolean checkEmailExists (String email){
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{email});
            boolean exists = cursor.getCount() > 0;
            cursor.close();
            return exists;
        }

        // Kiểm tra xem phone đã tồn tại trong cơ sở dữ liệu hay chưa
        public boolean checkPhoneExists (String phone){
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_PHONE + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{phone});
            boolean exists = cursor.getCount() > 0;
            cursor.close();
            return exists;
        }
//    public boolean updateHotel(long hotelId, String hotelName, String location, float starRating, String imageUrl) {
//        // Khởi tạo đối tượng SQLiteDatabase để ghi dữ liệu
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_ROOM_NAME, roomName);
//        contentValues.put(COLUMN_ROOM_TYPE_ID_FK, typeRoomid);
//        contentValues.put(COLUMN_PRICE, price);
//        contentValues.put(COLUMN_ROOM_IMAGE, image);
//
//        // Xác định điều kiện để cập nhật khách sạn
//        String selection = COLUMN_ROOM_ID + " = ?";
//        String[] selectionArgs = { roomId };
//
//        // Thực hiện cập nhật và kiểm tra kết quả
//        int rowsAffected = db.update(TABLE_ROOM, contentValues, selection, selectionArgs);
//        return rowsAffected > 0;
//    }

        public boolean deleteHotel (String hotelName){
            SQLiteDatabase db = this.getWritableDatabase();
            // Xóa khách sạn từ bảng hotel dựa trên tên khách sạn
            int rowsAffected = db.delete(TABLE_HOTEL, COLUMN_HOTEL_NAME + " = ?", new String[]{hotelName});
            db.close();
            // Trả về true nếu có ít nhất một hàng được xóa thành công, ngược lại trả về false
            return rowsAffected > 0;
        }

        public boolean deleteRoom (String roomName){
            SQLiteDatabase db = this.getWritableDatabase();
            // Xóa phòng từ bảng room dựa trên tên phòng
            int rowsAffected = db.delete(TABLE_ROOM, COLUMN_ROOM_NAME + " = ?", new String[]{roomName});
            db.close();
            // Trả về true nếu có ít nhất một hàng được xóa thành công, ngược lại trả về false
            return rowsAffected > 0;
        }

        public ArrayList<Booking> getAllBookings () {
            List<Booking> bookingList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = null;

            try {
                String query = "SELECT * FROM " + TABLE_BOOKING;
                cursor = db.rawQuery(query, null);

                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        Booking booking = new Booking();
                        booking.setId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_ID)));
                        booking.setRoomId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROOM_ID_FK)));
                        booking.setUserId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID_FK)));
                        booking.setCheckInDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CHECK_IN_DATE)));
                        booking.setCheckOutDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CHECK_OUT_DATE)));
                        booking.setIsConfirmed(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_CONFIRMED)));

                        bookingList.add(booking);
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                Log.e(TAG, "Error while fetching bookings: " + e.getMessage());
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                db.close();
            }

            return (ArrayList<Booking>) bookingList;
        }

        public List<RoomModel> getRoomsByHotelId ( int hotelId){
            List<RoomModel> roomList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM room WHERE hotel_id = ?", new String[]{String.valueOf(hotelId)});
            if (cursor.moveToFirst()) {
                do {
                    RoomModel room = new RoomModel();
                    room.setId(cursor.getInt(0));
                    room.setRoomName(cursor.getString(1));
                    room.setPrice(cursor.getDouble(2));
                    room.setRoomImage(cursor.getString(3));
                    room.setRoomStatus(cursor.getString(4));
                    room.setDescription(cursor.getString(5));
                    room.setHotelId(hotelId);
                    roomList.add(room);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return roomList;
        }

        ///Tìm kiếm booking
        public List<Booking> getBookingsByDate (String date){
            List<Booking> bookingList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT * FROM " + TABLE_BOOKING + " WHERE " + COLUMN_CHECK_IN_DATE + " = ?";
            Cursor cursor = db.rawQuery(selectQuery, new String[]{date});
            // Duyệt qua các hàng của kết quả truy vấn
            if (cursor.moveToFirst()) {
                do {
                    Booking booking = new Booking();
                    booking.setId(cursor.getString(0));
                    booking.setRoomId(cursor.getString(1));
                    booking.setUserId(cursor.getString(2));
                    booking.setCheckInDate(cursor.getString(3));
                    booking.setCheckOutDate(cursor.getString(4));
                    booking.setIsConfirmed(Integer.parseInt(cursor.getString(5)));
                    // Thêm booking vào danh sách
                    bookingList.add(booking);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return bookingList;
        }

        public boolean updateHotel1 (HotelModel hotel){
            SQLiteDatabase db = this.getWritableDatabase();
            // Khởi tạo đối tượng ContentValues để chứa các cặp giá trị cần cập nhật
            ContentValues values = new ContentValues();
            // Đưa các giá trị mới vào đối tượng ContentValues
            values.put(COLUMN_HOTEL_NAME, hotel.getHotelName());
            values.put(COLUMN_LOCATION, hotel.getLocation());
            values.put(COLUMN_STAR_RATING, hotel.getStarRating());
            values.put(COLUMN_IMAGE, hotel.getImage());
            // Thực hiện cập nhật thông tin trong bảng khách sạn dựa trên hotelId
            int affectedRows = db.update(TABLE_HOTEL, values, COLUMN_HOTEL_ID + " = ?", new String[]{String.valueOf(hotel.getId())});
            // Đóng kết nối với cơ sở dữ liệu
            db.close();
            // Trả về true nếu có ít nhất một hàng được cập nhật, ngược lại trả về false
            return affectedRows > 0;
        }

        public boolean adminUpdateHotel (HotelModel hotelModel, String hotel){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(COLUMN_HOTEL_NAME, hotelModel.getHotelName());
            cv.put(COLUMN_LOCATION, hotelModel.getLocation());
            cv.put(COLUMN_STAR_RATING, hotelModel.getStarRating());
            cv.put(COLUMN_IMAGE, hotelModel.getImage());

            db.update(TABLE_HOTEL, cv, "hotelName = ?", new String[]{hotel});
            return true;
        }

        public HotelModel hotelViewHotel (String hotel){
            SQLiteDatabase db = this.getReadableDatabase();
            String qry = "select * from hotel where hotelName = '" + hotel + "' ";
            Cursor cursor = db.rawQuery(qry, null);

            if (cursor != null) {
                cursor.moveToFirst();
            }
            HotelModel hotelModel = new HotelModel(cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)), cursor.getString(4));

            hotelModel.setHotelName(cursor.getString(1));
            hotelModel.setLocation(cursor.getString(2));
            hotelModel.setStarRating(Integer.parseInt(cursor.getString(3)));
            hotelModel.setImage(cursor.getString(4));


            db.close();
            cursor.close();
            return hotelModel;
        }


        public Room hotelViewRoom (String room){
            SQLiteDatabase db = this.getReadableDatabase();
            String qry = "select * from room where roomName = '" + room + "' ";
            Cursor cursor = db.rawQuery(qry, null);

            if (cursor != null) {
                cursor.moveToFirst();
            }
            Room roomModel = new Room(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

            roomModel.setRoomName(cursor.getString(1));
            roomModel.setRoomType(cursor.getString(6));
            roomModel.setPrice(cursor.getString(2));
            roomModel.setImage(cursor.getString(3));


            db.close();
            cursor.close();
            return roomModel;
        }


        public Room getRoomDetails (String roomName){
            Room room = null;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = null;

            try {
                String query = "SELECT * FROM " + TABLE_ROOM + " WHERE " + COLUMN_ROOM_NAME + " = ?";
                cursor = db.rawQuery(query, new String[]{roomName});

                if (cursor != null && cursor.moveToFirst()) {
                    // Lấy thông tin từ Cursor và tạo một đối tượng Room
                    int roomId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ROOM_ID));
                    String type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROOM_TYPE_ID_FK));
                    int price = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
                    String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROOM_IMAGE));

                    // Khởi tạo đối tượng Room
                    room = new Room(roomId, roomName, type, price, imageUrl);
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            return room;
        }
        //chuyen state room

    public void updateRoomStatus(int roomId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_STATUS, status);

        db.update(TABLE_ROOM, values, COLUMN_ROOM_ID + " = ?", new String[]{String.valueOf(roomId)});
        db.close();
    }

        //TÍNH TRUNG BÌNH   rating
        public static void updateAverageRatingForHotel (Context context,int hotelId){
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();


            Cursor cursor = db.rawQuery("SELECT AVG(" + COLUMN_RATING + ") FROM " + TABLE_REVIEW +
                    " WHERE " + COLUMN_HOTEL_ID_FK + " = ?", new String[]{String.valueOf(hotelId)});
            double averageRating = 5.0;

            // Lấy giá trị trung bình nếu có
            if (cursor != null && cursor.moveToFirst()) {
                averageRating = cursor.getDouble(0);
                Log.d(TAG, "updateAverageRatingForHotel: " + averageRating);
                cursor.close();
            }
            double roundedAverageRating = Math.round(averageRating * 10.0) / 10.0;
            // Cập nhật giá trị trung bình vào bảng hotel
            ContentValues values = new ContentValues();
            values.put(COLUMN_STAR_RATING, roundedAverageRating);




        // Đóng cursor và database
        if (cursor != null) {
            cursor.close();
        } db.update(TABLE_HOTEL, values, COLUMN_HOTEL_ID + " = ?", new String[]{String.valueOf(hotelId)});

            db.close();}

            public List<BookingModel> getBookingHistoryByUsername(String username) {
                List<BookingModel> bookingHistory = new ArrayList<>();
                SQLiteDatabase db = this.getReadableDatabase();

                // Câu truy vấn để lấy lịch sử đặt phòng của một người dùng
                String query = "SELECT * FROM " + TABLE_BOOKING + " WHERE " + COLUMN_USER_ID_FK + " = ?";

                // Thực hiện truy vấn với đối số là username
                Cursor cursor = db.rawQuery(query, new String[]{username});

                // Kiểm tra cursor có dữ liệu không
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        // Lấy dữ liệu từ cursor và tạo mới đối tượng BookingModel
                        int roomId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ROOM_ID_FK));
                        String hotelName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HOTEL_NAME_BOOKING));
                        String hotelLocation = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HOTEL_LOCATION_BOOKING));
                        String checkInDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CHECK_IN_DATE));
                        String checkOutDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CHECK_OUT_DATE));
                        String paymentMethod = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAYMENT_METHOD));
                        double totalAmount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_AMOUNT));
                        int isConfirmed = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_CONFIRMED));

                        // Tạo đối tượng BookingModel và thêm vào danh sách bookingHistory
                        BookingModel booking = new BookingModel(roomId, username, hotelName, hotelLocation, checkInDate, checkOutDate, paymentMethod, totalAmount, isConfirmed);
                        bookingHistory.add(booking);
                    } while (cursor.moveToNext());
                }
        db.close();

        return bookingHistory;



        }

    }


