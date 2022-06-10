/**
 * Copyright (C), 2021-2022, 我是你爸爸有限公司
 * FileName: login_panel
 * Author:   Administrator
 * Date:     2022/6/8 10:15
 * Description: login界面
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package mySwing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import dataClass.*;

/**
 * 〈一句话功能简述〉 
 * 〈login界面〉
 *
 * @author Administrator
 * @create 2022/6/8
 * @since 1.0.0
 */
public class loginPanel extends JPanel {
    private static loginPanel login_panel;
    private static JLabel user_label,password_label;
    private static JTextField user_text,password_text;
    private static JButton login_button;
    private static int staff_id = 0;
    private static ArrayList<userPasswordUnit> user_password;

    public static int get(){
        return staff_id;
    }

    public loginPanel(){
        login_panel = this;
        login_panel.setLayout(null);
        login_panel.setBounds(0,0,350,200);
//        login_panel.setBackground(new Color(200,200,200));

        user_label = new JLabel("user:");
        password_label = new JLabel("password:");
        user_text = new JTextField(20);
        password_text = new JTextField(20);
        login_button = new JButton("login");

        user_label.setBounds(10,20,80,25);
        password_label.setBounds(10,50,80,25);

        user_text.setBounds(100,20,150,25);
        password_text.setBounds(100,50,150,25);

        login_button.setBounds(200,100,80,25);

        login_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = user_text.getText();
                String password = password_text.getText();

                System.out.println("user:" + user + " password:" + password);

                if (userPassword.is_right(user, password)) {
                    staff_id = Integer.parseInt(user);
                    System.out.println("you are right! " + staff_id);
                }else {
                    System.out.println("you are wrong!");
                }
            }
        });

        login_panel.add(user_label);
        login_panel.add(user_text);
        login_panel.add(password_label);
        login_panel.add(password_text);
        login_panel.add(login_button);

    }

    public static void main(String[] args) {
        new loginPanel();
    }

}