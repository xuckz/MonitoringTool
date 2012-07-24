package mr.xuckz.monitoringTool.snmp.data;

import mr.xuckz.monitoringTool.snmp.data.device.SnmpDevice;
import mr.xuckz.monitoringTool.snmp.data.storage.SnmpStorage;
import mr.xuckz.monitoringTool.snmp.data.system.SnmpSystem;
import mr.xuckz.monitoringTool.snmp.util.SnmpConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SnmpObject implements SnmpObjectType
{
    static final Logger log = LoggerFactory.getLogger(SnmpObject.class);

    private SnmpConnection target;
    private SnmpSystem snmpSystem;
    private SnmpStorage snmpStorage;
	private SnmpDevice snmpDevice;

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
		snmpDevice = new SnmpDevice(target);

        return (this.initialized = update());
    }

    public boolean update()
    {
        if(snmpSystem.update() && snmpStorage.update() && snmpDevice.update())
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

	public SnmpDevice getSnmpDevice()
	{
		return snmpDevice;
	}
}
