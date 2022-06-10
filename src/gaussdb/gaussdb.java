/*
  Copyright (C), 2021-2022, 我是你爸爸有限公司
  FileName: gaussdb
  Author:   Administrator
  Date:     2022/6/6 17:23
  Description: 连接gaussdb的类
  History:
  <author>          <time>          <version>          <desc>
  作者姓名           修改时间           版本号              描述
 */
package gaussdb;

import dataClass.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * 〈一句话功能简述〉 
 * 〈连接gaussdb的类〉
 *
 * @author Administrator
 * @create 2022/6/6
 * @since 1.0.0
 */
public class gaussdb {
    //数据库的相关参数及变量
    static final String username = "luyuxiang";
    static final String password = "bigyuxiang@123";
    static final String driver = "org.postgresql.Driver";
    static final String sourceURL = "jdbc:postgresql://121.36.10.158:26000/human_resource";
    static Connection conn = null;
    static Statement stmt = null;

    //初始化
    public static void init(){
        try {
            //注册JDBC驱动
            Class.forName("org.postgresql.Driver");
            //打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(sourceURL, username, password);
            //执行查询
            System.out.println("实例化Statement对象...");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        } catch (Exception se){
            se.printStackTrace();
        }

    }

    //关闭连接
    public static void close(){
        try{
            if(stmt!=null)stmt.close();
        }catch (SQLException se2){
            se2.printStackTrace();
        }
        try{
            if(conn!=null)conn.close();
        }catch (SQLException se3){
            se3.printStackTrace();
        }

    }

    //打印表
    public static void print_table_query(String table_name){
        try{
            String sql = "SELECT * FROM " + table_name + ";";   //执行查询
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            ArrayList<String> printFormat = new ArrayList<>();
            for (int i = 1; i <= columnsNumber; i++) {
                int maxSize = rsmd.getColumnName(i).length();
                //计算占位符
                rs.first();
                do {
                    if (rs.getString(i) != null)
                        maxSize = Math.max(maxSize, rs.getString(i).length());
                }while (rs.next());
                printFormat.add("%-" + maxSize + "s | ");
                System.out.printf(printFormat.get(i-1), rsmd.getColumnName(i));
            }
//            System.out.format("%s", printFormat);
            System.out.print("\r\n");

            rs.first();
            do{
                for (int i = 1; i <= columnsNumber; i++){
                    System.out.printf(printFormat.get(i-1), rs.getString(i));
                }
                System.out.print("\r\n");
            }while (rs.next());
            rs.close();

        }catch (SQLException se){
            se.printStackTrace();
        }
        System.out.println("---------------------------------");
    }

    public static ArrayList<userPasswordUnit> get_user_password() {
        ArrayList<userPasswordUnit> res = new ArrayList<userPasswordUnit>();
        try{
            String sql = "SELECT staff_id,password FROM staffs;";
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();
            do{
                String user_ = rs.getString(1), passw_ = rs.getString(2);
                userPasswordUnit res_unit = new userPasswordUnit(user_, passw_);
                res.add(res_unit);
                System.out.println(res_unit);
            }while (rs.next());

//            System.out.println(res);
            rs.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }
        return res;
    }

    public static ResultSet get_1_data(int staff_id){
        ResultSet res = null;
        try {
            String sql = "SELECT * FROM staffs WHERE staff_id = " + staff_id + ";";
            res = stmt.executeQuery(sql);
        }catch (SQLException se){
            se.printStackTrace();
        }
        return res;
    }

    public static ResultSet get_2_data(int staff_id, boolean order, boolean order_by){
        ResultSet res = null;
        try {
            String sql = "SELECT * FROM staffs WHERE section_id = (SELECT section_id FROM staffs WHERE staff_id = "+staff_id+" ) order by "+(order_by?"staff_id":"salary")+" "+(order?"asc;":"desc;");
            res = stmt.executeQuery(sql);
        }catch (SQLException se){
            se.printStackTrace();
        }
        return  res;
    }

