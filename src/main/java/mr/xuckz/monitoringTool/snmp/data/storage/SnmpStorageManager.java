package mr.xuckz.monitoringTool.snmp.data.storage;

import mr.xuckz.monitoringTool.snmp.data.SnmpDataManager;
import mr.xuckz.monitoringTool.snmp.util.SnmpActions;
import mr.xuckz.monitoringTool.snmp.util.SnmpConnection;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnmpStorageManager extends SnmpDataManager
{
    private static final OID STORAGE_TOP_LEVEL = new OID("1.3.6.1.2.1.25.2");
    private static final OID STORAGE_INDEX = new OID("1.3.6.1.2.1.25.2.3.1.1");
    private static final OID STORAGE_TYPE = new OID("1.3.6.1.2.1.25.2.3.1.2");
    private static final OID STORAGE_DESCRIPTION = new OID("1.3.6.1.2.1.25.2.3.1.3");
    private static final OID STORAGE_BYTES_SIZE = new OID("1.3.6.1.2.1.25.2.3.1.5");
    private static final OID STORAGE_BYTES_USED = new OID("1.3.6.1.2.1.25.2.3.1.6");
    private static final OID STORAGE_ALLOCATION_UNITS = new OID("1.3.6.1.2.1.25.2.3.1.4");

    private Map<Integer, Storage> listOfStorageDevices;

    public SnmpStorageManager(SnmpConnection target)
    {
        super(target);
    }

    public boolean initialize()
    {
        List<Integer> listOfIndices = new ArrayList<Integer>();
        for (Variable var : SnmpActions.snmpGetSubTree(target, STORAGE_INDEX).values())
        {
            listOfIndices.add(var.toInt());
        }

        log.debug("STORAGE LIST SIZE: " + listOfIndices.size());

        listOfStorageDevices = new HashMap<Integer, Storage>();
        for (Integer index : listOfIndices)
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

            Variable storage_desc_var = SnmpActions.snmpGet(target, storage_desc_tmp);
            Variable storage_type_var = SnmpActions.snmpGet(target, storage_type_tmp);
            Variable storage_allocation_units_var = SnmpActions.snmpGet(target, storage_allocation_units_tmp);
            Variable storage_bytes_size_var = SnmpActions.snmpGet(target, storage_bytes_size_tmp);
            Variable storage_bytes_used_var = SnmpActions.snmpGet(target, storage_bytes_used_tmp);

            if (storage_desc_var != null &&
                    storage_type_var != null &&
                    storage_allocation_units_var != null &&
                    storage_bytes_size_var != null &&
                    storage_bytes_used_var != null)
            {
                listOfStorageDevices.put(index, StorageFactory.getStorage(
                        index,
                        storage_desc_var.toString(),
                        storage_type_var.toSubIndex(true),
                        storage_allocation_units_var.toInt(),
                        storage_bytes_size_var.toInt(),
                        storage_bytes_used_var.toInt()));
            }

            else
            {
                log.error("SnmpStorageManager for ip: '" + target.getClient().getIp() + "' could not be initialized!");
                return false;
            }
        }

        log.info("SnmpStorageManager for ip: '" + target.getClient().getIp() + "' initialized!");
        return true;
    }

    public boolean update()
    {
        for (Integer index : listOfStorageDevices.keySet())
        {
            OID storage_bytes_used_tmp = new OID(STORAGE_BYTES_USED);
            storage_bytes_used_tmp.append(index);

            Variable storage_bytes_used_var = SnmpActions.snmpGet(target, storage_bytes_used_tmp);

            if (storage_bytes_used_var != null)
            {
                listOfStorageDevices.get(index).update(storage_bytes_used_var.toInt());
            }

            else
            {
                log.error("SnmpStorageManager for ip: '" + target.getClient().getIp() + "' could not be updated!");
                return false;
            }
        }

        return true;
    }

    public Map<Integer, Storage> getListOfStorageDevices()
    {
        return listOfStorageDevices;
    }

    @Override
    public String toString()
    {
		StringBuilder sb = new StringBuilder();

        for (Storage storage : listOfStorageDevices.values())
        {
			sb.append(storage.toString());
			sb.append("\n\n");
        }

		if(sb.length() > 2)
			sb.delete(sb.length() - 2, sb.length() - 1);

		return sb.toString();
    }
}
