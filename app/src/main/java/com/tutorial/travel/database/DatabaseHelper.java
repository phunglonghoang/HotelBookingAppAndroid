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

import com.tutorial.travel.model.RoomModel;

import com.tutorial.travel.model.Booking;
import com.tutorial.travel.model.HotelModel;
import com.tutorial.travel.model.ReviewModel;
import com.tutorial.travel.model.RoomModel;
import com.tutorial.travel.model.User;

import java.util.ArrayList;
import java.util.List;


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


    public int getIdByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
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
    public long getHotelIdByName(String hotelName) {
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
    public boolean updateHotel(long hotelId, String hotelName, String location, float starRating, String imageUrl) {
        // Khởi tạo đối tượng SQLiteDatabase để ghi dữ liệu
        SQLiteDatabase db = this.getWritableDatabase();
        // Khởi tạo đối tượng ContentValues để chứa các cặp giá trị cần cập nhật
        ContentValues values = new ContentValues();
        // Đưa các giá trị mới vào đối tượng ContentValues
        values.put(COLUMN_HOTEL_NAME, hotelName);
        values.put(COLUMN_LOCATION, location);
        values.put(COLUMN_STAR_RATING, starRating);
        values.put(COLUMN_IMAGE, imageUrl);
        // Thực hiện cập nhật thông tin trong bảng khách sạn dựa trên hotelId
        int affectedRows = db.update(TABLE_HOTEL, values, COLUMN_HOTEL_ID + " = ?", new String[]{String.valueOf(hotelId)});
        // Đóng kết nối với cơ sở dữ liệu
        db.close();
        // Trả về true nếu có ít nhất một hàng được cập nhật, ngược lại trả về false
        return affectedRows > 0;
    }

    public boolean deleteHotel(String hotelName) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Xóa khách sạn từ bảng hotel dựa trên tên khách sạn
        int rowsAffected = db.delete(TABLE_HOTEL, COLUMN_HOTEL_NAME + " = ?", new String[]{hotelName});
        db.close();
        // Trả về true nếu có ít nhất một hàng được xóa thành công, ngược lại trả về false
        return rowsAffected > 0;
    }

    public HotelModel getHotelByName(String hotelName) {
        SQLiteDatabase db = this.getReadableDatabase();
        HotelModel hotel = null;

        // Câu truy vấn SQL để lấy thông tin của khách sạn dựa trên tên
        String query = "SELECT * FROM " + TABLE_HOTEL + " WHERE " + COLUMN_HOTEL_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{hotelName});

        // Kiểm tra xem có dữ liệu không và di chuyển con trỏ đến hàng đầu tiên
        if (cursor.moveToFirst()) {
            // Lấy thông tin từ cột tương ứng trong cơ sở dữ liệu
            int hotelId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HOTEL_ID));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION));
            int starRating = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STAR_RATING));
            String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
//            Double minprice = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE));


            // Tạo một đối tượng HotelModel từ dữ liệu truy vấn
            hotel = new HotelModel(hotelId, hotelName, location, starRating, imageUrl );
        }

        // Đóng con trỏ và đóng kết nối đến cơ sở dữ liệu
        cursor.close();
        db.close();

        // Trả về đối tượng HotelModel hoặc null nếu không tìm thấy
        return hotel;
    }
    public ArrayList<Booking> getAllBookings() {
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

}

