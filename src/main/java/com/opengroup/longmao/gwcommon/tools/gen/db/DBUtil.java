package com.opengroup.longmao.gwcommon.tools.gen.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.opengroup.longmao.gwcommon.tools.gen.util.Constant;
import com.opengroup.longmao.gwcommon.tools.gen.util.GenUtil;

public class DBUtil {

	// 创建连接
	private static Connection con;
	private static PreparedStatement pStemt = null;

	// 列名数组
	public static String[] colnames;
	// 列名数据库类型数组
	public static String[] colTypes;
	// 列名长度数组
	public static int[] colSizes;
	// 字段注释
	public static String[] remarks;
	// 列名java类型数组
	public static String[] columnClassName;

	// 处理表名字
	public static String bigTableName;
	// 首字母小写
	public static String smallTableName;

	/**
	 * Java方法 处理数据库字段
	 * 
	 */
	public static void batchTableProperty(String tablename) {
		// 处理表名字
		bigTableName = GenUtil.bigTableName(tablename);
		// 首字母小写
		smallTableName = GenUtil.strChange(tablename);
		// 查要生成实体类的表
		String sql = "select * from " + tablename;
		try {
			Class.forName(Constant.DRIVER);
			con = DriverManager.getConnection(Constant.URL, Constant.NAME, Constant.PASS);
			pStemt = con.prepareStatement(sql);
			ResultSetMetaData rsmd = pStemt.getMetaData();
			int size = rsmd.getColumnCount(); // 统计列
			// 列名数组
			colnames = new String[size];
			// 列名数据库类型数组
			colTypes = new String[size];
			// 列名长度数组
			colSizes = new int[size];
			// 字段注释
			remarks = new String[size];
			// 列名java类型数组
			columnClassName = new String[size];

			// 再次连接数据库得到字段注释
			DatabaseMetaData dbmd = con.getMetaData();
			ResultSet rs = dbmd.getColumns(null, "%", tablename, "%");

			for (int i = 0; i < size; i++) {
				colnames[i] = rsmd.getColumnName(i + 1);
				colTypes[i] = rsmd.getColumnTypeName(i + 1);
				colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
				columnClassName[i] = rsmd.getColumnClassName(i + 1);
				while (rs.next()) {
//					System.out.println("字段名：" + rs.getString("COLUMN_NAME") + "\t字段注释：" + rs.getString("REMARKS")+ "\t字段数据类型：" + rs.getString("TYPE_NAME"));
					if (rs.getString("COLUMN_NAME").equals(colnames[i])) {
						remarks[i] = rs.getString("REMARKS");
						break;
					}
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Java方法 得到当前数据库下所有的表名
	 * 
	 * @param con
	 */
	public List<String> getTableName() {
		List<String> list = new ArrayList<String>();
		try {
			DatabaseMetaData meta = con.getMetaData();
			ResultSet rs = meta.getTables(null, null, null, new String[] { "TABLE" });
			while (rs.next()) {
				list.add(rs.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
