package com.trustlook.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.widget.*;
import android.text.Html;
import android.util.Log;
import android.view.*;
import com.trustlook.app.R;

public class AppListAdapter extends ArrayAdapter<AppInfo> {
	private final String TAG = "TL";
	private final Context context;
	private List<AppInfo> objects;

	public AppListAdapter(Context context, List<AppInfo> objects) {
		super(context, R.layout.list_item, objects);

		this.context = context;
		this.objects = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.list_item, parent, false);

		TextView labelView = (TextView) rowView.findViewById(R.id.appLabel);
		TextView detailView = (TextView) rowView.findViewById(R.id.appDetails);
		TextView appSizeView = (TextView) rowView.findViewById(R.id.appSize);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.appLogo);
		TextView riskTextView = (TextView) rowView
				.findViewById(R.id.riskTextView);

		int width = this.getContext().getResources().getDisplayMetrics().widthPixels;
		// labelView.setMaxWidth(width - 120);
		// detailView.setMaxWidth(width - 300);
		// Log.d(TAG, "width = " + width);

		labelView.setTypeface(PkgUtils.getRegularFont());
		detailView.setTypeface(PkgUtils.getRegularFont());
		appSizeView.setTypeface(PkgUtils.getRegularFont());
		riskTextView.setTypeface(PkgUtils.getRegularFont());

		String virusName = objects.get(position).getVirusName();
		String summary = objects.get(position).getSummary();

		labelView.setText(objects.get(position).getDisplayName());
		// detailView.setText(((virusName != null) ? virusName : "") + " - " +
		// ((summary != null) ? summary : ""));

		Date lastUpdate = new Date(objects.get(position).getLastUpdate());
		String lastUpdateStr = new SimpleDateFormat("yyyy-MM-dd")
				.format(lastUpdate);
		// detailView.setText(Html.fromHtml("<font color=\"#AAAAAA\">" +
		// lastUpdateStr + "</font>" + "\t\t\t" + "<font color=\"#1874CD\">" +
		// PkgUtils.formatFileSize(objects.get(position).getSizeInBytes()) +
		// "</font>"));

		detailView.setText(Html.fromHtml("<font color=\"#AAAAAA\">"
				+ lastUpdateStr + "</font>"));
		appSizeView.setText(Html.fromHtml("<font color=\"#1874CD\">"
				+ PkgUtils.formatFileSize(objects.get(position)
						.getSizeInBytes()) + "</font>"));

		String scoreString = objects.get(position).getScore();
		PkgUtils.RISK_LEVEL riskLevel = PkgUtils.getRiskLevel(scoreString);
		String riskText = "L";
		int backgroundColor = Color.GRAY;
		GradientDrawable bgShape = (GradientDrawable) riskTextView
				.getBackground();
		switch (riskLevel) {
		case HIGH:
			riskText = "H";
			backgroundColor = Color.parseColor("#EE8B8B");
			break;
		case MEDIUM:
			riskText = "M";
			backgroundColor = Color.parseColor("#F7C98B");
			break;
		case LOW:
			riskText = "L";
			backgroundColor = Color.parseColor("#0CBA98");
			break;
		default:
			break;
		}
		bgShape.setColor(backgroundColor);
		riskTextView.setText(riskText);

		Drawable icon = objects.get(position).getIcon();

		if (icon != null)
			imageView.setImageDrawable(objects.get(position).getIcon());
		else
			imageView.setImageResource(R.drawable.amplifier_3d);

		return rowView;
	}
}
