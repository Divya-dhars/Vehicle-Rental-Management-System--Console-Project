import java.util.*;
import java.sql.*;
class Vehicle{
    private Connection con=null;
    public Connection getConnection() throws Exception{
        if(con==null){
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/vehiclerental","root","Divya29*");
        }
        return con;
    }
    private static void loginUser(String user_email,String user_password,Connection con)throws Exception{
        Statement stt=con.createStatement();
         ResultSet res=stt.executeQuery("select * from resgister where email='"+user_email+"'");
          if(!res.next()){
            System.out.println("User is not resgistered");
          }
          else{
            res=stt.executeQuery("select * from resgister where email='"+user_email+"' and password='"+user_password+"'");
            if(res.next()){
              String defaultUser=res.getString("defaultUser");
              if(defaultUser.equals("admin")){
                 System.out.println("Admin");
                Admin ad=new Admin();
                ad.print();
              }
              else if(defaultUser.equals("user")){
                System.out.println("User");
                Users user=new Users();
                String username=res.getString("username");
                String mobile=res.getString("mobile");
                user.print(username,user_email,mobile);
              }
              else{
                System.out.print("Invalid defaultUser");
              }
            }
            else{
            System.out.print("Invalid email or password");
            }
        }
    }
    private static  void registerUser(String name,String email,String password,String addr,String mobile,String license_no,String aadhaar_no,String amount,Connection con)throws Exception{
        if(amount==null){
            System.out.println("Pay the amount 30000 to continue");
          }
          else{
            System.out.println("Payment Successful");
          }
          Statement st=con.createStatement();
          ResultSet r=st.executeQuery("select * from resgister where email='"+email+"'");
          if(r.next()){
            System.out.println("User already resgistered");
          }else{
            int newUser=st.executeUpdate("insert into resgister(name,email,password,mobile,address,license_number,aadhaar_no,security_deposit,defaultUser)values('"+name+"','"+email+"','"+password+"','"+mobile+"','"+addr+"','"+license_no+"','"+aadhaar_no+"','"+amount+"','user')");
            if(newUser>0){
              System.out.println("User registered successfully");
            }
            else{
              System.out.println("Registration failed");
            }
    }
    }
    public class VehicleRentalSystem{
        public static void main(String[] args){
            Scanner s=new Scanner(System.in);
            try{
                Vehicle v=new Vehicle();
                Connection con=v.getConnection();
                System.out.print("1.Login 2.Register");
                int n=s.nextInt();
                s.nextLine();
                switch(n){
                    case 1:
                     System.out.println("Enter email:");
                     String user_email=s.nextLine();
                     System.out.println("Enter your password");
                     String user_password=s.nextLine();
                     loginUser(user_email,user_password,con);
                     break;
                    case 2:
                    System.out.println("Enter your name:");
                    String name=s.nextLine();
                    System.out.println("Enter your email:");
                    String email=s.nextLine();
                    System.out.println("Enter your password ");
                    String password=s.nextLine();
                    System.out.println("Enter your address:");
                    String addr=s.nextLine();
                    System.out.println("Enter your mobile:");
                    String mobile=s.nextLine();
                    System.out.println("Enter your license number:");
                    String license_no=s.nextLine();
                    System.out.println("Enter your Aadhaar number:");
                    String aadhaar_no=s.nextLine();
                    System.out.println("Initially you have to pay 30000 for security deposit to register,Enter the amount to pay and continue:");
                    String amount=s.nextLine();
                    registerUser(name,email,password,addr,mobile,license_no,aadhaar_no,amount,con);
                     break;
                    default:
                      System.out.print("Invalid Option");
                      break;
                }

            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
}

