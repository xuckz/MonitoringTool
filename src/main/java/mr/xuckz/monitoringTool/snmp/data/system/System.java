package mr.xuckz.monitoringTool.snmp.data.system;

public class System
{
    private Integer index;
    private String ip;
    private String description;
    private String location;
    private String contact;
    private String sys_name;
    private String uptime;
    private Integer num_processes;
    private String date;

    public System(Integer index, String ip, String description, String location, String contact, String sys_name, String uptime, Integer num_processes, String date)
    {
        this.index = index;
        this.ip = ip;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.sys_name = sys_name;
        this.uptime = uptime;
        this.num_processes = num_processes;
        this.date = date;
    }

    public void update(String uptime, Integer num_processes, String date)
    {
        this.uptime = uptime;
        this.num_processes = num_processes;
        this.date = date;
    }

    public Integer getIndex()
    {
        return index;
    }

    public void setIndex(Integer index)
    {
        this.index = index;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getContact()
    {
        return contact;
    }

    public void setContact(String contact)
    {
        this.contact = contact;
    }

    public String getSys_name()
    {
        return sys_name;
    }

    public void setSys_name(String sys_name)
    {
        this.sys_name = sys_name;
    }

    public String getUptime()
    {
        return uptime;
    }

    public void setUptime(String uptime)
    {
        this.uptime = uptime;
    }

    public Integer getNum_processes()
    {
        return num_processes;
    }

    public void setNum_processes(Integer num_processes)
    {
        this.num_processes = num_processes;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
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
