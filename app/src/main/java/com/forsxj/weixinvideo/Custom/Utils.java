package com.forsxj.weixinvideo.Custom;

import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils
{
	public static final int MSG_ACTION_UPDATE_SUCCESS = 0;
	public static final int MSG_ACTION_UPDATE_TITLES = 1;
	public static final int MSG_ARG_ALL_VIDEO = 0;
	public static final int MSG_ARG_SAVED_VIDEO = 1;
	public static final int MSG_ACTION_CALL_FRAGMENT_UPDATE = 2;
	public static final String MSG_IS_UPDATE = "MSG_IS_UPDATE";
	public static final String MSG_CONTENT_VIDEO_INFO_LIST = "MSG_CONTENT_VIDEO_INFO_LIST";
	private static final String OUTPUT_FOLDER = "/WeiXin_Video_Output/";

	public static String getInternalRootDirectoryPath()
	{
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	public static String getReservedDecimal(float decimal, int digit)
	{
		BigDecimal bigDecimal = new BigDecimal(decimal);
		bigDecimal = bigDecimal.setScale(digit, BigDecimal.ROUND_DOWN);
		return bigDecimal.toString();
	}

	//根据文件大小，耗时不同，最好放在线程中运行
	public static String getVidefoMD5(File file)
	{
		String value = null;
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(file);
			MappedByteBuffer byteBuffer = fis.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(byteBuffer);
			BigInteger bi = new BigInteger(1, messageDigest.digest());
			value = bi.toString(16);
		}
		catch (IOException | NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (null != fis)
			{
				try
				{
					fis.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	public static String getOutputPath()
	{
		return Utils.getInternalRootDirectoryPath() + OUTPUT_FOLDER;
	}

	public static String getWeiXinVideoPath()
	{
		return Utils.getInternalRootDirectoryPath() + "/tencent/MicroMsg/";
	}

	public static int getScreenHeight(Context context)
	{
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}
}