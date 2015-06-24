package student;

/**
 * Holder class for : StudentLibrary
 * 
 * @author OpenORB Compiler
 */
final public class StudentLibraryHolder
        implements org.omg.CORBA.portable.Streamable
{
    /**
     * Internal StudentLibrary value
     */
    public student.StudentLibrary value;

    /**
     * Default constructor
     */
    public StudentLibraryHolder()
    { }

    /**
     * Constructor with value initialisation
     * @param initial the initial value
     */
    public StudentLibraryHolder(student.StudentLibrary initial)
    {
        value = initial;
    }

    /**
     * Read StudentLibrary from a marshalled stream
     * @param istream the input stream
     */
    public void _read(org.omg.CORBA.portable.InputStream istream)
    {
        value = StudentLibraryHelper.read(istream);
    }

    /**
     * Write StudentLibrary into a marshalled stream
     * @param ostream the output stream
     */
    public void _write(org.omg.CORBA.portable.OutputStream ostream)
    {
        StudentLibraryHelper.write(ostream,value);
    }

    /**
     * Return the StudentLibrary TypeCode
     * @return a TypeCode
     */
    public org.omg.CORBA.TypeCode _type()
    {
        return StudentLibraryHelper.type();
    }

}
