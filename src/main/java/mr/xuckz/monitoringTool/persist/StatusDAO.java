package mr.xuckz.monitoringTool.persist;

import mr.xuckz.monitoringTool.web.model.Client;
import mr.xuckz.monitoringTool.web.model.Status;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.List;

public class StatusDAO extends HibernateDaoSupport
{

    public StatusDAO()
    {
    }

    public void save(Status status)
    {
        getHibernateTemplate().save(status);
    }

    public Status load(Integer id)
    {
        return (Status) getHibernateTemplate().load(Status.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Status> recentForClient(final Client client)
    {
        return (List<Status>) getHibernateTemplate().execute(
                new HibernateCallback()
                {
                    public Object doInHibernate(Session session)
                    {
                        Query query = getSession().getNamedQuery("Status.byClient");
                        query.setParameter("client", client);
                        return new ArrayList<Status>(query.list());
                    }
                });
    }
}
