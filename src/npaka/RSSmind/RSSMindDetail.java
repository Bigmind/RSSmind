package npaka.RSSmind;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RSSMindDetail extends Activity{

	private TextView mDescr;
	private TextView mLink;
	private String link;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_detail);

		Intent intent = getIntent();

		// タイトルの中身
		String title = intent.getStringExtra("TITLE");
		mDescr = (TextView) findViewById(R.id.title01);
		mDescr.setText(title);

		// 詳細の中身
		String descr = intent.getStringExtra("DESCRIPTION");
		mDescr = (TextView) findViewById(R.id.textView1);
		mDescr.setText(descr);

		// 詳細のリンク先ページ
		link = intent.getStringExtra("LINK");
		mLink = (TextView) findViewById(R.id.LinkButton01);
        mLink.setOnClickListener(new ClickListener());

	}

	class ClickListener implements View.OnClickListener {
		public void onClick(View v) {
			Uri uri = Uri.parse(link);
			Intent i = new Intent(Intent.ACTION_VIEW,uri);
			startActivity(i);
		}
	}

}
