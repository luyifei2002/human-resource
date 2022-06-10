/**
 * Copyright (C), 2021-2022, 我是你爸爸有限公司
 * FileName: showPanel3
 * Author:   Administrator
 * Date:     2022/6/9 8:52
 * Description: 人事经理界面
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package mySwing;

import gaussdb.gaussdb;
import dataClass.tableData;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 〈一句话功能简述〉 
 * 〈人事经理界面〉
 *
 * @author Administrator
 * @create 2022/6/9
 * @since 1.0.0
 */
public class showPanel3 extends JPanel {
    private showPanel3 show_panel;

    //jiben
    private JPanel show_panel_jiben;
    private JTable show_table_jiben;
    private tableData table_data_jiben,table_data_want_jiben;
    private DefaultTableModel tModel_jiben;
    private JLabel welcome_label_jiben, paixu_label_jiben,
                    id_label_jiben, name_label_jiben,
                    maxsal_label_jiben, minsal_label_jiben, avgsal_label_jiben,
                    succ_label_jiben;
    private JTextField id_text_jiben, name_text_jiben;
    private JButton id_button_jiben, name_button_jiben,jiben_button_jiben;
    private JRadioButton staff_id_asc_button_jiben, staff_id_desc_button_jiben,
            salary_asc_button_jiben, salary_desc_button_jiben;
    private ButtonGroup paixu_group_jiben;
    private JScrollPane scrollPane_jiben;
    private boolean order_jiben,//true->asc false->desc
            order_by_jiben,//true->staff_id false->salary
            sear_mode_jiben;

    //salary
    private JPanel show_panel_sal;
    private JTable show_table_sal;
    private tableData table_data_sal;
    private DefaultTableModel tModel_sal;
    private JScrollPane scrollPane_sal;

    //section
    private JPanel show_panel_sec;
    private JLabel id_label_sec,name_label_sec,succ_label_sec;
    private JTextField id_text_sec,name_text_sec;
    private JButton update_button_sec;
    private tableData table_data_sec;
    private JTable show_table_sec;
    private DefaultTableModel tModel_sec;
    private JScrollPane scrollPane_sec;

    //place
    private JPanel show_panel_pla;
    private JLabel place_id_label_pla,street_address_label_pla,postal_code_label_pla,city_label_pla,state_province_label_pla,state_id_label_pla,succ_label_pla;
    private JTextField place_id_text_pla,street_address_text_pla,postal_code_text_pla,city_text_pla,state_province_text_pla,state_id_text_pla;
    private JButton update_button_pla;
    private tableData table_data_pla;
    private JTable show_table_pla;
    private DefaultTableModel tModel_pla;
    private JScrollPane scrollPane_pla;

    //employment_history
    private JPanel show_panel_emh;
    private JLabel id_label_emh,succ_label_emh;
    private JTextField id_text_emh;
    private JButton id_button_emh,jiben_button_emh;
    private tableData table_data_emh,table_data_emh_want;
    private JTable show_table_emh;
    private DefaultTableModel tModel_emh;
    private JScrollPane scrollPane_emh;

    //control
    private JPanel show_panel_con;
    private JLabel jiben_label_con;
    private JComboBox<String> jiben_combobox_con;
    private JButton jiben_button_con,
                    sal_button_con,
                    sec_button_con,
                    pla_button_con,
                    emh_button_con;

    //info
    private int staff_id,sear_mode;//1->jiben 2->salary 3->section 4->places
    private String section_id;

    public showPanel3(int staffId){
        //初始化
        show_panel = this;
        staff_id = staffId;
        sear_mode = 1;
        section_id = "0";
        order_jiben = true;
        order_by_jiben = true;
        sear_mode_jiben = true;

        //定位
        show_panel.setLayout(null);
        show_panel.setSize(1000,600);
        show_panel.setBounds(0,0,1000,600);

        //初始化面板----------------------------------------------------
        init_jiben();
        init_sal();
        init_sec();
        init_pla();
        init_emh();
        init_con();

        //添加面板
        show_panel.add(show_panel_jiben);
        show_panel.add(show_panel_sal);
        show_panel.add(show_panel_sec);
        show_panel.add(show_panel_pla);
        show_panel.add(show_panel_emh);
        show_panel.add(show_panel_con);

        set_jiben();//默认界面为基本界面
    }

