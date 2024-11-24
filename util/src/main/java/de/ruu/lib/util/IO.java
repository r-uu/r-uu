package de.ruu.lib.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public interface IO
{
	public static String toString(InputStream inputStream) throws IOException
	{
		ByteArrayOutputStream result = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];

		int length;

		while ((length = inputStream.read(buffer)) != -1)
		{
			result.write(buffer, 0, length);
		}

		return result.toString(StandardCharsets.UTF_8.name());
	}

	public static boolean isListening(String host, int port) throws UnknownHostException
	{
		try (Socket socket = new Socket(host, port))
		{
			return true;
		}
		catch (IOException e)
		{
			return false;
		}
	}
}