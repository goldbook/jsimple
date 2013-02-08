namespace jsimple.io
{

	/// <summary>
	/// This class was based on, and modified from, the Apache Harmony java.io.InputStream class.  Unlike the Java
	/// InputStream class, this doesn't support mark, reset, or skip, doesn't throw any checked exceptions, and none of the
	/// methods are synchronized.
	/// <p/>
	/// The base class for all input streams. An input stream is a means of reading data from a source in a byte-wise
	/// manner.
	/// <p/>
	/// Some input streams also support marking a position in the input stream and returning to this position later. This
	/// abstract class does not provide a fully working implementation, so it needs to be subclassed, and at least the {@link
	/// #read()} method needs to be overridden. Overriding some of the non-abstract methods is also often advised, since it
	/// might result in higher efficiency.
	/// 
	/// @author Bret Johnson </summary>
	/// <seealso cref= jsimple.io.OutputStream
	/// @since 10/7/12 12:31 AM </seealso>
	public abstract class InputStream
	{
		/// <summary>
		/// Closes this stream.  If the stream is already closed, then this method should do nothing.  Concrete
		/// implementations of this class should free any resources during close.
		/// </summary>
		/// <exception cref="IOException"> if an error occurs while closing this stream </exception>
		public virtual void close()
		{
		}

		~InputStream()
		{
			close();
		}

		/// <summary>
		/// Reads a single byte from this stream and returns it as an integer in the range from 0 to 255. Returns -1 if the
		/// end of the stream has been reached. Blocks until one byte has been read, the end of the source stream is detected
		/// or an exception is thrown.
		/// </summary>
		/// <returns> the byte read or -1 if the end of stream has been reached </returns>
		/// <exception cref="IOException"> if the stream is closed or another IOException occurs </exception>
		public virtual int read()
		{
			sbyte[] buffer = new sbyte[1];
			int result = read(buffer, 0, 1);
			return result == -1 ? - 1 : buffer[0] & 0xff;
		}

		/// <summary>
		/// Reads bytes from this stream and stores them in the byte array {@code b}.
		/// </summary>
		/// <param name="buffer"> the byte array in which to store the bytes read </param>
		/// <returns> the number of bytes actually read or -1 if the end of the stream has been reached </returns>
		/// <exception cref="IOException"> if this stream is closed or another IOException occurs </exception>
		public virtual int read(sbyte[] buffer)
		{
			return read(buffer, 0, buffer.Length);
		}

		/// <summary>
		/// Reads at most {@code length} bytes from this stream and stores them in the byte array {@code b} starting at
		/// {@code offset}.
		/// </summary>
		/// <param name="buffer"> the byte array in which to store the bytes read </param>
		/// <param name="offset"> the initial position in {@code buffer} to store the bytes read from this stream </param>
		/// <param name="length"> the maximum number of bytes to store in {@code b} </param>
		/// <returns> the number of bytes actually read or -1 if the end of the stream has been reached </returns>
		/// <exception cref="IOException"> if the stream is closed or another IOException occurs reading the first byte </exception>
		public virtual int read(sbyte[] buffer, int offset, int length)
		{
			for (int i = 0; i < length; i++)
			{
				int c;
				try
				{
					if ((c = read()) == -1)
						return i == 0 ? - 1 : i;
				}
				catch (IOException e)
				{
					if (i != 0)
						return i;
					throw e;
				}
				buffer[offset + i] = (sbyte) c;
			}
			return length;
		}

		/// <summary>
		/// Write the remaining contents of this stream to the specified output stream, closing this input stream when done.
		/// </summary>
		/// <param name="outputStream"> output stream to copy to </param>
		public virtual void copyTo(OutputStream outputStream)
		{
			sbyte[] buffer = new sbyte[8 * 1024];

			while (true)
			{
				int bytesRead = read(buffer);
				if (bytesRead < 0)
					break;
				outputStream.write(buffer, 0, bytesRead);
			}

			close();
		}
	}

}