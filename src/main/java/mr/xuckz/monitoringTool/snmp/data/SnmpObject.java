package mr.xuckz.monitoringTool.snmp.data;

import mr.xuckz.monitoringTool.snmp.data.device.SnmpDevices;
import mr.xuckz.monitoringTool.snmp.data.storage.SnmpStorage;
import mr.xuckz.monitoringTool.snmp.data.system.SnmpSystem;
import mr.xuckz.monitoringTool.snmp.util.SnmpConnection;

public class SnmpObject extends SnmpObjectType
{
    private SnmpSystem snmpSystem;
    private SnmpStorage snmpStorage;
	private SnmpDevices snmpDevices;

    boolean initialized;

    public SnmpObject(SnmpConnection target)
    {
        super(target);

        snmpStorage = new SnmpStorage(target);
        snmpSystem = new SnmpSystem(target);
        snmpDevices = new SnmpDevices(target);
        this.initialized = false;
    }

    public boolean initialize()
    {
        if(!snmpSystem.initialize())
            return false;

        if(!snmpStorage.initialize())
            return false;

        if(!snmpDevices.initialize())
            return false;

        return true;
    }

    @Override
    public String toString()
    {
        return "";
    }

    public boolean update()
    {
        if(!snmpSystem.update())
            return false;

        if(!snmpStorage.update())
            return false;

        if(!snmpDevices.update())
            return false;

        return true;
    }

	public SnmpSystem getSnmpSystem()
    {
        return snmpSystem;
    }

    public SnmpStorage getSnmpStorage()
    {
        return snmpStorage;
    }

    public SnmpDevices getSnmpDevices()
    {
        return snmpDevices;
    }

    public boolean isInitialized()
    {
        return initialized;
    }

    public void setInitialized(boolean initialized)
    {
        this.initialized = initialized;
    }
}
