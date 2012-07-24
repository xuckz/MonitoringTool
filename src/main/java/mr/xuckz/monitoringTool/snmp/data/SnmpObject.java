package mr.xuckz.monitoringTool.snmp.data;

import mr.xuckz.monitoringTool.snmp.data.storage.SnmpStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SnmpObject implements SnmpObjectType
{
    static final Logger log = LoggerFactory.getLogger(SnmpObject.class);

    private SnmpConnection target;
    private SnmpSystem snmpSystem;
    private SnmpStorage snmpStorage;
    boolean initialized;

    public SnmpObject(SnmpConnection target)
    {
        this.target = target;
        this.initialized = false;
    }

    public boolean init()
    {
        snmpStorage = new SnmpStorage(target);
        snmpSystem = new SnmpSystem(target);

        return (this.initialized = update());
    }

    public boolean update()
    {
        if(snmpSystem.update() && snmpStorage.update())
        {
            return true;
        }

        return false;
    }

	public String toHtmlString()
	{
		return "";
	}

	public SnmpSystem getSnmpSystem()
    {
        return snmpSystem;
    }

    public SnmpStorage getSnmpStorage()
    {
        return snmpStorage;
    }
}
