/**
 * Copyright (C), 2021-2022, 我是你爸爸有限公司
 * FileName: showPanel2
 * Author:   Administrator
 * Date:     2022/6/8 15:28
 * Description: 经理界面
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
 * 〈经理界面〉
 *
 * @author Administrator
 * @create 2022/6/8
 * @since 1.0.0
 */
public class showPanel2 extends JPanel {
    private showPanel2 show_panel;
    private JTable show_table;
    private tableData table_data,table_data_want;
    private DefaultTableModel tModel;
    private JLabel welcome_label,paixu_label,id_label,name_label,maxsal_label,minsal_label,avgsal_label,succ_label;
    private JTextField id_text,name_text;
    private JButton id_button,name_button,jibeninf_button;
    private JRadioButton staff_id_asc_button, staff_id_desc_button,
                        salary_asc_button,salary_desc_button;
    private ButtonGroup paixu_group;
    private JScrollPane scrollPane;

    private int staff_id;
    private boolean order,//true->asc false->desc
                    order_by,//true->staff_id false->salary
                    sear_mode;//ture->jiben false->search

    public showPanel2(int staffId){
        show_panel = this;
        staff_id = staffId;
        show_panel.setLayout(null);
        order = true;
        order_by = true;
        sear_mode = true;

        //分配空间
        table_data = new tableData(gaussdb.get_2_data(staff_id,order,order_by));
        tModel = new DefaultTableModel(table_data.getTable_data(),table_data.getTable_name());
        show_table = new JTable(tModel);
        welcome_label = new JLabel("welcome! "+gaussdb.get_name_by_staff_id(staff_id)+"!");
        paixu_label = new JLabel("排序:");
        id_label = new JLabel("按编号搜索");
        name_label = new JLabel("按姓名搜索");
        maxsal_label = new JLabel();
        minsal_label = new JLabel();
        avgsal_label = new JLabel();
        succ_label = new JLabel();
        id_text = new JTextField();
        name_text = new JTextField();
        id_button = new JButton("搜索");
        name_button = new JButton("搜索");
        jibeninf_button = new JButton("基本信息查询");
        staff_id_asc_button = new JRadioButton("编号升序",true);
        staff_id_desc_button = new JRadioButton("编号降序",false);
        salary_asc_button = new JRadioButton("薪水升序",false);
        salary_desc_button = new JRadioButton("薪水降序",false);
        paixu_group = new ButtonGroup();
        scrollPane = new JScrollPane(show_table);

        //定位
        show_panel.setSize(800,600);
        welcome_label.setBounds(25,25,250,25);
        paixu_label.setBounds(25,125,50,25);
        id_label.setBounds(325,75,100,25);
        name_label.setBounds(325,125,100,25);
        maxsal_label.setBounds(25,75,90,25);
        minsal_label.setBounds(125,75,90,25);
        avgsal_label.setBounds(225,75,90,25);
        succ_label.setBounds(550,25,200,25);
        id_text.setBounds(425,75,150,25);
        name_text.setBounds(425,125,150,25);
        id_button.setBounds(600,75,100,25);
        name_button.setBounds(600,125,100,25);
        jibeninf_button.setBounds(325,25,200,25);
        staff_id_asc_button.setBounds(125,125,90,25);
        staff_id_desc_button.setBounds(225,125,90,25);
        salary_asc_button.setBounds(125,150,90,25);
        salary_desc_button.setBounds(225,150,90,25);
        scrollPane.setBounds(25,175,750,375);

        //计算部门薪水
        maxsal_label.setText("max:"+gaussdb.get_2_data_max_salary(staff_id));
        minsal_label.setText("min:"+gaussdb.get_2_data_min_salary(staff_id));
        avgsal_label.setText("avg:"+gaussdb.get_2_data_avg_salary(staff_id));

        //表格
        for (int i = 0;i < table_data.col_num;i++){
            show_table.getColumnModel().getColumn(i).setPreferredWidth(10*table_data.col_size[i]);
        }
        show_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) show_table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(renderer.CENTER);
        DefaultTableCellRenderer r=new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        show_table.setDefaultRenderer(Object.class,r);

