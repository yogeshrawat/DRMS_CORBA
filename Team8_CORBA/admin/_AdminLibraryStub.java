package admin;

/**
 * Interface definition: AdminLibrary.
 * 
 * @author OpenORB Compiler
 */
public class _AdminLibraryStub extends org.omg.CORBA.portable.ObjectImpl
        implements AdminLibrary
{
    static final String[] _ids_list =
    {
        "IDL:admin/AdminLibrary:1.0"
    };

    public String[] _ids()
    {
     return _ids_list;
    }

    private final static Class _opsClass = admin.AdminLibraryOperations.class;

    /**
     * Operation getNonReturners
     */
    public String getNonReturners(String AdminUsername, String strPassword, String InstitutionName, int NumDays)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("getNonReturners",true);
                    _output.write_string(AdminUsername);
                    _output.write_string(strPassword);
                    _output.write_string(InstitutionName);
                    _output.write_long(NumDays);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("getNonReturners",_opsClass);
                if (_so == null)
                   continue;
                admin.AdminLibraryOperations _self = (admin.AdminLibraryOperations) _so.servant;
                try
                {
                    return _self.getNonReturners( AdminUsername,  strPassword,  InstitutionName,  NumDays);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

}
