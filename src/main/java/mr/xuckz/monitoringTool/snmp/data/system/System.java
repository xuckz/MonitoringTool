package mr.xuckz.monitoringTool.snmp.data.system;

import mr.xuckz.monitoringTool.snmp.data.SnmpDataObject;

public class System extends SnmpDataObject
{
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

	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append("System #" + this.index + " IP: " + this.ip + "\n");
		sb.append("System #" + this.index + " Description: " + this.description + "\n");
		sb.append("System #" + this.index + " Location: " + this.location + "\n");
		sb.append("System #" + this.index + " Contact: " + this.contact + "\n");
		sb.append("System #" + this.index + " System Name: " + this.sys_name + "\n");
		sb.append("System #" + this.index + " Uptime: " + this.uptime + "\n");
		sb.append("System #" + this.index + " Date: " + this.date + "\n");
		sb.append("System #" + this.index + " Processes: " + this.num_processes);

		return sb.toString();
	}

    public String getDescription()
    {
        return description;
    }

    public String getLocation()
    {
        return location;
    }

    public String getContact()
    {
        return contact;
    }

    public String getSys_name()
    {
        return sys_name;
    }

    public String getUptime()
    {
        return uptime;
    }

    public Integer getNum_processes()
    {
        return num_processes;
    }

    public String getDate()
    {
        return date;
    }

    public String getIp()
    {
        return ip;
    }
}
