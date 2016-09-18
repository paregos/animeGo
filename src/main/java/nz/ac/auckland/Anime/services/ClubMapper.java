package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.Club;
import nz.ac.auckland.Anime.domain.Forum;
import nz.ac.auckland.Anime.domain.PersistenceManager;
import nz.ac.auckland.Anime.domain.User;
import nz.ac.auckland.Anime.dto.ClubDTO;
import nz.ac.auckland.Anime.dto.ForumDTO;
import nz.ac.auckland.Anime.dto.UserDTO;

import javax.persistence.EntityManager;
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
        //get moderators
        for(Long i : in.getMembers()){
            em.getTransaction().begin();
            User temp = em.find(User.class, i);
            members.add(temp);
            em.close();
        }

        //get comments
        for(ForumDTO i : in.getForums()){
            forums.add(ForumMapper.toDomainModel(i));
        }

        Club club = new Club(in.getId(), members, forums, in.getName());

        return club;
    }

    static ClubDTO toDto(Club forum) {

        Set<Long> members = new HashSet<Long>();
        Set<ForumDTO> forums = new HashSet<ForumDTO>();

        //get memebers
        for(User i : forum.getMembers()){
            members.add(i.getId());
        }

        //get forums
        for(Forum i : forum.getForums()){
            forums.add(ForumMapper.toDto(i));
        }

        ClubDTO in = new ClubDTO(forum.getId(), members, forums, forum.getName());

        return in;

    }

}