        //添加监听
        id_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String staff_id_want = id_text.getText();
                System.out.println("输了："+staff_id_want);
                //检查输入是否合法
                if (staff_id_want.matches("\\d\\d\\d")){
                    System.out.println("合法");
                    //检查是否该经理管辖
                    if (table_data.is_contain_id(staff_id_want)){
                        sear_mode = false;//切换模式
                        succ_label.setText("搜索成功!");
                        //更新table_data
                        table_data_want = new tableData(gaussdb.get_2_data_id(staff_id_want));
                        tModel.setDataVector(table_data_want.getTable_data(),table_data_want.getTable_name());
                        //设置列宽
                        for (int i = 0;i<table_data_want.col_num;i++)
                            show_table.getColumnModel().getColumn(i).setPreferredWidth(10*table_data_want.col_size[i]);
                    }else {
                        succ_label.setText("你部无此人");
                    }
                }else{
                    succ_label.setText("输入不合法!");
                }
            }
        });
        name_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name_want = name_text.getText();
                System.out.println("输了："+name_want);
                //检查输入是否合法
                if (Pattern.matches("[a-zA-Z]+",name_want)){
                    //检查是否该经理管辖
                    if (table_data.is_contain_name(name_want)){
                        sear_mode = false;//切换模式
                        succ_label.setText("搜索成功!");
                        //更新table_data
                        table_data_want = new tableData(gaussdb.get_2_data_name(name_want));
                        tModel.setDataVector(table_data_want.getTable_data(),table_data.getTable_name());
                        //设置列宽
                        for (int i = 0;i<table_data_want.col_num;i++)
                            show_table.getColumnModel().getColumn(i).setPreferredWidth(10*table_data_want.col_size[i]);
                    }else {
                        succ_label.setText("你部无此人!");
                    }
                }else {
                    succ_label.setText("输入不合法!");
                }
            }
        });
        staff_id_asc_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order = true;//切换为升序
                order_by = true;//编号
                System.out.println("asc");
                if (sear_mode){
                    table_data = new tableData(gaussdb.get_2_data(staff_id,order,order_by));
                    tModel.setDataVector(table_data.getTable_data(),table_data.getTable_name());
                    for (int i = 0;i<table_data.col_num;i++)
                        show_table.getColumnModel().getColumn(i).setPreferredWidth(10*table_data.col_size[i]);
                }
            }
        });
        staff_id_desc_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order = false;//切换为降序
                order_by = true;//编号
                System.out.println("desc");
                if (sear_mode){
                    table_data = new tableData(gaussdb.get_2_data(staff_id,order,order_by));
                    tModel.setDataVector(table_data.getTable_data(),table_data.getTable_name());
                    for (int i = 0;i<table_data.col_num;i++)
                        show_table.getColumnModel().getColumn(i).setPreferredWidth(10*table_data.col_size[i]);
                }
            }
        });
        salary_asc_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order = true;//切换为降序
                order_by = false;//编号
                System.out.println("desc");
                if (sear_mode){
                    table_data = new tableData(gaussdb.get_2_data(staff_id,order,order_by));
                    tModel.setDataVector(table_data.getTable_data(),table_data.getTable_name());
                    for (int i = 0;i<table_data.col_num;i++)
                        show_table.getColumnModel().getColumn(i).setPreferredWidth(10*table_data.col_size[i]);
                }
            }
        });
        salary_desc_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order = false;//切换为降序
                order_by = false;//编号
                System.out.println("desc");
                if (sear_mode){
                    table_data = new tableData(gaussdb.get_2_data(staff_id,order,order_by));
                    tModel.setDataVector(table_data.getTable_data(),table_data.getTable_name());
                    for (int i = 0;i<table_data.col_num;i++)
                        show_table.getColumnModel().getColumn(i).setPreferredWidth(10*table_data.col_size[i]);
                }
            }
        });
        jibeninf_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sear_mode = true;
                table_data = new tableData(gaussdb.get_2_data(staff_id,order,order_by));
                tModel.setDataVector(table_data.getTable_data(),table_data.getTable_name());
                for (int i = 0;i<table_data.col_num;i++)
                    show_table.getColumnModel().getColumn(i).setPreferredWidth(10*table_data.col_size[i]);
            }
        });

        //添加组件
        paixu_group.add(staff_id_asc_button);
        paixu_group.add(staff_id_desc_button);
        paixu_group.add(salary_asc_button);
        paixu_group.add(salary_desc_button);
        show_panel.add(welcome_label);
        show_panel.add(paixu_label);
        show_panel.add(id_label);
        show_panel.add(name_label);
        show_panel.add(maxsal_label);
        show_panel.add(minsal_label);
        show_panel.add(avgsal_label);
        show_panel.add(succ_label);
        show_panel.add(id_text);
        show_panel.add(name_text);
        show_panel.add(id_button);
        show_panel.add(name_button);
        show_panel.add(jibeninf_button);
        show_panel.add(staff_id_asc_button);
        show_panel.add(staff_id_desc_button);
        show_panel.add(salary_asc_button);
        show_panel.add(salary_desc_button);
        show_panel.add(scrollPane);

    }


}