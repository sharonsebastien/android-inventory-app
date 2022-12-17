package com.example.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DataBaseHelper extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "inventory2";

        private static final String TABLE_NAME_CATEGORY = "category";
        private static final String CATEGORY_KEY_ID = "id";
        private static final String CATEGORY_KEY_NAME = "name";

        private static final String TABLE_NAME_USERS = "users";
        private static final String USER_KEY_ID = "id";
        private static final String USER_KEY_NAME = "name";
        private static final String USER_KEY_TYPE = "user_type";
        private static final String USER_KEY_PH_NO = "phone_number";
        private static final String USER_KEY_PASSWORD = "password";

        private static final String TABLE_NAME_PRODUCTS = "items";
        private static final String PRODUCT_KEY_ID = "id";
        private static final String PRODUCT_KEY_NAME = "name";
        private static final String PRODUCT_KEY_STOCK_LEFT = "stockLeft";
        private static final String PRODUCT_KEY_CATEGORY = "category";

        private static final String TABLE_NAME_CARTS = "carts";
        private static final String CART_KEY_ID = "id";
        private static final String CART_KEY_QTY = "quantity";
        private static final String CART_KEY_USER_ID = "userId";
        private static final String CART_KEY_PRODUCT_ID = "productId";

        private static final String TABLE_NAME_ORDERS = "orders";
        private static final String ORDER_KEY_ID = "id";
        private static final String ORDER_KEY_QTY = "quantity";
        private static final String ORDER_KEY_STATUS = "status";
        private static final String ORDER_KEY_USER_ID = "userId";
        private static final String ORDER_KEY_PRODUCT_ID = "productId";
        private static final String ORDER_KEY_CREATED_AT = "createdAt";


        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null,DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME_CATEGORY + "(" +
                    CATEGORY_KEY_ID + " INTEGER PRIMARY KEY,"
                    + CATEGORY_KEY_NAME + " TEXT" + ")";

            String CREATE_CONTACTS_TABLE2 = "CREATE TABLE " + TABLE_NAME_USERS + "(" +
                    USER_KEY_ID + " INTEGER PRIMARY KEY,"
                    + USER_KEY_NAME + " TEXT,"
                    + USER_KEY_PH_NO + " TEXT,"
                    + USER_KEY_PASSWORD + " TEXT,"
                    + USER_KEY_TYPE + " TEXT" + ")";

            String CREATE_CONTACTS_TABLE3 = "CREATE TABLE " + TABLE_NAME_PRODUCTS + "(" +
                    PRODUCT_KEY_ID + " INTEGER PRIMARY KEY,"
                    + PRODUCT_KEY_NAME + " TEXT,"
                    + PRODUCT_KEY_STOCK_LEFT + " INTEGER,"
                    + PRODUCT_KEY_CATEGORY + " INTEGER,"
                    + "FOREIGN KEY (" + PRODUCT_KEY_CATEGORY + ") REFERENCES " + TABLE_NAME_CATEGORY + "(" + CATEGORY_KEY_NAME + "))";

            String CREATE_CONTACTS_TABLE4 = "CREATE TABLE " + TABLE_NAME_CARTS + "(" +
                    CART_KEY_ID + " INTEGER PRIMARY KEY,"
                    + CART_KEY_QTY + " INTEGER,"
                    + CART_KEY_USER_ID + " INTEGER " + " REFERENCES " + TABLE_NAME_USERS + "(" + USER_KEY_ID + "),"
                    + CART_KEY_PRODUCT_ID + " INTEGER" + " REFERENCES " + TABLE_NAME_PRODUCTS + "(" + PRODUCT_KEY_ID + "))";


            String CREATE_CONTACTS_TABLE5 = "CREATE TABLE " + TABLE_NAME_ORDERS + "(" +
                    ORDER_KEY_ID + " INTEGER PRIMARY KEY,"
                    + ORDER_KEY_QTY + " INTEGER,"
                    + ORDER_KEY_STATUS + " INTEGER,"
                    + ORDER_KEY_CREATED_AT + " DATE,"
                    + ORDER_KEY_USER_ID + " INTEGER " + " REFERENCES " + TABLE_NAME_USERS + "(" + USER_KEY_ID + "),"
                    + ORDER_KEY_PRODUCT_ID + " INTEGER" + " REFERENCES " + TABLE_NAME_PRODUCTS + "(" + PRODUCT_KEY_ID + "))";

            db.execSQL(CREATE_CONTACTS_TABLE);
            db.execSQL(CREATE_CONTACTS_TABLE2);
            db.execSQL(CREATE_CONTACTS_TABLE3);
            db.execSQL(CREATE_CONTACTS_TABLE4);
            db.execSQL(CREATE_CONTACTS_TABLE5);

            addDefaultDataUser(
                    db,
                    new ModelUser(
                    "Admin",
                    "9900990099",
                    "Asd@1234",
                    "ADMIN")
            );
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CATEGORY);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PRODUCTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CARTS);
            onCreate(db);
        }

        void addCategory(ModelCategory category) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(CATEGORY_KEY_NAME , category._name);

            db.insert(TABLE_NAME_CATEGORY, null, values);
            db.close();
        }

        public ArrayList<ModelCategory> getAllCategory() {
            ArrayList<ModelCategory> recordList = new ArrayList<>();
            String selectQuery = "SELECT * FROM " + TABLE_NAME_CATEGORY + " ORDER BY id DESC";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if(cursor.moveToFirst()) {
                do {
                    ModelCategory record = new ModelCategory();
                    record.setId(Integer.parseInt(cursor.getString(0)));
                    record.setName(cursor.getString(1));

                    recordList.add(record);
                } while(cursor.moveToNext());
            }

            return recordList;
        }
        ModelCategory getCategory(int id) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME_CATEGORY, new String[] {
                            CATEGORY_KEY_ID, CATEGORY_KEY_NAME}, CATEGORY_KEY_ID + "=?",
                    new String[] {String.valueOf(id)}, null, null,null,null);

            if(cursor != null) {
                cursor.moveToFirst();
            }
            ModelCategory category = new ModelCategory(
                    cursor.getInt(0),
                    cursor.getString(1)
            );

            return category;
        }

        public int updateCategory(ModelCategory category) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CATEGORY_KEY_NAME, category.getName());

            return db.update(TABLE_NAME_CATEGORY, values, CATEGORY_KEY_ID + "=?", new String[] { String.valueOf(category.getId())});
        }

        public void deleteRecord(ModelCategory category) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME_CATEGORY, CATEGORY_KEY_ID+ " = ?", new String[] {String.valueOf(category.getId())});

            db.close();
        }


        //MODEL PRODUCT
        void addProduct(ModelProduct product) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(PRODUCT_KEY_NAME , product._name);
            values.put(PRODUCT_KEY_STOCK_LEFT , Integer.valueOf(product._stockLeft));
            values.put(PRODUCT_KEY_CATEGORY , Integer.valueOf(product._category));

            db.insert(TABLE_NAME_PRODUCTS, null, values);
            db.close();
        }

        public ArrayList<ModelProduct> getAllProducts() {
            ArrayList<ModelProduct> recordList = new ArrayList<>();
            String selectQuery = "SELECT * FROM " + TABLE_NAME_PRODUCTS + " ORDER BY id DESC";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if(cursor.moveToFirst()) {
                do {
                    ModelProduct record = new ModelProduct();
                    record.setId(Integer.parseInt(cursor.getString(0)));
                    record.setName(cursor.getString(1));
                    record.setStockLeft(cursor.getString(2));
                    record.setCategory(cursor.getString(3));

                    recordList.add(record);
                } while(cursor.moveToNext());
            }

            return recordList;
        }
        ModelProduct getProduct(int id) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME_PRODUCTS, new String[] {
                            PRODUCT_KEY_ID, PRODUCT_KEY_NAME, PRODUCT_KEY_STOCK_LEFT, PRODUCT_KEY_CATEGORY}, PRODUCT_KEY_ID + "=?",
                    new String[] {String.valueOf(id)}, null, null,null,null);

            if(cursor != null) {
                cursor.moveToFirst();
            }
            ModelProduct user = new ModelProduct(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );

            return user;
        }

        public int updateProduct(ModelProduct product) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PRODUCT_KEY_NAME, product.getName());
            values.put(PRODUCT_KEY_STOCK_LEFT, Integer.valueOf(product.getStockLeft()));
            values.put(PRODUCT_KEY_CATEGORY, Integer.valueOf(product.getCategory()));

            return db.update(TABLE_NAME_PRODUCTS, values, PRODUCT_KEY_ID + "=?", new String[] { String.valueOf(product.getId())});
        }

        public void deleteProduct(ModelProduct product) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME_PRODUCTS, PRODUCT_KEY_ID+ " = ?", new String[] {String.valueOf(product.getId())});

            db.close();
        }



        //MODEL USER

        void addUser(ModelUser user) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(USER_KEY_NAME , user._name);
            values.put(USER_KEY_PH_NO , user._phoneNumber);
            values.put(USER_KEY_PASSWORD , user._password);
            values.put(USER_KEY_TYPE, user._userType);

            db.insert(TABLE_NAME_USERS, null, values);
            db.close();
        }

        void addDefaultDataUser(SQLiteDatabase db, ModelUser user) {
            ContentValues values = new ContentValues();

            values.put(USER_KEY_NAME , user._name);
            values.put(USER_KEY_PH_NO , user._phoneNumber);
            values.put(USER_KEY_PASSWORD , user._password);
            values.put(USER_KEY_TYPE, user._userType);

            db.insert(TABLE_NAME_USERS, null, values);
        }

        public ArrayList<ModelUser> getAllUsers() {
            ArrayList<ModelUser> recordList = new ArrayList<>();
            String selectQuery = "SELECT * FROM " + TABLE_NAME_USERS + " ORDER BY id DESC";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if(cursor.moveToFirst()) {
                do {
                    ModelUser record = new ModelUser();
                    record.setId(Integer.parseInt(cursor.getString(0)));
                    record.setName(cursor.getString(1));
                    record.setPhoneNumber(cursor.getString(2));
                    record.setPassword(cursor.getString(3));
                    record.setUserType(cursor.getString(4));

                    recordList.add(record);
                } while(cursor.moveToNext());
            }

            return recordList;
        }
        ModelUser getUser(int id) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME_USERS, new String[] {
                            USER_KEY_ID, USER_KEY_NAME, USER_KEY_PH_NO, USER_KEY_PASSWORD,USER_KEY_TYPE}, USER_KEY_ID + "=?",
                    new String[] {String.valueOf(id)}, null, null,null,null);

            if(cursor.moveToFirst()) {
                ModelUser user = new ModelUser(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                return user;
            }
            return new ModelUser();
        }

        public int updateUser(ModelUser user) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(USER_KEY_NAME, user.getName());
            values.put(USER_KEY_PH_NO, user.getPhoneNumber());
            values.put(USER_KEY_PASSWORD, user.getPassword());

            return db.update(TABLE_NAME_USERS, values, USER_KEY_ID + "=?", new String[] { String.valueOf(user.getId())});
        }

        public void deleteUser(ModelUser user) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME_USERS, USER_KEY_ID+ " = ?", new String[] {String.valueOf(user.getId())});

            db.close();
        }


        //ADMIN LOGIN

        int adminLogin(String phoneNumber,String password) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME_USERS, new String[] {
                            USER_KEY_ID}, USER_KEY_PH_NO + "=?" + " AND " + USER_KEY_PASSWORD + "=?" + " AND " + USER_KEY_TYPE + "=?",
                    new String[] {phoneNumber, password, "ADMIN"}, null, null,null,null);

            if(cursor.moveToFirst()) {
                return cursor.getInt(0);
            } else {
                return -1;
            }
        }

        //USER LOGIN
        int userLogin(String phoneNumber,String password) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME_USERS, new String[] {
                            USER_KEY_ID}, USER_KEY_PH_NO + "=?" + " AND " + USER_KEY_PASSWORD + "=?" + " AND " + USER_KEY_TYPE + "=?",
                    new String[] {phoneNumber, password, "USER"}, null, null,null,null);

            if(cursor.moveToFirst()) {
                return cursor.getInt(0);
            } else {
                return -1;
            }
        }

        //CART OPERATIONS
        void addToCart(int productId, int userId) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(CART_KEY_PRODUCT_ID , productId);
            values.put(CART_KEY_USER_ID , userId);
            values.put(CART_KEY_QTY , 1);

            db.insert(TABLE_NAME_CARTS, null, values);
            db.close();
        }
        int getCartItemsCount(int userId) {
            String selectQuery = "SELECT * FROM " + TABLE_NAME_CARTS + " WHERE " + CART_KEY_USER_ID + " = " + userId;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            return cursor.getCount();
        }


        public ArrayList<ModelProduct> getAllProductForUser(int userId, int categoryId) {
            ArrayList<ModelProduct> recordList = new ArrayList<>();
            String selectQuery = "SELECT * FROM " + TABLE_NAME_PRODUCTS + " LEFT JOIN " + TABLE_NAME_CARTS + " AS A ON "
                    + TABLE_NAME_PRODUCTS + "." + PRODUCT_KEY_ID + " IN (SELECT " + CART_KEY_PRODUCT_ID + " FROM " + TABLE_NAME_CARTS
                    + " AS B WHERE " + CART_KEY_USER_ID + " = " + userId + " AND A." + CART_KEY_PRODUCT_ID + " = B." + CART_KEY_PRODUCT_ID + ")"
                    + " WHERE " + TABLE_NAME_PRODUCTS + "." + PRODUCT_KEY_CATEGORY + " = " + categoryId;

            Log.d("CHCHCH", "getAllProductForUser: " + selectQuery);

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if(cursor.moveToFirst()) {
                do {
                    ModelProduct record = new ModelProduct();
                    record.setId(Integer.parseInt(cursor.getString(0)));
                    record.setName(cursor.getString(1));
                    record.setStockLeft(cursor.getString(2));
                    record.setCategory(cursor.getString(3));
                    record.setIsAddedToCart(cursor.getString(4) != null);

                    recordList.add(record);
                } while(cursor.moveToNext());
            }

            return recordList;
        }

        public ArrayList<ModelProduct> getAllCartItems(int userId) {
            ArrayList<ModelProduct> recordList = new ArrayList<>();
            String selectQuery = "SELECT * FROM " + TABLE_NAME_PRODUCTS + " JOIN " + TABLE_NAME_CARTS + " AS A ON "
                    + TABLE_NAME_PRODUCTS + "." + PRODUCT_KEY_ID + " IN (SELECT " + CART_KEY_PRODUCT_ID + " FROM " + TABLE_NAME_CARTS
                    + " AS B WHERE " + CART_KEY_USER_ID + " = " + userId + " AND A." + CART_KEY_PRODUCT_ID + " = B." + CART_KEY_PRODUCT_ID + ")";


            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);


            if(cursor.moveToFirst()) {
                do {
                    ModelProduct record = new ModelProduct();
                    record.setId(Integer.parseInt(cursor.getString(0)));
                    record.setName(cursor.getString(1));
                    record.setStockLeft(cursor.getString(2));
                    record.setCategory(cursor.getString(3));
                    record.setCartId(cursor.getInt(4));
                    record.setQuantity(cursor.getInt(5));

                    recordList.add(record);
                } while(cursor.moveToNext());
            }

            return recordList;
        }

        public ArrayList<ModelProduct> getAllAdminOrders() {
            ArrayList<ModelProduct> recordList = new ArrayList<>();
            String selectQuery = "SELECT * FROM " + TABLE_NAME_PRODUCTS + "," + TABLE_NAME_ORDERS + "," + TABLE_NAME_USERS + " WHERE "
                    + TABLE_NAME_PRODUCTS + "." + PRODUCT_KEY_ID + " = " + TABLE_NAME_ORDERS + "." + ORDER_KEY_PRODUCT_ID + " AND "
                    + TABLE_NAME_ORDERS + "." + ORDER_KEY_USER_ID + " = " + TABLE_NAME_USERS + "." + USER_KEY_ID + " ORDER BY "
                    + TABLE_NAME_ORDERS + "." + ORDER_KEY_ID + " DESC";

            Log.d("LOGGG", "getAllAdminOrders: " + selectQuery);

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);


            if(cursor.moveToFirst()) {
                do {
                    ModelProduct record = new ModelProduct();
                    record.setId(Integer.parseInt(cursor.getString(0)));
                    record.setName(cursor.getString(1));
                    record.setStockLeft(cursor.getString(2));
                    record.setCategory(cursor.getString(3));
                    record.setOrderId(cursor.getInt(4));
                    record.setQuantity(cursor.getInt(5));
                    record.setStatus(cursor.getString(6));
                    record.setCreatedAt(cursor.getString(7));
                    record.setUserName(cursor.getString(11));

                    recordList.add(record);
                } while(cursor.moveToNext());
            }

            return recordList;
        }

        public ArrayList<ModelProduct> getAllUserOrders(int userId) {
            ArrayList<ModelProduct> recordList = new ArrayList<>();
            String selectQuery = "SELECT * FROM " + TABLE_NAME_PRODUCTS + "," + TABLE_NAME_ORDERS + " WHERE "
                    + TABLE_NAME_PRODUCTS + "." + PRODUCT_KEY_ID + " = " + TABLE_NAME_ORDERS + "." + ORDER_KEY_PRODUCT_ID + " AND "
                    + TABLE_NAME_ORDERS + "." + ORDER_KEY_USER_ID + " = " + userId +  " ORDER BY "
                    + TABLE_NAME_ORDERS + "." + ORDER_KEY_ID + " DESC";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);


            if(cursor.moveToFirst()) {
                do {
                    ModelProduct record = new ModelProduct();
                    record.setId(Integer.parseInt(cursor.getString(0)));
                    record.setName(cursor.getString(1));
                    record.setStockLeft(cursor.getString(2));
                    record.setCategory(cursor.getString(3));
                    record.setOrderId(cursor.getInt(4));
                    record.setQuantity(cursor.getInt(5));
                    record.setStatus(cursor.getString(6));
                    record.setCreatedAt(cursor.getString(7)); ;

                    recordList.add(record);
                } while(cursor.moveToNext());
            }

            return recordList;
        }

        public ArrayList<Integer> getUserDashboard(int userId) {
            ArrayList<Integer> arr = new ArrayList<Integer>();
            String selectQueryP = "SELECT * FROM " + TABLE_NAME_ORDERS + " WHERE " + ORDER_KEY_STATUS + " = 'PENDING' AND " + ORDER_KEY_USER_ID + " = " + userId;
            String selectQueryA = "SELECT * FROM " + TABLE_NAME_ORDERS + " WHERE " + ORDER_KEY_STATUS + " = 'APPROVED' AND " + ORDER_KEY_USER_ID + " = " + userId;
            String selectQueryR = "SELECT * FROM " + TABLE_NAME_ORDERS + " WHERE " + ORDER_KEY_STATUS + " = 'REJECTED' AND " + ORDER_KEY_USER_ID + " = " + userId;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursorP = db.rawQuery(selectQueryP, null);
            Cursor cursorA = db.rawQuery(selectQueryA, null);
            Cursor cursorR = db.rawQuery(selectQueryR, null);

            arr.add(cursorP.getCount());
            arr.add(cursorA.getCount());
            arr.add(cursorR.getCount());

            return arr;
        }

        public ArrayList<Integer> getAdminDashboard() {
            ArrayList<Integer> arr = new ArrayList<Integer>();
            String selectQueryP = "SELECT * FROM " + TABLE_NAME_ORDERS + " WHERE " + ORDER_KEY_STATUS + " = 'PENDING'";
            String selectQueryA = "SELECT * FROM " + TABLE_NAME_ORDERS + " WHERE " + ORDER_KEY_STATUS + " = 'APPROVED'";
            String selectQueryR = "SELECT * FROM " + TABLE_NAME_ORDERS + " WHERE " + ORDER_KEY_STATUS + " = 'REJECTED'";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursorP = db.rawQuery(selectQueryP, null);
            Cursor cursorA = db.rawQuery(selectQueryA, null);
            Cursor cursorR = db.rawQuery(selectQueryR, null);

            arr.add(cursorP.getCount());
            arr.add(cursorA.getCount());
            arr.add(cursorR.getCount());

            return arr;
        }

        public int updateQuantity(int cartId, int quantity) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(CART_KEY_QTY, quantity);

            return db.update(TABLE_NAME_CARTS, values, CART_KEY_ID + "=?", new String[] { String.valueOf(cartId)});
        }

        public void deleteCartItem(int cartId) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME_CARTS, CART_KEY_ID+ " = ?", new String[] {String.valueOf(cartId)});

            db.close();
        }
        private String getDateTime() {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = new Date();
            return dateFormat.format(date);
        }

        public void placeOrder(int userId) {
            String selectQuery = "SELECT * FROM " + TABLE_NAME_PRODUCTS + " JOIN " + TABLE_NAME_CARTS + " AS A ON "
                    + TABLE_NAME_PRODUCTS + "." + PRODUCT_KEY_ID + " IN (SELECT " + CART_KEY_PRODUCT_ID + " FROM " + TABLE_NAME_CARTS
                    + " AS B WHERE " + CART_KEY_USER_ID + " = " + userId + " AND A." + CART_KEY_PRODUCT_ID + " = B." + CART_KEY_PRODUCT_ID + ")";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if(cursor.moveToFirst()) {
                do {
                    ContentValues values = new ContentValues();
                    values.put(ORDER_KEY_QTY , cursor.getInt(5));
                    values.put(ORDER_KEY_USER_ID , userId);
                    values.put(ORDER_KEY_PRODUCT_ID , cursor.getInt(0));
                    values.put(ORDER_KEY_STATUS, "PENDING");
                    values.put(ORDER_KEY_CREATED_AT, getDateTime());
                    db.insert(TABLE_NAME_ORDERS, null, values);


                } while(cursor.moveToNext());
            }
            db.delete(TABLE_NAME_CARTS, CART_KEY_USER_ID+ " = ?", new String[] {String.valueOf(userId)});
        }

    public int updateOrderStatus(int orderId, int productId, String status, int stockLeft) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ORDER_KEY_STATUS, status);
        db.update(TABLE_NAME_ORDERS, values, ORDER_KEY_ID + "=?", new String[] { String.valueOf(orderId)});


        if(status.equals("APPROVED")) {
            values = new ContentValues();
            values.put(PRODUCT_KEY_STOCK_LEFT, stockLeft);
            db.update(TABLE_NAME_PRODUCTS, values, PRODUCT_KEY_ID + "=?", new String[] { String.valueOf(productId)});
        }

        return 1;
    }
}
