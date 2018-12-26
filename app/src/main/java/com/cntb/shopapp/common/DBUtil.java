package com.cntb.shopapp.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by huangjianping on 2018/12/11.
 */

public class DBUtil {
    private  String name=null;
    private  String pass=null;

    public DBUtil(String m,String p){
        this.name=m;
        this.pass=p;
    }
    private static Connection getSQLConnection(String ip, String user, String pwd, String db)
    {
        Connection con = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://" + ip + ":1433/" + db + ";charset=utf8", user, pwd);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return con;
    }

    public int curAmount(String pguid)
    {
        int result=-1;
        try
        {
            Connection conn= getSQLConnection("192.168.1.103", name, pass, "PrintDB");
            String sql = "select sum(AMOUNT) as recAmount from PRT_TRADEINFOS where pguid=? and deleteflag=0";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, pguid);
            ResultSet rs = stat.executeQuery();
            while (rs.next())
            {
                String mount = rs.getString("recAmount");
                result= Integer.parseInt(mount);
            }
            rs.close();
            conn.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }

}
