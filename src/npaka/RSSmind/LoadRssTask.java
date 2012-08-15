package npaka.RSSmind;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.widget.TextView;
import java.io.InputStream;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;

public class LoadRssTask extends AsyncTask<Object, Integer, String> {

	private String rssUrl;
	private String blogTitle;
	private RssAdapter rssAdapter;
	private TextView titleView;
	private ProgressDialog dialog;
	private Context context;

	public LoadRssTask(Context context, String rssUrl, RssAdapter rssAdapter, TextView titleView) {
		this.context = context;
		this.rssUrl = rssUrl;
		this.rssAdapter = rssAdapter;
		this.titleView = titleView;
	}

	// 以下、オーバーライド ----------------------------------------------

	@Override
	protected void onPreExecute(){
		dialog = new ProgressDialog(context);
//		dialog.setMessage(rssUrl);
		dialog.setTitle("読み込み中");
		dialog.show();
	}

	@Override
	protected String doInBackground(Object... args) {
		_loadRss();
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... args){

	}

	@Override
	protected void onPostExecute(String result){
		titleView.setText(blogTitle);
		rssAdapter.notifyDataSetChanged();
		dialog.dismiss();
	}

	@Override
	protected void onCancelled(){

	}

	// 以下、プライベート ------------------------------------------------

	private void _loadRss(){
		HttpClient hc = new DefaultHttpClient();
		rssUrl = rssUrl.replace(" ","+");
		rssUrl = rssUrl.replace("　","+");
		HttpGet hg = new HttpGet(rssUrl);
		try{
			HttpResponse hr = hc.execute(hg);
			int status = hr.getStatusLine().getStatusCode();
			if(status != HttpStatus.SC_OK){
				throw new Exception("Load ERROR");
			}
			InputStream is = hr.getEntity().getContent();
			_parseXml(is);
		}catch (Exception e){
			Log.e(getClass().toString(), e.toString());
		}
	}

	private void _parseXml(InputStream is){
		XmlPullParser parser = Xml.newPullParser();	//パーサーを作成する。
		Boolean inItem = false;
		String tag = null;
		Item item = null;

		try{
			//パーサーにinputStraemをセットする。これはURLを読み込んでいるStream
			parser.setInput(is, null);
			//読み込みの進捗をここから取得ができる。（値はSTARTなどが存在する。）
			int eventType = parser.getEventType();
			while(eventType != XmlPullParser.END_DOCUMENT){
				tag = parser.getName();
				if(eventType == XmlPullParser.START_TAG){ // タグ開始のとき
					if(tag.equals("item")){
						inItem = true;
						item = new Item();
					}
					if(inItem){ // アイテムタグの中のとき
						if(tag.equals("title")){
							item.setTitle(parser.nextText());
						}
						if(tag.equals("description")){
							item.setDesc(parser.nextText());
						}
						if(tag.equals("pubDate")) {
							item.setDay(parser.nextText());
						}
						if(tag.equals("link")) {
							item.setLink(parser.nextText());
						}
					}else{
						if(tag.equals("title")){
							blogTitle = parser.nextText();
						}
					}
				}
				if(eventType == XmlPullParser.END_TAG){ //タグ終了
					if(tag.equals("item")){
						inItem = false;
						rssAdapter.addItem(item);
					}
				}
				eventType = parser.next();
			}
		}catch(Exception e){
			Log.e(getClass().toString(), e.toString());
		}
	}
}