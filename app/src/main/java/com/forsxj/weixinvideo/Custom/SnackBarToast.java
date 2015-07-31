package com.forsxj.weixinvideo.Custom;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;


public class SnackBarToast
{
	private static final int SNACKBAR_TOAST_DEFAULT_BG_COLOR = 0x000000;
	private static final int SNACKBAR_TOAST_DEFAULT_ACTIONTEXT_COLOR = 0x000000;

	public static void showSnackBarToast(View view, String message,int backgroundColor, int textColor, int time)
	{
		Snackbar snackbar = Snackbar.make(view, message,Snackbar.LENGTH_LONG);
		View snackBarView = snackbar.getView();
		snackBarView.setBackgroundColor(backgroundColor);
		TextView snackbar_text = (TextView)snackBarView.findViewById(android.support.design.R.id.snackbar_text);
		if (snackbar_text != null)
		{
			snackbar_text.setTextColor(textColor);
		}
		snackbar.show();
	}

	public static void showSnackBarToast_Long(View view, String message,int backgroundColor, int textColor)
	{
		showSnackBarToast(view,message,backgroundColor,textColor,Snackbar.LENGTH_LONG);
	}

	public static void showSnackBarToast_Short(View view, String message,int backgroundColor, int textColor)
	{
		showSnackBarToast(view,message,backgroundColor,textColor,Snackbar.LENGTH_SHORT);
	}

	public static void showDefaultSnackBarToast_Long(View view, String message)
	{
		showSnackBarToast(view,
				message,
				view.getResources().getColor(SNACKBAR_TOAST_DEFAULT_BG_COLOR),
				view.getResources().getColor(SNACKBAR_TOAST_DEFAULT_ACTIONTEXT_COLOR),
				Snackbar.LENGTH_LONG);
	}

	public static void showDefaultSnackBarToast_Short(View view, String message)
	{
		showSnackBarToast(view,
				message,
				view.getResources().getColor(SNACKBAR_TOAST_DEFAULT_BG_COLOR),
				view.getResources().getColor(SNACKBAR_TOAST_DEFAULT_ACTIONTEXT_COLOR),
				Snackbar.LENGTH_SHORT);
	}
}
