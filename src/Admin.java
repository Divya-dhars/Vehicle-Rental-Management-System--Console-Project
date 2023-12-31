import java.util.*;
import java.sql.*;
public class Admin{
    private Connection con=null;
    public Admin(){
        if(con==null){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/vehiclerental","root","Divya29*");
        }
        catch(Exception e){
            System.out.print(e);
        }
     }
    }
    public void addVehicle(String name,String number,String type,String price,String km,String condition,String service,String available)throws Exception{
        try{
          Statement st=con.createStatement();
          String query="insert into vehicle_details(vehicle_name,vehicle_number,vehicle_type,vehicle_price,vehicle_km,vehicle_condition,isNeedService,Available_sts)values('"+name+"','"+number+"','"+type+"','"+price+"','"+km+"','"+condition+"','"+service+"','"+available+"')";
          int inserted=st.executeUpdate(query);
          if(inserted==1){
            System.out.print("Vehicle details added");
          }
        }catch(Exception e){
           System.out.print(e);
        }
   }
   public void displayVehicle()throws Exception{
    try{
      Statement st=con.createStatement();
      String query="select * from vehicle";
      ResultSet rs=st.executeQuery(query);
      while(rs.next()){
       System.out.println(rs.getInt(1)+ " "+rs.getString(2) + " " +rs.getString(3) +  " "+ rs.getString(4)+ " "+rs.getInt(5)+ " "+ rs.getString(6) + " "+ rs.getString(7));
      }
    }catch(Exception e){
       System.out.print(e);
    }
}
public void deleteVehicle(String d_number)throws Exception{
    try{
      Statement st=con.createStatement();
      String query="delete from vehicle where v_number='"+d_number+"'";
      int deleted=st.executeUpdate(query);
      if(deleted!=-1){
        System.out.print("Vehicle details removed");
      }
    }catch(Exception e){
       System.out.print(e);
    }
}
public void display(String val)throws Exception{
    Statement s=con.createStatement();
    String query = "SELECT * FROM vehicle_details ORDER BY ='"+val+"' ASC";
    ResultSet rs=s.executeQuery(query);
    while(rs.next()){
        System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+" "+rs.getString(4 )+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7)+" "+rs.getString(8)+" "+rs.getString(9));
    }
}
public void searchByName(String s_name) throws Exception{
    try{
      Statement st=con.createStatement();
      String query="select * from vehicle_details where v_name='"+s_name+"'";
       ResultSet rs=st.executeQuery(query);
       while(rs.next()){
        System.out.println(rs.getInt(1)+ " "+rs.getString(2) + " " +rs.getString(3) +  " "+ rs.getString(4)+ " "+rs.getString(5)+ " "+ rs.getInt(6) + " "+ rs.getString(7));
       }
    }catch(Exception e){
        System.out.print(e);
    }
}
   public void searchByNumber(String s_number) throws Exception{
    try{
      Statement st=con.createStatement();
      String query="select * from vehicle_details where v_number='"+s_number+"'";
       ResultSet rs=st.executeQuery(query);
       while(rs.next()){
        System.out.println(rs.getInt(1)+ " "+rs.getString(2) + " " +rs.getString(3) +  " "+ rs.getString(4)+ " "+rs.getString(5)+ " "+ rs.getInt(6) + " "+ rs.getString(7));
       }
    }catch(Exception e){
        System.out.print(e);
    }
}
public void updateVehicleDetails(String vehicle_number,String val,String key)throws Exception{
    Statement s=con.createStatement();
    String query = String.format("UPDATE vehicle_details SET %s = '%s' WHERE vehicle_number = '%s'", key, val, vehicle_number);
    int updated = s.executeUpdate(query);
    System.out.printf("Updating %s....", key);
    if (updated == 1) {
        Thread.sleep(2000);
        System.out.printf("Updated %s", key);
    } else {
        System.out.printf("Failed to update %s", key);
    }
}
public void search(String val,String key)throws Exception{
    Statement s=con.createStatement();
    String query = "SELECT * FROM vehicle_details WHERE '"+key+"'='" +val+ "'";
    ResultSet rs=s.executeQuery(query);
    while(rs.next()){
        System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+" "+rs.getString(4 )+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7)+" "+rs.getString(8));
    }
}
public void updateUserDetails(String email,String val,String key)throws Exception{
    Statement s=con.createStatement();
    String query = String.format("UPDATE register SET %s = '%s' WHERE email = '%s'", key, val, email);
    int updated = s.executeUpdate(query);
    System.out.printf("Updating %s....", key);
    if (updated == 1) {
        Thread.sleep(2000);
        System.out.printf("Updated %s", key);
    } else {
        System.out.printf("Failed to update %s", key);
    }
}
   public void print() throws Exception{
    Scanner s=new Scanner(System.in);
    int ch;
    do{
        System.out.println("1.To Add Vehicle ||2.To Update vehicle || 3.To Delete Vehicle ||  4.Display Vehicles by Names || 5.Display Vehicles by Number || 6.Display Vehicles needed Services  || 7.Display Vehicles by Price  || 8.Search Vehicle by Name || 9.Search Vehicle by Number_Plate || 10.Display Rented Vehicle || 11.Display Fines || 12.Handle Users");
        ch=s.nextInt();
        s.nextLine();
        switch(ch){
            case 1:
            System.out.println("1.To Add new Vehicle  || 2.To add existing Vehicle");
            int add=s.nextInt();
            s.nextLine();
            switch(add){
                case 1:
                System.out.print("Enter Vehicle name:");
                String v_name=s.nextLine();
                System.out.print("Enter vehicle number plate:");
                String v_number=s.nextLine();
                System.out.print("Enter type of vehicle(car/bike):");
                String v_type=s.nextLine();
                System.out.print("Enter vehicle price:");
                String v_price=s.nextLine();
                addVehicle(v_name,v_number,v_type,v_price,"0","good","no","yes");
                break;
                case 2:
                System.out.print("Enter Vehicle name:");
                String ev_name=s.nextLine();
                System.out.print("Enter vehicle number plate:");
                String ev_number=s.nextLine();
                System.out.print("Enter type of vehicle(car/bike):");
                String ev_type=s.nextLine();
                System.out.print("Enter vehicle price:");
                String ev_price=s.nextLine();
                System.out.print("Enter Vehicle Km:");
                String ev_km=s.nextLine();
                System.out.print("Enter vehicle Condition:");
                String ev_con=s.nextLine();
                System.out.print("Need Service or not(yes/no):");
                String ev_service=s.nextLine();
                System.out.print("Vehicle Available or Not(yes/no):");
                String ev_available=s.nextLine();
                addVehicle(ev_name,ev_number,ev_type,ev_price,ev_km,ev_con,ev_service,ev_available);
                break;
                case 3:
                break;
                default:
                System.out.println("Invalid");
                break;
            }
            break;
            case 2:
            System.out.println("Updating vehicle details");
                    System.out.println();
                    displayVehicle();
                    System.out.println();
                    System.out.println("Enter vehcile number:");
                    String vehicle_number=s.nextLine();
                    int i=0;
                    do{
                        System.out.println("1.To Add Vehicle  || 2.To Update Vehicle  || 3.To Delete Vehicle  || 4.Display Vehicles  || 5.Search Vehicle  || 6.Display Rented Vehicle || 7.Display Fines || 8.Handle Users");
                        i=s.nextInt();
                        s.nextLine();
                        switch(i){
                            case 1:
                                System.out.println("Enter New Vehicle Name:");
                                String new_vehicleName=s.nextLine();
                                updateVehicleDetails(vehicle_number, new_vehicleName, "vehicle_name");
                                break;
                            case 2:
                                System.out.println("Enter New Vehicle Number:");
                                String v_new_number=s.nextLine();
                                updateVehicleDetails(vehicle_number, v_new_number, "vehicle_number");
                                break;
                            case 3:
                                System.out.println("Enter New Vehicle Type:");
                                String new_vehicleType=s.nextLine();
                                updateVehicleDetails(vehicle_number, new_vehicleType, "vehicle_type");
                                break;
                            case 4:
                                System.out.println("Enter New Vehicle Rent Price:");
                                String new_rent_price=s.nextLine();
                                updateVehicleDetails(vehicle_number, new_rent_price, "vehicle_price");
                                break;
                            case 5:
                                System.out.println("Enter New Vehicle Kilometer_Run:");
                                String new_vh_run=s.nextLine();
                                updateVehicleDetails(vehicle_number, new_vh_run, "vehicle_km");
                                break;
                            case 6:
                                System.out.println("Enter New Vehicle Quality:");
                                String new_vehicle_quality=s.nextLine();
                                updateVehicleDetails(vehicle_number, new_vehicle_quality, "vehicle_condition");
                                break;
                            case 7:
                                System.out.println("Enter New Vehicle Service Need:");
                                String new_vehicle_service=s.nextLine();
                                updateVehicleDetails(vehicle_number, new_vehicle_service, "isNeedService");
                                break;
                            case 8:
                                break;
                            default:
                                System.out.println("Invalid Options");
                                break;
                        }         
                    }
                    while(i!=8);
                    break;
            case 3:
            displayVehicle();
            System.out.println("Enter Vehicle Number:");
            String d_number=s.nextLine();
            deleteVehicle(d_number);
            break;
            case 4:
            System.out.println("Displaying vehicles");
                    int choice=0;
                    do{
                        System.out.println("1.Display vehicle by name  || 2.Display vehcile by number  || 3.Disply by service need  || 4.Display by price");
                        ch=s.nextInt();
                        s.nextLine();
                        switch(ch){
                            case 1:
                                display("vehicle_name");
                                break;
                            case 2:
                                display("vehicle_number");
                                break;
                            case 3:
                                display("isNeedService");
                                break;
                            case 4:
                                display("vehicle_price");
                                break;
                            case 5:
                                break;
                            default:
                                System.out.println("Invalid Options");
                                break;
                        }
                    }
                    while(choice!=5);
            break;
            case 5:
            System.out.println("Search vehicles");
                    int c=0;
                    do{
                        System.out.println("1.Search vehicle by name || 2.Search vehicle by number");
                        c=s.nextInt();
                        s.nextLine();
                        switch(c){
                            case 1:
                                System.out.println("Enter vehicle name:");
                                String vehicle_name=s.nextLine();
                                search(vehicle_name,"vehcile_name");
                                break;
                            case 2:
                                System.out.println("Enter vehicle number:");
                                String vehicle_Number=s.nextLine();
                                search(vehicle_Number,"vehcile_number");
                                break;
                            case 3:
                                break;
                            default:
                                System.out.println("Invalid options");
                                break;
                        }
                    }
                    while(c!=3);
                    break;
            case 6:
             //displayRentedVehicles();
            break;
            case 7:
              //displayFines();
            break;
            case 8:
            System.out.println("Updating user details...");
            System.out.println("Enter email-id");
            String email=s.nextLine();
            int j=0;
            do{
                System.out.println("1.To Update Username  || 2.To Update Email  || 3.To Update Mobile_Number  || 4.To Update Aadhaar  || 5.To Update License Number  || 6.To Update Address || 7.To Update Security deposit ");
                j=s.nextInt();
                s.nextLine();
                switch(j){
                    case 1:
                        System.out.println("Enter new Username:");
                        String newUsername=s.nextLine();
                        updateUserDetails(email, newUsername, "name");
                        break;
                    case 2:
                        System.out.println("Enter new email:");
                        String newEmail=s.nextLine();
                        updateUserDetails(email, newEmail, "email");
                        break;
                    case 3:
                        System.out.println("Enter new Mobile Number:");
                        String newMobileNumber=s.nextLine();
                        updateUserDetails(email, newMobileNumber, "mobile");
                        break;
                    case 4:
                        System.out.println("Enter new Aadhaar number:");
                        String newAadhaars=s.nextLine();
                        updateUserDetails(email, newAadhaars, "aadhaar_no");
                        break;
                    case 5:
                        System.out.println("Enter new License number:");
                        String license=s.nextLine();
                        updateUserDetails(email, license, "license_number");
                        break;
                    case 6:
                        System.out.println("Enter New Address:");
                        String address=s.nextLine();
                        updateUserDetails(email, address, "address");
                        break;
                    case 7:
                        System.out.println("Enter New Security deposit:");
                        String amount=s.nextLine();
                        updateUserDetails(email, amount, "security_deposit");
                        break;
                    case 8:
                        break;
                    default:
                        System.out.println("Invalid Options");
                        break;
                }
            }
            while(j!=8);
            break;
            case 9:
            break;
            default:
            System.out.println("Invalid Options");
        }
    }while(ch!=9);
   }
}