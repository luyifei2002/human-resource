/**
 * Copyright (C), 2021-2022, 我是你爸爸有限公司
 * FileName: showPanel
 * Author:   Administrator
 * Date:     2022/6/8 10:41
 * Description: 展示内容
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package mySwing;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dataClass.*;
import gaussdb.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

/**
 * 〈一句话功能简述〉 
 * 〈员工界面〉
 *
 * @author Administrator
 * @create 2022/6/8
 * @since 1.0.0
 */
public class showPanel1 extends JPanel {
    private showPanel1 show_panel;
    private JTable show_table;
    private JLabel welcome_label,phone_label,succ_label;
    private JButton phone_button;
    private JTextField phone_text;
    private tableData table_data;
    private int staff_id;
    private DefaultTableModel tModel;

    public showPanel1(int staffId){
        show_panel = this;
        staff_id = staffId;
        show_panel.setLayout(null);
//        show_panel.setBackground(new Color(200,200,200));

        table_data = new tableData(gaussdb.get_1_data(staff_id));
        //网上查的，用这个刷新表
        tModel = new DefaultTableModel(table_data.getTable_data(),table_data.getTable_name());

        show_table = new JTable(tModel);
        welcome_label = new JLabel("welcome! "+gaussdb.get_name_by_staff_id(staff_id)+"!");
        phone_label = new JLabel("新电话号码");
        phone_button = new JButton("修改电话号码");
        phone_text = new JTextField();
        succ_label = new JLabel("");

        show_panel.setSize(800,600);
//        show_table.setBounds(450,50,300,500);
        welcome_label.setBounds(25,25,350,25);
        phone_label.setBounds(425,25,100,25);
        phone_text.setBounds(525,25,150,25);
        phone_button.setBounds(600,75,75,25);
        succ_label.setBounds(425,75,100,25);

        //设置表格宽度
        for (int i = 0;i<table_data.col_num;i++)
            show_table.getColumnModel().getColumn(i).setPreferredWidth(10*table_data.col_size[i]);
        show_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // 设置表头文字居中显示
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) show_table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(renderer.CENTER);
        // 设置表格中的数据居中显示
        DefaultTableCellRenderer r=new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        show_table.setDefaultRenderer(Object.class,r);

        //设置滚动窗口
        JScrollPane scrollPane = new JScrollPane(show_table);
        scrollPane.setBounds(25,150,750,400);

        phone_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phone_num = phone_text.getText();//获取文本框信息
                System.out.println("输了："+phone_num);
                //检查电话号码    xxx.xxx(x).xxx
                if (Pattern.matches("\\d\\d\\d\\.\\d\\d\\d(\\d)?\\.\\d\\d\\d\\d",phone_num)){
                    gaussdb.update_phone(phone_num,staff_id);//数据库去修改
                    succ_label.setText("修改成功！");//显示信息
                    table_data = new tableData(gaussdb.get_1_data(staff_id));//更新table_data类
                    tModel.setDataVector(table_data.getTable_data(),table_data.getTable_name());//更新表
                    //设置列宽
                    for (int i = 0;i<table_data.col_num;i++)
                        show_table.getColumnModel().getColumn(i).setPreferredWidth(10*table_data.col_size[i]);
                    System.out.println("改了");

                }else{
                    succ_label.setText("格式不正确！");
                    System.out.println("格式不正确");
                }
                System.out.println("点完了");
            }
        });

        show_panel.add(welcome_label);
        show_panel.add(scrollPane);
        show_panel.add(phone_label);
        show_panel.add(phone_button);
        show_panel.add(phone_text);
        show_panel.add(succ_label);

    }

}