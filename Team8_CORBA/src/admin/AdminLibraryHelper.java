package admin;

/** 
 * Helper class for : AdminLibrary
 *  
 * @author OpenORB Compiler
 */ 
public class AdminLibraryHelper
{
    /**
     * Insert AdminLibrary into an any
     * @param a an any
     * @param t AdminLibrary value
     */
    public static void insert(org.omg.CORBA.Any a, admin.AdminLibrary t)
    {
        a.insert_Object(t , type());
    }

    /**
     * Extract AdminLibrary from an any
     *
     * @param a an any
     * @return the extracted AdminLibrary value
     */
    public static admin.AdminLibrary extract( org.omg.CORBA.Any a )
    {
        if ( !a.type().equivalent( type() ) )
        {
            throw new org.omg.CORBA.MARSHAL();
        }
        try
        {
            return admin.AdminLibraryHelper.narrow( a.extract_Object() );
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
     * Return the AdminLibrary TypeCode
     * @return a TypeCode
     */
    public static org.omg.CORBA.TypeCode type()
    {
        if (_tc == null) {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
            _tc = orb.create_interface_tc( id(), "AdminLibrary" );
        }
        return _tc;
    }

    /**
     * Return the AdminLibrary IDL ID
     * @return an ID
     */
    public static String id()
    {
        return _id;
    }

    private final static String _id = "IDL:admin/AdminLibrary:1.0";

    /**
     * Read AdminLibrary from a marshalled stream
     * @param istream the input stream
     * @return the readed AdminLibrary value
     */
    public static admin.AdminLibrary read(org.omg.CORBA.portable.InputStream istream)
    {
        return(admin.AdminLibrary)istream.read_Object(admin._AdminLibraryStub.class);
    }

    /**
     * Write AdminLibrary into a marshalled stream
     * @param ostream the output stream
     * @param value AdminLibrary value
     */
    public static void write(org.omg.CORBA.portable.OutputStream ostream, admin.AdminLibrary value)
    {
        ostream.write_Object((org.omg.CORBA.portable.ObjectImpl)value);
    }

    /**
     * Narrow CORBA::Object to AdminLibrary
     * @param obj the CORBA Object
     * @return AdminLibrary Object
     */
    public static AdminLibrary narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof AdminLibrary)
            return (AdminLibrary)obj;

        if (obj._is_a(id()))
        {
            _AdminLibraryStub stub = new _AdminLibraryStub();
            stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
            return stub;
        }

        throw new org.omg.CORBA.BAD_PARAM();
    }

    /**
     * Unchecked Narrow CORBA::Object to AdminLibrary
     * @param obj the CORBA Object
     * @return AdminLibrary Object
     */
    public static AdminLibrary unchecked_narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof AdminLibrary)
            return (AdminLibrary)obj;

        _AdminLibraryStub stub = new _AdminLibraryStub();
        stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
        return stub;

    }

}
