package mr.xuckz.monitoringTool.web.model;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name="Client.uniqueByIp", query="from Client c where c.ip = :ip")
})
public class Client
{
    @Id
    @Column(name="client_ip")
    private String ip;

    private String name;


    public Client()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }
}