    public static String get_2_data_max_salary(int staff_id){
        String res = "";
        try {
            String sql = "SELECT round(max(salary),2) FROM staffs WHERE section_id = (SELECT section_id FROM staffs WHERE staff_id = " + staff_id + ") ;";
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();
            res = rs.getString(1);
        }catch (SQLException se){
            se.printStackTrace();
        }
        return res;
    }

    public static String get_2_data_min_salary(int staff_id){
        String res = "";
        try {
            String sql = "SELECT round(min(salary),2) FROM staffs WHERE section_id = (SELECT section_id FROM staffs WHERE staff_id = " + staff_id + ") ;";
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();
            res = rs.getString(1);
        }catch (SQLException se){
            se.printStackTrace();
        }
        return res;
    }

    public static String get_2_data_avg_salary(int staff_id){
        String res = "";
        try {
            String sql = "SELECT round(avg(salary),2) FROM staffs WHERE section_id = (SELECT section_id FROM staffs WHERE staff_id = " + staff_id + ") ;";
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();
            res = rs.getString(1);
        }catch (SQLException se){
            se.printStackTrace();
        }
        return res;
    }

    public static ResultSet get_2_data_id(String staff_id){
        ResultSet res = null;
        try {
            String sql = "SELECT * FROM staffs WHERE staff_id = " + staff_id + ";";
            res = stmt.executeQuery(sql);
        }catch (SQLException se){
            se.printStackTrace();
        }
        return res;
    }

    public static ResultSet get_2_data_name(String name){
        ResultSet res = null;
        try {
            String sql = "SELECT * FROM staffs WHERE first_name = '" + name + "';";
            res = stmt.executeQuery(sql);
        }catch (SQLException se){
            se.printStackTrace();
        }
        return res;
    }

    public static ResultSet get_3_data(String section_id,boolean order,boolean order_by){
        ResultSet res = null;
        try {
            String sql = "";
            if (Objects.equals(section_id,"0")){
                sql = "SELECT * FROM staffs ORDER by "+(order_by?"staff_id":"salary")+" "+(order?"asc;":"desc;");
            }else {
                sql = "SELECT * FROM staffs WHERE section_id = "+section_id+" ORDER by "+(order_by?"staff_id":"salary")+" "+(order?"asc;":"desc;");
            }
            res = stmt.executeQuery(sql);
        }catch (SQLException se){
            se.printStackTrace();
        }
        return  res;
    }

    public static String get_3_data_max_salary(String section_id){
        String res = "";
        try {
            String sql = "";
            if (Objects.equals(section_id,"0")){
                sql = "SELECT round(max(salary),2) FROM staffs"+";";
            }else{
                sql = "SELECT round(max(salary),2) FROM staffs WHERE section_id = "+section_id+";";
            }
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();
            res = rs.getString(1);
        }catch (SQLException se){
            se.printStackTrace();
        }
        return res;
    }

    public static String get_3_data_min_salary(String section_id){
        String res = "";
        try {
            String sql = "";
            if (Objects.equals(section_id,"0")){
                sql = "SELECT round(min(salary),2) FROM staffs"+" ;";
            }else{
                sql = "SELECT round(min(salary),2) FROM staffs WHERE section_id = "+section_id+";";
            }
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();
            res = rs.getString(1);
        }catch (SQLException se){
            se.printStackTrace();
        }
        return res;
    }

    public static String get_3_data_avg_salary(String section_id){
        String res = "";
        try {
            String sql = "";
            if (Objects.equals(section_id,"0")){
                sql = "SELECT round(avg(salary),2) FROM staffs"+" ;";
            }else{
                sql = "SELECT round(avg(salary),2) FROM staffs WHERE section_id = "+section_id+" ;";
            }
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();
            res = rs.getString(1);
        }catch (SQLException se){
            se.printStackTrace();
        }
        return res;
    }

