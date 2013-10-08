package com.mechanic.graphics;

public interface Graphics
{
	public static enum ImageFormat
	{
		ARGB_8888, ARGB_4444, RGB_565
	}
	
	public Image NewImage(String fileName);
	
	public void Clear(int color);
	public void DrawPixel(int x, int y, int color);
	public void DrawLine(int x, int y, int x2, int y2, int color);
	public void DrawRect(int x, int y, int width, int height, int color);
	
	public void DrawImage(Image image, int x, int y, int srcX, int srcY,
			int srcWidth, int srcHeight);
	
	public void DrawImage(Image image, int x, int y);
	public int GetWidth();
	public int GetHeight();
}