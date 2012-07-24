package mr.xuckz.monitoringTool.snmp.util;

import mr.xuckz.monitoringTool.snmp.data.SnmpObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommunityTarget;
import org.snmp4j.Snmp;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;

public class SnmpConnection
{
    static final Logger log = LoggerFactory.getLogger(SnmpConnection.class);

    private String ip;
    private String community;
    private CommunityTarget communityTarget;
    private Snmp snmp;
    private SnmpObject snmpObject;

    private boolean established;

    public SnmpConnection(String ip, String community)
    {
        this.ip = ip;
        this.community = community;
        this.established = false;
    }

    public boolean initCommunityTarget()
    {
        if (ip != null && community != null)
        {
            Address targetAddress = GenericAddress.parse("udp:" + ip + "/" + SnmpConstants.DEFAULT_COMMAND_RESPONDER_PORT);
            log.info("init SNMP client for '{}'", targetAddress.toString());

            if (targetAddress.isValid())
            {
                try
                {
                    communityTarget = new CommunityTarget();
                    communityTarget.setCommunity(new OctetString(community));
                    communityTarget.setAddress(targetAddress);
                    communityTarget.setVersion(SnmpConstants.version2c);

                    snmp = new Snmp(new DefaultUdpTransportMapping());
                    snmp.listen();

                    established = true;

                    this.snmpObject = new SnmpObject(this);
                    if(this.snmpObject.init())
                        return true;

                    else
                    {
                        log.error("snmpObject could not be initialized!");
                    }
                }

                catch (IOException ioex)
                {
                    log.error("init failed for target '{}'!\n", targetAddress, ioex);
                }
            }

            else
            {
                log.warn("invalid target '{}'!\n", targetAddress);
            }
        }

        established = false;
        communityTarget = null;
        return false;
    }

    public boolean update()
    {
        if(snmpObject.update())
        {
            log.info("Client with ip: '{}' updated successfully", ip);
            return true;
        }

        log.warn("Client with ip: '{}' could not be updated", ip);
        return false;
    }

    public SnmpObject getSnmpObject()
    {
        return snmpObject;
    }

    public CommunityTarget getCommunityTarget()
    {
        return communityTarget;
    }

    public Snmp getSnmp()
    {
        return snmp;
    }

    public String getIp()
    {
        return ip;
    }

    public boolean isEstablished()
    {
        return established;
    }

    public void setEstablished(boolean established)
    {
        this.established = established;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Ip: '").append(ip).append("', ");
        sb.append("Community: '").append(community).append("'");

        return sb.toString();
    }
}

