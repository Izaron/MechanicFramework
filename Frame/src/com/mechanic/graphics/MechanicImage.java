package com.mechanic.graphics;

import com.mechanic.graphics.Graphics.ImageFormat;

import android.graphics.Bitmap;

public class MechanicImage implements Image
{
	Bitmap bitmap;
	ImageFormat format;
	
	public MechanicImage(Bitmap bitmap, ImageFormat format)
	{
		this.bitmap = bitmap;
		this.format = format;
	}
	
	@Override
	public int GetWidth()
	{
		return bitmap.getWidth();
	}
	
	@Override
	public int GetHeight()
	{
		return bitmap.getHeight();
	}
	
	@Override
	public ImageFormat GetFormat()
	{
		return format;
	}
	
	@Override
	public void Dispose()
	{
		bitmap.recycle();
	}
}
