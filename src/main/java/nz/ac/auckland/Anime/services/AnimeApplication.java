package nz.ac.auckland.Anime.services;



import nz.ac.auckland.Anime.domain.*;

import javax.persistence.EntityManager;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * JAX-RS application subclass for the Anime Web service. This class is
 * discovered by the JAX-RS run-time and is used to obtain a reference to the
 * UserResource object that will process Web service requests.
 * 
 * The base URI for the Anime Web service is:
 * 
 * http://<host-name>:<port>/services.
 * 
 * @author Ian Warren
 *
 */
@ApplicationPath("/services")
public class AnimeApplication extends Application {
   private Set<Object> singletons = new HashSet<Object>();
   private Set<Class<?>> classes = new HashSet<Class<?>>();

   //creating instances of all of the resource classes
   public AnimeApplication()
   {
      singletons.add(new UserResource());
      singletons.add(new AnimeResource());
      singletons.add(new ReviewResource());
      singletons.add(new RatingResource());
      singletons.add(new ForumResource());
      singletons.add(new ClubResource());
      singletons.add(new EntityNotFoundMapper());
      singletons.add(PersistenceManager.instance());
      setupDummyEntries();
   }

   //creating dummy entries in the h2 local database
   public void setupDummyEntries(){
      PersistenceManager p = PersistenceManager.instance();
      EntityManager em = p.createEntityManager();
      em.getTransaction().begin();

      //Creating a user dummy obj
      User user = new User("ben1","ben2","ben3");
      em.persist(user);

      //Creating a user dummy obj
      User user2 = new User("asd","asd","aaa");
      em.persist(user2);

      //Creating a anime dummy obj
      Anime anime = new Anime("umaruChan", new Long(12), new Long(2016), "Jun Xu");
      em.persist(anime);

      //Creating a review dummy obj
      Review review = new Review(anime, user, "hello");
      em.persist(review);

      //Creating a rating dummy obj
      Rating rating = new Rating(anime, user, "uo", new Long(5));
      em.persist(rating);

      //Creating a forum dummy obj
      Comment comment = new Comment(user, "this is a comment", new Long(1900));
      List<User> moderators = new ArrayList<User>();
      moderators.add(user);
      moderators.add(user2);
      List<Comment> comments = new ArrayList<Comment>();
      comments.add(comment);
      Forum forum = new Forum( moderators, comments, anime );
      em.persist(forum);

      //Creating a forum dummy obj
      Comment comment1 = new Comment(user, "this is a comment", new Long(1900));
      List<User> moderators1 = new ArrayList<User>();
      moderators.add(user);
      moderators.add(user2);
      List<Comment> comments1 = new ArrayList<Comment>();
      comments1.add(comment1);
      Forum forum1 = new Forum( moderators, comments, anime );
      em.persist(forum1);

      //creating a club dummy obj
      Set<User> members = new HashSet<User>();
      members.add(user);
      members.add(user2);
      Set<Forum> forums = new HashSet<Forum>();
      forums.add(forum);
      forums.add(forum1);
      Club club = new Club(members, forums, "The New Game club");
      em.persist(club);

      em.getTransaction().commit();
      em.close();

   }

   @Override
   public Set<Object> getSingletons()
   {
      return singletons;
   }
   
   @Override
   public Set<Class<?>> getClasses()
   {
      return classes;
   }
}
