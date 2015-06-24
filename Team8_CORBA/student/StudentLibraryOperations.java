package student;

/**
 * Interface definition: StudentLibrary.
 * 
 * @author OpenORB Compiler
 */
public interface StudentLibraryOperations
{
    /**
     * Operation createAccount
     */
    public boolean createAccount(String m_firstName, String m_lastName, String m_emailAddress, String m_phoneNumber, String m_username, String m_password, String m_educationalInstitution);

    /**
     * Operation reserveBook
     */
    public boolean reserveBook(String m_username, String m_password, String m_bookName, String m_author);

    /**
     * Operation checkUser
     */
    public int checkUser(String m_username, String m_password, String m_educationalInstitution);

    /**
     * Operation reserveInterLibrary
     */
    public boolean reserveInterLibrary(String m_username, String m_password, String m_bookName, String m_authorName);

}
