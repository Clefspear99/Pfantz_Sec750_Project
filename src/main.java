import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class main {

    static  String path="root/";

    public static void main(String args[]){
        Scanner scan= new Scanner(System.in);
        int userID;


        AccessControllSystem ACS = new AccessControllSystem();
        setupEnvironment(ACS);
        //unncoment this to have it print out what the Access controll system looks like
        //ACS.printUserDatabase();
        while(true) {

            userID=-1;
            while (userID == -1) {
                userID = ACS.login(scan);
                if (userID == -1)
                    System.out.println("That was an invalid username and password combo. Please try again!");
            }

            System.out.println("Welcome " + ACS.getNameByID(userID) + "!");
            selectFolder(userID, ACS, (new ArrayList<String>(Arrays.asList(path))), scan);

        }


    }

    public static void selectFolder(int userID, AccessControllSystem ACS, ArrayList<String>currLoc, Scanner scan){
        String[] dirs=new File(currLoc.get(0)).list();
        boolean valid=true;
        int permission=0;
        String inString;
        int inInt=-1;
        System.out.println("Which directory would you like to access?");

        while(true) {
            inInt=-1;
            inString="";
            valid=true;
            for (int i = 0; i < dirs.length; i++)
                System.out.println(i + ". " + dirs[i]);
            System.out.println(dirs.length+". Log out");
            System.out.println("Enter the number to select which folder / Option you would like to access!");
            inString=scan.nextLine();

            try{
                inInt=Integer.parseInt(inString);
            }catch(Exception e){
                valid=false;
            }



            if(inInt<0 || inInt> dirs.length)
                valid=false;

            if(inInt==dirs.length){
                System.out.println("You have logged out. Goodbye "+ACS.getNameByID(userID)+"!");
                return;}

            if(valid==false)
                System.out.println("Invalid selection please try again!");

            if(valid){
                permission= ACS.getPermissions(userID, dirs[inInt]);
                if(permission==2 || permission ==3){
                    currLoc.add(dirs[inInt]);
                    selectOpp(permission, currLoc, scan);
                }
                else
                    System.out.println("You don't have permission to access that folder!");
            }


        }
    }


    public static void selectOpp(int permission, ArrayList<String>currLoc, Scanner scan){
        String[] dirs=new File(currLoc.get(0)+currLoc.get(1)).list();
        boolean valid=true;
        String inString;
        int inInt=-1;
        String oppList[]={};




        if(permission==2)
            oppList= new String[] {"view"};

        if(permission==3)
            oppList=new String[] {"view", "append", "create", "delete"};



        while(true) {

            System.out.println("Current files in this directory are: ");
            for (int i = 0; i < dirs.length; i++)
                System.out.println(i + ". " + dirs[i]);
            System.out.println("What operation would you like to perform on a file?");

            inString="";
            inInt=-1;
            valid=true;


            for (int i = 0; i < oppList.length; i++)
                System.out.println(i + ". " + oppList[i]);
            System.out.println(oppList.length+". Go up a level");
            System.out.println("Enter the number to select which folder / Option you would like to access!");
            inString=scan.nextLine();

            try{
                inInt=Integer.parseInt(inString);
            }catch(Exception e){
                valid=false;
            }



            if(inInt<0 || inInt> oppList.length)
                valid=false;

            if(inInt==oppList.length){
                currLoc.remove(1);
                return;}

            if(valid==false)
                System.out.println("Invalid selection please try again!");

            if(valid){
                performOpp(inInt, currLoc, scan);
            }



        }

    }

    public static void performOpp(int opp, ArrayList<String>currLoc, Scanner scan){
        String fileName;
        File file=new File(currLoc.get(0)+currLoc.get(1));
        boolean exists=false;
        Scanner fileScan = null;
        BufferedWriter writer;
        String fileIn="";
        String userTextInput="";


        String[] opts=new File(currLoc.get(0)+currLoc.get(1)).list();


        System.out.println("Current files: ");
        for(int i=0; i<opts.length; i++){
            System.out.println(opts[i]);
        }


        while(!exists) {
            System.out.println("Type a new (if creating) or otherwise current file name. To select a different option type quit: ");
            fileName = scan.nextLine();
            if(fileName.equals("quit"))
                return;
            file = new File(currLoc.get(0) + currLoc.get(1) +"/"+ fileName);
            exists=file.exists();
            if(fileName.equals(""))
                exists=false;
            if(!exists && opp == 2)
                break;
            if(!exists)
                System.out.println("Invalid choice. Please try again");
        }

        if(opp==0){
            fileIn="";
            try{
                fileScan=new Scanner(file);
                while(fileScan.hasNext())
                    fileIn+=fileScan.nextLine()+"\n";
                System.out.println(fileIn);
                System.out.println("End of file. Press enter to continue.");
                scan.nextLine();
                fileScan.close();
            }catch (Exception e){
                System.err.println(e);
            }
        }

        if(opp==1){
            fileIn="";
            System.out.println("What would you like to append to the end of this file? ");
            userTextInput=scan.nextLine();

            try{
                fileScan=new Scanner(file);
                while(fileScan.hasNext())
                    fileIn+=fileScan.nextLine()+"\n";
                fileIn+=userTextInput;
                fileScan.close();
                writer=new BufferedWriter(new FileWriter(file));
                writer.write(fileIn);
                writer.close();
                System.out.println("Finished! Press enter to continue.");
                scan.nextLine();
            }catch (Exception e){
                System.err.println(e);
            }

        }


        if(opp==2){
            if(file.exists()){
                System.out.println("File already exists!");
                return;
            }
            try{
                writer=new BufferedWriter(new FileWriter(file));
                //This seems to work slightly better than the create new file method.
                //That method was behaving a bit odly for some reason
                writer.write("");
                writer.close();
                System.out.println("Finished! This operation may take a minute to show up. Press enter to continue.");
                scan.nextLine();
            }catch (Exception e){
                System.err.println(e);
            }
        }


        if(opp==3){
            if(!file.exists()){
                System.out.println("File already doesn't exist!");
                return;
            }
            if(file.delete()){
                System.out.println("Finished! This operation may take a minute to show up. Press enter to continue.");
                scan.nextLine();
            }
            else {
                System.out.println("There was some error deleting this file. Press enter to continue.");
                scan.nextLine();
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
