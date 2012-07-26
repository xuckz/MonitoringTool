package mr.xuckz.monitoringTool.persist;

import mr.xuckz.monitoringTool.snmp.data.SnmpManagingStatus;
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

    public SnmpManagingStatus load(Integer id)
    {
        return (SnmpManagingStatus) getHibernateTemplate().load(SnmpManagingStatus.class, id);
    }
}
