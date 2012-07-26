package mr.xuckz.monitoringTool.snmp;

import mr.xuckz.monitoringTool.config.ClientType;
import mr.xuckz.monitoringTool.snmp.data.device.SnmpDeviceManager;
import mr.xuckz.monitoringTool.snmp.data.device.SnmpDeviceManager_DDWRT;
import mr.xuckz.monitoringTool.snmp.data.device.SnmpDeviceManager_DESKTOP;
import mr.xuckz.monitoringTool.snmp.data.storage.SnmpStorageManager;
import mr.xuckz.monitoringTool.snmp.data.system.SnmpSystemManager;
import mr.xuckz.monitoringTool.snmp.util.SnmpConnection;

public class SnmpManagerFactory
{
	public static SnmpDeviceManager getSnmpDeviceManager(SnmpConnection target, ClientType clientType)
	{
		switch(clientType)
		{
			case DESKTOP: return new SnmpDeviceManager_DESKTOP(target);
			case DDWRT: return new SnmpDeviceManager_DDWRT(target);
		}

		return new SnmpDeviceManager(target);
	}

	public static SnmpStatusManager getSnmpStatusManager(SnmpConnection target)
	{
		return new SnmpStatusManager(target);
	}

	public static SnmpSystemManager getSnmpSystemManager(SnmpConnection target)
	{
		return new SnmpSystemManager(target);
	}

	public static SnmpStorageManager getSnmpStorageManager(SnmpConnection target)
	{
		return new SnmpStorageManager(target);
	}
}
