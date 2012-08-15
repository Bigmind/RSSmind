package npaka.RSSmind;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.AsyncTask;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RssListInsert extends AsyncTask<Object, Integer ,Object>  {

	private SQLiteDatabase db;
	private RSSmind rssReader;

	private String title;
	private String pubDate;

	public RssListInsert(String title, String day) {
		super();
		this.title = title;
		this.pubDate = day;
		_setDb();
		_insertRSSlist();
	}

	// 以下、オーバーライド -------------------------------------------
	@Override
	protected Object doInBackground(Object... arg0) {
		return null;
	}

	@Override
	protected void onPostExecute(Object result){
//		_insertRSSlist();
	}

	@Override
	protected void onProgressUpdate(Integer... args){
	}

	@Override
	protected void onCancelled(){

	}

	////////////////////SQLへリストをinsertする途中

	// 以下、プライベート ------------------------------------------------

	// データベースをつなぐ
	private void _setDb(){
		RssDbHandler rssDbHandler = new RssDbHandler(rssReader);
		try{
//			db = rssDbHandler.getWritableDatabase();
//			db.beginTransaction();
		}catch(SQLException e){
			_dbError(e);
		}
	}

	// エラー
	private void _dbError(SQLException e){
		rssReader.msg("データベースエラー", e.toString());
	}

	// キーワード登録
	private void _insertRSSlist(){
		Date nowDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy'-'MM'-'dd'-'HH'-'mm'-'ss");
		String created = sdf.format(nowDate);
		int del = 1;
		ContentValues cv = new ContentValues();
		cv.put("title", title);
		cv.put("pubDate",pubDate);
		cv.put("del", del);
	}
}