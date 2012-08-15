package npaka.RSSmind;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/*******************************************::
 *
 * widgetの表示関係の処理
 *
 ********************************************/

public class RSSWidget extends AppWidgetProvider{

	private LoadRssTask loadRssTask;
	private ListView rssList;
	private RssAdapter rssAdapter;
	private View mainLayout;
	private RssModel rssModel;

	/**
	 * AppWidgetが作成される際に呼ばれます。
	 * 同じAppWidgetを複数起動した際には、初回のみ呼ばれます。
	 * 全体的な初期化処理が必要な場合はここに記述します。
	 */
	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);

	}

	/**
	 * AppWidgetが更新される際に呼ばれます。
	 * updatePeriodMillis等で更新間隔を設定していれば、そのタイミングで呼ばれます。
	 * また、AppWidgetを起動した際にも一度呼ばれます。
	 */
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.v("HelloAndroidWidget", "onUpdate");
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	/**
	 * AppWidgetが削除された際に呼ばれます。
	 * 終了処理が必要な場合はここに記述します。
	 */
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		Log.v("HelloAndroidWidget", "onDeleted");
		super.onDeleted(context, appWidgetIds);
	}

	/**
	 * AppWidgetが全て削除された際に呼ばれます。
	 * 全体的な終了処理が必要な場合はここに記述します。
	 */
	@Override
	public void onDisabled(Context context) {
		Log.v("HelloAndroidWidget", "onDisabled");
		super.onDisabled(context);
	}

	/**
	 * アクションを受け取り、AppWidgetProviderの各メソッドの呼び出しを処理します。
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.v("HelloAndroidWidget", "onReceive");
		super.onReceive(context, intent);
	}
}

