package mr.xuckz.monitoringTool.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigParameters
{
    private static final Logger log = LoggerFactory.getLogger(ConfigParameters.class);

    private String DRIVER;
    private String ADDRESS;
    private String DATABASE;
    private String USER;
    private String PASSWORD;

    private List<String> clientIpList;

    public ConfigParameters(Properties properties)
    {
        this.ADDRESS = properties.getProperty("db.address");
        this.DATABASE = properties.getProperty("db.database");
        this.DRIVER = properties.getProperty("db.driver");
        this.PASSWORD = properties.getProperty("db.password");
        this.USER = properties.getProperty("db.user");

        clientIpList = new ArrayList<String>();
        int count = 1;
        while(properties.getProperty("client.ip." + Integer.toString(count)) != null)
        {
            clientIpList.add(properties.getProperty("client.ip." + Integer.toString(count)));
            log.debug("Client with ip: '{}' added.", properties.getProperty("client.ip." + Integer.toString(count)));
            count++;
        }
    }

    public String getDRIVER()
    {
        return DRIVER;
    }

    public String getADDRESS()
    {
        return ADDRESS;
    }

    public String getDATABASE()
    {
        return DATABASE;
    }

    public String getUSER()
    {
        return USER;
    }

    public String getPASSWORD()
    {
        return PASSWORD;
    }

    public List<String> getClientIpList()
    {
        return clientIpList;
    }
}
