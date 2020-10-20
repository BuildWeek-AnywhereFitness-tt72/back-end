package com.lambdaschool.fitnessanywhere;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.lambdaschool.fitnessanywhere.models.*;
// import com.lambdaschool.fitnessanywhere.models.Useremail;
import com.lambdaschool.fitnessanywhere.services.RoleService;
import com.lambdaschool.fitnessanywhere.services.SessionService;
import com.lambdaschool.fitnessanywhere.services.UserService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * SeedData puts both known and random data into the database. It implements CommandLineRunner.
 * <p>
 * CoomandLineRunner: Spring Boot automatically runs the run method once and only once
 * after the application context has been loaded.
 */
@Transactional
@Component
public class SeedData
    implements CommandLineRunner
{
    /**
     * Connects the Role Service to this process
     */
    @Autowired
    RoleService roleService;

    /**
     * Connects the user service to this process
     */
    @Autowired
    UserService userService;

    @Autowired
    SessionService sessionService;


    /**
     * Generates test, seed data for our application
     * First a set of known data is seeded into our database.
     * Second a random set of data using Java Faker is seeded into our database.
     * Note this process does not remove data from the database. So if data exists in the database
     * prior to running this process, that data remains in the database.
     *
     * @param args The parameter is required by the parent interface but is not used in this process.
     */

    @Transactional
    @Override
    public void run(String[] args) throws
    Exception
    {
        userService.deleteAll();
        roleService.deleteAll();
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");
        Role r4 = new Role("client");
        Role r5 = new Role("instructor");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);
        r4 = roleService.save(r4);
        r5 = roleService.save(r5);

        // admin, data, user
        User u1 = new User("admin",
            "password");
//            "admin@lambdaschool.local");
        u1.getRoles()
            .add(new UserRoles(u1,
                r1));
        u1.getRoles()
            .add(new UserRoles(u1,
                r2));
        u1.getRoles()
            .add(new UserRoles(u1,
                r3));
//        u1.getUseremails()
//            .add(new Useremail(u1,
//                "admin@email.local"));
//        u1.getUseremails()
//            .add(new Useremail(u1,
//                "admin@mymail.local"));
        User u2 = new User("client", "client");
        u2.getRoles().add(new UserRoles(u2, r2));
        u2.getRoles().add(new UserRoles(u2, r4));
        User u3 = new User("instructor", "instructor");
        u3.getRoles().add(new UserRoles(u3, r2));
        u3.getRoles().add(new UserRoles(u3, r5));

//        Date date = new GregorianCalendar(2020,11,11).getTime();
//        Location l = new Location("Sesame Street", "New York", "New York", "000000");
//
//        Session s1 = new Session("Hagrid's Yoga", "hatha yoga", date, "30 minutes",
//                "intense", 15);
//        s1.setLocations(l);
//        sessionService.save(s1);
//        Attendees a1 = new Attendees(s1, u1, false);
//        Attendees a2 = new Attendees(s1, u3, true);
//        u1.getSessions().add(a1);
//        u3.getSessions().add(a2);

        userService.save(u1);
        userService.save(u2);
        userService.save(u3);

        if (true)
        {
            // using JavaFaker create a bunch of regular users
            // https://www.baeldung.com/java-faker
            // https://www.baeldung.com/regular-expressions-java
            Random rand = new Random();
            FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-US"),
                    new RandomService());
            Faker faker = new Faker(new Locale("en-US"));
            List<User> arrofusers = new ArrayList<>();
            List<Session> arrofsessions = new ArrayList<>();
            for (int i = 0; i < 25; i++)
            {
                new User();
                User fakeUser;

                fakeUser = new User(faker.name().username(),
                        "password");
//                    nameFaker.internet()
//                        .emailAddress());
                fakeUser.getRoles()
                        .add(new UserRoles(fakeUser,
                                r2));
//                fakeUser.getUseremails()
//                    .add(new Useremail(fakeUser,
//                        fakeValuesService.bothify("????##@gmail.com")));
                arrofusers.add(fakeUser);
            }
            for (int i = 0; i < 10; i++)
            {
                Date d = new GregorianCalendar(
                        rand.nextInt(200) + 1820,
                        rand.nextInt(12)+1,
                        rand.nextInt(28) + 1)
                        .getTime();
                Location loc = new Location(
                        faker.address().streetName() + " "
                                + faker.address().buildingNumber() + " ",
                        faker.address().city(),
                        faker.address().state(),
                        faker.address().zipCode());
                Session s = new Session(
                        faker.chuckNorris().fact(),
                        faker.job().field(),
                        d,
                        faker.bothify("## minutes"),
                        faker.dog().breed(),
                        rand.nextInt(50) + 1);
                s.setLocations(loc);
                arrofsessions.add(s);
                sessionService.save(s);
                User rndUser = arrofusers.remove(rand.nextInt(arrofusers.size()));
                Attendees a = new Attendees(s, rndUser, true);
                rndUser.getSessions().add(a);
                userService.save(rndUser);
            }
            for (User u : arrofusers)
            {
                Attendees a =
                        new Attendees(
                                arrofsessions.get(rand.nextInt(arrofsessions.size())),
                                u,
                                false);
                u.getSessions().add(a);
                userService.save(u);
            }
        }
    }
}