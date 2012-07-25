package mr.xuckz.monitoringTool.snmp.util;

public class SnmpConnectionFactory
{
    public static SnmpConnection getSnmpConnection(String ip, String community)
    {
        return new SnmpConnection(ip, community);
    }

    public static SnmpConnection getPublicSnmpConnection(String ip)
    {
        return new SnmpConnection(ip, "public");
    }
}
