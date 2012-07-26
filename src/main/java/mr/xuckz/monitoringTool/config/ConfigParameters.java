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

    private List<Client> clientList;

    public ConfigParameters(Properties properties)
    {
        this.ADDRESS = properties.getProperty("db.address");
        this.DATABASE = properties.getProperty("db.database");
        this.DRIVER = properties.getProperty("db.driver");
        this.PASSWORD = properties.getProperty("db.password");
        this.USER = properties.getProperty("db.user");

        clientList = new ArrayList<Client>();
        int count = 1;
        while(properties.getProperty(Integer.toString(count) + ".client.ip") != null)
        {
            List<String> interfaceList = new ArrayList<String>();

            int iCount = 1;
            while(properties.getProperty(Integer.toString(count) + ".client.interface." + Integer.toString(iCount)) != null)
            {
                interfaceList.add(properties.getProperty(Integer.toString(count) + ".client.interface." + Integer.toString(iCount)));
                iCount++;
            }

            clientList.add(ClientFactory.getClient(properties.getProperty(Integer.toString(count) + ".client.ip"), interfaceList));

            log.info("Client added:\n" + clientList.get(count - 1).toString());
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

    public List<Client> getClientList()
    {
        return clientList;
    }
}
