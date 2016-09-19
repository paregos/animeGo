package nz.ac.auckland.Anime.test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.fail;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import nz.ac.auckland.Anime.domain.Comment;
import nz.ac.auckland.Anime.domain.User;
import nz.ac.auckland.Anime.dto.*;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

 /* Simple JUnit test to test the behaviour of the Parolee Web service.
 *
 * The test basically uses the Web service to create new parolees, to query a
 * parolee, to query a range of parolees, to update a parolee and to delete a
 * parolee.
 *
 * The test is implemented using the JAX-RS client API.
 *
 * @author Ian Warren
 */


public class animeApplicationTest {

	private static String WEB_SERVICE_URI = "http://localhost:10000/services/";

	private Logger _logger = LoggerFactory.getLogger(animeApplicationTest.class);

    public static Client _client;

    /**
     * One-time setup method that creates a Web service client.
     */
    @BeforeClass
    public static void setUpClient() {
        _client = ClientBuilder.newClient();
    }

    /**
     * One-time finalisation method that destroys the Web service client.
     */
    @AfterClass
    public static void destroyClient() {
        _client.close();
    }

	@Test
	public void addUser() {

        UserDTO user = new UserDTO("ben1","ben2","ben3", new HashSet<Long>(1));

        Response response = _client
                .target(WEB_SERVICE_URI+"User").request()
                .post(Entity.xml(user));
        if (response.getStatus() != 201) {
            fail("Failed to create new User");
        }

        String location = response.getLocation().toString();
        response.close();

        // Query the Web service for the new User.
        UserDTO userFromService = _client.target(location).request()
                .accept("application/xml").get(UserDTO.class);


        assertEquals(user.getFirstname(), userFromService.getFirstname());
        assertEquals(user.getLastname(), userFromService.getLastname());
        assertEquals(user.getUsername(), userFromService.getUsername());
        assertEquals(user.getFollowIds(), user.getFollowIds());

	}

    @Test
    public void addAnime() {

        AnimeDTO anime = new AnimeDTO("umaruChan", new Long(12), new Long(2016), "Jun Xu", new HashSet<Long>(1));

        Response response = _client
                .target(WEB_SERVICE_URI+"Anime").request()
                .post(Entity.xml(anime));
        if (response.getStatus() != 201) {
            fail("Failed to create new Anime");
        }

        String location = response.getLocation().toString();
        response.close();

        // Query the Web service for the new User.
        AnimeDTO animeFromService = _client.target(location).request()
                .accept("application/xml").get(AnimeDTO.class);


        assertEquals(anime.getEpisodes(), animeFromService.getEpisodes());
        assertEquals(anime.getSequelIds(), animeFromService.getSequelIds());
        assertEquals(anime.getTitle(), animeFromService.getTitle());
        assertEquals(anime.getSynopsis(), animeFromService.getSynopsis());

    }

    @Test
    public void addReview() {

        ReviewDTO review = new ReviewDTO(new Long(1), new Long(1), "hello");

        Response response = _client
                .target(WEB_SERVICE_URI+"Review").request()
                .post(Entity.xml(review));
        if (response.getStatus() != 201) {
            fail("Failed to create new Review");
        }

        String location = response.getLocation().toString();
        response.close();

        // Query the Web service for the new User.
        ReviewDTO reviewFromService = _client.target(location).request()
                .accept("application/xml").get(ReviewDTO.class);


        assertEquals(review.getShowID(), reviewFromService.getShowID());
        assertEquals(review.getReview(), reviewFromService.getReview());
        assertEquals(review.getReviewerID(), reviewFromService.getReviewerID());

    }

    @Test
    public void addRating() {

        RatingDTO rating = new RatingDTO(new Long(1), new Long(1), "hello", new Long(100));

        Response response = _client
                .target(WEB_SERVICE_URI+"Rating").request()
                .post(Entity.xml(rating));
        if (response.getStatus() != 201) {
            fail("Failed to create new Rating");
        }

        String location = response.getLocation().toString();
        response.close();

        // Query the Web service for the new User.
        RatingDTO ratingFromService = _client.target(location).request()
                .accept("application/xml").get(RatingDTO.class);


        assertEquals(rating.getShowID(), ratingFromService.getShowID());
        assertEquals(rating.getReview(), ratingFromService.getReview());
        assertEquals(rating.getReviewerID(), ratingFromService.getReviewerID());

    }

