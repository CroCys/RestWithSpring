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

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class ReceiverTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:17.0")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("password");

    private static SessionFactory sessionFactory;
    private EntityManager em;

    private Receiver receiver;
    private Sender sender;
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
        receiver.setName("Jane Doe");

        em.persist(sender);
        em.persist(receiver);

        message = new Message();
        message.setWeight(1.5);
        message.setSender(sender);
        message.setReceiver(receiver);
        receiver.setMessages(Collections.singletonList(message));

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
    void testSaveReceiver() {
        Receiver foundReceiver = em.find(Receiver.class, receiver.getId());
        assertNotNull(foundReceiver, "Получатель должен быть сохранен и найден");
        assertEquals("Jane Doe", foundReceiver.getName());
        assertFalse(foundReceiver.getMessages().isEmpty(), "Сообщения получателя должны быть сохранены");
    }

    @Test
    void testFindReceiverById() {
        Receiver foundReceiver = em.find(Receiver.class, receiver.getId());
        assertNotNull(foundReceiver, "Получатель должен быть найден");
        assertEquals("Jane Doe", foundReceiver.getName());
    }

    @Test
    void testDeleteReceiver() {
        em.getTransaction().begin();
        em.remove(receiver);
        em.getTransaction().commit();

        Receiver foundReceiver = em.find(Receiver.class, receiver.getId());
        assertNull(foundReceiver, "Получатель должен быть удален");
    }
}
