package mr.xuckz.monitoringTool.snmp.data;

import mr.xuckz.monitoringTool.snmp.util.SnmpConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.SMIConstants;
import org.snmp4j.smi.Variable;

public abstract class SnmpDataManager
{
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    protected SnmpConnection target;

    public SnmpDataManager(SnmpConnection target)
    {
        this.target = target;
    }

    public String toHtmlString()
    {
        return toString().replaceAll("\n", "<br>");
    }

    public String bytesToString(long bytes)
    {
        double result = 0.00;

        if (bytes <= 1024)
        {
            return bytes + " B";
        }

        else if (bytes > 1024 && bytes <= 1048576)
        {
            result = (new Double(bytes) / 1024 * 100);
            return new Double(new Double(java.lang.Math.round(result)) / 100).toString() + " KB";
        }

        else if (bytes > 1048576 && (bytes <= 1073741824))
        {
            result = (new Double(bytes) / 1024 / 1024 * 100);
            return new Double(new Double(java.lang.Math.round(result)) / 100).toString() + " MB";
        }

        else
        {
            result = (new Double(bytes) / 1024 / 1024 / 1024 * 100);
            return new Double(new Double(java.lang.Math.round(result)) / 100).toString() + " GB";
        }
    }

    public String varToString(Variable var)
    {
        switch(var.getSyntax())
        {
            case SMIConstants.SYNTAX_OCTET_STRING:
                return octetStringToString(var);
        }

        return var.toString();
    }

    /*
    A date-time specification.
    field  octets  contents                  range
    -----  ------  --------                  -----
      1      1-2   year*                     0..65536
      2       3    month                     1..12
      3       4    day                       1..31
      4       5    hour                      0..23
      5       6    minutes                   0..59
      6       7    seconds                   0..60
                   (use 60 for leap-second)
      7       8    deci-seconds              0..9
      8       9    direction from UTC        '+' / '-'
      9      10    hours from UTC*           0..13
     10      11    minutes from UTC          0..59
    * Notes:
                - the value of year is in network-byte order
                - daylight saving time in New Zealand is +13
    For example, Tuesday May 26, 1992 at 1:30:15 PM EDT would be
                displayed as:
                     1992-5-26,13:30:15.0,-4:0
    Note that if only local time is known, then timezone
                information (fields 8-10) is not present.
     */

    public String octetStringToDateString(Variable var)
    {
        byte[] b = OctetString.fromHexString(var.toString().substring(6, var.toString().length())).toByteArray();
        byte[] by = OctetString.fromHexString(var.toString().substring(0, 6)).toByteArray();

        int year = 0;
        for (int i = 0; i < by.length; i++)
        {
            //value += ((long) by[i] & 0xffL) << (8 * i); //First byte least significant
            year = (year << 8) + (by[i] & 0xff);        //First byte most significant
        }

        int month = (int)b[0];
        int day = (int)b[1];
        int hour = (int)b[2];
        int minute = (int)b[3];
        double second = (double)b[4] + ((double)b[5] / 100.0);

        return (day + "." + month + "." + year + " " + hour + ":" + minute + "." + second);
    }

    private String octetStringToString(Variable var)
    {
        try
        {
            String txtInHex = var.toString();
            txtInHex = txtInHex.replaceAll(":00", "");
            txtInHex = txtInHex.replaceAll(":", "");

            byte[] txtInByte = new byte[txtInHex.length() / 2];
            int j = 0;
            for (int i = 0; i < txtInHex.length(); i += 2)
            {
                txtInByte[j++] = Byte.parseByte(txtInHex.substring(i, i + 2), 16);
            }
            return new String(txtInByte);
        }

        catch (Exception e)
        {
            return var.toString();
        }
    }

    public boolean update()
    {
        return false;
    }

    public boolean initialize()
    {
        return false;
    }

    public abstract String toString();
}
