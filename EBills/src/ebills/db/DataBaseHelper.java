package ebills.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import ebills.models.Bill_Pic;
import ebills.models.Item;
import ebills.models.Seller;
import ebills.models.Tags;

public class DataBaseHelper extends SQLiteOpenHelper {

	//The Android's default system path of your application database.
	@SuppressLint("SdCardPath")
	private static String DB_PATH = "/data/data/com.apathon.ebills/databases/";

	private static String DB_NAME = "store.db";

	private SQLiteDatabase myDataBase;

	private final Context myContext;

	public static final String Table_android_metadata = "android_metadata";

	public static final String Table_bill_pic = "bill_pic";
	public static final String Table_item = "item";
	public static final String Table_seller = "seller";
	public static final String Table_tags = "tags";

	public static final String Column_tag_name = "tag_name";

	public static final String Column__id = "_id";
	public static final String Column_image_path = "image_path";
	public static final String Column_image_name = "image_name";
	public static final String Column_image_date = "image_date";
	public static final String Column_image_seller_id = "image_seller_id";

	public static final String Column_item_name = "item_name";
	public static final String Column_item_desc = "item_desc";
	public static final String Column_item_amount = "item_amount";
	public static final String Column_order_no = "order_no";
	public static final String Column_invoice_no = "invoice_no";
	public static final String Column_item_tag = "item_tag";
	public static final String Column_warranty = "warranty";
	public static final String Column_item_seller_id = "item_seller_id";
	public static final String Column_bill_pic_id = "bill_pic_id";

	public static final String Column_seller_name = "seller_name";
	public static final String Column_seller_address = "seller_address";
	public static final String Column_seller_city = "seller_city";
	public static final String Column_seller_state = "seller_state";
	public static final String Column_seller_pin = "seller_pin";
	public static final String Column_seller_tag = "seller_tag";