    @Test
    public void addForum() {

        CommentDTO comment = new CommentDTO(new Long(1), "this is a comment", new Long(1900));
        List<CommentDTO> comments = new ArrayList<CommentDTO>();
        comments.add(comment);

        ForumDTO forum = new ForumDTO( new ArrayList<Long>(1), comments, new Long(1) );

        Response response = _client
                .target(WEB_SERVICE_URI+"Forum").request()
                .post(Entity.xml(forum));
        if (response.getStatus() != 201) {
            fail("Failed to create new Forum");
        }

        String location = response.getLocation().toString();
        response.close();

        // Query the Web service for the new User.
        ForumDTO forumFromService = _client.target(location).request()
                .accept("application/xml").get(ForumDTO.class);


        assertEquals(forum.getAnimeTopicID(), forumFromService.getAnimeTopicID());

        assertEquals(forum.getComments().get(0).getComment(), forumFromService.getComments().get(0).getComment());
        assertEquals(forum.getComments().get(0).getDate(), forumFromService.getComments().get(0).getDate());
        assertEquals(forum.getComments().get(0).getCommenterID(), forumFromService.getComments().get(0).getCommenterID());

        assertEquals(forum.getModerators(), forumFromService.getModerators());

    }

    @Test
    public void addClub() {

        CommentDTO comment = new CommentDTO(new Long(1), "this is a comment", new Long(1900));
        List<CommentDTO> comments = new ArrayList<CommentDTO>();
        comments.add(comment);

        ForumDTO forum = new ForumDTO( new Long(1),  new ArrayList<Long>(1), comments, new Long(1) );

        Set<ForumDTO> forums = new HashSet<ForumDTO>();
        forums.add(forum);

        ClubDTO club = new ClubDTO( new HashSet<Long>(1), forums, "The new game club" );

        Response response = _client
                .target(WEB_SERVICE_URI+"Club").request()
                .post(Entity.xml(club));
        if (response.getStatus() != 201) {
            fail("Failed to create new Club");
        }

        String location = response.getLocation().toString();
        response.close();

        // Query the Web service for the new User.
        ClubDTO clubFromService = _client.target(location).request()
                .accept("application/xml").get(ClubDTO.class);


        assertEquals(club.getName(), clubFromService.getName());
        assertEquals(club.getMembers(), clubFromService.getMembers());

    }

    @Test
    public void updateUser() {

        UserDTO user = new UserDTO("aaaa","zzzz","yyyy");

        Response response = _client
                .target(WEB_SERVICE_URI+"User/1").request()
                .put(Entity.xml(user));
        if (response.getStatus() != 204) {
            fail("Failed to update User");
        }

        response.close();

        // Query the Web service for the new User.
        UserDTO userFromService = _client.target(WEB_SERVICE_URI+"User/1").request()
                .accept("application/xml").get(UserDTO.class);


        assertEquals(user.getFirstname(), userFromService.getFirstname());
        assertEquals(user.getLastname(), userFromService.getLastname());
        assertEquals(user.getUsername(), userFromService.getUsername());

    }

    @Test
    public void updateAnime() {

        AnimeDTO anime = new AnimeDTO("aaaa", new Long(12), new Long(1920), "title", new HashSet<Long>(1));

        Response response = _client
                .target(WEB_SERVICE_URI+"Anime/1").request()
                .put(Entity.xml(anime));
        if (response.getStatus() != 204) {
            fail("Failed to create new Anime");
        }

        response.close();

        // Query the Web service for the new User.
        AnimeDTO animeFromService = _client.target(WEB_SERVICE_URI+"Anime/1").request()
                .accept("application/xml").get(AnimeDTO.class);


        assertEquals(anime.getEpisodes(), animeFromService.getEpisodes());
        assertEquals(anime.getSequelIds(), animeFromService.getSequelIds());
        assertEquals(anime.getTitle(), animeFromService.getTitle());
        assertEquals(anime.getSynopsis(), animeFromService.getSynopsis());

    }

