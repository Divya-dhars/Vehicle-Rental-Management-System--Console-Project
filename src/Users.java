import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Users{
    private Connection con=null;
    public String[] fetchVehicleDetails(String str)throws Exception{
        String[] arr=new String[4];
        Statement s = con.createStatement();
        String query = "SELECT * FROM vehicle_details WHERE vehicle_name = '" + str + "' OR vehicle_number = '" + str + "' AND Available_sts='yes'";
        ResultSet resultSet = s.executeQuery(query);
        while (resultSet.next()) {
            arr[0] = resultSet.getString("vehicle_name");
            arr[1] = resultSet.getString("vehicle_number");
            arr[2]= resultSet.getString("rent_price");
            arr[3]= resultSet.getString("type");
        }
        return arr;
    }
    public void rentVehicle(String username,String email,String mobile,String vehicle_name,String vehicle_number,String rent_price,String type)throws Exception{
        Statement st=con.createStatement();
        int minDeposit=0;
        if("bike".equals(type)){
            minDeposit=3000;
        }
        else if("car".equals(type)){
            minDeposit=10000;
        }
        String q1="SELECT * FROM register WHERE email='"+email+"'";
        ResultSet rs=st.executeQuery(q1);
        if(rs.next()){
            int userDeposit=rs.getInt("security");
            if(userDeposit<minDeposit){
                System.out.println("Insufficient security deposit");
            }
            else{
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date currentDate = new Date();
                String rentDate = dateFormat.format(currentDate);
                long returnDateTime = currentDate.getTime() + (24 * 60 * 60 * 1000); 
                Date returnDate = new Date(returnDateTime);
                String returnDateString = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(returnDate);
                String rentQuery = "INSERT INTO order_details(vehicle_name,vehicle_number,vehicle_type,vehicle_user,email,mobile,rent_date,return_date) VALUES('" + vehicle_name + "', '" + vehicle_number + "', '" + type+ "', '" +username+ "', '" + email+ "', '"+mobile+"','"+rentDate+"','"+returnDateString+")";
                int inserted = st.executeUpdate(rentQuery);
                if (inserted == 1) {
                    System.out.println("Rented vehicle");
                }
            }
        }
    }
    public void updateVehicleRented(String rented,String vehicle_number)throws Exception{
        Statement st=con.createStatement();
        String query="UPDATE vehicle_details SET Available_sts ='"+rented+"' WHERE vehicle_number='"+vehicle_number+"'";
        int updated=st.executeUpdate(query);
        if(updated==1){
            System.out.println("Vehicle Rented Status updated successfully");
        }
    }
    public void addToCart(String vehicle_name,String vehicle_number,String vehicle_price,String vehicle_user,String email,String mobile)throws Exception{
        Statement s = con.createStatement();
        String query = "INSERT INTO cart(vehicle_name,vehicle_number,vehicle_price,vehicle_user,email,mobile) " +"VALUES('" + vehicle_name + "', '" + vehicle_number + "','"+vehicle_price+"','"+vehicle_user+"','"+email+"','"+mobile+"')";
        int inserted = s.executeUpdate(query);
        if (inserted == 1) {
            System.out.println("Added to cart");
        }
    }
    public String displayFinesToPay(String email)throws Exception{
        String fines="";
        Statement st=con.createStatement();
        String query="SELECT fines FROM payment WHERE email='" + email + "'";
        ResultSet resultSet = st.executeQuery(query);
        while (resultSet.next()) {
            fines+= resultSet.getString("fines");
            System.out.println("Fines to Pay: " + fines);
        }
        return fines;
    }
    public void payFines(String email,String val,String fines)throws Exception{
        double fineVal=Double.parseDouble(val);
        double currentFines=Double.parseDouble(fines);
        Statement st=con.createStatement();
        String query="UPDATE payment SET fines='"+(currentFines-fineVal)+"'' WHERE email='" + email+"'";        int rowsAffected = st.executeUpdate(query);
        if (rowsAffected > 0) {
            System.out.println("Fines paid successfully");
        } else {
            System.out.println("User not found or no fines to pay for user: " + email);
        }
    }
    public boolean canExtendRent(String email) throws SQLException {
        Statement st=con.createStatement();
        ResultSet resultSet=st.executeQuery("SELECT rent_count FROM orders_details WHERE email = '" +email+ "'");
        if (resultSet.next()) {
            int rentCount = resultSet.getInt("rent_count");
            return rentCount <= 2;
        }
        return false;
    }
    public void extendRent(String email, int numberOfDays) throws Exception {
        try {
            Statement s=con.createStatement();
            String updateQuery="UPDATE orders SET return_date = DATE_ADD(return_date, INTERVAL " + numberOfDays + " DAY), rent_count = rent_count + 1 WHERE email = '" + email + "'";
            int updated=s.executeUpdate(updateQuery);
            if(updated==1){
                System.out.println("Rent extended successfully.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void viewCart(String email)throws Exception{
        Statement st=con.createStatement();
        String query = "SELECT * FROM cart WHERE email = '" + email + "'";
        ResultSet res = st.executeQuery(query);
        while (res.next()){
            System.out.println(res.getString(3)+" "+res.getString(4)+" "+res.getString(5)+" "+res.getString(6));
        }
    }
    public void removeAllVehicle(String email)throws Exception{
        Statement st=con.createStatement();
        String query="DELETE FROM cart WHERE email='"+email+"'";
        int deleted=st.executeUpdate(query);
        if(deleted==1){
            System.out.println("deleted");
        }
        else{
            System.out.println("Error in deleting");
        }
    }
    public void removeVehicle(String number,String email)throws Exception{
        Statement st=con.createStatement();
        String query="DELETE FROM cart WHERE email = '" + email + "' AND vehicle_number = '" + number + "'";
        int rowsAffected = st.executeUpdate(query);
        if (rowsAffected > 0) {
            System.out.println("Vehicle removed successfully from the cart for user: " + email);
        } else {
            System.out.println("Vehicle not found in the cart or user not found: " + email);
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
            System.out.println("Enter Vehicle Name (or) Vehcile_Number:");
            String str=s.nextLine();
            String[] arr=fetchVehicleDetails(str);
            String vehicle_name=arr[0];
            String vehicle_number=arr[1];
            String vehicle_price=arr[2];
            String type=arr[3];
            System.out.println("1.To Rent a vehicle  || 2.Add vehicle to cart");
            int k=s.nextInt();
            s.nextInt();
            switch(k){
                case 1:
                    rentVehicle(username,user_email,mobile,vehicle_name,vehicle_number,vehicle_price,type);
                    updateVehicleRented("YES",vehicle_number);
                    break;
                case 2:
                    addToCart(vehicle_name,vehicle_number,vehicle_price,username,user_email,mobile);
                    updateVehicleRented("YES",vehicle_number);
                    break;
                default:
                    System.out.println("Invalid options");
                    break;
            }
            break;
            case 3:
            String fines=displayFinesToPay(user_email);
            System.out.println("Enter fine amount:");
            String val=s.nextLine();
            payFines(user_email,val,fines);
            break;
            case 4:
            System.out.println("Do you need to extend your rent for an extra day?");
                    System.out.println("1. Yes, extend rent || 2. Cancel extension");
                    int n=s.nextInt();
                    switch (n) {
                        case 1:
                            if (canExtendRent(user_email)) {
                                extendRent(user_email, 1);
                            } else {
                                System.out.println("Sorry, the vehicle cannot be rented for more than 2 days consecutively.");
                            }
                            break;
                        case 2:
                            break;
                        default:
                            System.out.println("Invalid options");
                            break;
                    }
                    break;   
            case 5:
            viewCart(user_email);
                    System.out.println("1.Remove all from cart || 2.Enter a vehicle number to remove");
                    int number=s.nextInt();
                    switch(number){
                        case 1:
                            removeAllVehicle(user_email); 
                            break;
                        case 2:
                            System.out.println("Enter vehicle number:");
                            String num=s.nextLine();
                            removeVehicle(num,user_email);
                            break;
                        default:
                            System.out.println("Invalid options");
                            break;
                    }
                    break;

          };
        }while(ch!=8);
    }
}
