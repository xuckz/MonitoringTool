package mr.xuckz.monitoringTool.snmp.data.device;

import mr.xuckz.monitoringTool.snmp.data.SnmpObjectType;
import mr.xuckz.monitoringTool.snmp.data.device.cpu.Cpu;
import mr.xuckz.monitoringTool.snmp.data.device.cpu.CpuFactory;
import mr.xuckz.monitoringTool.snmp.util.SnmpActions;
import mr.xuckz.monitoringTool.snmp.util.SnmpConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnmpDevice implements SnmpObjectType
{
	static final Logger log = LoggerFactory.getLogger(SnmpDevice.class);

	private static final OID DEVICE_INDEX = new OID("1.3.6.1.2.1.25.3.2.1.1");
	private static final OID DEVICE_DESCRIPTION = new OID("1.3.6.1.2.1.25.3.2.1.3");
	private static final OID DEVICE_STATUS = new OID("1.3.6.1.2.1.25.3.2.1.5");
	private static final OID DEVICE_ERRORS = new OID("1.3.6.1.2.1.25.3.2.1.6");
	private static final OID DEVICE_TYPE = new OID("1.3.6.1.2.1.25.3.2.1.2");

	private static final OID PROCESSOR_LOAD = new OID("1.3.6.1.2.1.25.3.3.1.2");

	private Map<Integer, Cpu> listOfCpus;
	private SnmpConnection target;

	public SnmpDevice(SnmpConnection target)
	{
		this.target = target;
	}

	public boolean update()
	{
		List<Integer> listOfIndices = new ArrayList<Integer>();
		listOfCpus = new HashMap<Integer, Cpu>();

		for(Variable var : SnmpActions.snmpGetSubTree(target, DEVICE_INDEX).values())
		{
			listOfIndices.add(var.toInt());
		}

		for(Integer index : listOfIndices)
		{
			OID device_type_tmp = new OID(DEVICE_TYPE);
			device_type_tmp.append(index);

			if(DeviceType.get(SnmpActions.snmpGet(target, device_type_tmp).toSubIndex(true)) == DeviceType.CPU)
			{
				OID device_desc_tmp = new OID(DEVICE_DESCRIPTION);
				device_desc_tmp.append(index);

				OID processor_load_tmp = new OID(PROCESSOR_LOAD);
				processor_load_tmp.append(index);

				listOfCpus.put(index, CpuFactory.getCpu(
						SnmpActions.snmpGet(target, device_desc_tmp).toString(),
						index,
						SnmpActions.snmpGet(target, processor_load_tmp).toInt()));
			}
		}

		if(!listOfCpus.isEmpty())
			return true;

		else return false;
	}

	public String toHtmlString()
	{
		return "";
	}

	public Map<Integer, Cpu> getListOfCpus()
	{
		return listOfCpus;
	}
}
