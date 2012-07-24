package mr.xuckz.monitoringTool.snmp.data.storage;

import mr.xuckz.monitoringTool.snmp.util.SnmpConnection;
import mr.xuckz.monitoringTool.snmp.data.SnmpObjectType;
import mr.xuckz.monitoringTool.snmp.util.SnmpActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnmpStorage implements SnmpObjectType
{
	static final Logger log = LoggerFactory.getLogger(SnmpStorage.class);

    private static final OID STORAGE_TOP_LEVEL = new OID("1.3.6.1.2.1.25.2");
	private static final OID STORAGE_INDEX = new OID("1.3.6.1.2.1.25.2.3.1.1");
	private static final OID STORAGE_TYPE = new OID("1.3.6.1.2.1.25.2.3.1.2");
	private static final OID STORAGE_DESCRIPTION = new OID("1.3.6.1.2.1.25.2.3.1.3");
	private static final OID STORAGE_BYTES_SIZE = new OID("1.3.6.1.2.1.25.2.3.1.5");
	private static final OID STORAGE_BYTES_USED = new OID("1.3.6.1.2.1.25.2.3.1.6");
	private static final OID STORAGE_ALLOCATION_UNITS = new OID("1.3.6.1.2.1.25.2.3.1.4");

    private SnmpConnection target;

    private Map<Integer, Storage> listOfStorageDevices;

    public SnmpStorage(SnmpConnection target)
    {
        this.target = target;
    }

    public boolean update()
    {
		List<Integer> listOfIndices = new ArrayList<Integer>();
		listOfStorageDevices = new HashMap<Integer, Storage>();

		for(Variable var : SnmpActions.snmpGetSubTree(target, STORAGE_INDEX).values())
		{
			listOfIndices.add(var.toInt());
		}

		for(Integer index : listOfIndices)
		{
			OID storage_type_tmp = new OID(STORAGE_TYPE);
			storage_type_tmp.append(index);

			OID storage_desc_tmp = new OID(STORAGE_DESCRIPTION);
			storage_desc_tmp.append(index);

			OID storage_bytes_size_tmp = new OID(STORAGE_BYTES_SIZE);
			storage_bytes_size_tmp.append(index);

			OID storage_bytes_used_tmp = new OID(STORAGE_BYTES_USED);
			storage_bytes_used_tmp.append(index);

			OID storage_allocation_units_tmp = new OID(STORAGE_ALLOCATION_UNITS);
			storage_allocation_units_tmp.append(index);

			listOfStorageDevices.put(index, StorageFactory.getStorage(index,
					SnmpActions.snmpGet(target, storage_desc_tmp).toString(),
					SnmpActions.snmpGet(target, storage_type_tmp).toSubIndex(true),
					SnmpActions.snmpGet(target, storage_allocation_units_tmp).toInt(),
					SnmpActions.snmpGet(target, storage_bytes_size_tmp).toInt(),
					SnmpActions.snmpGet(target, storage_bytes_used_tmp).toInt()));
		}

        if(!listOfStorageDevices.isEmpty())
            return true;

        return false;
    }

	public String toHtmlString()
	{
		return "";
	}

	public Map<Integer, Storage> getListOfStorageDevices()
	{
		return listOfStorageDevices;
	}
}
