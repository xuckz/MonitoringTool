package mr.xuckz.monitoringTool.snmp.data.system;

import mr.xuckz.monitoringTool.snmp.data.SnmpObjectType;
import mr.xuckz.monitoringTool.snmp.util.SnmpActions;
import mr.xuckz.monitoringTool.snmp.util.SnmpConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;

public class SnmpSystem implements SnmpObjectType
{
    private String name;
    private SnmpConnection target;
    static final Logger log = LoggerFactory.getLogger(SnmpSystem.class);

    private static final OID SYSTEM_NAME = new OID("1.3.6.1.2.1.1.5.0");

    public SnmpSystem(SnmpConnection target)
    {
        this.target = target;
    }

    public boolean update()
    {
        if(this.setName())
            return true;

        return false;
    }

	private boolean setName()
    {
        Variable var;
        if((var = SnmpActions.snmpGet(target, SYSTEM_NAME)) != null)
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

	public String toHtmlString()
	{
		return "";  //To change body of implemented methods use File | Settings | File Templates.
	}
}
