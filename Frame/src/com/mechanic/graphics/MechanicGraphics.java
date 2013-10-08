package com.mechanic.graphics;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

public class MechanicGraphics implements Graphics
{
	AssetManager assets;
	Bitmap buffer;
	Canvas canvas;
	Paint paint;
	Rect srcRect = new Rect(), dstRect = new Rect();
	
	public MechanicGraphics(AssetManager assets, Bitmap buffer)
	{
		this.assets = assets;
		this.buffer = buffer;
		this.canvas = new Canvas(buffer);
		this.paint = new Paint();
	}
	
	@Override
	public Image NewImage(String fileName)
	{
		ImageFormat format;
		InputStream file = null;
		Bitmap bitmap = null;
		
		try
		{
			file = assets.open(fileName);
			bitmap = BitmapFactory.decodeStream(file);
			
			if (bitmap == null)
				throw new RuntimeException("Нельзя загрузить изображение '"
						+ fileName + "'");
		}
		catch (IOException e)
		{
			throw new RuntimeException("Нельзя загрузить изображение '"
					+ fileName + "'");
		}
		finally
		{
				try
				{
					if(file != null)
						file.close();
				}
				catch(IOException e)
				{
					
				}
		}
		
		if (bitmap.getConfig() == Config.RGB_565)
			format = ImageFormat.RGB_565;
		else if (bitmap.getConfig() == Config.ARGB_4444)
			format = ImageFormat.ARGB_4444;
		else
			format = ImageFormat.ARGB_8888;
		
		return new MechanicImage(bitmap, format);
	}

	@Override
	public void Clear(int color)
	{
		canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8, (color & 0xff));
	}

	@Override
	public void DrawPixel(int x, int y, int color)
	{
		paint.setColor(color);
		canvas.drawPoint(x, y, paint);
	}

	@Override
	public void DrawLine(int x, int y, int x2, int y2, int color)
	{
		paint.setColor(color);
		canvas.drawLine(x, y, x2, y2, paint);
	}

	@Override
	public void DrawRect(int x, int y, int width, int height, int color)
	{
		paint.setColor(color);
		paint.setStyle(Style.FILL);
		canvas.drawRect(x, y, x + width - 1, y + width - 1, paint);
	}

	@Override
	public void DrawImage(Image image, int x, int y, int srcX, int srcY,
			int srcWidth, int srcHeight)
	{
		srcRect.left = srcX;
		srcRect.top = srcY;
		srcRect.right = srcX + srcWidth - 1;
		srcRect.bottom = srcY + srcHeight - 1;
		dstRect.left = x;
		dstRect.top = y;
		dstRect.right = x + srcWidth - 1;
		dstRect.bottom = y + srcHeight - 1;
		canvas.drawBitmap(((MechanicImage)image).bitmap, srcRect, dstRect,
				null);
	}

	@Override
	public void DrawImage(Image image, int x, int y)
	{
		canvas.drawBitmap(((MechanicImage)image).bitmap, x, y, null);
	}

	@Override
	public int GetWidth()
	{
		return buffer.getWidth();
	}
	
	@Override
	public int GetHeight()
	{
		return buffer.getHeight();
	}
}
