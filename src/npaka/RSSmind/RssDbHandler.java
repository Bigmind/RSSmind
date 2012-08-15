package npaka.RSSmind;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.content.ContentValues;
import android.content.Context;

public class RssDbHandler extends SQLiteOpenHelper {

	private static String DB_NAME = "rss.db";
	private static int DB_VERSION = 1;
	//  private static String CREATE_SQL = "CREATE table rss(id INTEGER PRIMARY KEY AUTOINCREMENT, title text, keyword text, created text, del INTEGER)";
	private static String CREATE_SQL_keyword_tbl = "CREATE table keyword_tbl(id INTEGER PRIMARY KEY AUTOINCREMENT, keyword text, created text, del INTEGER)";
	private static String CREATE_SQL_rsslist_tbl = "CREATE table rsslist_tbl(id INTEGER PRIMARY KEY AUTOINCREMENT, title text, pubDate text, del INTEGER)";
	private static String CREATE_SQL_rssdescription_tbl = "CREATE table rssdescription_tbl(id INTEGER PRIMARY KEY AUTOINCREMENT, title text, description text, link text, del INTEGER)";

	public RssDbHandler(Context context){
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try{
			db.execSQL(CREATE_SQL_rsslist_tbl);
			db.execSQL(CREATE_SQL_rssdescription_tbl);
			db.execSQL(CREATE_SQL_keyword_tbl);
			Date nowDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy'-'MM'-'dd'-'HH'-'mm'-'ss");
			String created = sdf.format(nowDate);
			ContentValues cv = new ContentValues();
			cv.put("keyword", "Android");
			cv.put("created", created);
			cv.put("del", 0);
			db.insert("keyword_tbl", null, cv);


		}catch(SQLiteException e){
			Log.e("DBERROR", e.toString());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}