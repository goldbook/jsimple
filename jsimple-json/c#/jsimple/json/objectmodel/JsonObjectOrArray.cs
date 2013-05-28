namespace jsimple.json.objectmodel
{

	using StringWriter = jsimple.io.StringWriter;
	using Writer = jsimple.io.Writer;
	using Serializer = jsimple.json.text.Serializer;

	/// <summary>
	/// This class is simply used to represent, in a type safe way, either a JSON object or an array.  According to the spec
	/// a JSON text, at the highest level, is allowed to be either a JSON object or array, so this class captures that.
	/// 
	/// @author Bret Johnson
	/// @since 7/8/12 1:56 PM
	/// </summary>
	public abstract class JsonObjectOrArray
	{
		public virtual void write(Writer writer)
		{
			Serializer serializer = new Serializer(writer);
			serializer.writeValue(this);
			serializer.write("\n"); // Terminate the last line
			serializer.flush();
		}

		public override string ToString()
		{
			StringWriter stringWriter = new StringWriter();
			write(stringWriter);
			return stringWriter.ToString();
		}
	}

}