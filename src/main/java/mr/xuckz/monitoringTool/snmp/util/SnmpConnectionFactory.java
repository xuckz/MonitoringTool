package mr.xuckz.monitoringTool.snmp.util;

import mr.xuckz.monitoringTool.config.Client;

public class SnmpConnectionFactory
{
    public static SnmpConnection getSnmpConnection(Client client, String community)
    {
        return new SnmpConnection(client, community);
    }

    public static SnmpConnection getPublicSnmpConnection(Client client)
    {
        return new SnmpConnection(client, "public");
    }
}
