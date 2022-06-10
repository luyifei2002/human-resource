/**
 * Copyright (C), 2021-2022, 我是你爸爸有限公司
 * FileName: user_password
 * Author:   Administrator
 * Date:     2022/6/6 21:38
 * Description: 用户名密码及用户类型
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package dataClass;

import java.util.HashMap;

/**
 * 〈一句话功能简述〉 
 * 〈用户名密码及用户类型〉
 *
 * @author Administrator
 * @create 2022/6/6
 * @since 1.0.0
 */
public class userPasswordUnit {
    public String user;
    public String password;
    public int role; //1:员工   2:部门经理 3:人事经理

    private static HashMap<String, Integer> emp_id_role;

    private static void init(){
        emp_id_role = new HashMap<String, Integer>();
        emp_id_role.put("200",2);
        emp_id_role.put("201",2);
        emp_id_role.put("114",2);
        emp_id_role.put("203",3);   //人事部的
        emp_id_role.put("121",2);
        emp_id_role.put("103",2);
        emp_id_role.put("204",2);
        emp_id_role.put("145",2);
        emp_id_role.put("100",2);
        emp_id_role.put("108",2);
        emp_id_role.put("205",2);
    }

    public userPasswordUnit(){
        init();
    }

    public userPasswordUnit(String USER, String PASSWORD){
        init();
        user = USER;
        password = PASSWORD;
        if (emp_id_role.containsKey(USER))
            role = emp_id_role.get(USER);
        else role = 1;
    }

    public String toString(){
        String res = "user:" + user +"  password:" + password + " role:" + role;
        return res;
    }

}