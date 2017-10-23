import java.io.*;
import javax.swing.*;

public final class NewlineFixer
{
    
    // Fixer's std in/out
    private static final BufferedReader in =
    new BufferedReader(new InputStreamReader(System.in));
    private static final PrintStream out = System.out;
    private static final PrintStream err;

    static
    {
        try
        {
        
            err = new PrintStream(new FileOutputStream("C:\\cleanup.log"));
            
        }
        catch (IOException exc)
        {
            
            throw new Error();
            
        }
        
    }
    
    private static final String[] endings = new String[]{
        "java", "properties", "css", "htm", "html",
        "txt", "log", "c", "cpp", "h", "hpp"
    };
    
    private static final FileFilter filter = new FileFilter()
    {
        
        public boolean accept(File file)
        {
            
            if (file.isDirectory())
                return true;
                
            for (String ending : endings)
                if (file.getName().endsWith("." + ending))
                    return true;
                    
            return false;
            
        }
        
    };
    
    public static void main(String[] args) throws IOException
    {
        
        String input = null;
        
        // Get the full file/dir
        if (args.length > 0)
        {
            
            if (args[0].equals("-help"))
            {
                
                showUsage();
                System.exit(1);
                
            }
            
            // Make path out of arguments
            input = buildPath(args);
            
        }
        else
        {
            
            // Prompt for file/dir input
            input = JOptionPane.showInputDialog("Enter File/Directory Path:");
            
            if (input == null)
            {
                
                showUsage();
                System.exit(1);
                
            }
            
        }
        
        final File root = new File(input);
        
        // Exit if file does not exist
        if (! root.exists())
        {
            
            System.out.println("Specified file/dir does not exist");
            System.exit(2);
            
        }
        
        scan(root);
                
    }
    
    private static String buildPath(String... path)
    {
        
        final StringBuilder builder = new StringBuilder();
        
        if (path.length > 0)
        {
        
            builder.append(path[0]);
            
        }
            
        for (int x = 1; x < path.length; ++x)
        {
        
            builder.append(" ").append(path[x]);
            
        }
        
        return builder.toString();
        
    }
    
    private static void scan(File file) throws IOException
    {
        
        File[] files = file.listFiles(filter);
        
        if (files == null)
        {
            
            fix(file);
            return;
            
        }
        
        out.println("Folder " + file.getAbsolutePath());
        out.println("Files and Folders: " + files.length);
        
        for (File f : files)
            try
            {
                
                out.println("Cleaning up " + f.getName() + "...");
                scan(f);
                
            }
            catch (IOException exc)
            {
                
                err.println("Error cleaning up " + f.getAbsolutePath());
                
            }
        
    }
    
    private static void fix(File file) throws IOException
    {
        
        final File dstFile = new File(file.getAbsolutePath());
        final File tmpFile = new File(System.getProperty("java.io.tmpdir"),
        file.getName());
        file.renameTo(tmpFile);
        final BufferedReader finput =
        new BufferedReader(new FileReader(tmpFile));
        final PrintWriter foutput =
        new PrintWriter(new FileWriter(dstFile));
        
        String line = null;
        
        while ((line = finput.readLine()) != null)
        {
            
            foutput.println(line);
            
        }
        
        finput.close();
        foutput.close();
        
    }
    
    private static void showUsage()
    {
        
        System.out.println("Newline replacer - v1.1");
        System.out.println("Usage: NewlineFixer [File | Root dir | nothing]");
        System.out.println();
        System.out.println("File     -\tFile to fix");
        System.out.println("Root dir -\tDirectory to recursively fix");
        System.out.println("nothing  -\tDir/file is specified using chooser");
        
    }
    
}
