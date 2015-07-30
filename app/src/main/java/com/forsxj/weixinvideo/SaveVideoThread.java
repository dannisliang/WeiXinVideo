package com.forsxj.weixinvideo;

import android.os.Handler;
import android.os.Message;

import com.forsxj.saveweixinvideo_utils.SDCardUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class SaveVideoThread extends Thread
{
	private ArrayList<File> mSelectedFiles = new ArrayList<>();
	private Handler mHandler;
	private boolean mFlag = false;
	public static final int OUTPUT_FILE_SUCCESS = 20;
	public static final int OUTPUT_FILE_GIVEUP = 21;
	public static final int OUTPUT_FILE_FAILE = 22;
	public static final int OUTPUT_FILE_PROGRESS = 23;
	private static final String OUTPUT_FOLDER = "WeiXin_Video_Output";
	private static String mOutput_Path;

	public SaveVideoThread(ArrayList<File> selectedFiles, Handler handler)
	{
		this.mSelectedFiles = selectedFiles;
		this.mHandler = handler;
	}

	public void stopOutPutFiles()
	{
		mFlag = true;
	}

	private boolean createOutputPath()
	{
		mOutput_Path = SDCardUtils.getInternalRootDirectoryPath() + "/" + OUTPUT_FOLDER;
		File file = new File(mOutput_Path);
		if (!file.exists())
		{
			return file.mkdir();
		}
		else
		{
			return true;
		}
	}

	private boolean copyFile(File srcFile)
	{
		FileInputStream fis = null;
		RandomAccessFile dstFile = null;
		try
		{
			fis = new FileInputStream(srcFile);
			dstFile = new RandomAccessFile(new File(mOutput_Path + "/" + srcFile.getName()),"rw");
			dstFile.setLength(srcFile.length());
			byte[] buffer = new byte[1024 * 10];
			int length;
			while ((length = fis.read(buffer)) != -1)
			{
				dstFile.write(buffer,0,length);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			if (fis != null)
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
			if (dstFile != null)
			{
				try
				{
					dstFile.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	private void sendMessage(int what)
	{
		sendMessage(what,-1,-1);
	}

	private void sendMessage(int what, int arg1)
	{
		sendMessage(what,arg1,-1);
	}

	private void sendMessage(int what, int arg1, int arg2)
	{
		Message message = mHandler.obtainMessage();
		message.what = what;
		message.arg1 = arg1;
		message.arg2 = arg2;
		message.sendToTarget();
	}

	@Override
	public void run()
	{
		if (!createOutputPath())
		{
			sendMessage(OUTPUT_FILE_FAILE);
			return;
		}
		for (int i = 0; i < mSelectedFiles.size(); i++)
		{
			if (mFlag)
			{
				sendMessage(OUTPUT_FILE_GIVEUP);
				return;
			}
			if (!copyFile(mSelectedFiles.get(i)))
			{
				sendMessage(OUTPUT_FILE_FAILE);
				return;
			}
			sendMessage(OUTPUT_FILE_PROGRESS, i, mSelectedFiles.size());
		}
		sendMessage(OUTPUT_FILE_SUCCESS);
	}
}
