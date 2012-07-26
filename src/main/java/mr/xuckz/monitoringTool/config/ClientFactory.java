package mr.xuckz.monitoringTool.config;

import java.util.List;

public class ClientFactory
{
    public static Client getClient(String ip, ClientType type, List<String> interfaceList)
    {
        return new Client(ip, type, interfaceList);
    }
}
