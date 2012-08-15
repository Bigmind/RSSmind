package npaka.RSSmind;

import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.*;
import android.view.LayoutInflater;
import java.util.ArrayList;

public class RssAdapter  extends BaseAdapter {
	private Context context;
	private View row;
	private ArrayList<Item> list;
	private RssListInsert rsslistinsert;

	public RssAdapter(Context context) {
		super();
		this.context = context;
		list = new ArrayList<Item>();
	}

	public void add(String title, String desc, String day, String link){
		Item item = new Item();
		item.setTitle(title);
		item.setDesc(desc);
		item.setDay(day);
		item.setLink(link);

		addItem(item);
	}

	public void addItem(Item item){
		list.add(item);
	}

	public void clear(){
		list.clear();
	}

	// 以下、オーバーライド --------------------------------------------------

	@Override
	public int getCount() {
		// TODO 自動生成されたメソッド・スタブ
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO 自動生成されたメソッド・スタブ
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自動生成されたメソッド・スタブ
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自動生成されたメソッド・スタブ
		View row = _getRow(position);
		return row;
	}

	// 以下、プライベート ---------------------------------------------------

	private View _getRow(int position){
		// レイアウト(row.xml)からインスタンス生成
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		row = inflater.inflate(R.layout.row, null);

		// row.xml の中の要素を取り出します。
		TextView title = (TextView) row.findViewById(R.id.titleText);
		//TextView desc = (TextView) row.findViewById(R.id.descText);
		TextView day = (TextView) row.findViewById(R.id.dayText);

		// データのセット
		Item item = (Item) getItem(position);
		title.setText(item.getTitle());
		//desc.setText(item.getDesc());
		day.setText(item.getDay());

		rsslistinsert = new RssListInsert(item.getTitle(),item.getDay());
//		rsslistinsert.execute();
		return row;
	}
}