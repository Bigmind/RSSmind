package npaka.RSSmind;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.AlertDialog.Builder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ButtonManager {

	private RSSmind rssRead;
	private Button listButton;
	private Button findButton;

	private String[] rssList;
	private AlertDialog listDialog;
	public static AlertDialog addDialog;	// 登録ダイアログ
	public static AlertDialog findDialog;	// 検索ダイアログ
	public static AlertDialog deleteDialog;	// 削除ダイアログ
	private EditText findEdit;
	private EditText setEdit;
	private Boolean[] deleteList;
	private RssModel rssModel;

	private static String RSS_URL = "http://news.google.com/news?as_scoring=n&hl=ja&ned=us&ie=UTF-8&oe=UTF-8&num=30&output=rss&q=";
	private static String SORT = "&scoring=n";

	ButtonManager(RSSmind rssReader){
		this.rssRead = rssReader;
		this.rssModel = rssReader.getModel();
		findEdit = new EditText(rssReader);
		setEdit = new EditText(rssReader);
		_setButton();
		_setAddDialog();
		_setFindDialog();
		update();
		_setEvent();
	}

	// 更新
	public void update(){
		// 一覧データを取得
		rssList = rssModel.getList();
		// ダイアログ更新
		_upListDialog();
		_upDeleteDialog();
	}

	// 以下、プライベート ------------------------------------------------

	private void _setButton(){
		this.listButton = (Button) rssRead.findViewById(R.id.listButton);
		this.findButton = (Button) rssRead.findViewById(R.id.findButton);
	}

	// 登録ダイアログ生成
	private void _setAddDialog(){
		Builder ab = new AlertDialog.Builder(rssRead);
		ab.setTitle("RSS登録");
		ab.setView(setEdit);
		ab.setPositiveButton("登録", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				String keyword = setEdit.getText().toString();

				if(rssModel.isExists(keyword)){
					rssRead.msg("登録に失敗しました", "すでに登録済みのRSSです");
					return ;
				}
				if(setEdit.getText().toString().equals("")){
					rssRead.msg("登録に失敗しました", "文字列を入れてください");
					return ;
				}
				CheckRssTask checkRssTask = new CheckRssTask(rssRead, keyword);
				checkRssTask.execute();
			}
		});
		ab.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		});
		addDialog = ab.create();
	}

	// 検索ダイアログ生成
	private void _setFindDialog(){
		Builder ab = new AlertDialog.Builder(rssRead);
		ab.setTitle("キーワード検索");
		ab.setView(findEdit);
		ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String keyword = findEdit.getText().toString();
				keyword = keyword.replaceAll(" ","+");
				keyword = keyword.replaceAll("　","+");
				rssRead.loadRss(RSS_URL + keyword + SORT);
			}
		});
		ab.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		});
		findDialog = ab.create();
	}


	// クリックイベント
	private void _setEvent(){
		findButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				findDialog.show();
			}
		});

		listButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				listDialog.show();
			}
		});
	}

	private void _changeRss(String keyword){
		String url = rssModel.getRss(keyword);
		rssRead.keyword = keyword;
		rssRead.loadRss(RSS_URL + url + SORT);
	}

	// 削除
	private void _deleteRss(){
		if(rssModel.delete(deleteList)){
			rssRead.msg("削除完了", "削除しました");
			update();
		}else{
			rssRead.msg("削除失敗", "削除に失敗しました");
		}
	}

	// 一覧ダイアログ更新
	private void _upListDialog(){
		Builder lb = new AlertDialog.Builder(rssRead);
		lb.setTitle("RSS選択");
		lb.setItems(rssList, new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				_changeRss(rssList[which]);
			}
		});

		lb.setPositiveButton("閉じる", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		});
		listDialog = lb.create();
	}

	// 削除ダイアログ更新
	private void _upDeleteDialog(){
		// 削除リスト初期化
		deleteList = new Boolean[rssList.length];
		for(int i=0;i<rssList.length;i++){
			deleteList[i] = false;
		}

		// 更新
		Builder delb = new AlertDialog.Builder(rssRead);
		delb.setTitle("削除");
		delb.setMultiChoiceItems(rssList, null,
				new DialogInterface.OnMultiChoiceClickListener(){
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				deleteList[which] = isChecked;
			}
		});
		delb.setPositiveButton("削除", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				_deleteRss();
			}
		});
		delb.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

			}
		});
		deleteDialog = delb.create();
	}
}