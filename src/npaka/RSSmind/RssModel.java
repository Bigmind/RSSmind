package npaka.RSSmind;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RssModel {
	private SQLiteDatabase db;
	private RSSmind rssReader;
	private String[] list;

	RssModel(RSSmind rssReader){
		this.rssReader = rssReader;
		_setDb();
		_upList();
	}

	public String[] getList(){
		_upList();
		return list;
	}

	/**
	 * タイトルからRSSのURLを取得
	 * @param String title
	 * @return String
	 */
	public String getRss(String keyword){
		String sql = "SELECT keyword FROM keyword_tbl WHERE keyword = '" + keyword + "' LIMIT 1";
		try{
			Cursor c = db.rawQuery(sql, null);
			c.moveToFirst();
			String url = c.getString(0);
			return url;
		}catch(SQLException e){
			_dbError(e);
			return null;
		}
	}

	public Boolean insert(ContentValues cv){
		try{
			db.insert("keyword_tbl", null, cv);
			Log.d(getClass().toString(), "登録完了");
			return true;
		}catch(SQLException e){
			_dbError(e);
		}
		return false;
	}

	/**
	 * 削除
	 * @param String[] deleteList
	 * @return Boolean
	 */
	public Boolean delete(Boolean[] deleteList){
		int max = deleteList.length;
		try{
			for(int i=0;i<max;i++){
				String keyword = list[i];
				if(deleteList[i]){
					db.delete("keyword_tbl", "keyword = ?", new String[]{keyword});
				}
			}
			return true;
		}catch(SQLException e){
			_dbError(e);
			return false;
		}
	}

	/**
	 * 登録済みかチェック
	 * @param String url
	 * @return
	 */
	public Boolean isExists(String keyword){
		String sql = "SELECT * FROM keyword_tbl WHERE keyword = '"+ keyword + "'";
		try{
			Cursor c = db.rawQuery(sql, null);
			if(c.getCount() != 0){
				return true;
			}
		}catch(SQLException e){
			_dbError(e);
		}
		return false;
	}

	// 以下、プライベート ---------------------------------------

	// データベースをつなぐ
	private void _setDb(){
		RssDbHandler rssDbHandler = new RssDbHandler(rssReader);
		try{
			db = rssDbHandler.getWritableDatabase();
		}catch(SQLException e){
			_dbError(e);
		}
	}

	// RSSの一覧を更新
	private void _upList(){
		String sql = "SELECT id, keyword FROM keyword_tbl ORDER BY created DESC";
		try{
			Cursor c = db.rawQuery(sql, null);
			c.moveToFirst();
			int total = c.getCount();
			list = new String[total];
			for(int i=0;i<total;i++){
				list[i] = (c.getString(1));
				c.moveToNext();
			}
			c.close();
		}catch(SQLException e){
			_dbError(e);
		}
	}

	// エラー
	private void _dbError(SQLException e){
		rssReader.msg("データベースエラー", e.toString());
	}
}