package mr.xuckz.monitoringTool.snmp.data.device;

import mr.xuckz.monitoringTool.snmp.data.SnmpObjectType;
import mr.xuckz.monitoringTool.snmp.data.device.cpu.Cpu;
import mr.xuckz.monitoringTool.snmp.data.device.cpu.CpuFactory;
import mr.xuckz.monitoringTool.snmp.data.device.network.Network;
import mr.xuckz.monitoringTool.snmp.data.device.network.NetworkFactory;
import mr.xuckz.monitoringTool.snmp.util.SnmpActions;
import mr.xuckz.monitoringTool.snmp.util.SnmpConnection;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnmpDevices extends SnmpObjectType
{
	private static final OID DEVICE_INDEX       = new OID("1.3.6.1.2.1.25.3.2.1.1");
	private static final OID DEVICE_DESCRIPTION = new OID("1.3.6.1.2.1.25.3.2.1.3");
	private static final OID DEVICE_STATUS      = new OID("1.3.6.1.2.1.25.3.2.1.5");
	private static final OID DEVICE_ERRORS      = new OID("1.3.6.1.2.1.25.3.2.1.6");
	private static final OID DEVICE_TYPE        = new OID("1.3.6.1.2.1.25.3.2.1.2");

	private static final OID NETWORK_IF_INDEX = new OID("1.3.6.1.2.1.2.2.1.1");
	private static final OID NETWORK_IF_DESCRIPTION = new OID("1.3.6.1.2.1.2.2.1.2");
	private static final OID NETWORK_IF_TYPE = new OID("1.3.6.1.2.1.2.2.1.3");

	private static final OID NETWORK_IF_OUT_OCTETS = new OID("1.3.6.1.2.1.2.2.1.16");
	private static final OID NETWORK_IF_IN_OCTETS  = new OID("1.3.6.1.2.1.2.2.1.10");

	private static final OID PROCESSOR_LOAD = new OID("1.3.6.1.2.1.25.3.3.1.2");

	private Map<Integer, Cpu> listOfCpus;
	private Map<Integer, Network> listOfNetwork;

	/*
	HEX FIX NEEDED
		http://www.mail-archive.com/snmp4j@agentpp.org/msg00349.html
		 */

	public SnmpDevices(SnmpConnection target)
	{
		super(target);
	}

	@Override
	public boolean initialize()
	{
		List<Integer> listOfCpuIndices = new ArrayList<Integer>();
		for (Variable var : SnmpActions.snmpGetSubTree(target, DEVICE_INDEX).values())
		{
			OID device_type_tmp = new OID(DEVICE_TYPE);
			device_type_tmp.append(var.toInt());

			if (DeviceType.get(SnmpActions.snmpGet(target, device_type_tmp).toSubIndex(true)) == DeviceType.CPU)
			{
				listOfCpuIndices.add(var.toInt());
			}
		}

		List<Integer> listOfNetworkIndices = new ArrayList<Integer>();
		for (Variable var : SnmpActions.snmpGetSubTree(target, NETWORK_IF_INDEX).values())
		{
            Integer index = var.toInt();

            OID interface_desc_tmp = new OID(NETWORK_IF_DESCRIPTION);
            interface_desc_tmp.append(index);

            Variable interface_description_var = SnmpActions.snmpGet(target, interface_desc_tmp);

            if(target.getClient().getNetworkInterfaces().contains(varToString(interface_description_var)))
            {
			    listOfNetworkIndices.add(index);
            }
		}

		listOfCpus = new HashMap<Integer, Cpu>();
		for (Integer index : listOfCpuIndices)
		{
			OID device_desc_tmp = new OID(DEVICE_DESCRIPTION);
			device_desc_tmp.append(index);
			OID processor_load_tmp = new OID(PROCESSOR_LOAD);
			processor_load_tmp.append(index);

			Variable device_desc_var = SnmpActions.snmpGet(target, device_desc_tmp);
			Variable processor_load_var = SnmpActions.snmpGet(target, processor_load_tmp);

			if (device_desc_var != null && processor_load_var != null)
			{
				listOfCpus.put(index, CpuFactory.getCpu(device_desc_var.toString(), index, processor_load_var.toInt()));
			}

			else
			{
				log.error("SnmpDevices(CPU) for ip: '" + target.getClient().getIp() + "' could not be initialized!");
				return false;
			}
		}

		listOfNetwork = new HashMap<Integer, Network>();
		for (Integer index : listOfNetworkIndices)
		{
			OID interface_desc_tmp = new OID(NETWORK_IF_DESCRIPTION);
			interface_desc_tmp.append(index);
			OID interface_type_tmp = new OID(NETWORK_IF_TYPE);
			interface_type_tmp.append(index);
			OID interface_out_octets_tmp = new OID(NETWORK_IF_OUT_OCTETS);
			interface_out_octets_tmp.append(index);
			OID interface_in_octets_tmp = new OID(NETWORK_IF_IN_OCTETS);
			interface_in_octets_tmp.append(index);

			Variable interface_description_var = SnmpActions.snmpGet(target, interface_desc_tmp);
			Variable interface_type_var = SnmpActions.snmpGet(target, interface_desc_tmp);
			Variable interface_out_octets_var = SnmpActions.snmpGet(target, interface_out_octets_tmp);
			Variable interface_in_octets_var = SnmpActions.snmpGet(target, interface_in_octets_tmp);

			if (interface_in_octets_var != null && interface_out_octets_var != null && interface_description_var != null && interface_type_var != null)
			{
				listOfNetwork.put(index, NetworkFactory.getNetwork(varToString(interface_description_var),
                        varToString(interface_type_var),
                        index,
                        interface_in_octets_var.toLong(),
                        interface_out_octets_var.toLong()));
			}

			else
			{
				log.error("SnmpDevices(Network) for ip: '" + target.getClient().getIp() + "' could not be initialized!");
				return false;
			}
		}

		log.info("SnmpDevices for ip: '"+target.getClient().getIp()+"' initialized!");
		return true;
	}

	@Override
	public boolean update()
	{
		return (updateCpu() && updateNetwork());
	}

	private boolean updateNetwork()
	{
		for (Integer index : listOfNetwork.keySet())
		{
			OID interface_out_octets_tmp = new OID(NETWORK_IF_OUT_OCTETS);
			interface_out_octets_tmp.append(index);
			OID interface_in_octets_tmp = new OID(NETWORK_IF_IN_OCTETS);
			interface_in_octets_tmp.append(index);

			Variable interface_out_octets_var = SnmpActions.snmpGet(target, interface_out_octets_tmp);
			Variable interface_in_octets_var = SnmpActions.snmpGet(target, interface_in_octets_tmp);

			if (interface_out_octets_var != null && interface_in_octets_var != null)
			{
				listOfNetwork.get(index).update(interface_in_octets_var.toLong(), interface_out_octets_var.toLong());
			}

			else
			{
				log.error("SnmpDevices(Network) for ip: '" + target.getClient().getIp() + "' could not be updated!");
				return false;
			}
		}

		return true;
	}

	private boolean updateCpu()
	{
		for (Integer index : listOfCpus.keySet())
		{

			OID processor_load_tmp = new OID(PROCESSOR_LOAD);
			processor_load_tmp.append(index);

			Variable processor_load_var = SnmpActions.snmpGet(target, processor_load_tmp);

			if (processor_load_var != null)
			{
				listOfCpus.get(index).update(processor_load_var.toInt());
			}

			else
			{
				log.error("SnmpDevices(CPU) for ip: '" + target.getClient().getIp() + "' could not be updated!");
				return false;
			}
		}

		return true;
	}

	public Map<Integer, Cpu> getListOfCpus()
	{
		return listOfCpus;
	}

	public Map<Integer, Network> getListOfNetwork()
	{
		return listOfNetwork;
	}

	@Override
	public String toString()
	{
		String result = "";

		for (Cpu cpu : listOfCpus.values())
		{
			result += "CPU #" + cpu.getIndex() + ": " + cpu.getDescription() + "\n";
			result += "CPU #" + cpu.getIndex() + " load: " + cpu.getLoad() + "\n\n";
		}

		for (Network net : listOfNetwork.values())
		{
			result += "Interface #" + net.getIndex() + ": " + net.getDescription() + "\n";
			result += "Interface #" + net.getIndex() + " type: " + net.getType() + "\n";
			result += "Interface #" + net.getIndex() + " total in: " + bytesToString(net.getBytes_in()) + "\n";
			result += "Interface #" + net.getIndex() + " total out: " + bytesToString(net.getBytes_out()) + "\n";
			result += "Interface #" + net.getIndex() + " bytes in per second: " + bytesToString(net.getDelta_in()) + "\n";
			result += "Interface #" + net.getIndex() + " bytes out per second: " + bytesToString(net.getDelta_out()) + "\n\n";
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
