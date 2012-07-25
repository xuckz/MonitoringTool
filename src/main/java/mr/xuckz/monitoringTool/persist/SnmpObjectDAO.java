package mr.xuckz.monitoringTool.persist;

import mr.xuckz.monitoringTool.snmp.data.SnmpObject;
import mr.xuckz.monitoringTool.web.model.Status;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class SnmpObjectDAO extends HibernateDaoSupport
{
    public SnmpObjectDAO()
    {
    }

    public void save(Status status)
    {
        getHibernateTemplate().save(status);
    }

    public SnmpObject load(Integer id)
    {
        return (SnmpObject) getHibernateTemplate().load(SnmpObject.class, id);
    }
}
