package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.PersistenceManager;
import nz.ac.auckland.Anime.domain.User;
import nz.ac.auckland.Anime.dto.UserDTO;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ben on 9/8/2016.
 */

public class UserMapper {

    //This method maps UserDTO objects into User domain model Objects.
    static User toDomainModel(UserDTO in) {

        Set<User> friends = new HashSet<User>();

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();

        if(in.getFollowIds() != null) {
            for (Long i : in.getFollowIds()) {
                em = p.createEntityManager();
                em.getTransaction().begin();
                User temp = em.find(User.class, i);
                friends.add(temp);
                em.close();
            }
        }

        User user = new User(in.getId(), in.getUsername(), in.getLastname(), in.getFirstname(), friends);

        return user;
    }

    //This method does the opposite of the one above, it maps User objects into UserDTO objects.
    static UserDTO toDto(User user) {

        Set<Long> friendIds = new HashSet<Long>();

        for(User i : user.getFollowers()){
            friendIds.add(i.getId());
        }

        UserDTO in = new UserDTO(user.getId(), user.getUsername(), user.getLastname(), user.getFirstname(), friendIds);

        return in;

    }

}
