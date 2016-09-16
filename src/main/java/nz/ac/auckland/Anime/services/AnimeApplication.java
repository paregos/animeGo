package nz.ac.auckland.Anime.services;



import nz.ac.auckland.Anime.domain.*;

import javax.persistence.EntityManager;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
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
      singletons.add(PersistenceManager.instance());
      setupDummyEntries();
   }

   public void setupDummyEntries(){
      PersistenceManager p = PersistenceManager.instance();
      EntityManager em = p.createEntityManager();
      em.getTransaction().begin();
      User user = new User("ben1","ben2","ben3");
      em.persist(user);
      Anime anime = new Anime("umaruChan", 12, 2016, "Jun Xu");
      em.persist(anime);
      Review review = new Review(anime, user, "hello");
      em.persist(review);
      Rating rating = new Rating(anime, user, "uo", 5);
      em.persist(rating);
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