	/**
	 * Constructor
	 * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
	 *
	 * @param context
	 */
	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, 12);
		this.myContext = context;
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own database.
	 */
	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			//do nothing - database already exist
		} else {

			//By calling this method and empty database will be created into the default system path
			//of your application so we are gonna be able to overwrite that database with our database.
			this.getReadableDatabase();

			try {
				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each time you open the application.
	 *
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {

			//database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created empty database in the
	 * system folder, from where it can be accessed and handled.
	 * This is done by transfering bytestream.
	 */
	private void copyDataBase() throws IOException {

		//Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		//Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		//Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {

		//Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		/* String query="CREATE TABLE \"bill_pic\" (\"_id\" INTEGER PRIMARY KEY  NOT NULL ,\"image_path\" TEXT,\"image_name\" TEXT,\"image_date\" TEXT DEFAULT (null) ,\"image_seller_id\" INTEGER)";
		 db.execSQL(query);
		 String query2 = "CREATE TABLE \"item\" (\"_id\" INTEGER PRIMARY KEY  NOT NULL ,\"item_name\" TEXT,\"item_desc\" TEXT DEFAULT (null) ,\"item_amount\" TEXT DEFAULT (null) ,\"order_no\" TEXT DEFAULT (null) ,\"invoice_no\" TEXT DEFAULT (null) ,\"item_tag\" TEXT DEFAULT (null) ,\"warranty\" INTEGER DEFAULT (null) ,\"item_seller_id\" INTEGER DEFAULT (null) ,\"bill_pic_id\" INTEGER DEFAULT (null) )";
		 db.execSQL(query2);
		 String query3 = "CREATE TABLE \"seller\" (\"seller_name\" TEXT DEFAULT (null) ,\"seller_address\" TEXT,\"seller_city\" TEXT,\"seller_state\" TEXT,\"seller_pin\" TEXT,\"seller_tin\" TEXT,\"seller_tag\" TEXT,\"_id\" INTEGER PRIMARY KEY  NOT NULL )";
		 db.execSQL(query3);*/
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	@SuppressLint("NewApi")
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		super.onDowngrade(db, oldVersion, newVersion);
	}

	/**
	 *
	 * @return all tags
	 */
	public ArrayList<Tags> getAllTags() {
		String[] projection = { Column__id, Column_tag_name };
		Cursor cursor = null;
		ArrayList<Tags> sellers = new ArrayList<>();
		try {
			cursor = getReadableDatabase().query(Table_tags, projection, null, null, null, null, null);
			cursor.moveToFirst();
			do {
				Tags seller = new Tags();
				seller.setColumn__id(cursor.getString(cursor.getColumnIndex(Column__id)));
				seller.setColumn_tags_name(cursor.getString(cursor.getColumnIndex(Column_tag_name)));
				sellers.add(seller);
			} while (cursor.moveToNext());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeCursor(cursor);
		}
		return sellers;
	}

	/**
	 *
	 * @return all sellers
	 */
	public ArrayList<Seller> getAllSellers() {
		String[] projection = { Column__id, Column_seller_name, Column_seller_address, Column_seller_city, Column_seller_state, Column_seller_pin, Column_seller_tag };
		Cursor cursor = null;
		ArrayList<Seller> sellers = new ArrayList<>();
		try {
			cursor = getReadableDatabase().query(Table_seller, projection, null, null, null, null, null);
			cursor.moveToFirst();
			do {
				Seller seller = new Seller();
				seller.setColumn__id(cursor.getString(cursor.getColumnIndex(Column__id)));
				seller.setColumn_seller(cursor.getString(cursor.getColumnIndex(Column_seller_name)));
				seller.setColumn_seller_address(cursor.getString(cursor.getColumnIndex(Column_seller_address)));
				seller.setColumn_seller_city(cursor.getString(cursor.getColumnIndex(Column_seller_city)));
				seller.setColumn_seller_state(cursor.getString(cursor.getColumnIndex(Column_seller_state)));
				seller.setColumn_seller_pin(cursor.getString(cursor.getColumnIndex(Column_seller_pin)));
				seller.setColumn_seller_tag(cursor.getString(cursor.getColumnIndex(Column_seller_tag)));
				sellers.add(seller);
			} while (cursor.moveToNext());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeCursor(cursor);
		}
		return sellers;
	}

	/**
	 *
	 * @return all Items
	 */
	public ArrayList<Item> getAllItems() {
		Cursor cursor = null;
		ArrayList<Item> items = new ArrayList<>();
		try {
			cursor = getReadableDatabase().query(Table_item, null, null, null, null, null, null);
			cursor.moveToFirst();
			do {
				Item item = new Item();
				item.setColumn__id(cursor.getString(cursor.getColumnIndex(Column__id)));
				item.setColumn_item_name(cursor.getString(cursor.getColumnIndex(Column_item_name)));
				item.setColumn_item_amount(cursor.getString(cursor.getColumnIndex(Column_item_amount)));
				item.setColumn_order_no(cursor.getString(cursor.getColumnIndex(Column_order_no)));
				item.setColumn_invoice_no(cursor.getString(cursor.getColumnIndex(Column_invoice_no)));
				item.setColumn_item_tag(cursor.getString(cursor.getColumnIndex(Column_item_tag)));
				item.setColumn_warranty(cursor.getString(cursor.getColumnIndex(Column_warranty)));
				item.setColumn_item_seller_id(cursor.getString(cursor.getColumnIndex(Column_item_seller_id)));
				item.setColumn_bill_pic_id(cursor.getString(cursor.getColumnIndex(Column_bill_pic_id)));
				items.add(item);
			} while (cursor.moveToNext());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeCursor(cursor);
		}
		return items;
	}

	/**
	 *
	 * @return all BillsPics
	 */
	public ArrayList<Bill_Pic> getAllBill_Pics() {
		String[] projection = { Column__id, Column_image_path, Column_image_name, Column_image_date };
		Cursor cursor = null;
		ArrayList<Bill_Pic> quotes = new ArrayList<>();
		try {
			cursor = getReadableDatabase().query(Table_bill_pic, projection, null, null, null, null, null);
			cursor.moveToFirst();
			do {
				Bill_Pic quote = new Bill_Pic();
				quote.setColumn__id(cursor.getString(cursor.getColumnIndex(Column__id)));
				quote.setColumn_image_path(cursor.getString(cursor.getColumnIndex(Column_image_path)));
				quote.setColumn_image_name(cursor.getString(cursor.getColumnIndex(Column_image_name)));
				quote.setColumn_image_date(cursor.getString(cursor.getColumnIndex(Column_image_date)));
				quotes.add(quote);
			} while (cursor.moveToNext());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeCursor(cursor);
		}
		return quotes;
	}

	/**
	 *
	 * @param item is the prepared object of Seller class
	 * @return -1 for error , row count on success
	 */
	public long insertTags(Tags item) {
		SQLiteDatabase db = getWritableDatabase();
		long i = -1;
		try {
			ContentValues cv = new ContentValues();
			cv.put(Column_tag_name, item.getColumn_tags_name());
			i = db.insert(Table_tags, null, cv);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null)
				db.close();
		}
		return i;
	}

	public long insertBill_Pic(Bill_Pic item) {
		SQLiteDatabase db = getWritableDatabase();
		long i = -1;
		try {
			ContentValues cv = new ContentValues();
			cv.put(Column_image_name, item.getColumn_image_name());
			cv.put(Column_image_path, item.getColumn_image_path());
			cv.put(Column_image_seller_id, item.getColumn_image_seller_id());
			i = db.insert(Table_bill_pic, null, cv);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null)
				db.close();
		}
		return i;
	}

	/**
	 *
	 * @param item is the prepared object of Seller class
	 * @return -1 for error , row count on success
	 */
	public long insertItem(Item item) {
		SQLiteDatabase db = getWritableDatabase();
		long i = -1;
		try {
			ContentValues cv = new ContentValues();
			cv.put(Column_item_name, item.getColumn_item_name());
			cv.put(Column_item_amount, item.getColumn_item_amount());
			cv.put(Column_order_no, item.getColumn_order_no());
			cv.put(Column_invoice_no, item.getColumn_invoice_no());
			cv.put(Column_item_tag, item.getColumn_item_tag());
			cv.put(Column_warranty, item.getColumn_warranty());
			cv.put(Column_item_seller_id, item.getColumn_item_seller_id());
			cv.put(Column_bill_pic_id, item.getColumn_bill_pic_id());
			i = db.insert(Table_item, null, cv);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null)
				db.close();
		}
		return i;
	}

	/**
	 *
	 * @param seller is the prepared object of Seller class
	 * @return -1 for error , row count on success
	 */
	public long insertSeller(Seller seller) {
		SQLiteDatabase db = getWritableDatabase();
		long i = -1;
		try {
			ContentValues cv = new ContentValues();
			cv.put(Column_seller_name, seller.getColumn_seller());
			cv.put(Column_seller_address, seller.getColumn_seller_address());
			cv.put(Column_seller_city, seller.getColumn_seller_city());
			cv.put(Column_seller_state, seller.getColumn_seller_state());
			cv.put(Column_seller_pin, seller.getColumn_seller_pin());
			cv.put(Column_seller_tag, seller.getColumn_seller_tag());
			i = db.insert(Table_seller, null, cv);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null)
				db.close();
		}
		return i;
	}

	public ArrayList<Tags> searchTag(String tagValue) {
		String[] projection = { Column__id, Column_tag_name };

		Cursor cursor = null;
		ArrayList<Tags> tagsList = new ArrayList<>();
		Tags tags = null;
		try {
			cursor = getReadableDatabase().query(Table_tags, projection, Column_tag_name + "=?", new String[] { tagValue }, null, null, null);
			cursor.moveToFirst();
			do {
				tags = new Tags();
				tags.setColumn__id(cursor.getString(cursor.getColumnIndex(Column__id)));
				tags.setColumn_tags_name(cursor.getString(cursor.getColumnIndex(Column_tag_name)));
				tagsList.add(tags);
			} while (cursor.moveToNext());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeCursor(cursor);
		}
		return tagsList;
	}

	/**
	 *
	 * @param column_value is the value to search
	 * @param column_KEY is the key of the column in databsae
	 * @return List of Sellers depending on params
	 */
	public ArrayList<Seller> searchSeller(String column_value, String column_KEY) {
		String[] projection = { Column__id, Column_seller_name, Column_seller_address, Column_seller_city, Column_seller_state, Column_seller_pin, Column_seller_tag };

		Cursor cursor = null;
		ArrayList<Seller> sellers = new ArrayList<>();
		Seller seller = null;
		try {
			cursor = getReadableDatabase().query(Table_seller, projection, column_KEY + "=?", new String[] { column_value }, null, null, null);
			cursor.moveToFirst();
			do {
				seller = new Seller();
				seller.setColumn__id(cursor.getString(cursor.getColumnIndex(Column__id)));
				seller.setColumn_seller(cursor.getString(cursor.getColumnIndex(Column_seller_name)));
				seller.setColumn_seller_address(cursor.getString(cursor.getColumnIndex(Column_seller_address)));
				seller.setColumn_seller_city(cursor.getString(cursor.getColumnIndex(Column_seller_city)));
				seller.setColumn_seller_state(cursor.getString(cursor.getColumnIndex(Column_seller_state)));
				seller.setColumn_seller_pin(cursor.getString(cursor.getColumnIndex(Column_seller_pin)));
				seller.setColumn_seller_tag(cursor.getString(cursor.getColumnIndex(Column_seller_tag)));
				sellers.add(seller);
			} while (cursor.moveToNext());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeCursor(cursor);
		}
		return sellers;
	}

	/**
	 *
	 * @param column_value is the value to search
	 * @param column_KEY is the key of the column in databsae
	 * @return List of Sellers depending on params
	 */
	public ArrayList<Item> searchItem(String column_value, String column_KEY) {
		String[] projection = { Column__id, Column_item_name, Column_item_desc, Column_item_amount, Column_order_no, Column_invoice_no, Column_item_tag, Column_warranty,
				Column_item_seller_id };
		Cursor cursor = null;
		ArrayList<Item> items = new ArrayList<>();
		try {
			cursor = getReadableDatabase().query(Table_item, projection, Column_item_tag + " LIKE ? OR " + Column_item_name + " LIKE ? ",
					new String[] { "%" + column_value + "%", "%" + column_value + "%" }, null, null, null);
			cursor.moveToFirst();
			do {
				Item item = new Item();
				item.setColumn__id(cursor.getString(cursor.getColumnIndex(Column__id)));
				item.setColumn_item_name(cursor.getString(cursor.getColumnIndex(Column_item_name)));
				item.setColumn_item_amount(cursor.getString(cursor.getColumnIndex(Column_item_amount)));
				item.setColumn_order_no(cursor.getString(cursor.getColumnIndex(Column_order_no)));
				item.setColumn_invoice_no(cursor.getString(cursor.getColumnIndex(Column_invoice_no)));
				item.setColumn_item_tag(cursor.getString(cursor.getColumnIndex(Column_item_tag)));
				item.setColumn_warranty(cursor.getString(cursor.getColumnIndex(Column_warranty)));
				item.setColumn_item_seller_id(cursor.getString(cursor.getColumnIndex(Column_item_seller_id)));
				items.add(item);
			} while (cursor.moveToNext());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeCursor(cursor);
		}
		return items;
	}

	public ArrayList<Bill_Pic> searchBill_Pic(String column_value) {
		String[] projection = { Column__id, Column_image_path, Column_image_name, Column_image_date };
		Cursor cursor = null;
		ArrayList<Bill_Pic> items = new ArrayList<>();
		try {
			cursor = getReadableDatabase().query(Table_bill_pic, projection, Column__id + "=?", new String[] { column_value }, null, null, null);
			cursor.moveToFirst();
			do {
				Bill_Pic item = new Bill_Pic();
				item.setColumn__id(cursor.getString(cursor.getColumnIndex(Column__id)));
				item.setColumn_image_path(cursor.getString(cursor.getColumnIndex(Column_image_path)));
				items.add(item);
			} while (cursor.moveToNext());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeCursor(cursor);
		}
		return items;
	}

	public Cursor search(String query) {
		return searchItem(new String[] { Column_tag_name }, query);
	}

	public Cursor searchItem(String[] key, String query) {
		String[] projection = { Column__id, Column_tag_name };
		return getReadableDatabase().query(Table_tags, projection, "tag_name like ?", new String[] { "%" + query + "%" }, null, null, null);
	}

	/**
	 *
	 * @param _id unique id of seller
	 * @param seller updated object of seller
	 * @return
	 */
	public long updateSeller(String _id, Seller seller) {
		SQLiteDatabase db = getWritableDatabase();
		long i = -1;
		try {
			ContentValues cv = new ContentValues();
			cv.put(Column_seller_name, seller.getColumn_seller());
			cv.put(Column_seller_address, seller.getColumn_seller_address());
			cv.put(Column_seller_city, seller.getColumn_seller_city());
			cv.put(Column_seller_state, seller.getColumn_seller_state());
			cv.put(Column_seller_pin, seller.getColumn_seller_pin());
			cv.put(Column_seller_tag, seller.getColumn_seller_tag());
			i = db.update(Table_seller, cv, Column__id + "=?", new String[] { _id });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null)
				db.close();
		}
		return i;
	}

	/**
	 *
	 * @param _id unique id of item
	 * @param item the updated object of Item class
	 * @return
	 */
	public long updateItem(String _id, Item item) {
		SQLiteDatabase db = getWritableDatabase();
		long i = -1;
		try {
			ContentValues cv = new ContentValues();
			cv.put(Column_item_name, item.getColumn_item_name());
			cv.put(Column_item_amount, item.getColumn_item_amount());
			cv.put(Column_order_no, item.getColumn_order_no());
			cv.put(Column_invoice_no, item.getColumn_order_no());
			cv.put(Column_item_tag, item.getColumn_order_no());
			cv.put(Column_warranty, item.getColumn_order_no());
			cv.put(Column_item_seller_id, item.getColumn_order_no());
			i = db.update(Table_seller, cv, Column__id + "=?", new String[] { _id });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null)
				db.close();
		}
		return i;
	}

	private static final void closeCursor(Cursor cursor) {
		try {
			if (cursor != null && !cursor.isClosed())
				cursor.close();
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

}