    @Test
    public void updateReview() {

        ReviewDTO review = new ReviewDTO(new Long(1), new Long(1), "bob");

        Response response = _client
                .target(WEB_SERVICE_URI+"Review/1").request()
                .put(Entity.xml(review));
        if (response.getStatus() != 204) {
            fail("Failed to create new Review");
        }

        response.close();

        // Query the Web service for the new User.
        ReviewDTO reviewFromService = _client.target(WEB_SERVICE_URI+"Review/1").request()
                .accept("application/xml").get(ReviewDTO.class);


        assertEquals(review.getShowID(), reviewFromService.getShowID());
        assertEquals(review.getReview(), reviewFromService.getReview());
        assertEquals(review.getReviewerID(), reviewFromService.getReviewerID());

    }

    @Test
    public void updateRating() {

        RatingDTO rating = new RatingDTO(new Long(1), new Long(1), "new", new Long(1234));

        Response response = _client
                .target(WEB_SERVICE_URI+"Rating/2").request()
                .put(Entity.xml(rating));
        if (response.getStatus() != 204) {
            fail("Failed to create new Rating");
        }

        response.close();

        // Query the Web service for the new User.
        RatingDTO ratingFromService = _client.target(WEB_SERVICE_URI+"Rating/2").request()
                .accept("application/xml").get(RatingDTO.class);


        assertEquals(rating.getShowID(), ratingFromService.getShowID());
        assertEquals(rating.getReview(), ratingFromService.getReview());
        assertEquals(rating.getReviewerID(), ratingFromService.getReviewerID());

    }

    @Test
    public void updateForum() {

        CommentDTO comment = new CommentDTO(new Long(1), "tyooooont", new Long(1233));
        List<CommentDTO> comments = new ArrayList<CommentDTO>();
        comments.add(comment);

        ForumDTO forum = new ForumDTO( new ArrayList<Long>(1), comments, new Long(1) );

        Response response = _client
                .target(WEB_SERVICE_URI+"Forum/1").request()
                .put(Entity.xml(forum));
        if (response.getStatus() != 204) {
            fail("Failed to create new Forum");
        }

        response.close();

        // Query the Web service for the new User.
        ForumDTO forumFromService = _client.target(WEB_SERVICE_URI+"Forum/1").request()
                .accept("application/xml").get(ForumDTO.class);


        assertEquals(forum.getAnimeTopicID(), forumFromService.getAnimeTopicID());

        assertEquals(forum.getComments().get(0).getComment(), forumFromService.getComments().get(0).getComment());
        assertEquals(forum.getComments().get(0).getDate(), forumFromService.getComments().get(0).getDate());
        assertEquals(forum.getComments().get(0).getCommenterID(), forumFromService.getComments().get(0).getCommenterID());

        assertEquals(forum.getModerators(), forumFromService.getModerators());

    }

    @Test
    public void updateClub() {

        CommentDTO comment = new CommentDTO(new Long(1), "this is a sdfs", new Long(111));
        List<CommentDTO> comments = new ArrayList<CommentDTO>();
        comments.add(comment);

        ForumDTO forum = new ForumDTO( new Long(1),  null, comments, null );

        Set<ForumDTO> forums = new HashSet<ForumDTO>();
        forums.add(forum);

        ClubDTO club = new ClubDTO( new HashSet<Long>(1), forums, "asdasdasd" );

        Response response = _client
                .target(WEB_SERVICE_URI+"Club/1").request()
                .put(Entity.xml(club));
        if (response.getStatus() != 204) {
            fail("Failed to create new Club");
        }

        response.close();

        // Query the Web service for the new User.
        ClubDTO clubFromService = _client.target(WEB_SERVICE_URI+"Club/1").request()
                .accept("application/xml").get(ClubDTO.class);


        assertEquals(club.getName(), clubFromService.getName());
        assertEquals(club.getMembers(), clubFromService.getMembers());

    }

    @Test
    public void deleteUser() {

        UserDTO user = new UserDTO("ben1","ben2","ben3", new HashSet<Long>(1));

        Response response = _client
                .target(WEB_SERVICE_URI+"User").request()
                .post(Entity.xml(user));
        if (response.getStatus() != 201) {
            fail("Failed to create new User");
        }

        String location = response.getLocation().toString();
        response.close();

        // Query the Web service for the new User.
        _client.target(location).request().accept("application/xml").delete(UserDTO.class);

        // Query the Web service for the new User.
        response = _client.target(location).request()
                .accept("application/xml").get();

        response.close();
        assertEquals(response.getStatus(), 404);

    }

