package mr.xuckz.monitoringTool.persist;

import mr.xuckz.monitoringTool.web.model.Client;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.List;

public class ClientDAO extends HibernateDaoSupport {

    public ClientDAO() {}

    public Client findByIp(final String ip) {
        return (Client) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().getNamedQuery("Client.uniqueByIp");
                query.setString("ip", ip);
                return (Client) query.uniqueResult();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public List<Client> all() {
        return new ArrayList<Client>( getHibernateTemplate().loadAll(Client.class) );
    }

}