package mr.xuckz.monitoringTool.persist;

import mr.xuckz.monitoringTool.snmp.SnmpStatusManager;
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

    public SnmpStatusManager load(Integer id)
    {
        return (SnmpStatusManager) getHibernateTemplate().load(SnmpStatusManager.class, id);
    }
}
