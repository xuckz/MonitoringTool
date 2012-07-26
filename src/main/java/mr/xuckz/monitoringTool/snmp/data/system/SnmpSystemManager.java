package mr.xuckz.monitoringTool.snmp.data.system;

import mr.xuckz.monitoringTool.snmp.data.SnmpDataManager;
import mr.xuckz.monitoringTool.snmp.util.SnmpActions;
import mr.xuckz.monitoringTool.snmp.util.SnmpConnection;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;

public class SnmpSystemManager extends SnmpDataManager
{
    private static final OID SYSTEM_DESCRIPTION = new OID("1.3.6.1.2.1.1.1.0");
    private static final OID SYSTEM_UPTIME = new OID("1.3.6.1.2.1.25.1.1.0");
    private static final OID SYSTEM_CONTACT = new OID("1.3.6.1.2.1.1.4.0");
    private static final OID SYSTEM_NAME = new OID("1.3.6.1.2.1.1.5.0");
    private static final OID SYSTEM_LOCATION = new OID("1.3.6.1.2.1.1.6.0");
    private static final OID SYSTEM_NUM_PROCESSES = new OID("1.3.6.1.2.1.25.1.6.0");
    private static final OID SYSTEM_DATE = new OID("1.3.6.1.2.1.25.1.2.0");

	private String name;
    private System system;

    public SnmpSystemManager(SnmpConnection target)
    {
        super(target);
    }

    public boolean initialize()
    {

        Variable system_desc_var = SnmpActions.snmpGet(target, SYSTEM_DESCRIPTION);
        Variable system_location_var = SnmpActions.snmpGet(target, SYSTEM_LOCATION);
        Variable system_contact_var = SnmpActions.snmpGet(target, SYSTEM_CONTACT);
        Variable system_name_var = SnmpActions.snmpGet(target, SYSTEM_NAME);
        Variable system_uptime_var = SnmpActions.snmpGet(target, SYSTEM_UPTIME);
        Variable system_num_var = SnmpActions.snmpGet(target, SYSTEM_NUM_PROCESSES);
        Variable system_date_var = SnmpActions.snmpGet(target, SYSTEM_DATE);


        if (system_desc_var != null &&
                system_location_var != null &&
                system_contact_var != null &&
                system_name_var != null &&
                system_uptime_var != null &&
                system_num_var != null &&
                system_date_var != null)
        {
            system = SystemFactory.getSystem(system_desc_var.toString(), target.getClient().getIp(), system_location_var.toString(), system_contact_var.toString(),
                    system_name_var.toString(), system_uptime_var.toString(), system_num_var.toInt(), octetStringToDateString(system_date_var));

            log.info("SnmpSystemManager for ip: '" + target.getClient().getIp() + "' initialized!");
            return true;
        }


        log.error("SnmpSystemManager for ip: '" + target.getClient().getIp() + "' could not be initialized!");
        return false;
    }

    public boolean update()
    {
        Variable system_uptime_var = SnmpActions.snmpGet(target, SYSTEM_UPTIME);
        Variable system_num_var = SnmpActions.snmpGet(target, SYSTEM_NUM_PROCESSES);
        Variable system_date_var = SnmpActions.snmpGet(target, SYSTEM_DATE);

        if (system_uptime_var != null &&
                system_num_var != null &&
                system_date_var != null)
        {
            system.update(system_uptime_var.toString(), system_num_var.toInt(), octetStringToDateString(system_date_var));
            return true;
        }

        log.error("SnmpSystemManager for ip: '" + target.getClient().getIp() + "' could not be updated!");
        return false;
    }

    private boolean setName()
    {
        Variable var;
        if ((var = SnmpActions.snmpGet(target, SYSTEM_NAME)) != null)
        {
            name = var.toString();
            return true;
        }

        return false;
    }

    public String getName()
    {
        return name;
    }

    //(Integer index, String description, String location, String contact, String sys_name, String uptime, Integer num_processes, String date)
    @Override
    public String toString()
    {
		StringBuilder sb = new StringBuilder();

		sb.append(system.toString());

        return sb.toString();
    }

    public System getSystem()
    {
        return system;
    }
}
