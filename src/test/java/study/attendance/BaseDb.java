package study.attendance;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;


/**
 * DB接続ベースクラス 接続と解放は当クラスで実装
 * @author favor
 *
 */
public class BaseDb {

	/**
	 * コネクション取得
	 * @return
	 * @throws Exception
	 */
	Connection getConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306?serverTimezone=JST", "root",
				"xxxxxx");
		return con;
	}

	/**
	 * DBUnit用のコネクション取得
	 * @param con
	 * @return
	 * @throws Exception
	 */
	IDatabaseConnection getIDatabaseConnection(Connection con) throws Exception {
		return new DatabaseConnection(con,"appendance");
	}


	/**
	 * コネクション解放
	 * @param con
	 * @throws Exception
	 */
	void closeConnection(Connection con) throws Exception{
		if (con!= null) {
			con.close();
		}
	}

	/**
	 * 勤怠テーブルにテストデータ登録
	 * @param dbcon
	 * @throws Exception
	 */
	void insertTestData(IDatabaseConnection dbcon) throws Exception{
		String testDataFileName = BaseDb.class.getClassLoader().getResource("testData/appnedance.xlsx").getPath();
		DatabaseConfig config = dbcon.getConfig();
		config.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
		IDataSet testDataSet = new XlsDataSet(new File(testDataFileName));
		DatabaseOperation.CLEAN_INSERT.execute(dbcon, testDataSet);
	}

}
