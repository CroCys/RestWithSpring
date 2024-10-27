package org.example.entity;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class SenderTest {

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
        receiver.setName("Jane Smith");

        em.persist(receiver);
        em.persist(sender);

        message = new Message();
        message.setWeight(1.5);
        message.setSender(sender);
        message.setReceiver(receiver);
        sender.setMessages(Collections.singletonList(message));

        em.persist(message);
        em.getTransaction().commit();
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
    void testSaveSender() {
        Sender foundSender = em.find(Sender.class, sender.getId());
        assertNotNull(foundSender, "Отправитель должен быть сохранен и найден");
        assertEquals("John Doe", foundSender.getName());
        assertFalse(foundSender.getMessages().isEmpty(), "Сообщения отправителя должны быть сохранены");
    }

    @Test
    void testFindSenderById() {
        Sender foundSender = em.find(Sender.class, sender.getId());
        assertNotNull(foundSender, "Отправитель должен быть найден");
        assertEquals("John Doe", foundSender.getName());
    }

    @Test
    void testDeleteSender() {
        em.getTransaction().begin();
        em.remove(sender);
        em.getTransaction().commit();

        Sender foundSender = em.find(Sender.class, sender.getId());
        assertNull(foundSender, "Отправитель должен быть удален");
    }
}
