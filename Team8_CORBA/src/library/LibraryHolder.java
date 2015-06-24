package library;

/**
 * Holder class for : Library
 * 
 * @author OpenORB Compiler
 */
final public class LibraryHolder
        implements org.omg.CORBA.portable.Streamable
{
    /**
     * Internal Library value
     */
    public library.Library value;

    /**
     * Default constructor
     */
    public LibraryHolder()
    { }

    /**
     * Constructor with value initialisation
     * @param initial the initial value
     */
    public LibraryHolder(library.Library initial)
    {
        value = initial;
    }

    /**
     * Read Library from a marshalled stream
     * @param istream the input stream
     */
    public void _read(org.omg.CORBA.portable.InputStream istream)
    {
        value = LibraryHelper.read(istream);
    }

    /**
     * Write Library into a marshalled stream
     * @param ostream the output stream
     */
    public void _write(org.omg.CORBA.portable.OutputStream ostream)
    {
        LibraryHelper.write(ostream,value);
    }

    /**
     * Return the Library TypeCode
     * @return a TypeCode
     */
    public org.omg.CORBA.TypeCode _type()
    {
        return LibraryHelper.type();
    }

}
