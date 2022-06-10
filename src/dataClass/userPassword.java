/**
 * Copyright (C), 2021-2022, 我是你爸爸有限公司
 * FileName: userPassword
 * Author:   Administrator
 * Date:     2022/6/8 11:52
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package dataClass;

import java.util.ArrayList;
import java.util.Objects;

import gaussdb.*;

/**
 * 〈一句话功能简述〉 
 * 〈〉
 *
 * @author Administrator
 * @create 2022/6/8
 * @since 1.0.0
 */
public class userPassword {
    private static ArrayList<userPasswordUnit> data;

    public static void init(){
        data = gaussdb.get_user_password();
    }

    public static boolean is_right(String user,String password){
        for (userPasswordUnit i : data)
            if (Objects.equals(i.user, user) & Objects.equals(i.password, password))
                return true;
        return false;
    }

    public static int get_role(String staff_id){
        for (userPasswordUnit i : data) {
            if (Objects.equals(i.user, staff_id))
                return i.role;
        }
        return 0;
    }

}