    @Test
    public void deleteAnime() {

        AnimeDTO anime = new AnimeDTO("umaruChan", new Long(12), new Long(2016), "Jun Xu", new HashSet<Long>(1));

        Response response = _client
                .target(WEB_SERVICE_URI+"Anime").request()
                .post(Entity.xml(anime));
        if (response.getStatus() != 201) {
            fail("Failed to create new Anime");
        }

        String location = response.getLocation().toString();
        response.close();

        // Query the Web service for the new User.
        _client.target(location).request().accept("application/xml").delete(AnimeDTO.class);

        // Query the Web service for the new User.
         response = _client.target(location).request()
                .accept("application/xml").get();
        response.close();

        assertEquals(response.getStatus(), 404);

    }


        @Test
    public void deleteReview() {

        ReviewDTO review = new ReviewDTO(new Long(1), new Long(1), "hello");

        Response response = _client
                .target(WEB_SERVICE_URI+"Review").request()
                .post(Entity.xml(review));
        if (response.getStatus() != 201) {
            fail("Failed to create new Review");
        }

        String location = response.getLocation().toString();
        response.close();

            // Query the Web service for the new User.
            _client.target(location).request().accept("application/xml").delete(ReviewDTO.class);

        // Query the Web service for the new User.
            response = _client.target(location).request()
                .accept("application/xml").get();

            response.close();

            assertEquals(response.getStatus(), 404);
    }

        @Test
    public void deleteRating() {

        RatingDTO rating = new RatingDTO(new Long(1), new Long(1), "hello", new Long(100));

        Response response = _client
                .target(WEB_SERVICE_URI+"Rating").request()
                .post(Entity.xml(rating));
        if (response.getStatus() != 201) {
            fail("Failed to create new Rating");
        }

        String location = response.getLocation().toString();
        response.close();

            // Query the Web service for the new User.
            _client.target(location).request().accept("application/xml").delete(ReviewDTO.class);

        // Query the Web service for the new User.
            response = _client.target(location).request()
                .accept("application/xml").get();


            response.close();

            assertEquals(response.getStatus(), 404);

    }

        @Test
    public void deleteForum() {

        CommentDTO comment = new CommentDTO(new Long(1), "this is a comment", new Long(1900));
        List<CommentDTO> comments = new ArrayList<CommentDTO>();
        comments.add(comment);

        ForumDTO forum = new ForumDTO( new ArrayList<Long>(1), comments, new Long(1) );

        Response response = _client
                .target(WEB_SERVICE_URI+"Forum").request()
                .post(Entity.xml(forum));
        if (response.getStatus() != 201) {
            fail("Failed to create new Forum");
        }

        String location = response.getLocation().toString();
        response.close();

            _client.target(location).request().accept("application/xml").delete(ForumDTO.class);

            response = _client.target(location).request()
                .accept("application/xml").get();

            response.close();

            assertEquals(response.getStatus(), 404);

    }

        @Test
    public void deleteClub() {

        CommentDTO comment = new CommentDTO(new Long(1), "this is a comment", new Long(1900));
        List<CommentDTO> comments = new ArrayList<CommentDTO>();
        comments.add(comment);

        ForumDTO forum = new ForumDTO( new Long(1),  new ArrayList<Long>(1), comments, new Long(1) );

        Set<ForumDTO> forums = new HashSet<ForumDTO>();
        forums.add(forum);

        ClubDTO club = new ClubDTO( new HashSet<Long>(1), forums, "The new game club" );

        Response response = _client
                .target(WEB_SERVICE_URI+"Club").request()
                .post(Entity.xml(club));
        if (response.getStatus() != 201) {
            fail("Failed to create new Club");
        }

        String location = response.getLocation().toString();
        response.close();

            _client.target(location).request().accept("application/xml").delete(ClubDTO.class);

        // Query the Web service for the new User.
            response = _client.target(location).request()
                .accept("application/xml").get();

            response.close();

            assertEquals(response.getStatus(), 404);
    }


}