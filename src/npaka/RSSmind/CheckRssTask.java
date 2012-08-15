package npaka.RSSmind;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.xmlpull.v1.XmlPullParser;
import android.os.AsyncTask;
import android.util.Xml;
import android.app.ProgressDialog;
import android.content.ContentValues;

public class CheckRssTask extends AsyncTask<Object, Integer ,Object>  {

	private RSSmind rssRead;
	private ProgressDialog dialog;
	private String keyword;
//	private String blogTitle;
	private String erMsg = null;
	private RssModel rssModel;
	private Boolean isRss = false;

	public CheckRssTask(RSSmind rssReader, String keyword) {
		super();
		this.rssRead = rssReader;
		this.keyword = keyword;
		this.rssModel = rssReader.getModel();

	}

	// 以下、オーバーライド -------------------------------------------
	@Override
	protected Object doInBackground(Object... arg0) {
		return null;
	}

	@Override
	protected void onPostExecute(Object result){
		_insertUrl();
		rssRead.update();
		dialog.dismiss();
	}


	@Override
	protected void onPreExecute(){
		dialog = new ProgressDialog(rssRead);
		dialog.setTitle("検索中");
		dialog.setMessage(keyword);
		dialog.show();
	}

	protected Object doInBackground1(Object... arg0) {
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... args){

	}

	@Override
	protected void onCancelled(){

	}

	// 以下、プライベート ------------------------------------------------

	// キーワード登録
	private void _insertUrl(){
		Date nowDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy'-'MM'-'dd'-'HH'-'mm'-'ss");
		String created = sdf.format(nowDate);
	//	String title = blogTitle;
		int del = 1;
		ContentValues cv = new ContentValues();
		cv.put("keyword", keyword);
		cv.put("del", del);
		cv.put("created", created);

		if(rssModel.insert(cv)){
			rssRead.msg("登録完了", keyword + " \n登録しました");
		}
	}
}