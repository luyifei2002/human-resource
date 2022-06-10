/**
 * Copyright (C), 2021-2022, 我是你爸爸有限公司
 * FileName: table_data
 * Author:   Administrator
 * Date:     2022/6/8 10:45
 * Description: 表格的信息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package dataClass;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Objects;

/**
 * 〈一句话功能简述〉 
 * 〈表格的信息〉
 *
 * @author Administrator
 * @create 2022/6/8
 * @since 1.0.0
 */
public class tableData {
    public Object[][] table_data;
    public String[] table_name;
    public int row_num, col_num;
    public int[] col_size;

    public Object[][] getTable_data(){return table_data;}
    public String[] getTable_name(){return table_name;}

    public boolean is_contain_id(String staff_id){
        for (int i=0;i<row_num;i++){
            if (Objects.equals(table_data[i][0],staff_id)){
                return true;
            }
        }
        return false;
    }

    public boolean is_contain_name(String name){
        for (int i=0;i<row_num;i++){
            if (Objects.equals(table_data[i][1],name)){
                return true;
            }
        }
        return false;
    }

    public tableData(ResultSet rs){
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.last();
            row_num = rs.getRow();
            col_num = rsmd.getColumnCount();
            System.out.println("row_num = "+row_num+" col_num:"+col_num);

            table_data = new Object[row_num][col_num];
            table_name = new String[col_num];
            col_size = new int[col_num];

            for (int i=0;i<col_num;i++){
                table_name[i] = rsmd.getColumnName(i+1);

//                System.out.println(table_name[i]);
            }

            rs.first();
            for (int i=0;i<row_num;i++){
                for (int j=0;j<col_num;j++){
                    table_data[i][j] = rs.getString(j+1);
//                    System.out.print(table_data[i][j]+" ");
                }
                rs.next();
//                System.out.println();
            }

            rs.first();
            for (int i = 0;i<col_num;i++){
                col_size[i] = table_name[i].length();
                do {
                    if (rs.getString(i+1) != null)
                        col_size[i] = Math.max(col_size[i],rs.getString(i+1).length());
                }while (rs.next());
//                System.out.print(col_size[i]+"  ");
                rs.first();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}