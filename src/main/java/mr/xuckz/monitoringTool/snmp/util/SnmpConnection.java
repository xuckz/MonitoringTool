package mr.xuckz.monitoringTool.snmp.util;

import mr.xuckz.monitoringTool.config.Client;
import mr.xuckz.monitoringTool.snmp.data.SnmpManagingStatus;
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

    private Client client;
    private String community;
    private CommunityTarget communityTarget;
    private Snmp snmp;

    private SnmpManagingStatus snmpStatus;

    private boolean established;

    public SnmpConnection(Client client, String community)
    {
        this.client = client;
        this.community = community;
        this.established = false;
    }

    public boolean initCommunityTarget()
    {
        if (client.getIp() != null && community != null)
        {
            Address targetAddress = GenericAddress.parse("udp:" + client.getIp() + "/" + SnmpConstants.DEFAULT_COMMAND_RESPONDER_PORT);
            log.info("init SNMP client for '{}'", targetAddress.toString());

            if (targetAddress.isValid())
            {
                try
                {
                    communityTarget = new CommunityTarget();
                    communityTarget.setCommunity(new OctetString(community));
                    communityTarget.setAddress(targetAddress);
                    communityTarget.setVersion(SnmpConstants.version2c);

                    communityTarget.setTimeout(1500);
                    communityTarget.setRetries(2);
                    communityTarget.setMaxSizeRequestPDU(65535);

                    snmp = new Snmp(new DefaultUdpTransportMapping());
                    snmp.listen();

                    established = true;

                    this.snmpStatus = new SnmpManagingStatus(this);

                    if(this.snmpStatus.initialize())
                        return true;

                    else
                    {
                        log.error("snmpStatus could not be initialized!");
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
        if(snmpStatus.update())
        {
            log.info("Client with ip: '{}' updated successfully", client.getIp());
            return true;
        }

        log.warn("Client with ip: '{}' could not be updated", client.getIp());
        return false;
    }

    public SnmpManagingStatus getSnmpStatus()
    {
        return snmpStatus;
    }

    public CommunityTarget getCommunityTarget()
    {
        return communityTarget;
    }

    public Snmp getSnmp()
    {
        return snmp;
    }

    public Client getClient()
    {
        return client;
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
        sb.append("Ip: '").append(client.getIp()).append("', ");
        sb.append("Community: '").append(community).append("'");

        return sb.toString();
    }
}

