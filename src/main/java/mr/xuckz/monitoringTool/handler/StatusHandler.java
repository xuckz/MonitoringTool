package mr.xuckz.monitoringTool.handler;

import mr.xuckz.monitoringTool.web.model.Client;
import mr.xuckz.monitoringTool.web.model.FileSystem;
import mr.xuckz.monitoringTool.web.model.Performance;
import mr.xuckz.monitoringTool.web.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class StatusHandler
{
    static final Logger log = LoggerFactory.getLogger(StatusHandler.class);

    public static Status retrieveStatus(String ip)
    {
        Status status = new Status();

        log.info("reading status info");

        Client client = new Client();
        client.setIp(ip);
        client.setName("clientName");

        status.setClient(client);

        Performance performance = new Performance();
        performance.setCpu("10");
        performance.setRam("20");
        performance.setStatus(status);

        status.setPerformance(performance);

        FileSystem fileSystem = new FileSystem();
        fileSystem.setFree("80");
        fileSystem.setUsed("20");
        fileSystem.setStatus(status);

        status.setFileSystem(fileSystem);
        status.setDate(new Date());

        return status;
    }
}
