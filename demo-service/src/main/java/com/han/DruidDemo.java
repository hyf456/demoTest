package com.han;


/**
 * @Author: hanyf
 * @Description: druid源码测试
 * @Date: 2018/11/2 14:50
 */
public class DruidDemo {
    // public static void main(String[] args) throws SQLException {
    //     Connection conn = null;
    //     PreparedStatement ps = null;
    //     ResultSet rs = null;
    //     try {
    //         // 1,创建Druid连接池对象
    //         DruidDataSource dataSource = new DruidDataSource();
    //         // 2,为数据库添加配置文件
    //         dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    //         dataSource
    //                 .setUrl("jdbc:mysql://192.168.177.159/oms?useSSL=false&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&autoReconnect=true&failOverReadOnly=false");
    //         dataSource.setUsername("scmUser");
    //         dataSource.setPassword("111111");
    //         // 用Druid来连接
    //         conn = dataSource.getConnection();
    //
    //
    //         // 2,执行数据库语句
    //         String sql = "SELECT * FROM order_policy";
    //
    //
    //         // 3,用prepareStatement获取sql语句
    //         ps = conn.prepareStatement(sql);
    //
    //
    //         // 4,执行sql语句,查询用executeQuery,增删改用executeUpdate
    //         rs = ps.executeQuery();
    //         while (rs.next()) {
    //             System.out.println(rs.getInt("id") + " "
    //                     + rs.getString("sales_channel_id"));
    //         }
    //         String sql1 = "SELECT * FROM order_policy_pre_sale";
    //
    //         ps = conn.prepareStatement(sql1);
    //         ps.executeQuery();
    //     } catch (Exception e) {
    //         // TODO: handle exception
    //         e.printStackTrace();
    //     } finally {
    //         // 释放资源
    //         conn.close();
    //     }
    // }

}
