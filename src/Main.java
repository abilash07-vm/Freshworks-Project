
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Scanner sc;
    private static String fileName;
    private static Crud crud;
    public static void main(String[] args) throws Exception {
        sc=new Scanner(System.in);
        crud=new Crud();
        showIntialOptions();

    }

    private static void showIntialOptions() throws Exception {
        System.out.println("Welcome \n Enter Anyone of the Operation\n\t1.Create New File\n\t2.Open Existing File\n\t3.Exit");
        switch (sc.nextInt()){
            case 1:
                sc.nextLine();
                System.out.println("Enter filename :");
                fileName=sc.nextLine();
                crud.setFilename(fileName+".json");
                if(crud.isFileAlreadyPresent()){
                    System.out.println("File Already Exist");
                    System.out.println("Press 1 to go back \n\t 2 to exit");
                    if(sc.nextInt()==1){
                        showIntialOptions();
                    }else{
                        return;
                    }
                }else {
                    System.out.println("Created Sucessfully");
                    crud.writeIntoJson(new HashMap<>());
                    getUserData();
                }
                break;
            case 2:
                sc.nextLine();
                System.out.println("Enter filename :");
                fileName=sc.nextLine();
                crud.setFilename(fileName+".json");
                if(crud.isFileAlreadyPresent()){
                    System.out.println("Opening "+fileName+".json");
                    getUserData();
                }else{
                    System.out.println("File Not Found");
                    System.out.println("Press 1 to go back \n\t 2 to exit");
                    if(sc.nextInt()==1){
                        showIntialOptions();
                    }else{
                        return;
                    }
                }
                break;
            case 3:
                return;
            default:
                break;
        }
    }

    private static void AskKey(String type) throws Exception {
        System.out.println("Enter a key of maximum of 32 character");
        String key=sc.nextLine();
        if(crud.isValidKey(key,type)){
            crud.setKey(key);
        }else{
            System.out.println();
            System.out.println("Invalid Key");
            AskKey(type);
        }

    }

    private static void getUserData() throws Exception {
        System.out.println();
        System.out.println("Choose the operation \n \t 1. Read \n\t 2. Write \n\t 3. Delete \n\t 4. Exit");
        switch (sc.nextInt()) {
            case 1:
                sc.nextLine();
                AskKey("read");
                Map data = crud.readJsonWithKey();
                if (data == null) {
                    System.out.println("No Data Found");
                } else {
                    for (Object i : data.keySet()) {
                        System.out.println(i + " : " + data.get(i));
                    }
                }
                System.out.println("Press 1 to Go Back\n press 2 for Restart\n Press other numbers to Exit");
                int op=sc.nextInt();
                if (op== 1) {
                    getUserData();
                }else if(op==2){
                    showIntialOptions();
                }else{
                    return;
                }
                break;
            case 2:
                sc.nextLine();
                Map writedata = new HashMap();
                AskKey("write");
                System.out.println("Enter Name :");
                writedata.put("name", GetData());
                System.out.println("Enter Age :");
                writedata.put("age", GetData());
                System.out.println("Enter Email :");
                writedata.put("email", GetData());
                System.out.println("Enter Contact Number :");
                writedata.put("contact", GetData());
                crud.writeIntoJson(writedata);
                System.out.println("Press 1 to Go Back\n press 2 for Restart\n Press other numbers to Exit");
                int op3=sc.nextInt();
                if (op3== 1) {
                    getUserData();
                }else if(op3==2){
                    showIntialOptions();
                }else{
                    return;
                }
                break;
            case 3:
                sc.nextLine();
                AskKey("delete");
                crud.deleteJsonData();
                System.out.println("Deleted Sucessfully...");
                System.out.println("Press 1 to Go Back\n press 2 for Restart\n Press other numbers to Exit");
                int op2=sc.nextInt();
                if (op2== 1) {
                    getUserData();
                }else if(op2==2){
                    showIntialOptions();
                }else{
                    return;
                }
                break;
            case 4:
                return;
            default:
                System.out.println("Please Enter the correct number");
                getUserData();
        }
    }
    public static String GetData(){
        String str=sc.nextLine();
        if(str.equals("")){
            System.out.println("Data Cannot be null");
            str=GetData();
        }
        return str;
    }


}
