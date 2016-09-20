package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.*;
import nz.ac.auckland.Anime.dto.CommentDTO;
import nz.ac.auckland.Anime.dto.ForumDTO;
import nz.ac.auckland.Anime.dto.UserDTO;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 9/17/2016.
 */
public class ForumMapper {

    //Maps ForumDTO objects to corresponding Forum objects. Lists of Longs passed in from the
    //DTO objects are translated to their respective list of objects by mapping the Long values
    //to object id's.
    static Forum toDomainModel(ForumDTO in) {

        List<User> moderators = new ArrayList<User>();
        List<Comment> comments = new ArrayList<Comment>();

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();

        //get moderators
        if(in.getModerators() != null) {
            for (Long i : in.getModerators()) {
                em = p.createEntityManager();
                em.getTransaction().begin();
                User reviewer = i == null ? null : em.find(User.class, i);
                User temp = em.find(User.class, i);
                moderators.add(temp);
                em.close();
            }
        }

        //get comments
        if(in.getComments() != null) {
            for (CommentDTO i : in.getComments()) {
                comments.add(CommentMapper.toDomainModel(i));
            }
        }

        em = p.createEntityManager();
        em.getTransaction().begin();
        Anime temp = in.getAnimeTopicID() == null ? null : em.find(Anime.class, in.getAnimeTopicID());
        em.close();

        Forum forum = new Forum(in.getId(), moderators, comments, temp);

        return forum;
    }

    //Maps forum objects to ForumDTO objects, where lists of objects such as users
    //are represented in the DTO obj as a set of Longs where each long value is the
    //value of the related user's id.
    static ForumDTO toDto(Forum forum) {

        List<Long> moderators = new ArrayList<Long>();
        List<CommentDTO> comments = new ArrayList<CommentDTO>();

        //get moderators
        if(forum.getModerators() != null) {
            for (User i : forum.getModerators()) {
                moderators.add(i.getId());
            }
        }

        //get comments
        if(forum.getModerators() != null) {
            for (Comment i : forum.getComments()) {
                comments.add(CommentMapper.toDto(i));
            }
        }

        Long animeTopicId = forum.getAnimeTopic() == null ? null : forum.getAnimeTopic().getId();

        ForumDTO in = new ForumDTO(forum.getId(), moderators, comments, animeTopicId);

        return in;

    }


}
