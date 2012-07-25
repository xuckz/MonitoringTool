package mr.xuckz.monitoringTool.snmp.data.storage;

import mr.xuckz.monitoringTool.snmp.data.SnmpObjectType;
import mr.xuckz.monitoringTool.snmp.util.SnmpActions;
import mr.xuckz.monitoringTool.snmp.util.SnmpConnection;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnmpStorage extends SnmpObjectType
{
    private static final OID STORAGE_TOP_LEVEL = new OID("1.3.6.1.2.1.25.2");
    private static final OID STORAGE_INDEX = new OID("1.3.6.1.2.1.25.2.3.1.1");
    private static final OID STORAGE_TYPE = new OID("1.3.6.1.2.1.25.2.3.1.2");
    private static final OID STORAGE_DESCRIPTION = new OID("1.3.6.1.2.1.25.2.3.1.3");
    private static final OID STORAGE_BYTES_SIZE = new OID("1.3.6.1.2.1.25.2.3.1.5");
    private static final OID STORAGE_BYTES_USED = new OID("1.3.6.1.2.1.25.2.3.1.6");
    private static final OID STORAGE_ALLOCATION_UNITS = new OID("1.3.6.1.2.1.25.2.3.1.4");

    private Map<Integer, Storage> listOfStorageDevices;

    public SnmpStorage(SnmpConnection target)
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
                listOfStorageDevices.put(index, StorageFactory.getStorage(index,
                        storage_desc_var.toString(),
                        storage_type_var.toSubIndex(true),
                        storage_allocation_units_var.toInt(),
                        storage_bytes_size_var.toInt(),
                        storage_bytes_used_var.toInt()));
            }

            else
            {
                log.error("SnmpStorage for ip: '" + target.getIp() + "' could not be initialized!");
                return false;
            }
        }

        log.info("SnmpStorage for ip: '" + target.getIp() + "' initialized!");
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
                log.error("SnmpStorage for ip: '" + target.getIp() + "' could not be updated!");
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
        String result = "";

        for (Storage storage : listOfStorageDevices.values())
        {
            result += "Description: " + storage.getDescription() + "\n";
            result += "Type: " + storage.getStorageType().toString() + "\n";
            result += "Size: " + bytesToString(storage.getBytes_size()) + "\n";
            result += "Used: " + bytesToString(storage.getBytes_used()) + "\n";
            result += "Free: " + bytesToString(storage.getBytes_free()) + "\n\n";
        }

        if (result.length() > 2)
        {
            StringBuilder b = new StringBuilder(result);
            b.substring(0, result.length() - 2);
            return b.toString();
        }

        return result;
    }
}
