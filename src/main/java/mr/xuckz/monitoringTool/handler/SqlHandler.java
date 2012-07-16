package mr.xuckz.monitoringTool.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlHandler
{
	private String DRIVER;
	private String HOST;
	private String PORT;
	private String DATABASE;
	private String USER;
	private String PASSWORD;

	private Connection connection = null;

	public SqlHandler()
	{
		DRIVER = "org.postgresql.Driver";
		HOST = "192.168.255.50";
		PORT = "5432";
		DATABASE = "postgres";
		USER = "postgres";
		PASSWORD = "";
	}

	public void disconnect()
	{
		try
		{
			connection.close();
		}

		catch (SQLException e)
		{
			e.printStackTrace();
		}

		System.out.println("\nconnection closed");
	}

	private String getUrl()
	{
		// jdbc:postgresql://host:port/database
		return ("jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE);
	}

	private boolean loadJdbcDriver()
	{
		try
		{
			Class.forName(DRIVER);
		}

		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			return false;
		}

		System.out.println("driver loaded");
		return true;
	}

	public boolean connect()
	{
		try
		{
			connection = DriverManager.getConnection(getUrl(), USER, PASSWORD);
		}

		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}

		System.out.println("connection opened");
		return true;
	}
}
