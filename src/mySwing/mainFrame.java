/*
  Copyright (C), 2021-2022, 我是你爸爸有限公司
  FileName: mainFrame
  Author:   Administrator
  Date:     2022/6/8 10:21
  Description: 主窗口
  History:
  <author>          <time>          <version>          <desc>
  作者姓名           修改时间           版本号              描述
 */
package mySwing;

import javax.swing.*;
import dataClass.*;
import gaussdb.*;

/**
 * 〈一句话功能简述〉 
 * 〈主窗口〉
 *
 * @author Administrator
 * @create 2022/6/8
 * @since 1.0.0
 */
public class mainFrame extends JFrame {
    private static mainFrame main_frame;
    private static loginPanel login_panel;
    private static showPanel1 show_panel1;
    private static showPanel2 show_panel2;
    private static showPanel3 show_panel3;
    private static int staff_id,role;

    public static void run_login(){
        staff_id = 0;
        while (staff_id == 0){
            staff_id = loginPanel.get();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        role = userPassword.get_role(Integer.toString(staff_id));
        System.out.println("frame:staff_id: "+staff_id + " role:" + role);
    }

    public mainFrame(){
        main_frame = this;
        login_panel = new loginPanel();

        //初始化
        gaussdb.init();
        userPassword.init();

        JFrame.setDefaultLookAndFeelDecorated(true);
        main_frame.setLayout(null);
        main_frame.setTitle("Login");
        main_frame.setSize(350,200);
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main_frame.setLocation(500,300);

//        main_frame.setContentPane(login_panel);
        main_frame.add(login_panel);
        login_panel.setEnabled(true);
        login_panel.setVisible(true);
        main_frame.setVisible(true);

        //登录------------------------------------------------
        main_frame.run_login();
        //登录结束-----------------------------------------------

        login_panel.setVisible(false);
        login_panel.setEnabled(false);
        if (role == 1){
            main_frame.setTitle("员工界面");
            main_frame.setSize(800,600);
            main_frame.setLocation(300,100);

            show_panel1 = new showPanel1(staff_id);
            main_frame.setContentPane(show_panel1);
//            main_frame.add(show_panel1);
            show_panel1.setEnabled(true);
            show_panel1.setVisible(true);

//            main_frame.setVisible(true);

        }else if (role == 2){
            main_frame.setTitle("经理界面");
            main_frame.setSize(800,600);
            main_frame.setLocation(300,100);

            show_panel2 = new showPanel2(staff_id);
            main_frame.setContentPane(show_panel2);
            show_panel2.setEnabled(true);
            show_panel2.setVisible(true);

        }else if (role == 3){
            main_frame.setTitle("人事经理界面");
            main_frame.setSize(1000,600);
            main_frame.setLocation(250,50);

            show_panel3 = new showPanel3(staff_id);
            main_frame.setContentPane(show_panel3);
            show_panel3.setEnabled(true);
            show_panel3.setVisible(true);
        }
        System.out.println("Frame:没我事了，先润了哈");


    }

    public static void main(String[] args) {
        new mainFrame();
    }


}