    public static ResultSet get_3_data_salary(){
        ResultSet res = null;
        try {
            String sql = "\n" +
                    "select B.section_id,B.section_name,A.max_salary,A.min_salary,A.avg_salary " +
                    "from (" +
                            "select section_id," +
                                    "round(max(salary),2) max_salary," +
                                    "round(min(salary),2) min_salary," +
                                    "round(avg(salary),2) avg_salary " +
                            "from staffs group by section_id order by section_id" +
                        ") A," +
                    "sections B " +
                    "where A.section_id = B.section_id;";
            res = stmt.executeQuery(sql);
        }catch (SQLException se){
            se.printStackTrace();
        }
        return res;
    }

    public static String[] get_3_data_section_list(){
        String[] res = null;
        try {
            String sql = "select B.section_name from " +
                            "(select section_id from staffs group by section_id order by section_id) " +
                            "A,sections B " +
                            "where A.section_id = B.section_id;";
            ResultSet rs = stmt.executeQuery(sql);
            rs.last();
            int row_num = rs.getRow();
            res = new String[row_num+1];
            res[0]="all";
            rs.first();
            for (int i=1;i<=row_num;i++){
                res[i] = rs.getString(1);
                rs.next();
            }
        }catch (SQLException se){
            se.printStackTrace();
        }
        return  res;
    }

    public static ResultSet get_3_data_section(){
        ResultSet res = null;
        try {
            String sql = "SELECT * FROM sections order by section_id;";
            res = stmt.executeQuery(sql);
        }catch (SQLException se){
            se.printStackTrace();
        }
        return res;
    }

    public static ResultSet get_3_data_place(){
        ResultSet res = null;
        try {
            String sql = "SELECT * FROM places ORDER by place_id;";
            res = stmt.executeQuery(sql);
        }catch (SQLException se){
            se.printStackTrace();
        }
        return res;
    }

    public static ResultSet get_3_data_employment_history(){
        ResultSet res = null;
        try {
            String sql = "SELECT * FROM employment_history ORDER by staff_id;";
            res = stmt.executeQuery(sql);
        }catch (SQLException se){
            se.printStackTrace();
        }
        return res;
    }

    public static ResultSet get_3_data_employment_history_by_staff_id(String staff_id){
        ResultSet res = null;
        try {
            String sql = "SELECT * FROM employment_history WHERE staff_id = "+staff_id+";";
            res = stmt.executeQuery(sql);
        }catch (SQLException se){
            se.printStackTrace();
        }
        return res;
    }

    public static String get_section_id_by_section_name(String section_name){
        String res = null;
        try {
            String sql = "SELECT section_id FROM sections WHERE section_name = '"+section_name+"';";
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();
            res = rs.getString(1);
        }catch (SQLException se){
            se.printStackTrace();
        }
        return res;
    }

    public static String get_name_by_staff_id(int staff_id){
        String res = "";
        try{
            String sql = "SELECT first_name,last_name FROM staffs WHERE staff_id = "+staff_id+";";
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();
            res += rs.getString(1)+" "+rs.getString(2);
        }catch (SQLException se){
            se.printStackTrace();
        }
        return res;
    }

    public static void update_phone(String phone_num,int staff_id){
        try {
            String sql = "UPDATE staffs SET phone_number = '"+phone_num+"' WHERE staff_id = "+staff_id+";";
            stmt.executeUpdate(sql);
        }catch (SQLException se){
            se.printStackTrace();
        }
    }

    public static void update_section_name(String section_id, String section_name){
        try {
            String sql = "UPDATE sections SET section_name = '"+section_name+"' WHERE section_id = "+section_id+";";
            stmt.executeUpdate(sql);
        }catch (SQLException se){
            se.printStackTrace();
        }
    }

    public static void insert_place(String place_id,String street_address,String postal_code,String city,String state_province,String state_id){
        try {
            String sql = "INSERT INTO places VALUES('"+place_id+"','"+street_address+"','"+postal_code+"','"+city+"','"+state_province+"','"+state_id+"');";
            stmt.execute(sql);
        }catch (SQLException se){
            se.printStackTrace();
        }
    }

    public static void main(String[] args) {
        init();
        print_table_query("staffs");
//        print_table_query("employments");
//        get_user_password();
        close();
    }

}