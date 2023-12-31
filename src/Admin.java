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
public void displayByName() throws Exception{
    try{
      Statement st=con.createStatement();
      String query="select * from vehicle orderby v_name asc";
       ResultSet rs=st.executeQuery(query);
       while(rs.next()){
        System.out.println(rs.getInt(1)+ " "+rs.getString(2) + " " +rs.getString(3) +  " "+ rs.getString(4)+ " "+rs.getString(5)+ " "+ rs.getInt(6) + " "+ rs.getString(7));
       }
    }catch(Exception e){
        System.out.print(e);
    }
}
public void displayByNumber() throws Exception{
    try{
      Statement st=con.createStatement();
      String query="select * from vehicle orderby v_number asc";
       ResultSet rs=st.executeQuery(query);
       while(rs.next()){
        System.out.println(rs.getInt(1)+ " "+rs.getString(2) + " " +rs.getString(3) +  " "+ rs.getString(4)+ " "+rs.getString(5)+ " "+ rs.getInt(6) + " "+ rs.getString(7));
       }
    }catch(Exception e){
        System.out.print(e);
    }
}
public void displayByService() throws Exception{
    try{
      Statement st=con.createStatement();
      String query="select * from vehicle v_service='yes'";
       ResultSet rs=st.executeQuery(query);
       while(rs.next()){
        System.out.println(rs.getInt(1)+ " "+rs.getString(2) + " " +rs.getString(3) +  " "+ rs.getString(4)+ " "+rs.getString(5)+ " "+ rs.getInt(6) + " "+ rs.getString(7));
       }
    }catch(Exception e){
        System.out.print(e);
    }
}
public void displayByPrice() throws Exception{
    try{
      Statement st=con.createStatement();
      String query="select * from vehicle orderby v_price asc";
       ResultSet rs=st.executeQuery(query);
       while(rs.next()){
        System.out.println(rs.getInt(1)+ " "+rs.getString(2) + " " +rs.getString(3) +  " "+ rs.getString(4)+ " "+rs.getString(5)+ " "+ rs.getInt(6) + " "+ rs.getString(7));
       }
    }catch(Exception e){
        System.out.print(e);
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
            break;
            case 3:
            displayVehicle();
            System.out.println("Enter Vehicle Number:");
            String d_number=s.nextLine();
            deleteVehicle(d_number);
            break;
            case 4:
            displayByName();
            break;
            case 5:
            displayByNumber();
            break;
            case 6:
            displayByService();
            break;
            case 7:
            displayByPrice();
            break;
            case 8:
            break;
            


        }
    }while(ch!=4);
   }
}