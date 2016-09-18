package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.Comment;
import nz.ac.auckland.Anime.domain.PersistenceManager;
import nz.ac.auckland.Anime.domain.User;
import nz.ac.auckland.Anime.dto.CommentDTO;

import javax.persistence.EntityManager;

/**
 * Created by Ben on 9/17/2016.
 */
public class CommentMapper {

    static Comment toDomainModel(CommentDTO in) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();
        User temp = em.find(User.class, in.getCommenterID());
        em.close();

       Comment comment = new Comment(temp, in.getComment(), in.getDate());

        return comment;
    }

    static CommentDTO toDto(Comment comment) {

        CommentDTO commentDTO = new CommentDTO(comment.getCommenter().getId(), comment.getComment(), comment.getDate());

        return commentDTO;
    }
}
