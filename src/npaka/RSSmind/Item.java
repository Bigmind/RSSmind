package npaka.RSSmind;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

import android.text.Html;

public class Item {

	private static String DAY_FORMAT = "yyyy年M月d日";
	private static String DAY_PATTERN[] = { DateUtils.PATTERN_RFC1123 };

	private String title;
	private String desc = "";
	private String day = "";
	private String link = "";


	private CharSequence mDescription = "";
	private String sDescription = "";

	public void setTitle(String title){
		this.title = title;
	}

	public void setLink(String link){
		this.link = link;
	}

	public void setDesc(String desc){
		// 記事をhtmlからstringで変換
		mDescription = Html.fromHtml((String) desc);
		// stringで変数にセット
		sDescription = mDescription.toString();

		// 文字化けを解消
		sDescription = (sDescription.replaceAll("￼",""));
		sDescription = (sDescription.replaceAll("[\n][\n][\n][\n]",""));

		this.desc = sDescription;
	}

	public void setDay(String day){
		this.day = day;
	}

	public String getTitle(){
		return title;
	}

	public String getDesc(){
		return desc;
	}

	public String getLink(){
		return link;
	}

	public String getDay(){
		SimpleDateFormat sdf1 = new SimpleDateFormat(DAY_FORMAT);
		try {
			Date date = DateUtils.parseDate(day, DAY_PATTERN);
			return sdf1.format(date);
		} catch (DateParseException e) {
			return "";
		}
	}
}