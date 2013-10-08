package com.mechanic.fileio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.res.AssetManager;
import android.os.Environment;


public class MechanicFileIO implements FileIO
{
	AssetManager assets;
	String ExternalStoragePath;
	
	public MechanicFileIO(AssetManager assets)
	{
		this.assets = assets;
		ExternalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath() + 
				File.separator;
	}
	
	public InputStream ReadAsset(String name) throws IOException
	{
		return assets.open(name);
	}
	
	public InputStream ReadFile(String name) throws IOException
	{
		return new FileInputStream(ExternalStoragePath + name);
	}
	
	public OutputStream WriteFile(String name) throws IOException
	{
		return new FileOutputStream(ExternalStoragePath + name);
	}
}
