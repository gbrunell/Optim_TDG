import java.io.*;
import java.util.*;

public class generate
{
	public static void main(String[] args)
    {
	 String s0 = "1000";
	 String s1 = "000";
	 String s2;
	 int seq;

	 String c1 = "\"C:\\Program Files\\IBM Optim\\RT\\BIN\\pr0cmnd.exe\" /R DIRECTORY=OPTIMDIR TYPE=INSERT REQUEST=EXPEDIA.INSERT1 OUTPUT=optim_log.txt+ QUIET+";
	 String c2 = "\"C:\\Program Files\\IBM Optim\\RT\\BIN\\pr0cmnd.exe\" /EXPORT DIRECTORY=OPTIMDIR CONTINUEONERROR+ TYPE=ColumnMap NAME=EXPEDIA.COLMAP1 FILE=C:\\Optim\\Data\\colmap1.txt";
	 String c3 = "\"C:\\Program Files\\IBM Optim\\RT\\BIN\\pr0cmnd.exe\" /IMPORT DIRECTORY=OPTIMDIR INPUT=C:\\Optim\\Data\\colmap1.txt OVERWRITE+ CONTINUEONERROR+";
	 String c4 = "\"C:\\Program Files\\IBM Optim\\RT\\BIN\\pr0cmnd.exe\" /R DIRECTORY=OPTIMDIR TYPE=EXTRACT REQUEST=EXPEDIA.EXTRACT2 OUTPUT=optim_log.txt+ QUIET+";
	 String c5 = "\"C:\\Program Files\\IBM Optim\\RT\\BIN\\pr0cmnd.exe\" /R DIRECTORY=OPTIMDIR TYPE=CONVERT REQUEST=EXPEDIA.CONVERT1 OUTPUT=optim_log.txt+ QUIET+";
	 String c6 = "\"C:\\Program Files\\IBM Optim\\RT\\BIN\\pr0cmnd.exe\" /R DIRECTORY=OPTIMDIR TYPE=INSERT REQUEST=EXPEDIA.INSERT2 OUTPUT=optim_log.txt+ QUIET+";

	try {

		 // Read input from user
		 System.out.println();
		 System.out.println("Optim Test Data Generation Demo");
		 System.out.println("-------------------------------");
		 System.out.print("Enter number of times to insert the Optim extract file into the target table: ");
		 System.out.println();
		 BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		 seq = Integer.parseInt(in.readLine());
		 System.out.println();
		 System.out.print("Generating " + seq);
		 System.out.println(" inserts to OPTDEV...");
		 System.out.println();

		 // Execute Windows commands
		 Process p1a = Runtime.getRuntime().exec(c1);
		 BufferedReader stdInput1a = new BufferedReader(new InputStreamReader(p1a.getInputStream()));
      	 BufferedReader stdError1a = new BufferedReader(new InputStreamReader(p1a.getErrorStream()));
      	 String ms1a = null;
      	 System.out.println("Iteration 1...");
      	 System.out.println("INSERT OPERATION");
  	     while ((ms1a = stdInput1a.readLine()) != null) {System.out.println(ms1a);}
         while ((ms1a = stdError1a.readLine()) != null) {System.out.println(ms1a);}

 		 for(int i=2; i<=seq; i++){
			 System.out.println("Iteration " +i+"...");
			 // Export
		 	 Process p2 = Runtime.getRuntime().exec(c2);
		 	 BufferedReader stdInput2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
      	 	 BufferedReader stdError2 = new BufferedReader(new InputStreamReader(p2.getErrorStream()));
      	 	 String ms2 = null;
      	 	 System.out.println("CHANGING COLUMN MAP");
  	     	 while ((ms2 = stdInput2.readLine()) != null) {System.out.println(ms2);}
        	 while ((ms2 = stdError2.readLine()) != null) {System.out.println(ms2);}

			 // Change Optim column map file
			 String i_str = Integer.toString(i);
			 s2 = i_str+s1;
			 File file1 = new File("C:\\Optim\\Data\\colmap1.txt");
			 file1.renameTo(new File("C:\\Optim\\Data\\colmap_tmp.txt"));
			 char[] buf = new char[5000];
			 int len = 0;
			 StringBuilder builder = new StringBuilder();
			 FileReader reader = new FileReader("C:\\Optim\\Data\\colmap_tmp.txt");
			 while (((len = reader.read(buf, 0, buf.length)) != -1)) {builder.append(buf, 0, len);}
			 String text1 = builder.toString();
			 String text2 = text1.replaceAll(s0, s2);
			 BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Optim\\Data\\colmap1.txt"));
			 out.write(text2);
			 out.close();
			 reader.close();
			 File file2 = new File("C:\\Optim\\Data\\colmap_tmp.txt");
			 file2.delete();
             s0 = s2;

			 // Import
		 	 Process p3 = Runtime.getRuntime().exec(c3);
		 	 BufferedReader stdInput3 = new BufferedReader(new InputStreamReader(p3.getInputStream()));
      	 	 BufferedReader stdError3 = new BufferedReader(new InputStreamReader(p3.getErrorStream()));
      	 	 String ms3 = null;
  	     	 while ((ms3 = stdInput3.readLine()) != null) {System.out.println(ms3);}
        	 while ((ms3 = stdError3.readLine()) != null) {System.out.println(ms3);}

        	 // Insert
        	 Process p1b = Runtime.getRuntime().exec(c1);
			 BufferedReader stdInput1b = new BufferedReader(new InputStreamReader(p1b.getInputStream()));
			 BufferedReader stdError1b = new BufferedReader(new InputStreamReader(p1b.getErrorStream()));
			 String ms1b = null;
			 System.out.println("INSERT OPERATION");
			 while ((ms1b = stdInput1b.readLine()) != null) {System.out.println(ms1b);}
             while ((ms1b = stdError1b.readLine()) != null) {System.out.println(ms1b);}
         }

		// Extract unmasked records
        Process p4 = Runtime.getRuntime().exec(c4);
		BufferedReader stdInput4 = new BufferedReader(new InputStreamReader(p4.getInputStream()));
		BufferedReader stdError4 = new BufferedReader(new InputStreamReader(p4.getErrorStream()));
		String ms4 = null;
		System.out.println();
		System.out.println("EXTRACT UNMASKED RECORDS");
		while ((ms4 = stdInput4.readLine()) != null) {System.out.println(ms4);}
        while ((ms4 = stdError4.readLine()) != null) {System.out.println(ms4);}

		// Convert extract file
        Process p5 = Runtime.getRuntime().exec(c5);
		BufferedReader stdInput5 = new BufferedReader(new InputStreamReader(p5.getInputStream()));
		BufferedReader stdError5 = new BufferedReader(new InputStreamReader(p5.getErrorStream()));
		String ms5 = null;
		System.out.println("CONVERT EXTRACT FILE");
		while ((ms5 = stdInput5.readLine()) != null) {System.out.println(ms5);}
        while ((ms5 = stdError5.readLine()) != null) {System.out.println(ms5);}

		// Insert masked records
        Process p6 = Runtime.getRuntime().exec(c6);
		BufferedReader stdInput6 = new BufferedReader(new InputStreamReader(p6.getInputStream()));
		BufferedReader stdError6 = new BufferedReader(new InputStreamReader(p6.getErrorStream()));
		String ms6 = null;
		System.out.println("INSERT MASKED RECORDS");
		while ((ms6 = stdInput6.readLine()) != null) {System.out.println(ms6);}
        while ((ms6 = stdError6.readLine()) != null) {System.out.println(ms6);}

		// Clean up
		System.out.println();
		System.out.println("Cleaning up...");
		File file1 = new File("C:\\Optim\\Data\\colmap1.txt");
		file1.renameTo(new File("C:\\Optim\\Data\\colmap_tmp.txt"));
		char[] buf = new char[5000];
		int len = 0;
		StringBuilder builder = new StringBuilder();
		FileReader reader = new FileReader("C:\\Optim\\Data\\colmap_tmp.txt");
		while (((len = reader.read(buf, 0, buf.length)) != -1)) {builder.append(buf, 0, len);}
		String text1 = builder.toString();
		String text2 = text1.replaceAll(s0, "1000");
		BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Optim\\Data\\colmap1.txt"));
		out.write(text2);
		out.close();
		reader.close();
		File file2 = new File("C:\\Optim\\Data\\colmap_tmp.txt");
		file2.delete();

		Process p3a = Runtime.getRuntime().exec(c3);
		BufferedReader stdInput3a = new BufferedReader(new InputStreamReader(p3a.getInputStream()));
      	BufferedReader stdError3a = new BufferedReader(new InputStreamReader(p3a.getErrorStream()));
      	String ms3a = null;
  	    while ((ms3a = stdInput3a.readLine()) != null) {System.out.println(ms3a);}
        while ((ms3a = stdError3a.readLine()) != null) {System.out.println(ms3a);}
        int records = 180 * seq;
        System.out.println("Records generated: "+ records);
		} catch(FileNotFoundException filenotfound){}
		  catch(IOException ioexception){}



    }
}