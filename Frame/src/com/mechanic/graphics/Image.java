package com.mechanic.graphics;

import com.mechanic.graphics.Graphics.ImageFormat;

public interface Image
{
	public int GetWidth();
	public int GetHeight();
	public ImageFormat GetFormat();
	public void Dispose();
}