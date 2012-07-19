package mr.xuckz.monitoringTool.snmp.data;

import mr.xuckz.monitoringTool.snmp.util.SnmpActions;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;

import java.util.HashMap;
import java.util.Map;

public class SnmpStorage implements SnmpObjectType
{
    private static final OID STORAGE_TOP_LEVEL = new OID("1.3.6.1.2.1.25.2");

    private SnmpConnection target;

    private Map<OID, Variable> listOfStorageDevices;

    public SnmpStorage(SnmpConnection target)
    {
        this.target = target;
    }

    public boolean update()
    {
        this.listOfStorageDevices = new HashMap<OID, Variable>();
        listOfStorageDevices = SnmpActions.snmpGetSubTree(target, STORAGE_TOP_LEVEL);

        if(!listOfStorageDevices.isEmpty())
            return true;

        return false;
    }

    public Map<OID, Variable> getListOfStorageDevices()
    {
        return listOfStorageDevices;
    }
}
