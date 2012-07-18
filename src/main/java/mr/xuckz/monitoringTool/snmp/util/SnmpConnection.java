package mr.xuckz.monitoringTool.snmp.util;

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

    private String ip = null;
    private String community = null;
    private CommunityTarget communityTarget = null;
    private Snmp snmp = null;

    public SnmpConnection()
    {
    }

    public SnmpConnection(String ip, String community)
    {
        setIp(ip);
        setCommunity(community);

        initCommunityTarget();
    }

    private boolean initCommunityTarget()
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

                    return true;

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

        communityTarget = null;
        return false;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getCommunity()
    {
        return community;
    }

    public final void setCommunity(String community)
    {
        this.community = community;
    }

    public boolean isReady()
    {
        return (communityTarget != null && snmp != null);
    }

    public CommunityTarget getCommunityTarget()
    {
        return communityTarget;
    }

    void setCommunityTarget(CommunityTarget communityTarget)
    {
        this.communityTarget = communityTarget;
    }

    public Snmp getSnmp()
    {
        return snmp;
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

