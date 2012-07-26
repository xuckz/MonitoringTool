package mr.xuckz.monitoringTool.snmp;

import mr.xuckz.monitoringTool.config.Client;
import mr.xuckz.monitoringTool.config.ConfigParameters;
import mr.xuckz.monitoringTool.snmp.util.SnmpConnection;
import mr.xuckz.monitoringTool.snmp.util.SnmpConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnmpHandler
{
    static final Logger log = LoggerFactory.getLogger(SnmpHandler.class);
    private Map<SnmpConnection, Boolean> listOfTargets;
    private final ConfigParameters config;

    public SnmpHandler(ConfigParameters config)
    {
        this.config = config;
        listOfTargets = new HashMap<SnmpConnection, Boolean>();

        for(Client client : config.getClientList())
        {
            listOfTargets.put(SnmpConnectionFactory.getPublicSnmpConnection(client), false);
        }
    }

    public void initialize()
    {
        for(SnmpConnection target : listOfTargets.keySet())
        {
            if(target.initCommunityTarget())
                listOfTargets.put(target, true);

            else listOfTargets.put(target, false);
        }
    }

    public void updateTargetConnections()
    {
        for(SnmpConnection target : getActiveTargets())
        {
            if(!target.update())
                listOfTargets.put(target, false);
        }
    }

    public void reconnect()
    {
        for(SnmpConnection target : getInactiveTargets())
        {
            if(target.initCommunityTarget())
                listOfTargets.put(target, true);

            else listOfTargets.put(target, false);
        }
    }

    public List<SnmpConnection> getInactiveTargets()
    {
        List<SnmpConnection> listOfInactiveConnections = new ArrayList<SnmpConnection>();

        for(Map.Entry entry : listOfTargets.entrySet())
        {
            if(!(Boolean) entry.getValue())
                listOfInactiveConnections.add((SnmpConnection) entry.getKey());
        }

        return listOfInactiveConnections;
    }

    public List<SnmpConnection> getActiveTargets()
    {
        List<SnmpConnection> listOfActiveConnections = new ArrayList<SnmpConnection>();

        for(Map.Entry entry : listOfTargets.entrySet())
        {
            if((Boolean) entry.getValue())
                listOfActiveConnections.add((SnmpConnection) entry.getKey());
        }

        return listOfActiveConnections;
    }

    public Map<SnmpConnection, Boolean> getListOfTargets()
    {
        return listOfTargets;
    }
}