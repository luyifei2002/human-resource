/**
 * Copyright (C), 2021-2022, 我是你爸爸有限公司
 * FileName: mySwingDemo3
 * Author:   Administrator
 * Date:     2022/6/9 10:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package swingDemo;

import javax.swing.*;
import java.awt.*;

/**
 * 〈一句话功能简述〉 
 * 〈〉
 *
 * @author Administrator
 * @create 2022/6/9
 * @since 1.0.0
 */
public class mySwingDemo3 {

    public mySwingDemo3(){
        JFrame jFrame = new JFrame();
        jFrame.setSize(new Dimension(800,600));
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel1 = new JPanel(), panel2 = new JPanel();
        panel1.setBackground(new Color(0, 0, 0));
        panel2.setBackground(new Color(200,200,200));

        panel1.setPreferredSize(new Dimension(750,550));
        panel1.setBounds(25,25,750,550);
        panel2.setPreferredSize(new Dimension(700,500));
        panel2.setBounds(25,25,700,500);

        panel1.add(panel2);
        jFrame.add(panel1);

        jFrame.setVisible(true);

    }

    public static void main(String[] args) {
        new mySwingDemo3();
    }

}