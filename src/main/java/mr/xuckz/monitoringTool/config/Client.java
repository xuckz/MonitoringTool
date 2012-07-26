package mr.xuckz.monitoringTool.config;

import java.util.List;

public class Client
{
    String ip;
	ClientType type;
    List<String> networkInterfaces;

    public Client(){}

    public Client(String ip, ClientType type, List<String> networkInterfaces)
    {
        this.ip = ip;
		this.type = type;
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

	public ClientType getType()
	{
		return type;
	}

	@Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("IP: " + ip + "\n");
		sb.append("Type: " + type + "\n");

        for(String iface : networkInterfaces)
        {
            sb.append("Interface: " + iface + "\n");
        }

        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

	public String toHtmlString()
	{
		return toString().replaceAll("\n", "<br>");
	}
}
