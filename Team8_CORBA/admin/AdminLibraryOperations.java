package admin;

/**
 * Interface definition: AdminLibrary.
 * 
 * @author OpenORB Compiler
 */
public interface AdminLibraryOperations
{
    /**
     * Operation getNonReturners
     */
    public String getNonReturners(String AdminUsername, String strPassword, String InstitutionName, int NumDays);

}
