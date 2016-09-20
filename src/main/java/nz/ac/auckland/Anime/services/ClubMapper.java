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


    //This method maps ClubDTO objects to Domain Club objects. Two sets of Longs are passed
    //along with the ClubDTO object representing the set of members and forums that the Club
    //owns.
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


    //When a club is grabbed from the database and before it is passed to the user it is
    // translated into a clubDTO object using this method.
    static ClubDTO toDto(Club club) {

        Set<Long> members = new HashSet<Long>();
        Set<ForumDTO> forums = new HashSet<ForumDTO>();

        //get memebers
        if(club.getMembers() != null) {
            for (User i : club.getMembers()) {
                members.add(i.getId());
            }
        }

        //get forums
        if(club.getForums() != null) {
            for (Forum i : club.getForums()) {
                forums.add(ForumMapper.toDto(i));
            }
        }

        ClubDTO in = new ClubDTO(club.getId(), members, forums, club.getName());

        return in;

    }
}
