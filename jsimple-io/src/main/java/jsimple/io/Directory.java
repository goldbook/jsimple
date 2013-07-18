package jsimple.io;

/**
 * @author Bret Johnson
 * @since 11/22/12 12:14 AM
 */
public abstract class Directory extends Path {
    /**
     * Get a file under the directory, which may or may not already exist.
     *
     * @param name file name
     * @return File object, that's a child of this directory
     */
    public abstract File getFile(String name);

    /**
     * Get the child directory, which may or may not already exist.
     *
     * @param name directory name
     * @return Directory object, that's a child of this directory
     */
    public abstract Directory getDirectory(String name);

    /**
     * Get the child directory, creating it if it doesn't already exist.
     *
     * @param name directory name
     * @return Directory object, that's a child of this directory
     */
    public Directory createDirectory(String name) {
        Directory directory = getDirectory(name);
        directory.create();

        return directory;
    }

    /**
     * See if the directory exists.  If there's an error checking if the directory exists, this method throws an
     * exception when possible, though for some platform implementations it'll just return false if platform can't
     * distinguish not existing from there being an error checking.
     *
     * @return true if the file exists
     */
    public abstract boolean exists();

    /**
     * Create the directory, if it doesn't already exist.  All ancestor directories are created as well.  If the
     * directory already exists, this method does nothing.
     */
    public abstract void create();

    /**
     * Visit the child elements of this path--basically list the files and subdirectories of a directory, calling the
     * visitor for each.  Just direct children are listed, not all descendants; callers can call this method recursively
     * if they want to visit all descendants.
     */
    public abstract void visitChildren(DirectoryVisitor visitor);

    /**
     * Delete this directory.  The directory must be empty; if it isn't the results are undefined--for some
     * implementations it will fail and for others delete the directory and its contents.
     */
    public abstract void delete();

    /**
     * Delete the contents of this directory, recursively.
     */
    public void deleteContents() {
        visitChildren(new DirectoryVisitor() {
            @Override public boolean visit(File file, PathAttributes attributes) {
                file.delete();
                return true;
            }

            @Override public boolean visit(Directory directory, PathAttributes attributes) {
                directory.deleteContents();
                directory.delete();
                return true;
            }
        });
    }

    /**
     * Check if this directory has any contents.
     *
     * @return true if the directory is empty, containing no files or subdirectories
     */
    public boolean isEmpty() {
        final boolean[] foundSomething = new boolean[1];
        foundSomething[0] = false;

        visitChildren(new DirectoryVisitor() {
            @Override public boolean visit(File file, PathAttributes attributes) {
                foundSomething[0] = true;
                return false;
            }

            @Override public boolean visit(Directory directory, PathAttributes attributes) {
                foundSomething[0] = true;
                return false;
            }
        });

        return !foundSomething[0];
    }
}
