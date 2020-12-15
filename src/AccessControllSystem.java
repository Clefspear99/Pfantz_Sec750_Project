import java.util.*;

public class AccessControllSystem {

    private ArrayList<User> userDatabase;

    public AccessControllSystem(){
        userDatabase=new ArrayList<>();
    }

    public int login(Scanner scan){

        String username, pswd;
        System.out.println("Please enter your username (or exit to shutdown): ");
        username=scan.nextLine();
        if(username.equals("exit")){
            scan.close();
            System.exit(0);}
        System.out.print("Please enter your password: ");
        pswd=scan.nextLine();

        for(int i=0; i<userDatabase.size(); i++){
            if(userDatabase.get(i).loginMatch(username, pswd))
                return userDatabase.get(i).getID();
        }
        return -1;
    }

    public int getPermissions(int userID, String folder){
        return getFromUserDatabaseByID(userID).getPermissions(folder);
    }

    public String getNameByID(int in){
        for(int i=0; i<userDatabase.size(); i++){
            if(userDatabase.get(i).getID()==in)
                return userDatabase.get(i).getName();
        }

        return "Not in Database";
    }

    public String toString(){
        StringBuilder strBuild = new StringBuilder();
        for(int i=0; i<userDatabase.size(); i++){
            strBuild.append(userDatabase.get(i).toString()+"\n");
        }
        return strBuild.toString();
    }

    public void printUserDatabase(){
        System.out.println(this.toString());
    }

    public void addUserToDatabase(User newUser){
        userDatabase.add(newUser);
    }

    public void removeUserFromDatabaseByID(int userID){
        for(int i = 0; i<userDatabase.size(); i++){
            if(userID == userDatabase.get(i).getID())
                userDatabase.remove(i);
        }
    }

    public User getFromUserDatabaseByID(int userID) {
        for (User user:userDatabase){
            if (user.getID()==userID)
                return user;
        }
        return new User("null", "null");
    }

    public ArrayList getUserDatabase() {
        return userDatabase;
    }


}
