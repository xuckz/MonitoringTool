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

		sb.append("System #").append(this.index).append(" IP: ").append(this.ip).append("\n");
		sb.append("System #").append(this.index).append(" Description: ").append(this.description).append("\n");
		sb.append("System #").append(this.index).append(" Location: ").append(this.location).append("\n");
		sb.append("System #").append(this.index).append(" Contact: ").append(this.contact).append("\n");
		sb.append("System #").append(this.index).append(" System Name: ").append(this.sys_name).append("\n");
		sb.append("System #").append(this.index).append(" Uptime: ").append(this.uptime).append("\n");
		sb.append("System #").append(this.index).append(" Date: ").append(this.date).append("\n");
		sb.append("System #").append(this.index).append(" Processes: ").append(this.num_processes);

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
