package mr.xuckz.monitoringTool.config;

import java.util.List;

public class Client
{
    String ip;
    List<String> networkInterfaces;

    public Client(){}

    public Client(String ip, List<String> networkInterfaces)
    {
        this.ip = ip;
        this.networkInterfaces = networkInterfaces;
    }

    public List<String> getNetworkInterfaces()
    {
        return networkInterfaces;
    }

    public String getIp()
    {
        return ip;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("IP: " + ip + "\n");

        for(String iface : networkInterfaces)
        {
            sb.append("Interface: " + iface + "\n");
        }

        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }
}
