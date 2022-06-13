## jar包在./out目录下

## 使用方法
在你自己的数据库的staffs表里加一列，列名为'password'
在gaussdb类中将相关参数改为你自己的数据库即可


## 项目结构
 
#### dataClass软件包中有三个类：
tableData类是gaussdb和mySwing联系的桥梁，能将gaussdb获取的ResultSet类转换为swing的JTable类能使用的数据。
userPassword类是储存loginPanel登录界面所需要的用户名、密码和权限组的类
userPasswordUnit类是userPassword类的单元，实际上userPassword就是userPasswordUnit的一个ArrayList

#### gaussdb软件包只有一个类：
gaussdb类里面是许多方法，是连接数据库获取数据的类


#### mySwing软件包有五个类：
mainFrame类继承自JFrame，是主窗口
loginPanel类继承自JPanel，是登录界面
showPanel1类继承自JPanel，是员工界面
showPanel2类继承自JPanel，是经理界面
showPanel3类继承自JPanel，是人事经理界面

#### main类是主方法（只是调用一下mainFrame而已）
