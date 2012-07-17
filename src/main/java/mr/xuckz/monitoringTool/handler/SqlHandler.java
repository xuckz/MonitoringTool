package mr.xuckz.monitoringTool.handler;

import mr.xuckz.monitoringTool.config.ConfigParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlHandler
{
	private String DRIVER;
	private String ADDRESS;
	private String DATABASE;
	private String USER;
	private String PASSWORD;

    private boolean isConnected;

	private Connection connection = null;

    static final Logger log = LoggerFactory.getLogger(SqlHandler.class);

	public SqlHandler(ConfigParameters config)
	{
        this.DRIVER = config.getDRIVER();
        this.ADDRESS = config.getADDRESS();
        this.DATABASE = config.getDATABASE();
        this.USER = config.getUSER();
        this.PASSWORD = config.getPASSWORD();
	}


    //## CONNECTION
	private String getUrl()
	{
		return (ADDRESS + "/" + DATABASE);
	}

    public void disconnect()
    {
        isConnected = false;

        try
        {
            if(connection != null)
                connection.close();
        }

        catch (SQLException e)
        {
            log.error("Disconnect Failed!", e);
        }
    }

    public boolean connect()
    {
        if(!checkForDriver(DRIVER))
            return false;

        connection = null;

        try
        {
            connection = DriverManager.getConnection(getUrl(), USER, PASSWORD);
            isConnected = true;
            return true;
        }

        catch (SQLException sqle)
        {
            log.error("Connection Failed!", sqle);
            isConnected = false;
            return false;
        }
    }

    private boolean checkForDriver(String DRIVER)
    {
        try
        {
            Class.forName(DRIVER);
            return true;
        }

        catch (ClassNotFoundException e)
        {
            log.error("PostgreSQL JDBC Driver not found", e);
            return false;
        }
    }
}
