/**
 * Copyright (C), 2021-2022, 我是你爸爸有限公司
 * FileName: mySwing
 * Author:   Administrator
 * Date:     2022/6/8 9:42
 * Description: GUI
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package swingDemo;

/**
 * 〈一句话功能简述〉 
 * 〈GUI〉
 *
 * @author Administrator
 * @create 2022/6/8
 * @since 1.0.0
 */
import gaussdb.*;

import javax.swing.*;
import java.awt.*;

public class mySwingDemo {

    public static void place_login_panel(JPanel login_panel){
        login_panel.setLayout(null);

        JLabel user_label = new JLabel("user:"), password_label = new JLabel("password:");
        JTextField user_text = new JTextField(20), password_text = new JTextField(20);
        JButton login_button = new JButton("login");

        user_label.setBounds(10,20,80,25);
        password_label.setBounds(10,50,80,25);

        user_text.setBounds(100,20,150,25);
        password_text.setBounds(100,50,150,25);

        login_button.setBounds(200,100,80,25);

        login_panel.add(user_label);
        login_panel.add(user_text);
        login_panel.add(password_label);
        login_panel.add(password_text);
        login_panel.add(login_button);
    }

    public static void show_login(){
        //确保一个漂亮的外观风格
        JFrame.setDefaultLookAndFeelDecorated(true);

        //创建一个JFrame实例
        JFrame frame = new JFrame("Login");

        //设置框体的宽和高
        frame.setSize(350,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(500,300);

        //创建面板
        JPanel login_panel = new JPanel();

        place_login_panel(login_panel);

        //添加面板
        frame.add(login_panel);

        //部署部件
        place_login_panel(login_panel);

        //设置可见
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        show_login();
    }


}