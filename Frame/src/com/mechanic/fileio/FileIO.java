package com.mechanic.fileio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileIO
{
	public InputStream ReadAsset(String name) throws IOException;
	public InputStream ReadFile(String name) throws IOException;
	public OutputStream WriteFile(String name) throws IOException;
}
