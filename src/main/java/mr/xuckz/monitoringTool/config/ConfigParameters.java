package mr.xuckz.monitoringTool.config;

import java.util.Properties;

public class ConfigParameters
{
    private String DRIVER;
    private String ADDRESS;
    private String DATABASE;
    private String USER;
    private String PASSWORD;

    public ConfigParameters(Properties properties)
    {
        this.setADDRESS(properties.getProperty("db.address"));
        this.setDATABASE(properties.getProperty("db.database"));
        this.setDRIVER(properties.getProperty("db.driver"));
        this.setPASSWORD(properties.getProperty("db.password"));
        this.setUSER(properties.getProperty("db.user"));
    }

    public String getDRIVER()
    {
        return DRIVER;
    }

    public void setDRIVER(String DRIVER)
    {
        this.DRIVER = DRIVER;
    }

    public String getADDRESS()
    {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS)
    {
        this.ADDRESS = ADDRESS;
    }

    public String getDATABASE()
    {
        return DATABASE;
    }

    public void setDATABASE(String DATABASE)
    {
        this.DATABASE = DATABASE;
    }

    public String getUSER()
    {
        return USER;
    }

    public void setUSER(String USER)
    {
        this.USER = USER;
    }

    public String getPASSWORD()
    {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD)
    {
        this.PASSWORD = PASSWORD;
    }
}
