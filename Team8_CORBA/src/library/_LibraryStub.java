package library;

/**
 * Interface definition: Library.
 * 
 * @author OpenORB Compiler
 */
public class _LibraryStub extends org.omg.CORBA.portable.ObjectImpl
        implements Library
{
    static final String[] _ids_list =
    {
        "IDL:library/Library:1.0"
    };

    public String[] _ids()
    {
     return _ids_list;
    }

    private final static Class _opsClass = library.LibraryOperations.class;

    /**
     * Operation createAccount
     */
    public boolean createAccount(String m_firstName, String m_lastName, String m_emailAddress, String m_phoneNumber, String m_username, String m_password, String m_educationalInstitution)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("createAccount",true);
                    _output.write_string(m_firstName);
                    _output.write_string(m_lastName);
                    _output.write_string(m_emailAddress);
                    _output.write_string(m_phoneNumber);
                    _output.write_string(m_username);
                    _output.write_string(m_password);
                    _output.write_string(m_educationalInstitution);
                    _input = this._invoke(_output);
                    boolean _arg_ret = _input.read_boolean();
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
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("createAccount",_opsClass);
                if (_so == null)
                   continue;
                library.LibraryOperations _self = (library.LibraryOperations) _so.servant;
                try
                {
                    return _self.createAccount( m_firstName,  m_lastName,  m_emailAddress,  m_phoneNumber,  m_username,  m_password,  m_educationalInstitution);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation reserveBook
     */
    public boolean reserveBook(String m_username, String m_password, String m_bookName, String m_author)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("reserveBook",true);
                    _output.write_string(m_username);
                    _output.write_string(m_password);
                    _output.write_string(m_bookName);
                    _output.write_string(m_author);
                    _input = this._invoke(_output);
                    boolean _arg_ret = _input.read_boolean();
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
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("reserveBook",_opsClass);
                if (_so == null)
                   continue;
                library.LibraryOperations _self = (library.LibraryOperations) _so.servant;
                try
                {
                    return _self.reserveBook( m_username,  m_password,  m_bookName,  m_author);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation checkUser
     */
    public int checkUser(String m_username, String m_password, String m_educationalInstitution)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("checkUser",true);
                    _output.write_string(m_username);
                    _output.write_string(m_password);
                    _output.write_string(m_educationalInstitution);
                    _input = this._invoke(_output);
                    int _arg_ret = _input.read_long();
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
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("checkUser",_opsClass);
                if (_so == null)
                   continue;
                library.LibraryOperations _self = (library.LibraryOperations) _so.servant;
                try
                {
                    return _self.checkUser( m_username,  m_password,  m_educationalInstitution);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation reserveInterLibrary
     */
    public boolean reserveInterLibrary(String m_username, String m_password, String m_bookName, String m_authorName)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("reserveInterLibrary",true);
                    _output.write_string(m_username);
                    _output.write_string(m_password);
                    _output.write_string(m_bookName);
                    _output.write_string(m_authorName);
                    _input = this._invoke(_output);
                    boolean _arg_ret = _input.read_boolean();
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
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("reserveInterLibrary",_opsClass);
                if (_so == null)
                   continue;
                library.LibraryOperations _self = (library.LibraryOperations) _so.servant;
                try
                {
                    return _self.reserveInterLibrary( m_username,  m_password,  m_bookName,  m_authorName);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

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
                library.LibraryOperations _self = (library.LibraryOperations) _so.servant;
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
