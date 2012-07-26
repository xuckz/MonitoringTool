package mr.xuckz.monitoringTool.config;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name="Client.uniqueByIp", query="from Client c where c.ip = :ip")
})
public class Client
{
    @Id
    String ip;

    @ElementCollection(fetch = FetchType.EAGER)
    List<String> networkInterfaces;

    public Client(){}

    public Client(String ip, List<String> networkInterfaces)
    {
        this.ip = ip;
        this.networkInterfaces = networkInterfaces;
    }

    public void setNetworkInterfaces(List<String> networkInterfaces)
    {
        this.networkInterfaces = networkInterfaces;
    }

    public List<String> getNetworkInterfaces()
    {
        return networkInterfaces;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
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
