package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.Club;
import nz.ac.auckland.Anime.domain.Forum;
import nz.ac.auckland.Anime.domain.PersistenceManager;
import nz.ac.auckland.Anime.domain.User;
import nz.ac.auckland.Anime.dto.ClubDTO;
import nz.ac.auckland.Anime.dto.ForumDTO;
import nz.ac.auckland.Anime.dto.UserDTO;

import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ben on 9/17/2016.
 */
public class ClubMapper {


    static Club toDomainModel(ClubDTO in) {

        Set<User> members = new HashSet<User>();
        Set<Forum> forums = new HashSet<Forum>();


        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();

        //get members
        if(in.getMembers() != null) {
            for (Long i : in.getMembers()) {
                em = p.createEntityManager();
                em.getTransaction().begin();
                User temp = i == null ? null : em.find(User.class, i);
                members.add(temp);
                em.close();
            }
        }

        //get forums
        if(in.getForums() != null) {
            for (ForumDTO i : in.getForums()) {
                Forum temp = i == null ? null : ForumMapper.toDomainModel(i);
                forums.add(temp);
            }
        }


        Club club = new Club(in.getId(), members, forums, in.getName());

        return club;
    }

    static ClubDTO toDto(Club forum) {

        Set<Long> members = new HashSet<Long>();
        Set<ForumDTO> forums = new HashSet<ForumDTO>();

        //get memebers
        if(forum.getMembers() != null) {
            for (User i : forum.getMembers()) {
                members.add(i.getId());
            }
        }

        //get forums
        if(forum.getForums() != null) {
            for (Forum i : forum.getForums()) {
                forums.add(ForumMapper.toDto(i));
            }
        }

        ClubDTO in = new ClubDTO(forum.getId(), members, forums, forum.getName());

        return in;

    }

}
