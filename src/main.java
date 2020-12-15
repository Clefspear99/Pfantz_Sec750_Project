import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class main {

    static  String path="root/";

    public static void main(String args[]){
        Scanner scan= new Scanner(System.in);
        int userID=-1;


        AccessControllSystem ACS = new AccessControllSystem();
        setupEnvironment(ACS);
        //ACS.printUserDatabase();
        File root=new File(path);
        File currLocation=root;
        String[] dirs = root.list();
        while(userID==-1){
            userID=ACS.login(scan);
            if(userID==-1)
                System.out.println("That was an invalid username and password combo. Please try again!");
        }

        System.out.println("Welcome "+ACS.getNameByID(userID)+"!");
        currLocation=selectFolder(userID, ACS, root, scan);



    }

    public static File selectFolder(int userID, AccessControllSystem ACS, File root, Scanner scan){
        String[] dirs=root.list();
        boolean valid=true;
        int permission=0;
        String inString;
        int inInt=-1;
        System.out.println("Which directory would you like to access?");

        while(true) {
            valid=true;
            for (int i = 0; i < dirs.length; i++)
                System.out.println(i + ". " + dirs[i]);
            System.out.println("Select which folder you would like to access!");
            inString=scan.nextLine();

            try{
                inInt=Integer.parseInt(inString);
            }catch(Exception e){
                valid=false;
            }

            if(inInt<0 || inInt>= dirs.length)
                valid=false;

            if(valid==false)
                System.out.println("Invalid selection please try again!");

            if(valid){
                permission= ACS.isAccessible(userID, dirs[inInt]);
            }



        }
    }

    public static void setupEnvironment(AccessControllSystem ACS){
        String[] initDirs={"System", "Shared", "Engineer_group", "Accounting_group"};

        File directory=new File(path);
        if(!directory.exists())
            directory.mkdir();

        for(int i=0; i<initDirs.length; i++){
            directory=new File(path+initDirs[i]);
            if(!directory.exists())
                directory.mkdir();
        }


        User admin=new User("Admin", "root");
        ArrayList<CapabilitiesObject> adminCAs=new ArrayList<>();
        for(int i=0; i<initDirs.length; i++){
            adminCAs.add(new CapabilitiesObject(initDirs[i], 3));
        }
        admin.addCAs(adminCAs);
        ACS.addUserToDatabase(admin);

        User bob = new User("Bob", "veggieburger");
        bob.addCA(new CapabilitiesObject(initDirs[1], 3));
        bob.addCA(new CapabilitiesObject(initDirs[2], 2));
        ACS.addUserToDatabase(bob);


        User johnny = new User("Johnny", "weakpassword");
        johnny.addCA(new CapabilitiesObject(initDirs[1], 3));
        johnny.addCA(new CapabilitiesObject(initDirs[2], 3));
        johnny.addCA(new CapabilitiesObject(initDirs[3], 2));
        ACS.addUserToDatabase(johnny);

        User charles = new User("Charles", "1234");
        charles.addCA(new CapabilitiesObject(initDirs[1], 3));
        charles.addCA(new CapabilitiesObject(initDirs[3], 3));
        ACS.addUserToDatabase(charles);

    }

}
