package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.Club;
import nz.ac.auckland.Anime.domain.Forum;
import nz.ac.auckland.Anime.domain.User;
import nz.ac.auckland.Anime.dto.ClubDTO;
import nz.ac.auckland.Anime.dto.ForumDTO;
import nz.ac.auckland.Anime.dto.UserDTO;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ben on 9/17/2016.
 */
public class ClubMapper {


    static Club toDomainModel(ClubDTO in) {

        Set<User> members = new HashSet<User>();
        Set<Forum> forums = new HashSet<Forum>();

        //get moderators
        for(UserDTO i : in.getMembers()){
            members.add(UserMapper.toDomainModel(i));
        }

        //get comments
        for(ForumDTO i : in.getForums()){
            forums.add(ForumMapper.toDomainModel(i));
        }

        Club forum = new Club(in.getId(), members, forums);

        return forum;
    }

    static ClubDTO toDto(Club forum) {

        Set<UserDTO> members = new HashSet<UserDTO>();
        Set<ForumDTO> forums = new HashSet<ForumDTO>();

        //get memebers
        for(User i : forum.getMembers()){
            members.add(UserMapper.toDto(i));
        }

        //get forums
        for(Forum i : forum.getForums()){
            forums.add(ForumMapper.toDto(i));
        }

        ClubDTO in = new ClubDTO(forum.getId(), members, forums);

        return in;

    }

}
