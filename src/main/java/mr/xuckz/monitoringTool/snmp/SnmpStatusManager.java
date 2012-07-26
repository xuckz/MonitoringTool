package mr.xuckz.monitoringTool.snmp;

import mr.xuckz.monitoringTool.snmp.data.SnmpDataManager;
import mr.xuckz.monitoringTool.snmp.data.device.SnmpDeviceManager;
import mr.xuckz.monitoringTool.snmp.data.storage.SnmpStorageManager;
import mr.xuckz.monitoringTool.snmp.data.system.SnmpSystemManager;
import mr.xuckz.monitoringTool.snmp.util.SnmpConnection;

public class SnmpStatusManager extends SnmpDataManager
{
    private SnmpSystemManager snmpSystemManager;
    private SnmpStorageManager snmpStorageManager;
	private SnmpDeviceManager snmpDevices;

    boolean initialized;

    public SnmpStatusManager(SnmpConnection target)
    {
        super(target);

        snmpStorageManager = SnmpManagerFactory.getSnmpStorageManager(target);
        snmpSystemManager = SnmpManagerFactory.getSnmpSystemManager(target);
        snmpDevices = SnmpManagerFactory.getSnmpDeviceManager(target, target.getClient().getType());

        this.initialized = false;
    }

    public boolean initialize()
    {
        if(!snmpSystemManager.initialize())
            return false;

        if(!snmpStorageManager.initialize())
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
        if(!snmpSystemManager.update())
            return false;

        if(!snmpStorageManager.update())
            return false;

        if(!snmpDevices.update())
            return false;

        return true;
    }

	public SnmpSystemManager getSnmpSystemManager()
    {
        return snmpSystemManager;
    }

    public SnmpStorageManager getSnmpStorageManager()
    {
        return snmpStorageManager;
    }

    public SnmpDeviceManager getSnmpDevices()
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
