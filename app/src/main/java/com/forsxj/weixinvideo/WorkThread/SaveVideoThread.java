package com.forsxj.weixinvideo.WorkThread;

import android.os.Handler;
import android.os.Message;

import com.forsxj.weixinvideo.Custom.Utils;

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
	public static final int OUTPUT_FILE_CANCEL = 21;
	public static final int OUTPUT_FILE_FAILED = 22;
	public static final int OUTPUT_FILE_PROGRESS = 23;
	public static final int OUTPUT_FILE_START = 24;
	private static String mOutput_Path;

	public SaveVideoThread(ArrayList<File> selectedFiles, Handler handler)
	{
		this.mSelectedFiles = selectedFiles;
		this.mHandler = handler;
	}

	private boolean createOutputPath()
	{
		mOutput_Path = Utils.getOutputPath();
		File file = new File(mOutput_Path);
		return file.exists() || file.mkdir();
	}

	private boolean copyFile(File srcFile)
	{
		FileInputStream fis = null;
		RandomAccessFile dstFile = null;
		File newFile = new File(mOutput_Path + srcFile.getName());
		try
		{
			fis = new FileInputStream(srcFile);
			dstFile = new RandomAccessFile(newFile,"rw");
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
		setSrcFileTimeToNewFile(srcFile, newFile);//将文件原始时间写入新文件
		return true;
	}

	private void sendMessage(int what)
	{
		sendMessage(what,-1,-1);
	}

	private void sendMessage(int what, int arg1)
	{
		sendMessage(what, arg1, -1);
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
			sendMessage(OUTPUT_FILE_FAILED);
			return;
		}

		for (int i = 0; i < mSelectedFiles.size(); i++)
		{
			if (mFlag)
			{
				sendMessage(OUTPUT_FILE_CANCEL,mSelectedFiles.size());
				return;
			}
			//开始复制的时候
			if (i == 0)
			{
				sendMessage(OUTPUT_FILE_START);
			}
			if (!copyFile(mSelectedFiles.get(i)))
			{
				sendMessage(OUTPUT_FILE_FAILED);//复制失败
				return;
			}
			//更新复制文件进度
			sendMessage(OUTPUT_FILE_PROGRESS, i);
		}
		sendMessage(OUTPUT_FILE_SUCCESS);//复制文件成功
	}

	//将文件原始时间写入新文件
	private boolean setSrcFileTimeToNewFile(File srcFile, File newFile)
	{
		return (srcFile.isFile() && srcFile.exists() && srcFile.canRead()
				&& newFile.isFile() && newFile.exists() && newFile.canWrite())
				&& newFile.setLastModified(srcFile.lastModified());
	}

	//取消文件复制
	public void cancel()
	{
		mFlag = true;
	}
}
