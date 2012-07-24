package mr.xuckz.monitoringTool.snmp;

import mr.xuckz.monitoringTool.snmp.util.SnmpConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SnmpHandler
{
    static final Logger log = LoggerFactory.getLogger(SnmpHandler.class);
    private List<SnmpConnection> listOfTargets;

    public SnmpHandler()
    {
        listOfTargets = new ArrayList<SnmpConnection>();
    }

    public boolean addTargetConnection(String ip, String community)
    {
        SnmpConnection snmpConnection = new SnmpConnection(ip, community);

        if(snmpConnection.initCommunityTarget())
        {
            listOfTargets.add(snmpConnection);
            log.info("Target with ip: '{}' and community: '{}' was added successfully!", ip, community);
            return true;
        }

        else
        {
            log.error("Target with ip: '{}' and community: '{}' could not be added!", ip, community);
            return false;
        }
    }

    public void updateTargetConnections()
    {
        for(SnmpConnection target : listOfTargets)
        {
            target.update();
        }
    }

    public List<SnmpConnection> getListOfTargets()
    {
        return listOfTargets;
    }
}