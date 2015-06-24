package student;

/** 
 * Helper class for : StudentLibrary
 *  
 * @author OpenORB Compiler
 */ 
public class StudentLibraryHelper
{
    /**
     * Insert StudentLibrary into an any
     * @param a an any
     * @param t StudentLibrary value
     */
    public static void insert(org.omg.CORBA.Any a, student.StudentLibrary t)
    {
        a.insert_Object(t , type());
    }

    /**
     * Extract StudentLibrary from an any
     *
     * @param a an any
     * @return the extracted StudentLibrary value
     */
    public static student.StudentLibrary extract( org.omg.CORBA.Any a )
    {
        if ( !a.type().equivalent( type() ) )
        {
            throw new org.omg.CORBA.MARSHAL();
        }
        try
        {
            return student.StudentLibraryHelper.narrow( a.extract_Object() );
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
     * Return the StudentLibrary TypeCode
     * @return a TypeCode
     */
    public static org.omg.CORBA.TypeCode type()
    {
        if (_tc == null) {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
            _tc = orb.create_interface_tc( id(), "StudentLibrary" );
        }
        return _tc;
    }

    /**
     * Return the StudentLibrary IDL ID
     * @return an ID
     */
    public static String id()
    {
        return _id;
    }

    private final static String _id = "IDL:student/StudentLibrary:1.0";

    /**
     * Read StudentLibrary from a marshalled stream
     * @param istream the input stream
     * @return the readed StudentLibrary value
     */
    public static student.StudentLibrary read(org.omg.CORBA.portable.InputStream istream)
    {
        return(student.StudentLibrary)istream.read_Object(student._StudentLibraryStub.class);
    }

    /**
     * Write StudentLibrary into a marshalled stream
     * @param ostream the output stream
     * @param value StudentLibrary value
     */
    public static void write(org.omg.CORBA.portable.OutputStream ostream, student.StudentLibrary value)
    {
        ostream.write_Object((org.omg.CORBA.portable.ObjectImpl)value);
    }

    /**
     * Narrow CORBA::Object to StudentLibrary
     * @param obj the CORBA Object
     * @return StudentLibrary Object
     */
    public static StudentLibrary narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof StudentLibrary)
            return (StudentLibrary)obj;

        if (obj._is_a(id()))
        {
            _StudentLibraryStub stub = new _StudentLibraryStub();
            stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
            return stub;
        }

        throw new org.omg.CORBA.BAD_PARAM();
    }

    /**
     * Unchecked Narrow CORBA::Object to StudentLibrary
     * @param obj the CORBA Object
     * @return StudentLibrary Object
     */
    public static StudentLibrary unchecked_narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof StudentLibrary)
            return (StudentLibrary)obj;

        _StudentLibraryStub stub = new _StudentLibraryStub();
        stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
        return stub;

    }

}