    private void init_con(){
        //分配空间
        show_panel_con = new JPanel();
        jiben_label_con = new JLabel("请选择部门");
        jiben_combobox_con = new JComboBox<String>(gaussdb.get_3_data_section_list());
        jiben_button_con = new JButton("员工基本信息");
        sal_button_con = new JButton("部门薪水信息");
        sec_button_con = new JButton("部门基本信息");
        pla_button_con = new JButton("工作地点信息");
        emh_button_con = new JButton("查询员工历史");

        //定位
        show_panel_con.setLayout(null);
        show_panel_con.setSize(200,600);
        show_panel_con.setBounds(0,0,200,600);
        jiben_label_con.setBounds(25,25,150,25);
        jiben_combobox_con.setBounds(25,75,150,25);
        jiben_button_con.setBounds(25,125,150,25);
        sal_button_con.setBounds(25,225,150,25);
        sec_button_con.setBounds(25,275,150,25);
        pla_button_con.setBounds(25,325,150,25);
        emh_button_con.setBounds(25,375,150,25);

        jiben_combobox_con.setSelectedIndex(0);

        //监听
        jiben_combobox_con.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String section_name_want = (String) jiben_combobox_con.getSelectedItem();
                if (Objects.equals(section_name_want,"all")){
                    section_id = "0";
                }else {
                    section_id = gaussdb.get_section_id_by_section_name(section_name_want);
                }
                System.out.println(section_id);

                table_data_jiben = new tableData(gaussdb.get_3_data(section_id,order_jiben, order_by_jiben));
                tModel_jiben.setDataVector(table_data_jiben.getTable_data(),table_data_jiben.getTable_name());
                for (int i = 0;i<table_data_jiben.col_num;i++)
                    show_table_jiben.getColumnModel().getColumn(i).setPreferredWidth(10*table_data_jiben.col_size[i]);

                //重新计算部门薪水
                maxsal_label_jiben.setText("max:"+gaussdb.get_3_data_max_salary(section_id));
                minsal_label_jiben.setText("min:"+gaussdb.get_3_data_min_salary(section_id));
                avgsal_label_jiben.setText("avg:"+gaussdb.get_3_data_avg_salary(section_id));
            }
        });
        jiben_button_con.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                set_jiben();
            }
        });
        sal_button_con.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                set_sal();
            }
        });
        sec_button_con.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                set_sec();
            }
        });
        pla_button_con.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                set_pla();
            }
        });
        emh_button_con.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                set_emh();
            }
        });

        //添加组件
        show_panel_con.add(jiben_label_con);
        show_panel_con.add(jiben_combobox_con);
        show_panel_con.add(jiben_button_con);
        show_panel_con.add(sal_button_con);
        show_panel_con.add(sec_button_con);
        show_panel_con.add(pla_button_con);
        show_panel_con.add(emh_button_con);
    }

    private void init_emh(){
        //分配空间
        show_panel_emh = new JPanel();
        id_label_emh = new JLabel("请输入编号");
        succ_label_emh = new JLabel();
        id_text_emh = new JTextField();
        id_button_emh = new JButton("提交");
        jiben_button_emh = new JButton("全部历史记录");
        table_data_emh = new tableData(gaussdb.get_3_data_employment_history());
        tModel_emh = new DefaultTableModel(table_data_emh.getTable_data(),table_data_emh.getTable_name());
        show_table_emh = new JTable(tModel_emh);
        scrollPane_emh = new JScrollPane(show_table_emh);

        //定位
        show_panel_emh.setLayout(null);
        show_panel_emh.setSize(800,600);
        show_panel_emh.setBounds(200,0,800,600);
        id_label_emh.setBounds(325,75,100,25);
        succ_label_emh.setBounds(550,25,200,25);
        id_text_emh.setBounds(425,75,150,25);
        id_button_emh.setBounds(600,75,100,25);
        jiben_button_emh.setBounds(325,25,200,25);
        scrollPane_emh.setBounds(25,175,750,375);

        //表格
        for (int i = 0;i < table_data_emh.col_num;i++){
            show_table_emh.getColumnModel().getColumn(i).setPreferredWidth(10*table_data_emh.col_size[i]);
        }
        show_table_emh.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) show_table_emh.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(renderer.CENTER);
        DefaultTableCellRenderer r=new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        show_table_emh.setDefaultRenderer(Object.class,r);

        //监听
        jiben_button_emh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tModel_emh.setDataVector(table_data_emh.getTable_data(),table_data_emh.getTable_name());
                for (int i = 0;i < table_data_emh.col_num;i++){
                    show_table_emh.getColumnModel().getColumn(i).setPreferredWidth(10*table_data_emh.col_size[i]);
                }
            }
        });
        id_button_emh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String staff_id_emh_want = id_text_emh.getText();
                System.out.println("输入："+staff_id_emh_want);
                if (!staff_id_emh_want.matches("\\d\\d\\d")){
                    succ_label_emh.setText("输入不合法");
                    return;
                }
                if (!table_data_emh.is_contain_id(staff_id_emh_want)){
                    succ_label_emh.setText("没这个人");
                    return;
                }
                table_data_emh_want = new tableData(gaussdb.get_3_data_employment_history_by_staff_id(staff_id_emh_want));
                tModel_emh.setDataVector(table_data_emh_want.getTable_data(),table_data_emh_want.getTable_name());
                for (int i = 0;i < table_data_emh_want.col_num;i++){
                    show_table_emh.getColumnModel().getColumn(i).setPreferredWidth(10*table_data_emh_want.col_size[i]);
                }
            }
        });

        //添加组件
        show_panel_emh.add(id_label_emh);
        show_panel_emh.add(succ_label_emh);
        show_panel_emh.add(id_text_emh);
        show_panel_emh.add(id_button_emh);
        show_panel_emh.add(jiben_button_emh);
        show_panel_emh.add(scrollPane_emh);
    }

    private void init_pla(){
        //分配空间
        show_panel_pla = new JPanel();
        place_id_label_pla = new JLabel("place_id");
        street_address_label_pla = new JLabel("street_address");
        postal_code_label_pla = new JLabel("postal_code");
        city_label_pla = new JLabel("city");
        state_province_label_pla = new JLabel("state_province");
        state_id_label_pla = new JLabel("state_id");
        succ_label_pla = new JLabel();
        place_id_text_pla = new JTextField();
        street_address_text_pla = new JTextField();
        postal_code_text_pla =new JTextField();
        city_text_pla = new JTextField();
        state_province_text_pla = new JTextField();
        state_id_text_pla = new JTextField();
        update_button_pla = new JButton("添加新地点");
        table_data_pla = new tableData(gaussdb.get_3_data_place());
        tModel_pla = new DefaultTableModel(table_data_pla.getTable_data(),table_data_pla.getTable_name());
        show_table_pla = new JTable(tModel_pla);
        scrollPane_pla = new JScrollPane(show_table_pla);

        //定位
        show_panel_pla.setLayout(null);
        show_panel_pla.setSize(800,600);
        show_panel_pla.setBounds(200,0,800,600);
        place_id_label_pla.setBounds(25,50,100,25);
        street_address_label_pla.setBounds(145,50,100,25);
        postal_code_label_pla.setBounds(265,50,100,25);
        city_label_pla.setBounds(385,50,100,25);
        state_province_label_pla.setBounds(505,50,100,25);
        state_id_label_pla.setBounds(625,50,100,25);
        succ_label_pla.setBounds(500,125,200,25);
        place_id_text_pla.setBounds(25,75,100,25);
        street_address_text_pla.setBounds(145,75,100,25);
        postal_code_text_pla.setBounds(265,75,100,25);
        city_text_pla.setBounds(385,75,100,25);
        state_province_text_pla.setBounds(505,75,100,25);
        state_id_text_pla.setBounds(625,75,100,25);
        update_button_pla.setBounds(600,125,100,25);
        scrollPane_pla.setBounds(25,175,750,375);

        //表格
        for (int i = 0;i < table_data_pla.col_num;i++){
            show_table_pla.getColumnModel().getColumn(i).setPreferredWidth(10*table_data_pla.col_size[i]);
        }
        show_table_pla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) show_table_pla.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(renderer.CENTER);
        DefaultTableCellRenderer r=new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        show_table_pla.setDefaultRenderer(Object.class,r);

        //监听
        update_button_pla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String place_id_pla_want = place_id_text_pla.getText(),
                        street_address_pla_want = street_address_text_pla.getText(),
                        postal_code_pla_want = postal_code_text_pla.getText(),
                        city_pla_want = city_text_pla.getText(),
                        state_province_pla_want = state_province_text_pla.getText(),
                        state_id_pla_want = state_id_text_pla.getText();
                System.out.println("输入"+place_id_pla_want+" "+street_address_pla_want+" "+postal_code_pla_want+" "+city_pla_want+" "+state_province_pla_want+" "+state_id_pla_want);

                if(!place_id_pla_want.matches("\\d\\d\\d\\d") || !street_address_pla_want.matches(".*\\S.*") || !city_pla_want.matches(".*\\S.*") || !state_id_pla_want.matches("[A-Z][A-Z]")){
                    succ_label_pla.setText("输入不合法");
                    return;
                }
                if (table_data_pla.is_contain_id(place_id_pla_want)){
                    succ_label_pla.setText("编号重复");
                    return;
                }
                System.out.println("pla:success!");
                gaussdb.insert_place(place_id_pla_want,street_address_pla_want,postal_code_pla_want,city_pla_want,state_province_pla_want,state_id_pla_want);
                table_data_pla = new tableData(gaussdb.get_3_data_place());
                tModel_pla.setDataVector(table_data_pla.getTable_data(),table_data_pla.getTable_name());
                //设置列宽
                for (int i = 0;i<table_data_pla.col_num;i++)
                    show_table_pla.getColumnModel().getColumn(i).setPreferredWidth(10*table_data_pla.col_size[i]);
            }
        });

        //添加组件
        show_panel_pla.add(place_id_label_pla);
        show_panel_pla.add(street_address_label_pla);
        show_panel_pla.add(postal_code_label_pla);
        show_panel_pla.add(city_label_pla);
        show_panel_pla.add(state_province_label_pla);
        show_panel_pla.add(state_id_label_pla);
        show_panel_pla.add(succ_label_pla);
        show_panel_pla.add(place_id_text_pla);
        show_panel_pla.add(street_address_text_pla);
        show_panel_pla.add(postal_code_text_pla);
        show_panel_pla.add(city_text_pla);
        show_panel_pla.add(state_province_text_pla);
        show_panel_pla.add(state_id_text_pla);
        show_panel_pla.add(update_button_pla);
        show_panel_pla.add(scrollPane_pla);
    }

    private void init_sec(){
        //分配空间
        show_panel_sec = new JPanel();
        id_label_sec = new JLabel("请输入编号:");
        name_label_sec = new JLabel("请输入新名字:");
        succ_label_sec = new JLabel();
        id_text_sec = new JTextField();
        name_text_sec = new JTextField();
        update_button_sec = new JButton("修改名字");
        table_data_sec = new tableData(gaussdb.get_3_data_section());
        tModel_sec = new DefaultTableModel(table_data_sec.getTable_data(),table_data_sec.getTable_name());
        show_table_sec = new JTable(tModel_sec);
        scrollPane_sec = new JScrollPane(show_table_sec);

        //定位
        show_panel_sec.setLayout(null);
        show_panel_sec.setSize(800,600);
        show_panel_sec.setBounds(200,0,800,600);
        scrollPane_sec.setBounds(25,175,750,375);
        id_label_sec.setBounds(325,75,100,25);
        name_label_sec.setBounds(325,125,100,25);
        succ_label_sec.setBounds(550,25,200,25);
        id_text_sec.setBounds(425,75,150,25);
        name_text_sec.setBounds(425,125,150,25);
        update_button_sec.setBounds(600,125,100,25);

        //表格
        for (int i = 0;i < table_data_sec.col_num;i++){
            show_table_sec.getColumnModel().getColumn(i).setPreferredWidth(10*table_data_sec.col_size[i]);
        }
        show_table_sec.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) show_table_sec.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(renderer.CENTER);
        DefaultTableCellRenderer r=new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        show_table_sec.setDefaultRenderer(Object.class,r);

        //监听
        update_button_sec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String section_id_sec_want = id_text_sec.getText(),
                        section_new_name_sec_want = name_text_sec.getText();
                System.out.println("编号:"+section_id_sec_want+" 新名字:"+section_new_name_sec_want);
                if (!section_id_sec_want.matches("\\d\\d(\\d)?") || !section_new_name_sec_want.matches("[a-zA-Z\s]+")){
                    succ_label_sec.setText("输入不合法");
                    return;
                }
                if (!table_data_sec.is_contain_id(section_id_sec_want)){
                    succ_label_sec.setText("没这个部");
                    return;
                }
                gaussdb.update_section_name(section_id_sec_want,section_new_name_sec_want);
                table_data_sec = new tableData(gaussdb.get_3_data_section());
                tModel_sec.setDataVector(table_data_sec.getTable_data(),table_data_sec.getTable_name());
                //设置列宽
                for (int i = 0;i<table_data_sec.col_num;i++)
                    show_table_sec.getColumnModel().getColumn(i).setPreferredWidth(10*table_data_sec.col_size[i]);
            }
        });

        //添加组件
        show_panel_sec.add(id_label_sec);
        show_panel_sec.add(name_label_sec);
        show_panel_sec.add(succ_label_sec);
        show_panel_sec.add(id_text_sec);
        show_panel_sec.add(name_text_sec);
        show_panel_sec.add(update_button_sec);
        show_panel_sec.add(scrollPane_sec);
    }

    private void init_sal(){
        //分配空间
        show_panel_sal = new JPanel();
        table_data_sal = new tableData(gaussdb.get_3_data_salary());
        tModel_sal = new DefaultTableModel(table_data_sal.getTable_data(),table_data_sal.getTable_name());
        show_table_sal = new JTable(tModel_sal);
        scrollPane_sal = new JScrollPane(show_table_sal);

        //定位
        show_panel_sal.setLayout(null);
        show_panel_sal.setSize(800,600);
        show_panel_sal.setBounds(200,0,800,600);
        scrollPane_sal.setBounds(25,175,750,375);

        //表格
        for (int i = 0;i < table_data_sal.col_num;i++){
            show_table_sal.getColumnModel().getColumn(i).setPreferredWidth(10*table_data_sal.col_size[i]);
        }
        show_table_sal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) show_table_sal.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(renderer.CENTER);
        DefaultTableCellRenderer r=new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        show_table_sal.setDefaultRenderer(Object.class,r);

        //没按钮啥的不需要监听

        //添加组件
        show_panel_sal.add(scrollPane_sal);

    }

    private void init_jiben() {
        //分配空间
        show_panel_jiben = new JPanel();
        table_data_jiben = new tableData(gaussdb.get_3_data(section_id,order_jiben, order_by_jiben));
        tModel_jiben = new DefaultTableModel(table_data_jiben.getTable_data(), table_data_jiben.getTable_name());
        show_table_jiben = new JTable(tModel_jiben);
        welcome_label_jiben = new JLabel("welcome! "+gaussdb.get_name_by_staff_id(staff_id)+"!");
        paixu_label_jiben = new JLabel("排序:");
        id_label_jiben = new JLabel("按编号搜索");
        name_label_jiben = new JLabel("按姓名搜索");
        maxsal_label_jiben = new JLabel();
        minsal_label_jiben = new JLabel();
        avgsal_label_jiben = new JLabel();
        succ_label_jiben = new JLabel();
        id_text_jiben = new JTextField();
        name_text_jiben = new JTextField();
        id_button_jiben = new JButton("搜索");
        name_button_jiben = new JButton("搜索");
        jiben_button_jiben = new JButton("基本信息查询");
        staff_id_asc_button_jiben = new JRadioButton("编号升序",true);
        staff_id_desc_button_jiben = new JRadioButton("编号降序",false);
        salary_asc_button_jiben = new JRadioButton("薪水升序",false);
        salary_desc_button_jiben = new JRadioButton("薪水降序",false);
        paixu_group_jiben = new ButtonGroup();
        scrollPane_jiben = new JScrollPane(show_table_jiben);

        //定位
        show_panel_jiben.setLayout(null);
        show_panel_jiben.setSize(800,600);
        show_panel_jiben.setBounds(200,0,800,600);
        welcome_label_jiben.setBounds(25,25,250,25);
        paixu_label_jiben.setBounds(25,125,50,25);
        id_label_jiben.setBounds(325,75,100,25);
        name_label_jiben.setBounds(325,125,100,25);
        maxsal_label_jiben.setBounds(25,75,90,25);
        minsal_label_jiben.setBounds(125,75,90,25);
        avgsal_label_jiben.setBounds(225,75,90,25);
        succ_label_jiben.setBounds(550,25,200,25);
        id_text_jiben.setBounds(425,75,150,25);
        name_text_jiben.setBounds(425,125,150,25);
        id_button_jiben.setBounds(600,75,100,25);
        name_button_jiben.setBounds(600,125,100,25);
        jiben_button_jiben.setBounds(325,25,200,25);
        staff_id_asc_button_jiben.setBounds(125,125,90,25);
        staff_id_desc_button_jiben.setBounds(225,125,90,25);
        salary_asc_button_jiben.setBounds(125,150,90,25);
        salary_desc_button_jiben.setBounds(225,150,90,25);
        scrollPane_jiben.setBounds(25,175,750,375);

        //计算部门薪水
        maxsal_label_jiben.setText("max:"+gaussdb.get_3_data_max_salary(section_id));
        minsal_label_jiben.setText("min:"+gaussdb.get_3_data_min_salary(section_id));
        avgsal_label_jiben.setText("avg:"+gaussdb.get_3_data_avg_salary(section_id));

        //表格
        for (int i = 0;i < table_data_jiben.col_num;i++){
            show_table_jiben.getColumnModel().getColumn(i).setPreferredWidth(10*table_data_jiben.col_size[i]);
        }
        show_table_jiben.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) show_table_jiben.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(renderer.CENTER);
        DefaultTableCellRenderer r=new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        show_table_jiben.setDefaultRenderer(Object.class,r);

        //添加监听
        id_button_jiben.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String staff_id_want = id_text_jiben.getText();
                System.out.println("输了："+staff_id_want);
                //检查输入是否合法
                if (staff_id_want.matches("\\d\\d\\d")){
                    System.out.println("合法");
                    //检查是否该经理管辖
                    if (table_data_jiben.is_contain_id(staff_id_want)){
                        sear_mode_jiben = false;//切换模式
                        succ_label_jiben.setText("搜索成功!");
                        //更新table_data
                        table_data_want_jiben = new tableData(gaussdb.get_2_data_id(staff_id_want));
                        tModel_jiben.setDataVector(table_data_want_jiben.getTable_data(),table_data_want_jiben.getTable_name());
                        //设置列宽
                        for (int i = 0;i<table_data_want_jiben.col_num;i++)
                            show_table_jiben.getColumnModel().getColumn(i).setPreferredWidth(10*table_data_want_jiben.col_size[i]);
                    }else {
                        succ_label_jiben.setText("你部无此人");
                    }
                }else{
                    succ_label_jiben.setText("输入不合法!");
                }
            }
        });
        name_button_jiben.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name_want = name_text_jiben.getText();
                System.out.println("输了："+name_want);
                //检查输入是否合法
                if (Pattern.matches("[a-zA-Z]+",name_want)){
                    //检查是否该经理管辖
                    if (table_data_jiben.is_contain_name(name_want)){
                        sear_mode_jiben = false;//切换模式
                        succ_label_jiben.setText("搜索成功!");
                        //更新table_data
                        table_data_want_jiben = new tableData(gaussdb.get_2_data_name(name_want));
                        tModel_jiben.setDataVector(table_data_want_jiben.getTable_data(),table_data_jiben.getTable_name());
                        //设置列宽
                        for (int i = 0;i<table_data_want_jiben.col_num;i++)
                            show_table_jiben.getColumnModel().getColumn(i).setPreferredWidth(10*table_data_want_jiben.col_size[i]);
                    }else {
                        succ_label_jiben.setText("你部无此人!");
                    }
                }else {
                    succ_label_jiben.setText("输入不合法!");
                }
            }
        });
        staff_id_asc_button_jiben.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order_jiben = true;//切换为升序
                order_by_jiben = true;//编号
                System.out.println("asc");
                if (sear_mode_jiben){
                    table_data_jiben = new tableData(gaussdb.get_3_data(section_id,order_jiben, order_by_jiben));
                    tModel_jiben.setDataVector(table_data_jiben.getTable_data(),table_data_jiben.getTable_name());
                    for (int i = 0;i<table_data_jiben.col_num;i++)
                        show_table_jiben.getColumnModel().getColumn(i).setPreferredWidth(10*table_data_jiben.col_size[i]);
                }
            }
        });
        staff_id_desc_button_jiben.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order_jiben = false;//切换为降序
                order_by_jiben = true;//编号
                System.out.println("desc");
                if (sear_mode_jiben){
                    table_data_jiben = new tableData(gaussdb.get_3_data(section_id,order_jiben, order_by_jiben));
                    tModel_jiben.setDataVector(table_data_jiben.getTable_data(),table_data_jiben.getTable_name());
                    for (int i = 0;i<table_data_jiben.col_num;i++)
                        show_table_jiben.getColumnModel().getColumn(i).setPreferredWidth(10*table_data_jiben.col_size[i]);
                }
            }
        });
        salary_asc_button_jiben.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order_jiben = true;//切换为降序
                order_by_jiben = false;//编号
                System.out.println("desc");
                if (sear_mode_jiben){
                    table_data_jiben = new tableData(gaussdb.get_3_data(section_id,order_jiben, order_by_jiben));
                    tModel_jiben.setDataVector(table_data_jiben.getTable_data(),table_data_jiben.getTable_name());
                    for (int i = 0;i<table_data_jiben.col_num;i++)
                        show_table_jiben.getColumnModel().getColumn(i).setPreferredWidth(10*table_data_jiben.col_size[i]);
                }
            }
        });
        salary_desc_button_jiben.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order_jiben = false;//切换为降序
                order_by_jiben = false;//编号
                System.out.println("desc");
                if (sear_mode_jiben){
                    table_data_jiben = new tableData(gaussdb.get_3_data(section_id,order_jiben, order_by_jiben));
                    tModel_jiben.setDataVector(table_data_jiben.getTable_data(),table_data_jiben.getTable_name());
                    for (int i = 0;i<table_data_jiben.col_num;i++)
                        show_table_jiben.getColumnModel().getColumn(i).setPreferredWidth(10*table_data_jiben.col_size[i]);
                }
            }
        });
        jiben_button_jiben.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sear_mode_jiben = true;
                table_data_jiben = new tableData(gaussdb.get_3_data(section_id,order_jiben, order_by_jiben));
                tModel_jiben.setDataVector(table_data_jiben.getTable_data(),table_data_jiben.getTable_name());
                for (int i = 0;i<table_data_jiben.col_num;i++)
                    show_table_jiben.getColumnModel().getColumn(i).setPreferredWidth(10*table_data_jiben.col_size[i]);
            }
        });

        //添加组件
        paixu_group_jiben.add(staff_id_asc_button_jiben);
        paixu_group_jiben.add(staff_id_desc_button_jiben);
        paixu_group_jiben.add(salary_asc_button_jiben);
        paixu_group_jiben.add(salary_desc_button_jiben);
        show_panel_jiben.add(welcome_label_jiben);
        show_panel_jiben.add(paixu_label_jiben);
        show_panel_jiben.add(id_label_jiben);
        show_panel_jiben.add(name_label_jiben);
        show_panel_jiben.add(maxsal_label_jiben);
        show_panel_jiben.add(minsal_label_jiben);
        show_panel_jiben.add(avgsal_label_jiben);
        show_panel_jiben.add(succ_label_jiben);
        show_panel_jiben.add(id_text_jiben);
        show_panel_jiben.add(name_text_jiben);
        show_panel_jiben.add(id_button_jiben);
        show_panel_jiben.add(name_button_jiben);
        show_panel_jiben.add(jiben_button_jiben);
        show_panel_jiben.add(staff_id_asc_button_jiben);
        show_panel_jiben.add(staff_id_desc_button_jiben);
        show_panel_jiben.add(salary_asc_button_jiben);
        show_panel_jiben.add(salary_desc_button_jiben);
        show_panel_jiben.add(scrollPane_jiben);
    }

    private void set_emh(){
        sear_mode = 5;
        show_panel_jiben.setVisible(false);
        show_panel_jiben.setEnabled(false);
        show_panel_sal.setVisible(false);
        show_panel_sal.setEnabled(false);
        show_panel_sec.setVisible(false);
        show_panel_sec.setEnabled(false);
        show_panel_pla.setVisible(false);
        show_panel_pla.setEnabled(false);
        show_panel_emh.setVisible(true);
        show_panel_emh.setEnabled(true);
    }

    private void set_pla(){
        sear_mode = 4;
        show_panel_jiben.setVisible(false);
        show_panel_jiben.setEnabled(false);
        show_panel_sal.setVisible(false);
        show_panel_sal.setEnabled(false);
        show_panel_sec.setVisible(false);
        show_panel_sec.setEnabled(false);
        show_panel_pla.setVisible(true);
        show_panel_pla.setEnabled(true);
        show_panel_emh.setVisible(false);
        show_panel_emh.setEnabled(false);
    }

    private void set_sec(){
        sear_mode = 3;
        show_panel_jiben.setVisible(false);
        show_panel_jiben.setEnabled(false);
        show_panel_sal.setVisible(false);
        show_panel_sal.setEnabled(false);
        show_panel_sec.setVisible(true);
        show_panel_sec.setEnabled(true);
        show_panel_pla.setVisible(false);
        show_panel_pla.setEnabled(false);
        show_panel_emh.setVisible(false);
        show_panel_emh.setEnabled(false);
    }

    private void set_sal(){
        sear_mode = 2;
        show_panel_jiben.setVisible(false);
        show_panel_jiben.setEnabled(false);
        show_panel_sal.setVisible(true);
        show_panel_sal.setEnabled(true);
        show_panel_sec.setVisible(false);
        show_panel_sec.setEnabled(false);
        show_panel_pla.setVisible(false);
        show_panel_pla.setEnabled(false);
        show_panel_emh.setVisible(false);
        show_panel_emh.setEnabled(false);
    }

    private void set_jiben(){
        sear_mode = 1;
        show_panel_jiben.setVisible(true);
        show_panel_jiben.setEnabled(true);
        show_panel_sal.setVisible(false);
        show_panel_sal.setEnabled(false);
        show_panel_sec.setVisible(false);
        show_panel_sec.setEnabled(false);
        show_panel_pla.setVisible(false);
        show_panel_pla.setEnabled(false);
        show_panel_emh.setVisible(false);
        show_panel_emh.setEnabled(false);
    }


}