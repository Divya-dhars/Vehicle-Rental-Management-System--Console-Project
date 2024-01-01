import java.util.*;
import java.sql.*;
public class Users{
    public void print(String username,String user_email,String mobile) throws Exception{
        Scanner s=new Scanner(System.in);
        Admin ad=new Admin();
        int ch;
        do{
          System.out.println("1.Display Available Vehicles ||  2.Rent a vehicle || 3.Pay Fines||4.Extend rent || 5.View Cart || 6.Payment || 7.View History");
          ch=s.nextInt();
          s.nextLine();
          switch(ch){
            case 1:
             ad.displayVehicle();
             break;
           
          }
        }while(ch!=8);
    }
}
