package mr.xuckz.monitoringTool.handler;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SimpleSnmpHandler
{
    public SimpleSnmpHandler()
    {

    }

    public static void snmpSet(String host, String community, String strOID, int Value)
    {
        host = host + "/" + "161";
        Address tHost = GenericAddress.parse(host);
        Snmp snmp;
        try
        {
            TransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            transport.listen();
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString(community));
            target.setAddress(tHost);
            target.setRetries(2);
            target.setTimeout(5000);
            target.setVersion(SnmpConstants.version1); //Set the correct SNMP version here
            PDU pdu = new PDU();
            //Depending on the MIB attribute type, appropriate casting can be done here
            pdu.add(new VariableBinding(new OID(strOID), new Integer32(Value)));
            pdu.setType(PDU.SET);
            ResponseListener listener = new ResponseListener()
            {
                public void onResponse(ResponseEvent event)
                {
                    PDU strResponse;
                    String result;
                    ((Snmp) event.getSource()).cancel(event.getRequest(), this);
                    strResponse = event.getResponse();
                    if (strResponse != null)
                    {
                        result = strResponse.getErrorStatusText();
                        System.out.println("Set Status is: " + result);
                    }
                }
            };
            snmp.send(pdu, target, null, listener);
            snmp.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String snmpGet(String host, String community, String strOID)
    {
        String strResponse = "";
        ResponseEvent response;
        Snmp snmp;
        try
        {
            OctetString community1 = new OctetString(community);
            host = host + "/" + "161";
            Address tHost = new UdpAddress(host);
            TransportMapping transport = new DefaultUdpTransportMapping();
            transport.listen();
            CommunityTarget comtarget = new CommunityTarget();
            comtarget.setCommunity(community1);
            comtarget.setVersion(SnmpConstants.version1);
            comtarget.setAddress(tHost);
            comtarget.setRetries(2);
            comtarget.setTimeout(5000);
            PDU pdu = new PDU();
            pdu.add(new VariableBinding(new OID(strOID)));
            pdu.setType(PDU.GET);
            snmp = new Snmp(transport);
            response = snmp.get(pdu, comtarget);
            if (response != null)
            {
                if (response.getResponse().getErrorStatusText().equalsIgnoreCase("Success"))
                {
                    PDU pduresponse = response.getResponse();
                    strResponse = pduresponse.getVariableBindings().firstElement().toString();
                    if (strResponse.contains("="))
                    {
                        int len = strResponse.indexOf("=");
                        strResponse = strResponse.substring(len + 1, strResponse.length());
                    }
                }
            }

            else
            {
                System.out.println("Looks like a TimeOut occured ");
            }

            snmp.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
//System.out.println("Response="+strResponse);
        return strResponse;
    }
}
