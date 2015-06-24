package admin;

/**
 * Holder class for : AdminLibrary
 * 
 * @author OpenORB Compiler
 */
final public class AdminLibraryHolder
        implements org.omg.CORBA.portable.Streamable
{
    /**
     * Internal AdminLibrary value
     */
    public admin.AdminLibrary value;

    /**
     * Default constructor
     */
    public AdminLibraryHolder()
    { }

    /**
     * Constructor with value initialisation
     * @param initial the initial value
     */
    public AdminLibraryHolder(admin.AdminLibrary initial)
    {
        value = initial;
    }

    /**
     * Read AdminLibrary from a marshalled stream
     * @param istream the input stream
     */
    public void _read(org.omg.CORBA.portable.InputStream istream)
    {
        value = AdminLibraryHelper.read(istream);
    }

    /**
     * Write AdminLibrary into a marshalled stream
     * @param ostream the output stream
     */
    public void _write(org.omg.CORBA.portable.OutputStream ostream)
    {
        AdminLibraryHelper.write(ostream,value);
    }

    /**
     * Return the AdminLibrary TypeCode
     * @return a TypeCode
     */
    public org.omg.CORBA.TypeCode _type()
    {
        return AdminLibraryHelper.type();
    }

}
