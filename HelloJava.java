import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
import java.lang.*;

class Main {
    public static void main(String[] args) {
        String name, Njobtype, Nlocation, Nworkingshift, a;
        int Nsalary, Nworkinghours,s;

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your Name");
        name = sc.nextLine();
        System.out.println("Enter needed Job Type");
        Njobtype = sc.nextLine();
        System.out.println("Enter the Need Salary");
        Nsalary = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the Need Location");
        Nlocation = sc.nextLine();
        System.out.println("Enter your Working Hours");
        Nworkinghours = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter your Working Shift");
        Nworkingshift = sc.nextLine();
        System.out.println("Employee Details have been collected\n\n");

        try {
            Scanner sc1 = new Scanner(System.in);
            String b;
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con1 = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/publicemp", "root", "12345");
            Statement stmt1 = con1.createStatement();

            Connection con2=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/publicemp","root","12345");
            Statement stmt2=con2.createStatement();

            Connection con3 = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/publicemp", "root", "12345");
            Statement stmt5 = con3.createStatement();

            String query1 = "select * from pubemp";
            ResultSet s1 = stmt1.executeQuery(query1);

            String query2 = "select * from salarydetail";
            ResultSet s2 = stmt2.executeQuery(query2);

            String query3 = "select * from pubemp";
            ResultSet s3 = stmt5.executeQuery(query3);

            while (s1.next()) {
                String jt1 = s1.getString("JobType");
                String lc = s1.getString("Location");
                int wh = s1.getInt("WorkingHours");
                int sal = s1.getInt("Salary");
                String ws = s1.getString("WorkingShift");

                if ((jt1.equals(Njobtype)) && (Nworkinghours >= wh) && (ws.equals(Nworkingshift))) {
                    System.out.println("You have been assigned to the Job");
                    String sql = "UPDATE pubemp SET Assign='" + name + "'WHERE JobType='" + jt1 + "'";
                    System.out.println("\nWilling to join the company? (yes/no)");
                    b=sc1.nextLine();
                    if(b.equals("yes")){
                        String sql1 = "UPDATE pubemp SET Available='" + b + "'WHERE JobType='" + jt1 + "'";
                        PreparedStatement stmt3 = con1.prepareStatement(sql1);
                        stmt3.executeUpdate(sql1);
                        System.out.println("Your availability has been updated");
                    }
                    if(b.equals("no")){
                        String sql2 = "delete from pubemp where Assign ='"+name+"'";
                        PreparedStatement preparedStmt = con1.prepareStatement(sql2);
                        preparedStmt.execute(sql2);

                        int rs=stmt5.executeUpdate("INSERT INTO pubemp(JobType,Salary,Location,WorkingHours,WorkingShift) VALUES('"+jt1+"','"+sal+"','"+lc+"','"+wh+"','"+ws+"')");
                        String sql3 = "UPDATE pubemp SET Assign = null WHERE '"+name+"'";
                        PreparedStatement stmt4 = con1.prepareStatement(sql3);
                        stmt4.executeUpdate(sql3);
                        System.out.println("You have been removed from the assigned job");
                    }
                    PreparedStatement stmt = con1.prepareStatement(sql);
                    stmt.executeUpdate(sql);

                    if (b.equals("yes")) {
                        System.out.println("Salary Payment Details");
                        System.out.println("1.Bank Account");
                        System.out.println("2.UPI");
                        System.out.println("3.Debit Card");
                        System.out.println("Enter the desired payment option");
                        s= sc.nextInt();
                        switch (s) {
                            case 1:
                                System.out.println("Redirecting to Banking Website......");
                                int rs1=stmt2.executeUpdate("INSERT INTO salarydetail(Name,Salary) VALUES('"+name+"','"+sal+"')");
                                System.out.println("Your salary amount "+sal+" is sent to your Bank Account");
                                break;
                            case 2:
                                System.out.println("Redirecting to UPI Payment Store......");
                                int rs2=stmt2.executeUpdate("INSERT INTO salarydetail(Name,Salary) VALUES('"+name+"','"+sal+"')");
                                System.out.println("Your salary amount "+sal+" is sent to your UPI Account");
                                break;
                            case 3:
                                System.out.println("Redirecting to Debit Payment Details......");
                                int rs3=stmt2.executeUpdate("INSERT INTO salarydetail(Name,Salary) VALUES('"+name+"','"+sal+"')");
                                System.out.println("Your salary amount "+sal+" is sent to your Debit Card");
                                break;
                        }
                    }
                    else {
                        System.out.println("Retry");
                    }
                }

            }
            con1.close();
            con2.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}