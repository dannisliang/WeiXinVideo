package com.forsxj.weixinvideo.Custom;

import android.os.Environment;

import java.math.BigDecimal;

public class Utils
{
	public static String getInternalRootDirectoryPath()
	{
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	public static String getReservedDecimal(float decimal, int digit)
	{
		BigDecimal bigDecimal = new BigDecimal(decimal);
		bigDecimal = bigDecimal.setScale(digit,BigDecimal.ROUND_DOWN);
		return bigDecimal.toString();
	}
}
