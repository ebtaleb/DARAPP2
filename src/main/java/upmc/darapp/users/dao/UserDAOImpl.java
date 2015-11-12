package upmc.darapp.users.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;

import upmc.darapp.users.model.User;
import upmc.darapp.users.model.UserRole;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@SuppressWarnings("unchecked")
    @Transactional
	public User findByUserName(String username) {

		List<User> users = new ArrayList<User>();

		users = sessionFactory.getCurrentSession()
                .createQuery("from User where username = :u")
                .setParameter("u", username)
				.list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

    @Transactional
    public void createNewUserRole(User u)
    {
        Session session = sessionFactory.getCurrentSession();
        session.save(new UserRole(u, "ROLE_USER"));
        session.flush();
    }

    @Transactional
    public void createNewUser(User u)
    {
        Session session = sessionFactory.getCurrentSession();
        session.save(u);
        session.flush();
    }

}