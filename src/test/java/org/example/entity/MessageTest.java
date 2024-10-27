package org.example.entity;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class MessageTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:17.0")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("password");

    private static SessionFactory sessionFactory;
    private EntityManager em;

    private Sender sender;
    private Receiver receiver;
    private Message message;

    @BeforeAll
    static void setUpAll() {
        Configuration configuration = new Configuration().configure();
        configuration.setProperty("hibernate.connection.url", postgresContainer.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", postgresContainer.getUsername());
        configuration.setProperty("hibernate.connection.password", postgresContainer.getPassword());

        sessionFactory = configuration.buildSessionFactory();
    }

    @BeforeEach
    void setUp() {
        Session session = sessionFactory.openSession();
        em = session.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        sender = new Sender();
        sender.setName("John Doe");

        receiver = new Receiver();
        receiver.setName("John Smith");

        em.persist(sender);
        em.persist(receiver);

        message = new Message();
        message.setWeight(2.5);
        message.setSender(sender);
        message.setReceiver(receiver);
    }

    @AfterEach
    void tearDown() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        em.close();
    }

    @AfterAll
    static void tearDownAll() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    @Transactional
    void testSaveMessage() {
        em.persist(message);
        em.getTransaction().commit();

        Message foundMessage = em.find(Message.class, message.getId());
        assertNotNull(foundMessage, "Сообщение должно быть сохранено и найдено");
        assertEquals(2.5, foundMessage.getWeight());
        assertEquals(sender, foundMessage.getSender());
        assertEquals(receiver, foundMessage.getReceiver());
    }

    @Test
    @Transactional
    void testFindMessageById() {
        em.persist(message);
        em.getTransaction().commit();

        Message foundMessage = em.find(Message.class, message.getId());
        assertNotNull(foundMessage, "Сообщение должно быть найдено");
        assertEquals(2.5, foundMessage.getWeight());
    }

    @Test
    @Transactional
    void testDeleteMessage() {
        em.persist(message);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.remove(message);
        em.getTransaction().commit();

        Message foundMessage = em.find(Message.class, message.getId());
        assertNull(foundMessage, "Сообщение должно быть удалено");
    }
}
