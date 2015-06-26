package library;

/**
 * Interface definition: Library.
 * 
 * @author OpenORB Compiler
 */
public interface LibraryOperations
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

    /**
     * Operation getNonReturners
     */
    public String getNonReturners(String AdminUsername, String strPassword, String InstitutionName, int NumDays);

    /**
     * Operation GetNonReturnersByServer
     */
    public String GetNonReturnersByServer(int NumDays);

}
