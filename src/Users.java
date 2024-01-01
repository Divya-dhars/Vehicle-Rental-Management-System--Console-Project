import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Users{
    private Connection con=null;
    public String[] fetchVehicleDetails(String str)throws Exception{
        String[] arr=new String[3];
        try{
            Statement s = con.createStatement();
            String query = "SELECT * FROM vehicle WHERE vehicle_name = '" + str + "' OR vehicle_number = '" + str + "' AND rented='0'";
            ResultSet resultSet = s.executeQuery(query);
            while (resultSet.next()) {
                arr[0] = resultSet.getString("vehicle_name");
                arr[1] = resultSet.getString("vehicle_number");
                arr[2]= resultSet.getString("rent_price");
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return arr;
    }
    public void rentVehicle(String username, String email, String mobile, String vehicle_name, String vehicle_number, String rent_price) throws Exception {
      try {
          Statement s = con.createStatement();
          String rentQuery = "INSERT INTO rented_vehicles(vehicle_name, vehicle_number, vehicle_user, email, mobile, rented) VALUES('" + vehicle_name + "', '" + vehicle_number + "', '" + username + "', '" + email + "', '" + mobile + "', '1')";
          int inserted = s.executeUpdate(rentQuery);
          if (inserted == 1) {
              System.out.println("Rented vehicle");
              SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              Date currentDate = new Date();
              String rentDate = dateFormat.format(currentDate);
              long returnDateTime = currentDate.getTime() + (24 * 60 * 60 * 1000); 
              Date returnDate = new Date(returnDateTime);
              String returnDateString = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(returnDate);
              String query1 = "INSERT INTO cart(user_email, vehicle_number, rent_date, return_date, total_amount, rent_count) VALUES('" + email + "', '" + vehicle_number + "', '" + rentDate + "', '" + returnDateString + "', '" + rent_price + "', 1)";
              s.executeUpdate(query1);
              System.out.println("Added to cart");
          }
      } catch (Exception e) {
          System.out.println(e);
      }
  }
  public void updateVehicleRented(String rented,String vehicle_number)throws Exception{
        try{
            Statement s=con.createStatement();
            String query="UPDATE vehicle SET rented ='"+rented+"' WHERE vehicle_number='"+vehicle_number+"'";
            int updated=s.executeUpdate(query);
            if(updated==1){
                System.out.println("Vehicle Rented Status updated successfully");
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public void addToCart(String email, String vehicle_number, String rent_price, int rent_count) throws Exception {
        try {
            Statement s = con.createStatement();
            String query = "INSERT INTO cart(user_email, vehicle_number, rent_date, return_date, total_amount, rent_count) " +"VALUES('" + email + "', '" + vehicle_number + "', '0000-00-00 00:00:00', '0000-00-00 00:00:00', '" + rent_price + "', " + rent_count + ")";
            int inserted = s.executeUpdate(query);
            if (inserted == 1) {
                System.out.println("Added to cart");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
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
            case 2:
            System.out.println("Enter Vehicle Name or Number:");
            String rent=s.nextLine();
            String[] arr=fetchVehicleDetails(rent);
            String vehicle_name=arr[0];
            String vehicle_number=arr[1];
            String vehicle_price=arr[2];
            System.out.print("1.To rent || 2.Add vehicle to cart");
            int choice=s.nextInt();
            s.nextLine();
            switch(choice){
               case 1:
                rentVehicle(username,user_email,mobile,vehicle_name,vehicle_number,vehicle_price);
                updateVehicleRented("1",vehicle_number);
                break;
               case 2:
               addToCart(user_email,vehicle_number,"0",0);
                break;
               default:
                System.out.print("Invalid Options");
                break;
            }
            break;
          }
        }while(ch!=8);
    }
}
