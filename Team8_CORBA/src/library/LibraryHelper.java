package library;

/** 
 * Helper class for : Library
 *  
 * @author OpenORB Compiler
 */ 
public class LibraryHelper
{
    /**
     * Insert Library into an any
     * @param a an any
     * @param t Library value
     */
    public static void insert(org.omg.CORBA.Any a, library.Library t)
    {
        a.insert_Object(t , type());
    }

    /**
     * Extract Library from an any
     *
     * @param a an any
     * @return the extracted Library value
     */
    public static library.Library extract( org.omg.CORBA.Any a )
    {
        if ( !a.type().equivalent( type() ) )
        {
            throw new org.omg.CORBA.MARSHAL();
        }
        try
        {
            return library.LibraryHelper.narrow( a.extract_Object() );
        }
        catch ( final org.omg.CORBA.BAD_PARAM e )
        {
            throw new org.omg.CORBA.MARSHAL(e.getMessage());
        }
    }

    //
    // Internal TypeCode value
    //
    private static org.omg.CORBA.TypeCode _tc = null;

    /**
     * Return the Library TypeCode
     * @return a TypeCode
     */
    public static org.omg.CORBA.TypeCode type()
    {
        if (_tc == null) {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
            _tc = orb.create_interface_tc( id(), "Library" );
        }
        return _tc;
    }

    /**
     * Return the Library IDL ID
     * @return an ID
     */
    public static String id()
    {
        return _id;
    }

    private final static String _id = "IDL:library/Library:1.0";

    /**
     * Read Library from a marshalled stream
     * @param istream the input stream
     * @return the readed Library value
     */
    public static library.Library read(org.omg.CORBA.portable.InputStream istream)
    {
        return(library.Library)istream.read_Object(library._LibraryStub.class);
    }

    /**
     * Write Library into a marshalled stream
     * @param ostream the output stream
     * @param value Library value
     */
    public static void write(org.omg.CORBA.portable.OutputStream ostream, library.Library value)
    {
        ostream.write_Object((org.omg.CORBA.portable.ObjectImpl)value);
    }

    /**
     * Narrow CORBA::Object to Library
     * @param obj the CORBA Object
     * @return Library Object
     */
    public static Library narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof Library)
            return (Library)obj;

        if (obj._is_a(id()))
        {
            _LibraryStub stub = new _LibraryStub();
            stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
            return stub;
        }

        throw new org.omg.CORBA.BAD_PARAM();
    }

    /**
     * Unchecked Narrow CORBA::Object to Library
     * @param obj the CORBA Object
     * @return Library Object
     */
    public static Library unchecked_narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof Library)
            return (Library)obj;

        _LibraryStub stub = new _LibraryStub();
        stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
        return stub;

    }

}
