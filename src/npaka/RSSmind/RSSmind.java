package npaka.RSSmind;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class RSSmind extends Activity{
	//public static String RSS_URL = "http://news.google.com/news?hl=ja&ned=us&ie=UTF-8&oe=UTF-8&num=30&output=rss&q=";
//	public static String RSS_URL = "http://news.google.com/news?hl=ja&ned=us&ie=UTF-8&oe=UTF-8&num=30&output=rss&scoring=n&q=";	// 日付順
	public static String RSS_URL = "http://news.google.com/news?hl=ja&ned=us&num=30&output=rss&q=";	// 注目順
	public String keyword = null;	// 検索ワード

	private LoadRssTask loadRssTask;
	private ListView rssList;
	private RssAdapter rssAdapter;
	private View mainLayout;
	private ButtonManager buttonManager;
	private RssModel rssModel;


	/**
	 *
	 * @param rssUrl
	 */
	public void loadRss(String rssUrl){
		rssAdapter.clear();	// リストを削除
		TextView titleView = (TextView) findViewById(R.id.blogTitle);
		loadRssTask = new LoadRssTask(this, rssUrl, rssAdapter, titleView);
		loadRssTask.execute();
	}

	public RssModel getModel(){
		return rssModel;
	}

	public void update(){
		buttonManager.update();
	}

	public void msg(String title, String msg){
		new AlertDialog.Builder(this)
		.setTitle(title)
		.setMessage(msg)
		.setPositiveButton("閉じる", null)
		.show();
	}

	// 以下、オーバーライド -----------------------------------------------

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_setLayoutInstance();
		_setModel();
		_setRssList();
		loadRss(RSS_URL);
		buttonManager = new ButtonManager(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	//メニューが生成される際に起動される。
	//この中でメニューのアイテムを追加したりする。
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
		//メニューインフレーターを取得
		MenuInflater inflater = getMenuInflater();
		//xmlのリソースファイルを使用してメニューにアイテムを追加
		inflater.inflate(R.menu.mainmenu, menu);
		//できたらtrueを返す
		return true;
	}


	//メニューのアイテムが選択された際に起動される。
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_dialog1:
			ButtonManager.addDialog.show();
			break;
		case R.id.menu_dialog2:
			ButtonManager.deleteDialog.show();
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	// 以下、プライベート----------------------------------------------

	// 一覧画面のレイアウト と 詳細画面のレイアウト をインスタンスにしてとっておく
	private void _setLayoutInstance(){
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//	detailLayout = inflater.inflate(R.layout.detail, null);
		mainLayout = inflater.inflate(R.layout.main, null);
		setContentView(mainLayout);
	}

	private void _setModel(){
		rssModel = new RssModel(this);
	}

	private void _setRssList(){
		rssList = (ListView) findViewById(R.id.rssList);
		rssAdapter = new RssAdapter(this);
		rssList.setAdapter(rssAdapter);

		// クリックイベント
		rssList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ListView listView = (ListView) parent;
				// クリックされたアイテムを取得します
				Item item = (Item) listView.getItemAtPosition(position);
				_onItemClick(item);
			}
		});
	}

	// RSSの中身関連
	private void _onItemClick(Item item) {
		Intent intent = new Intent(this, RSSMindDetail.class);
		intent.putExtra("TITLE", item.getTitle());
		intent.putExtra("DESCRIPTION", item.getDesc());
		intent.putExtra("LINK", item.getLink());
		startActivity(intent);
	}
}