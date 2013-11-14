package de.upb.mobilerendering;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class AssetCopier
{
	private static final boolean OVERWRITE_EXISTING_FILES = true;
	
	/**
	 * Path to which the assets should be copied with a trailing "/"
	 */
	private static final String pathToCopyTo = "/sdcard/MobileRendering/";
	
	/**
	 * List of absolute path names of files which should be ignored when copying.
	 */
	private static final String ignoreList[] = { "images",
												 "sounds",
												 "webkit" };
	
	/**
	 * Copies the files in assets to "pathToCopyTo" defined above.
	 * Files on the ignoreList are ignored.
	 */
	public static void copyAssets(Context context)
	{
		// check if media is mounted and writable
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
		{
			try {
				Log.d(MobileRendering.LOG_TAG, "Copying assets:");
				copyRecursive(context, "");
			} catch (IOException e) {
				Log.e(MobileRendering.LOG_TAG, "Error copying the assets.");
				Log.e(MobileRendering.LOG_TAG, e.getMessage());
				System.exit(-1);
			}
		}
	}
	
	/**
	 * Recursively copies files from assets to the "pathToCopyTo".
	 * 
	 * @param path relative path to handle without trailing "/"
	 */
	private static void copyRecursive(Context context, String path) throws IOException
	{
		String files[] = context.getAssets().list(path);
		
		if(path.equals(""))
		{
			if(files.length == 0)
				Log.d(MobileRendering.LOG_TAG, "\tnone");
		} else {
			path += "/";
		}
		for(String fileName : files)
		{
			fileName = path + fileName;
			
			if(shouldBeIgnored(fileName))
			{
				continue;
			} else {
				if(context.getAssets().list(fileName).length == 0)
					copyFile(context, fileName);
				else
					copyRecursive(context, fileName);
			}
		}
	}
	
	/**
	 * Copies file from assets to "pathToCopyTo".
	 * 
	 * @param fileName path to the file relative to the assets directory
	 */
	private static void copyFile(Context context, String fileName) throws IOException
	{
		Log.d(MobileRendering.LOG_TAG, "\t" + fileName);
		
		File file = new File(pathToCopyTo + fileName);
		
		if(file.exists())
		{
			if(OVERWRITE_EXISTING_FILES)
				file.delete();
			else
				return;
		}
	
		file.getParentFile().mkdirs();
		file.createNewFile();
		
		InputStream is = context.getAssets().open(fileName);
		FileOutputStream fos = new FileOutputStream(file);
		
		int b = is.read();
		while(b != -1)
		{
			fos.write(b);
			b = is.read();
		}
		
		fos.close();
		is.close();
	}
	
	
	/**
	 * Returns true if the specified file is on the ignoreList.
	 */
	private static boolean shouldBeIgnored(String fileName)
	{
		for(String ignore : ignoreList)
			if(fileName.equals(ignore))
				return true;
		
		return false;
	}
}
