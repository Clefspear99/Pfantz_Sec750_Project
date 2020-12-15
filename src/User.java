import java.util.*;
public class User {


    private static int idCount=1;

    private ArrayList<CapabilitiesObject> files;
    private int ID;
    private String name;
    private String pswd;


    public User(String name, String pswd){
        this.files=new ArrayList<>();
        this.name=name;
        this.pswd=pswd;
        this.ID=idCount;
        idCount++;
    }



    public boolean loginMatch(String name, String pswd){
        if(this.name.equals(name) && this.pswd.equals(pswd))
            return true;
        return false;
    }

    public int getPermissions(String folder){
        for(int i=0; i<files.size(); i++)
            if(files.get(i).getFolder().equals(folder))
                return files.get(i).getPermission();
        return 0;
    }

    public String toString(){
        StringBuilder strBuild=new StringBuilder();
        strBuild.append(name);
        strBuild.append(": { ");
        for(int i=0; i<files.size(); i++){
            if(i>0)
                strBuild.append(" ");
            strBuild.append("("+files.get(i).getFolder()+" : "+files.get(i).getPermission()+")");
        }
        strBuild.append(" }");
        return strBuild.toString();
    }

    public void addCAs(CapabilitiesObject[] in){
        for(int i=0; i<in.length; i++)
            files.add(in[i]);
    }

    public void addCAs(ArrayList in){
        files.addAll(in);
    }

    public void addCA(CapabilitiesObject in){
        files.add(in);
    }


    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }


}

