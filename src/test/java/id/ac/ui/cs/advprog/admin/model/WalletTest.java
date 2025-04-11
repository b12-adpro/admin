package id.ac.ui.cs.advprog.admin.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class WalletTest {

    Wallet wallet;

    @BeforeEach
    void setUp() {
        wallet = new Wallet();
        wallet.setId(1L);
        wallet.setBalance(100000.0);
    }

    @Test
    void testOnCreateSetsCreatedAtAndUpdatedAt() {
        wallet.onCreate();
        LocalDateTime now = LocalDateTime.now();

        assertNotNull(wallet.getCreatedAt(), "createdAt should not be null");
        assertNotNull(wallet.getUpdatedAt(), "updatedAt should not be null");

        assertTrue(wallet.getCreatedAt().isBefore(now.plusSeconds(1)) &&
                wallet.getCreatedAt().isAfter(now.minusSeconds(1)));

        assertTrue(wallet.getUpdatedAt().isBefore(now.plusSeconds(1)) &&
                wallet.getUpdatedAt().isAfter(now.minusSeconds(1)));
    }

    @Test
    void testOnUpdateSetsUpdatedAt() throws InterruptedException {
        wallet.onCreate();
        LocalDateTime originalCreatedAt = wallet.getCreatedAt();
        LocalDateTime firstUpdatedAt = wallet.getUpdatedAt();

        Thread.sleep(10); // supaya ada perbedaan waktu update

        wallet.onUpdate();
        LocalDateTime secondUpdatedAt = wallet.getUpdatedAt();

        assertEquals(originalCreatedAt, wallet.getCreatedAt(), "createdAt should remain unchanged");
        assertTrue(secondUpdatedAt.isAfter(firstUpdatedAt), "updatedAt should be updated to a later time");
    }
}
