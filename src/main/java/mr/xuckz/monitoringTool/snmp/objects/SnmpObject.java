package mr.xuckz.monitoringTool.snmp.objects;

import org.snmp4j.smi.OID;

import java.util.HashMap;
import java.util.Map;

public class SnmpObject
{
    private OID oid;
    private Integer maxEntries;
    private Map<OID, Object> mibData;

    public SnmpObject(OID oid, Integer maxEntries)
    {
        this.mibData = new HashMap<OID, Object>();
        this.oid = oid;
        this.maxEntries = maxEntries;
    }

    public Map<OID,Object> snmpWalk(OID oid)
    {
        return snmpWalk(oid, 1000);
    }
}
