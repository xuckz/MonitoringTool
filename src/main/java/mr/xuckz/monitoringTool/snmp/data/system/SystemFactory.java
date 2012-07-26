package mr.xuckz.monitoringTool.snmp.data.system;

public class SystemFactory
{
    public static System getSystem(String description, String ip, String location, String contact, String sys_name, String uptime, Integer num_processes, String date)
    {
        return new System(0, ip, description, location, contact, sys_name, uptime, num_processes, date);
    }
}
