package admin;

/**
 * Interface definition: AdminLibrary.
 * 
 * @author OpenORB Compiler
 */
public abstract class AdminLibraryPOA extends org.omg.PortableServer.Servant
        implements AdminLibraryOperations, org.omg.CORBA.portable.InvokeHandler
{
    public AdminLibrary _this()
    {
        return AdminLibraryHelper.narrow(_this_object());
    }

    public AdminLibrary _this(org.omg.CORBA.ORB orb)
    {
        return AdminLibraryHelper.narrow(_this_object(orb));
    }

    private static String [] _ids_list =
    {
        "IDL:admin/AdminLibrary:1.0"
    };

    public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte [] objectId)
    {
        return _ids_list;
    }

    public final org.omg.CORBA.portable.OutputStream _invoke(final String opName,
            final org.omg.CORBA.portable.InputStream _is,
            final org.omg.CORBA.portable.ResponseHandler handler)
    {

        if (opName.equals("getNonReturners")) {
                return _invoke_getNonReturners(_is, handler);
        } else {
            throw new org.omg.CORBA.BAD_OPERATION(opName);
        }
    }

    // helper methods
    private org.omg.CORBA.portable.OutputStream _invoke_getNonReturners(
            final org.omg.CORBA.portable.InputStream _is,
            final org.omg.CORBA.portable.ResponseHandler handler) {
        org.omg.CORBA.portable.OutputStream _output;
        String arg0_in = _is.read_string();
        String arg1_in = _is.read_string();
        String arg2_in = _is.read_string();
        int arg3_in = _is.read_long();

        String _arg_result = getNonReturners(arg0_in, arg1_in, arg2_in, arg3_in);

        _output = handler.createReply();
        _output.write_string(_arg_result);

        return _output;
    }

}
