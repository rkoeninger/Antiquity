import java.io.*;

public class DelEmptyDir{
    public static void main(String[] args){
        BufferedReader console = new BufferedReader(
        new InputStreamReader(System.in));
        System.out.print("Root directory: ");
        String dname = null;

        try {
            dname = console.readLine();
        } catch (IOException exc) {
            exc.printStackTrace();
            System.exit(-1);
        }

        if (dname == null){
            System.err.println("Target not supplied");
            System.exit(3);
        }

        File dir = new File(dname);

        if (! dir.exists()){
            System.err.println("Target directory does not exist");
            System.exit(1);
        } else if (! dir.isDirectory()){
            System.err.println("Target is not a directory");
            System.exit(2);
        }

        try {
            deleteEmptyFolders(dir);
        } catch (IOException exc) {
            System.err.println();
            System.err.println();
            exc.printStackTrace();
            System.exit(-1);
        }

        System.exit(0);

    }

    public static void deleteEmptyFolders(File dir) throws IOException{
        File[] files = dir.listFiles();

        // Check all sub-directories for emptiness recursively
        for (int x=0; x < files.length; ++x){
            if (files[x].isDirectory()){
                if (files[x].listFiles().length == 0){
                    System.out.print("Deleting \"" +
                    files[x].getAbsolutePath() + "\"...");
                    if (!dir.delete()){
//                        throw new IOException("Dir \"" +
//                        dir.toString() + "\" could not be deleted");
                    }
                    System.out.println(" Deleted");
                }
                deleteEmptyFolders(files[x]);
            }
        }

        // Re-check this dir to see if it is empty
        if (dir.isDirectory() && (files.length == 0)){
            System.out.println("Deleting \"" +
            dir.getAbsolutePath() + "\"...");
            if (!dir.delete()){
//                throw new IOException("Dir \"" +
//                dir.toString() + "\" could not be deleted");
            }
            System.out.println(" Deleted");
        }
    }
}
