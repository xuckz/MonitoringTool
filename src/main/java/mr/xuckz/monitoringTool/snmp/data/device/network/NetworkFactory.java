package mr.xuckz.monitoringTool.snmp.data.device.network;

import java.util.Date;

public class NetworkFactory
{
    public static Network getNetwork(String description, String type, Integer index, long bytes_in, long bytes_out)
    {
        return new Network(description, type, index, bytes_in, bytes_out, new Date());
    }
}
