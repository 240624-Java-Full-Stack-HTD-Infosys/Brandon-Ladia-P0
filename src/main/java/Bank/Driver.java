package Bank;

import Bank.utils.ServerUtil;

import java.io.IOException;
import java.sql.SQLException;

//do we need this? the answer is yes
public class Driver {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        ServerUtil.getServerUtil().initialize();

        //find a way to run the script into main
        //or just copy/paste it into DBeaver
    }
}
