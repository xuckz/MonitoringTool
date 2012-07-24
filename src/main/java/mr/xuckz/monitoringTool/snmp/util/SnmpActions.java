package mr.xuckz.monitoringTool.snmp.util;

import mr.xuckz.monitoringTool.snmp.data.SnmpConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.PDU;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnmpActions
{
    static final Logger log = LoggerFactory.getLogger(SnmpActions.class);

    public static Map<OID, Variable> snmpGetSubTree(SnmpConnection target, OID startOid)
    {
        Map<OID, Variable> subTree = new HashMap<OID, Variable>();

        TreeUtils treeUtils = new TreeUtils(target.getSnmp(), new DefaultPDUFactory());

        List<TreeEvent> listOfTreeEvents = treeUtils.getSubtree(target.getCommunityTarget(), startOid);
        List<VariableBinding> listOfVariableBindings = new ArrayList<VariableBinding>();

        for(TreeEvent treeEvent : listOfTreeEvents)
        {
            VariableBinding[] variableBindings = treeEvent.getVariableBindings();

            if(variableBindings != null)
            {for(int i = 0; i < variableBindings.length; i++)
                listOfVariableBindings.add(variableBindings[i]);}
        }

        for(VariableBinding variableBinding : listOfVariableBindings)
        {
            subTree.put(variableBinding.getOid(), variableBinding.getVariable());
        }

        return subTree;
    }

    public static Variable snmpGet(SnmpConnection target, OID oid)
    {
        Variable result = null;

        //oid.append(0);
        log.debug("get OID {}", oid);

        ResponseEvent responseEvent = snmpRequest(target, oid, PDU.GET);
        if (responseEvent != null)
        {
            PDU responsePDU = responseEvent.getResponse();

            if (responsePDU != null)
            {
                result = responsePDU.get(0).getVariable();
                log.debug("read PDU '{}'", result);
            }

            else
            {
                log.warn("request returned no value with OID '{}'!\n", oid);
            }
        }

        return result;
    }

    private static ResponseEvent snmpRequest(SnmpConnection target, OID oid, int cmd)
    {
        PDU requestPDU = new PDU();
        requestPDU.setMaxRepetitions(10);

        requestPDU.add(new VariableBinding(oid));
        requestPDU.setType(cmd);

        try
        {
            ResponseEvent responseEvent = target.getSnmp().send(requestPDU, target.getCommunityTarget());

            if (responseEvent.getResponse() == null)
            {
                log.debug("request timed out!");
            }

            return responseEvent;

        }

        catch (IOException ioex)
        {
            log.warn("sending snmp msg failed!", ioex);
        }

        return null;
    }
}
