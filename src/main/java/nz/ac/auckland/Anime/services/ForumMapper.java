package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.Comment;
import nz.ac.auckland.Anime.domain.Forum;
import nz.ac.auckland.Anime.domain.User;
import nz.ac.auckland.Anime.dto.CommentDTO;
import nz.ac.auckland.Anime.dto.ForumDTO;
import nz.ac.auckland.Anime.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 9/17/2016.
 */
public class ForumMapper {

    static Forum toDomainModel(ForumDTO in) {

        List<User> moderators = new ArrayList<User>();
        List<Comment> comments = new ArrayList<Comment>();

        //get moderators
        for(UserDTO i : in.getModerators()){
            System.out.println(i.getId());
            System.out.println(i.getFirstname());
            moderators.add(UserMapper.toDomainModel(i));
        }

        //get comments
        for(CommentDTO i : in.getComments()){
            System.out.println(i.getComment());
            comments.add(CommentMapper.toDomainModel(i));
        }

        Forum forum = new Forum(in.getId(), moderators, comments, AnimeMapper.toDomainModel(in.getAnimeTopic()));

        return forum;
    }

    static ForumDTO toDto(Forum forum) {

        List<UserDTO> moderators = new ArrayList<UserDTO>();
        List<CommentDTO> comments = new ArrayList<CommentDTO>();

        //get moderators
        for(User i : forum.getModerators()){
            moderators.add(UserMapper.toDto(i));
        }

        //get comments
        for(Comment i : forum.getComments()){
            comments.add(CommentMapper.toDto(i));
        }

        ForumDTO in = new ForumDTO(forum.getId(), moderators, comments, AnimeMapper.toDto(forum.getAnimeTopic()));

        return in;

    }


}
