public class CapabilitiesObject {

    private String folder;
    private int permission;

    public CapabilitiesObject(String folder, int permission){
        this.folder=folder;
        this.permission=permission;

        if(permission>3)
            this.permission=3;

        if(permission<0)
            this.permission=0;
    }

    public String toString(){
        return this.folder+": "+ Integer.toString(this.permission);
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }



}
