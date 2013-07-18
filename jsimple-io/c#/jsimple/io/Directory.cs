namespace jsimple.io
{

	/// <summary>
	/// @author Bret Johnson
	/// @since 11/22/12 12:14 AM
	/// </summary>
	public abstract class Directory : Path
	{
		/// <summary>
		/// Get a file under the directory, which may or may not already exist.
		/// </summary>
		/// <param name="name"> file name </param>
		/// <returns> File object, that's a child of this directory </returns>
		public abstract File getFile(string name);

		/// <summary>
		/// Get the child directory, which may or may not already exist.
		/// </summary>
		/// <param name="name"> directory name </param>
		/// <returns> Directory object, that's a child of this directory </returns>
		public abstract Directory getDirectory(string name);

		/// <summary>
		/// Get the child directory, creating it if it doesn't already exist.
		/// </summary>
		/// <param name="name"> directory name </param>
		/// <returns> Directory object, that's a child of this directory </returns>
		public virtual Directory createDirectory(string name)
		{
			Directory directory = getDirectory(name);
			directory.create();

			return directory;
		}

		/// <summary>
		/// See if the directory exists.  If there's an error checking if the directory exists, this method throws an
		/// exception when possible, though for some platform implementations it'll just return false if platform can't
		/// distinguish not existing from there being an error checking.
		/// </summary>
		/// <returns> true if the file exists </returns>
		public abstract bool exists();

		/// <summary>
		/// Create the directory, if it doesn't already exist.  All ancestor directories are created as well.  If the
		/// directory already exists, this method does nothing.
		/// </summary>
		public abstract void create();

		/// <summary>
		/// Visit the child elements of this path--basically list the files and subdirectories of a directory, calling the
		/// visitor for each.  Just direct children are listed, not all descendants; callers can call this method recursively
		/// if they want to visit all descendants.
		/// </summary>
		public abstract void visitChildren(DirectoryVisitor visitor);

		/// <summary>
		/// Delete this directory.  The directory must be empty; if it isn't the results are undefined--for some
		/// implementations it will fail and for others delete the directory and its contents.
		/// </summary>
		public abstract void delete();

		/// <summary>
		/// Delete the contents of this directory, recursively.
		/// </summary>
		public virtual void deleteContents()
		{
			visitChildren(new DirectoryVisitorAnonymousInnerClassHelper(this));
		}

		private class DirectoryVisitorAnonymousInnerClassHelper : DirectoryVisitor
		{
			private readonly Directory outerInstance;

			public DirectoryVisitorAnonymousInnerClassHelper(Directory outerInstance)
			{
				this.outerInstance = outerInstance;
			}

			public override bool visit(File file, PathAttributes attributes)
			{
				file.delete();
				return true;
			}

			public override bool visit(Directory directory, PathAttributes attributes)
			{
				directory.deleteContents();
				directory.delete();
				return true;
			}
		}

		/// <summary>
		/// Check if this directory has any contents.
		/// </summary>
		/// <returns> true if the directory is empty, containing no files or subdirectories </returns>
		public virtual bool Empty
		{
			get
			{
				bool[] foundSomething = new bool[1];
				foundSomething[0] = false;
    
				visitChildren(new DirectoryVisitorAnonymousInnerClassHelper2(this, foundSomething));
    
				return !foundSomething[0];
			}
		}

		private class DirectoryVisitorAnonymousInnerClassHelper2 : DirectoryVisitor
		{
			private readonly Directory outerInstance;

			private bool[] foundSomething;

			public DirectoryVisitorAnonymousInnerClassHelper2(Directory outerInstance, bool[] foundSomething)
			{
				this.outerInstance = outerInstance;
				this.foundSomething = foundSomething;
			}

			public override bool visit(File file, PathAttributes attributes)
			{
				foundSomething[0] = true;
				return false;
			}

			public override bool visit(Directory directory, PathAttributes attributes)
			{
				foundSomething[0] = true;
				return false;
			}
		}
	}

}