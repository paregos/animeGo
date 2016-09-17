package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.Comment;
import nz.ac.auckland.Anime.dto.CommentDTO;

/**
 * Created by Ben on 9/17/2016.
 */
public class CommentMapper {

    static Comment toDomainModel(CommentDTO in) {

        Comment comment = new Comment(UserMapper.toDomainModel(in.getCommenter()), in.getComment(), in.getDate());

        return comment;
    }

    static CommentDTO toDto(Comment comment) {

        CommentDTO commentDTO = new CommentDTO(UserMapper.toDto(comment.getCommenter()), comment.getComment(), comment.getDate());

        return commentDTO;
    }
}
