package mr.xuckz.monitoringTool.snmp.data.storage;

import mr.xuckz.monitoringTool.config.Client;
import org.snmp4j.smi.OID;

public class StorageFactory
{
	public static Storage getStorage(Client client, Integer index, String description, OID storageTypeOID, Integer allocation_units, Integer bytes_size, Integer bytes_used)
	{
		return new Storage(client, index, description, StorageType.get(storageTypeOID), allocation_units, bytes_size, bytes_used);
	}
}
