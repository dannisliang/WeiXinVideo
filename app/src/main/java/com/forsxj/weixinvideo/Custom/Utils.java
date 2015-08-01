package com.forsxj.weixinvideo.Custom;

import android.os.Environment;

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
	public String getVidefoMD5(File file)
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
}