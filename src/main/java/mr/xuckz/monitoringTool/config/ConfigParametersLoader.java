package mr.xuckz.monitoringTool.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class ConfigParametersLoader
{
    private static final String PROPERTIES_FILENAME = "monitoringTool.properties";

    private static final Logger log = LoggerFactory.getLogger(ConfigParametersLoader.class);

    private ConfigParametersLoader()
    {
    }

    public static ConfigParameters loadParameters()
    {
        ConfigParameters result;

        try
        {
            Properties properties = new Properties();
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILENAME));

            if(!properties.isEmpty())
            {
                result = new ConfigParameters(properties);
                log.info(PROPERTIES_FILENAME + " loaded successfully");
            }

            else
            {
                log.error("could not load: " + PROPERTIES_FILENAME);
                result = null;
            }
        }

        catch (IOException e)
        {
            log.error(e.getMessage(), e);
            result = null;
        }

        return result;
    }
}
