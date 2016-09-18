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
 * JAX-RS application subclass for the Parolee Web service. This class is
 * discovered by the JAX-RS run-time and is used to obtain a reference to the
 * UserResource object that will process Web service requests.
 * 
 * The base URI for the Parolee Web service is:
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

   public AnimeApplication()
   {
      singletons.add(new UserResource());
      singletons.add(new AnimeResource());
      singletons.add(new ReviewResource());
      singletons.add(new RatingResource());
      singletons.add(new ForumResource());
      singletons.add(new ClubResource());
      singletons.add(PersistenceManager.instance());
      setupDummyEntries();
   }

   public void setupDummyEntries(){
      PersistenceManager p = PersistenceManager.instance();
      EntityManager em = p.createEntityManager();
      em.getTransaction().begin();

      //Creating a user dummy obj
      User user = new User("ben1","ben2","ben3");
      em.persist(user);

      //Creating a anime dummy obj
      Anime anime = new Anime("umaruChan", 12, 2016, "Jun Xu");
      em.persist(anime);

      //Creating a review dummy obj
      Review review = new Review(anime, user, "hello");
      em.persist(review);

      //Creating a rating dummy obj
      Rating rating = new Rating(anime, user, "uo", 5);
      em.persist(rating);

      //Creating a forum dummy obj
      Comment comment = new Comment(user, "this is a comment", 1900);
      List<User> moderators = new ArrayList<User>();
      moderators.add(user);
      List<Comment> comments = new ArrayList<Comment>();
      comments.add(comment);
      Forum forum = new Forum( moderators, comments, anime );
      em.persist(forum);

      //creating a club dummy obj
      Set<User> members = new HashSet<User>();
      members.add(user);
      Set<Forum> forums = new HashSet<Forum>();
      forums.add(forum);
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
